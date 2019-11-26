package com.example.myapplication.Schedule;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Database.Day;
import com.example.myapplication.Database.Place;
import com.example.myapplication.Database.Trip;
import com.example.myapplication.Database.User;
import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//TODO: 스크롤 될 때 목록 자동 접고 펴기
//TODO: Place 실제 위, 경도 값 받아서 타임라인 표시
public class ScheduleForm extends AppCompatActivity implements ExpandableListAdapter.OnAdapterInteractionListener, ExpandableListAdapter.OnStartDragListener, View.OnClickListener {
    ViewGroup mapContainer;
    private RecyclerView recyclerView;
    String title_str, start_str, finish_str;
    Date start_date, finish_date;
    TextView title, date;
    ImageButton btn_diary,btn_save;
    User user = User.getInstance();
    Trip thisTrip;
    Boolean save_flag = false;
    int period;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scheduleForm_diaryBtn:
                startActivity(new Intent(ScheduleForm.this, MyDiary.class));
                break;
            case R.id.btn_save:
                if(!save_flag){ //편집 -> 저장
                    Log.d("save?: ", "save! ");
                    //스케줄 자체 디비에도 더해준다
                    DatabaseReference schedule = FirebaseDatabase.getInstance().getReference().child("schedule");
                    schedule.child("title").setValue(title.getText().toString());
                    schedule.child("start_date").setValue(start_str);
                    schedule.child("start_date").setValue(finish_str);

                    DatabaseReference place_ref = schedule.push();
                    DatabaseReference diary_ref = schedule.child("diaries");

                    // 다이어리는 어쩌지..
                    for(int i=1; i<=period; i++){
                        DatabaseReference temp_ref = place_ref.child("Day"+i);
                        for(int j=0; j<thisTrip.getDays().size(); j++){
                            temp_ref.child(String.valueOf(j)).setValue(thisTrip.getDays().get(j));
                        }
                    }

                    //유저한테도 더해준다
                    user.schedules_id.add(schedule.getKey());
                    Log.d("sche key: ", schedule.getKey());

                }else{ //저장 -> 편집

                }
                break;
        }
    }

    public static ViewGroup mapViewContainer;
    public static MapView mapView;
//    private MapPoint mapPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        btn_diary = findViewById(R.id.scheduleForm_diaryBtn);
        btn_save = findViewById(R.id.btn_save);
        btn_diary.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        // 넘어오는 인텐트 받기 -- 새로 만드는거 아니면 인텐트로 받아오는거 없는데 머어쩌지......
        try{
            Intent intent = getIntent();
            Log.d("title: ", intent.getExtras().getString("title"));
            title_str = intent.getExtras().getString("title");
            start_str = intent.getExtras().getString("start_date");
            finish_str = intent.getExtras().getString("finish_date");
            start_date =new SimpleDateFormat("yyyy/MM/dd").parse(start_str);
            finish_date  =new SimpleDateFormat("yyyy/MM/dd").parse(finish_str);

            title.setText(title_str);
            date.setText(start_str+" - "+finish_str);

        }catch (Exception e){

        }

        final DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        final NavigationView navigationView = findViewById(R.id.navigationview);

        //drawer toggle 세트로 drawerlayout, toolbar 등 해서 생성 - 드로어 여닫기 완성
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.closed);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //
        ImageView drawer_opener = findViewById(R.id.drawer_opener);
        drawer_opener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        // 유저 스케줄 가져오기 -> 드로어용
        ArrayList<MyData> schedules = new ArrayList<>();

        //drawer recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        RecyclerView drawer_recyclerview = findViewById(R.id.drawer_recyclerview);
        drawer_recyclerview.setLayoutManager(layoutManager);
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this, schedules, 2);
        drawer_recyclerview.setAdapter(scheduleAdapter);

        mapViewContainer = findViewById(R.id.scheduleForm_mapContainer);
        mapView = new MapView(this);
        mapViewContainer.addView(mapView);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(34.746487,127.748342),false);
        mapView.setZoomLevel(3, false);
//        mapView.setMapViewEventListener(this);
        //TODO: 1. 특정 데이인 것 구분 후 아래 기능(지도에 마킹) 들어가야 함,
        //       2. 다중 마커 좀 더 효율적으로
        Place place1 = new Place("여수세계박람회 크루즈공원", 34.753264, 127.754638);
        Place place2 = new Place("한화아쿠아플라넷 여수", 34.746487, 127.748342);
        Place place3 = new Place("오동도 유람선터미널", 34.740861, 127.755591);

        MapPOIItem marker1 = new MapPOIItem();
        MapPOIItem marker2 = new MapPOIItem();
        MapPOIItem marker3 = new MapPOIItem();
        marker1.setItemName(place1.getName());
        marker2.setItemName(place2.getName());
        marker3.setItemName(place3.getName());
        marker1.setMapPoint(MapPoint.mapPointWithGeoCoord(place1.getLatitude(), place1.getLongitude()));
        marker2.setMapPoint(MapPoint.mapPointWithGeoCoord(place2.getLatitude(), place2.getLongitude()));
        marker3.setMapPoint(MapPoint.mapPointWithGeoCoord(place3.getLatitude(), place3.getLongitude()));
        marker1.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker2.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker3.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker1.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        marker3.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker1);
        mapView.addPOIItem(marker2);
        mapView.addPOIItem(marker3);

        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 51, 0));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(place1.getLatitude(), place1.getLongitude()));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(place2.getLatitude(), place2.getLongitude()));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(place3.getLatitude(), place3.getLongitude()));

        mapView.addPolyline(polyline);

        // 지도뷰의 중심좌표와 줌레벨을 polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; //px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));


        recyclerView = findViewById(R.id.scheduleForm_planRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        List<ExpandableListAdapter.Item> data = new ArrayList<>();

        try {
            period = (int)(start_date.getTime() - finish_date.getTime())/(24*60*60*1000);
            thisTrip = new Trip(title_str, start_date, period);

            for(int i=0; i<period; i++){
                List<Place> fourthDayPlaces = new ArrayList<>();
            fourthDayPlaces.add(new Place("작은 동물원"));
            fourthDayPlaces.add(new Place("작은 박물관"));
            fourthDayPlaces.add(new Place("멋진 휴양림"));
            fourthDayPlaces.add(new Place("거대한 휴양림"));
                thisTrip.addDay(i, new Day(i, fourthDayPlaces));
            }


            SimpleDateFormat transFormat = new SimpleDateFormat("MM/dd E");
            ExpandableListAdapter.OnStartDragListener startDragListener = new ExpandableListAdapter.OnStartDragListener() {
                @Override
                public void onStartDrag(ExpandableListAdapter.ListChildViewHolder listChildViewHolder) {
                }
            };
            ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(data, this, startDragListener);
            PlaceItemTouchHelperCallback placeItemTouchHelperCallback = new PlaceItemTouchHelperCallback(expandableListAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(placeItemTouchHelperCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);

            Date dummy_date = start_date;
            for(int i=0; i<thisTrip.getPeriod(); i++){
                // 먼저 day 수만큼 HEADER 추가
                data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day "+String.valueOf(i+1), transFormat.format(dummy_date)));
                dummy_date = new Date(dummy_date.getTime() +(1000*60*60*24*-1));

                // 각 날짜별로 포함된 location 을 CHILD로 추가
                List<Place> spots = thisTrip.getDay(i).getSpots();
                int order = 0;
                if(spots.isEmpty() || spots == null){
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.EMPTY_CHILD));
                }
                for(int j=0; j<spots.size(); j++){
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, spots.get(j), ++order));
                    if(j == spots.size()-1 ){
                        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.EMPTY_CHILD));
                    }
                }
            }

            recyclerView.setAdapter(expandableListAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override  //ExpandableListAdapter.OnAdapterInteractionListener
    public void addBtnClickedInAdapter(boolean isClicked){
        if(isClicked){
//            Toast.makeText(this, "check adapter data sended to activity", Toast.LENGTH_SHORT).show();
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fm.beginTransaction();
//            fragmentTransaction.add(R.id.scheduleForm_fragment_container, new ScheduleFormMapFragment());
//            fragmentTransaction.commit();
        }
    }

    @Override
    public void onStartDrag(ExpandableListAdapter.ListChildViewHolder listChildViewHolder) {

    }

}

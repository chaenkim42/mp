package com.example.myapplication.Schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.Day;
import com.example.myapplication.Database.NewPlace;
import com.example.myapplication.Database.Place;
import com.example.myapplication.Database.Trip;
import com.example.myapplication.Database.User;
import com.example.myapplication.Database.UserDb;
import com.example.myapplication.R;
import com.example.myapplication.Search.SearchMap;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
import java.util.Iterator;
import java.util.List;


//TODO: 스크롤 될 때 목록 자동 접고 펴기
//TODO: Place 실제 위, 경도 값 받아서 타임라인 표시
public class ScheduleForm extends AppCompatActivity implements ExpandableListAdapter.OnAdapterInteractionListener,ExpandableListAdapter.OnStartDragListener, View.OnClickListener {
    private RecyclerView recyclerView;
    String title_str, start_str, finish_str;
    Date start_date, finish_date;
    TextView title, date, header_name, addPlaceBtn;
    ConstraintLayout addPlaceText;
    ImageButton btn_diary,btn_save;
    User user = User.getInstance();
    Trip thisTrip;
    Boolean save_flag = false;
    int period;

    public static ViewGroup mapViewContainer;
    public static MapView mapView;
//    private MapPoint mapPoint;

    NewPlace newPlace;

    private int overallXScroll = 0;


    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        header_name = findViewById(R.id.header_name);
        btn_diary = findViewById(R.id.scheduleForm_diaryBtn);
        btn_save = findViewById(R.id.btn_save);
        mapViewContainer = findViewById(R.id.scheduleForm_mapContainer);
        btn_diary.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        // 맵 생성
        mapView = new MapView(this);
        mapViewContainer.addView(mapView);


        // 넘어오는 인텐트 받기 -- 새로 만드는거 아니면 인텐트로 받아오는거 없는데 머어쩌지......
        checkDaySelected();
//        intentFunc();
        //tmp user
//        user.setData(24, "chaen42@ajou.ac.kr", "김채은", "12345","F");
//        user.preferences.add("#계곡");
//        user.preferences.add("#동물원");
//        user.preferences.add("#박물관");
//        user.locations.add("경주");
//        user.locations.add("서울");
//        user.locations.add("부산");
//        user.locations.add("평창");


        // 드로어 달기
        setDrawer();





    }



    @Override
    public void onStartDrag(ExpandableListAdapter.ListChildViewHolder listChildViewHolder) {

    }

    public void setScheduleRecyclerView(){
        // 장소 리사이클러뷰 ------------------------------------------------------------------

        recyclerView = findViewById(R.id.scheduleForm_planRecyclerView);
//        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));

        //final LinearLayoutManager manager = (LinearLayoutManager)recyclerView.getLayoutManager();

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
////                overallXScroll = overallXScroll + dy; // 스크롤위치 좌표값
//
//                Log.d("scroll test item", String.valueOf(manager.findFirstVisibleItemPosition()));
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });

        List<ExpandableListAdapter.Item> data = new ArrayList<>();

        try {
            // 플레이스 장소 정보 가져오기

            SimpleDateFormat transFormat = new SimpleDateFormat("MM/dd  E");
            ExpandableListAdapter.OnStartDragListener startDragListener = new ExpandableListAdapter.OnStartDragListener() {
                @Override
                public void onStartDrag(ExpandableListAdapter.ListChildViewHolder listChildViewHolder) {
                }
            };
            ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(data, this, startDragListener);
            expandableListAdapter.setOnItemClickListener(new ExpandableListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Log.d("scroll position", position+"");
                    //position 계산
                    int sumOfSpots = 0;
                    for(int dayCalculating=1; dayCalculating<=thisTrip.getPeriod(); dayCalculating++){
                        if(dayCalculating==1){
                            sumOfSpots += (thisTrip.days.get(dayCalculating-1).getSpots().size() + 1);
                        }else if(dayCalculating>1){
                            sumOfSpots += (thisTrip.days.get(dayCalculating-1).getSpots().size() + 2);
                        }
                        if(position == sumOfSpots){
                            //position은 daycalculating 번째 데이(1부터 시작)
                            newPlace = NewPlace.getInstance();
                            newPlace.setSelectedDay(dayCalculating);
                            Intent intent = new Intent(ScheduleForm.this, SearchMap.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            mapViewContainer.removeAllViews();
                            startActivity(intent);
//                            Toast.makeText(ScheduleForm.this, ""+dayCalculating, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            });


            PlaceItemTouchHelperCallback placeItemTouchHelperCallback = new PlaceItemTouchHelperCallback(expandableListAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(placeItemTouchHelperCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);


            Date calculating_date = start_date;
            for(int i=0; i<thisTrip.getPeriod(); i++){
                // 먼저 day 수만큼 HEADER 추가
                data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day "+String.valueOf(i+1), transFormat.format(calculating_date)));
                calculating_date = new Date(calculating_date.getTime() +(1000*60*60*24*1)); // 하루 더해줌

                // 각 날짜별로 포함된 location 을 CHILD로 추가
                List<Place> spots = thisTrip.getDay(i).getSpots();
                int order = 0;
                if(spots.isEmpty() || spots == null){
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.EMPTY_CHILD, i+1));
                }
                for(int j=0; j<spots.size(); j++){
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, spots.get(j), ++order));
                    if(j == spots.size()-1 ){
                        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.EMPTY_CHILD,i+1));
                    }
                }
            }

            recyclerView.setAdapter(expandableListAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkDaySelected(){
        newPlace = NewPlace.getInstance();
        if(newPlace.getSelectedDay() != -1){
            DatabaseReference schedulesRef = myRef.child("schedules");
            schedulesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                    while(child.hasNext()){
                        DataSnapshot tmpSchedule = child.next();
                        if(tmpSchedule.child("title").getValue(String.class).equals(newPlace.getSelectedTripName())){
                            Iterator<DataSnapshot> days = tmpSchedule.child("days").getChildren().iterator();
                            while(days.hasNext()){
                                DataSnapshot tmpDay = days.next();
                                if(tmpDay.child("order").getValue(Integer.class) == newPlace.getSelectedDay()){
                                    DatabaseReference spots = tmpDay.child("spots").getRef();
                                    spots.push().setValue(newPlace.getSelectedPlace());
                                    break;
                                }
                            }
                            break;
                        }
                        intentFunc();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            intentFunc();
        }
    }

    public void intentFunc(){
        newPlace = NewPlace.getInstance();
        newPlace.setSelectedTripName("55");//임시
        if(newPlace.getSelectedTripName() != "tmp") {
            title_str = newPlace.getSelectedTripName();
            DatabaseReference schedulesRef = myRef.child("schedules");
            schedulesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                    while (child.hasNext()) {
                        DataSnapshot temp = child.next(); //각각의 schedule 객체
                        if (temp.child("title").getValue().toString().equals(title_str)) {
                            start_str = temp.child("start_date").getValue(String.class);
                            finish_str = temp.child("end_date").getValue(String.class);
                            period = temp.child("period").getValue(Integer.class);
                            title.setText(title_str);
                            date.setText(start_str + " - " + finish_str);
                            try {
                                start_date = new SimpleDateFormat("yyyy/MM/dd").parse(start_str);
                                finish_date = new SimpleDateFormat("yyyy/MM/dd").parse(finish_str);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            thisTrip = new Trip(title_str, start_date, period);
                            Iterator<DataSnapshot> days = temp.child("days").getChildren().iterator();
                            int i = 0;
                            while (days.hasNext()) {
                                DataSnapshot tmpday = days.next();
                                Iterator<DataSnapshot> spots = tmpday.child("spots").getChildren().iterator();
                                while (spots.hasNext()) {
                                    DataSnapshot tmpspot = spots.next();
                                    Place tmpplace = new Place(tmpspot.child("name").getValue(String.class),
                                            tmpspot.child("latitude").getValue(Double.class),
                                            tmpspot.child("longitude").getValue(Double.class));
                                    thisTrip.days.get(i).addSpot(tmpplace);
                                }
                                i++;
                            }
                            setMapPOI();
                            setScheduleRecyclerView();
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else{
            setMapPOI();
            setScheduleRecyclerView();
        }
    }
    public void setMapPOI(){
        //TODO: 1. 특정 데이인 것 구분 후 아래 기능(지도에 마킹) 들어가야 함,
        //       2. 다중 마커 좀 더 효율적으로
//        thisTrip.getDay(0).getSpots();//List<Place>
        if(thisTrip.getDays() != null) {
            Iterator<Place> spots = thisTrip.getDay(0).getSpots().iterator();
            Place tmpPlace;
            while (spots.hasNext()) {
                tmpPlace = spots.next();
                MapPOIItem marker = new MapPOIItem();
                marker.setItemName(tmpPlace.getName());
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(tmpPlace.getLatitude(), tmpPlace.getLongitude()));
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                mapView.addPOIItem(marker);
            }
            if(thisTrip.getDay(0).getSpots().size() > 0) {
                MapPolyline polyline = new MapPolyline();
                polyline.setTag(1000);
                polyline.setLineColor(Color.argb(128, 255, 51, 0));
//                while (spots.hasNext()) {
//                    tmpPlace = spots.next();
//                }

                for(int j=0; j<thisTrip.getDay(0).getSpots().size(); j++){
                    polyline.addPoint(MapPoint.mapPointWithGeoCoord(thisTrip.getDay(0).getSpots().get(j).getLatitude(), thisTrip.getDay(0).getSpots().get(j).getLongitude()));
                    if(j == thisTrip.getDay(0).getSpots().size()-1){
                        mapView.addPolyline(polyline);
                    }
                }

                // 지도뷰의 중심좌표와 줌레벨을 polyline이 모두 나오도록 조정.
                MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
                int padding = 100; //px
                mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
            }
//        Place place1 = new Place("여수세계박람회 크루즈공원", 34.753264, 127.754638);
//        Place place2 = new Place("한화아쿠아플라넷 여수", 34.746487, 127.748342);
//        Place place3 = new Place("오동도 유람선터미널", 34.740861, 127.755591);
//
//        MapPOIItem marker1 = new MapPOIItem();
//        MapPOIItem marker2 = new MapPOIItem();
//        MapPOIItem marker3 = new MapPOIItem();
//        marker1.setItemName(place1.getName());
//        marker2.setItemName(place2.getName());
//        marker3.setItemName(place3.getName());
//        marker1.setMapPoint(MapPoint.mapPointWithGeoCoord(place1.getLatitude(), place1.getLongitude()));
//        marker2.setMapPoint(MapPoint.mapPointWithGeoCoord(place2.getLatitude(), place2.getLongitude()));
//        marker3.setMapPoint(MapPoint.mapPointWithGeoCoord(place3.getLatitude(), place3.getLongitude()));
//        marker1.setMarkerType(MapPOIItem.MarkerType.BluePin);
//        marker2.setMarkerType(MapPOIItem.MarkerType.BluePin);
//        marker3.setMarkerType(MapPOIItem.MarkerType.BluePin);
//        marker1.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
//        marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
//        marker3.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
//
//        mapView.addPOIItem(marker1);
//        mapView.addPOIItem(marker2);
//        mapView.addPOIItem(marker3);
//
//        MapPolyline polyline = new MapPolyline();
//        polyline.setTag(1000);
//        polyline.setLineColor(Color.argb(128, 255, 51, 0));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(place1.getLatitude(), place1.getLongitude()));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(place2.getLatitude(), place2.getLongitude()));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(place3.getLatitude(), place3.getLongitude()));
//
//        mapView.addPolyline(polyline);
//
//        // 지도뷰의 중심좌표와 줌레벨을 polyline이 모두 나오도록 조정.
//        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
//        int padding = 100; //px
//        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
        } else{
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.555212, 126.970573), false); //서울역
            mapView.setZoomLevel(3, false);
        }
    }

    public void setDrawer(){
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
    }


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
                    user.getSchedules().add(schedule.getKey());
                    Log.d("sche key: ", schedule.getKey());

                }else{ //저장 -> 편집

                }
                break;
        }
    }


    @Override
    public void addBtnClickedInAdapter(boolean isClicked) {

    }
}
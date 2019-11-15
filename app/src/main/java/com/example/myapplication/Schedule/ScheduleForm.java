package com.example.myapplication.Schedule;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Database.Day;
import com.example.myapplication.Database.Place;
import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;

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
public class ScheduleForm extends AppCompatActivity implements ExpandableListAdapter.OnAdapterInteractionListener, ExpandableListAdapter.OnStartDragListener {
    ViewGroup mapContainer;
    private RecyclerView recyclerView;

    //TODO: 별도 class 로 분리한다. Trip, Day 클래스를.
    public static class Trip{
        private String title;
        private int period;
        private Date startDate;
        private List<Day> days = new ArrayList<>();

        public Trip(String title, Date startDate, int period){
            this.title = title;
            this.period = period;
            this.startDate = startDate;
            for(int i=0; i<period; i++){
                days.add(new Day(i));
            }
        }

        public void addDay(int onWhichIndex, Day day){
            this.days.add(onWhichIndex, day);
        }

        public void editDay(int onWhichIndex, Day day){
            this.days.remove(onWhichIndex);
            this.days.add(onWhichIndex, day);
        }

        public String getTitle() {return title;}
        public Date getStartDate() {return startDate;}
        public int getPeriod() {return period;}
        public List<Day> getDays() {return days;}

        public Day getDay(int index){
            return this.days.get(index);
        }

    }


    public static ViewGroup mapViewContainer;
    public static MapView mapView;
//    private MapPoint mapPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);

//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.add(R.id.scheduleForm_mapContainer, new MapFragment());
//        fragmentTransaction.commit();

        //data 있는 schedules 초기화
        ArrayList<MyData> schedules = new ArrayList<>();
        schedules.add(new MyData("여수 식도락 여행", R.drawable.location0 ));
        schedules.add(new MyData("데이식스 부산콘 겸 우정여행",R.drawable.location1));
        schedules.add(new MyData("TianJia와 함께 하는 서울 나들이",R.drawable.location3 ));
        schedules.add(new MyData("경주 문화유산 답사기",R.drawable.location5));
        schedules.add(new MyData("여름 평창 여행",R.drawable.location5));

        schedules.add(new MyData("여수 식도락 여행", R.drawable.location0 ));
        schedules.add(new MyData("데이식스 부산콘 겸 우정여행",R.drawable.location1));
        schedules.add(new MyData("TianJia와 함께 하는 서울 나들이",R.drawable.location3 ));
        schedules.add(new MyData("경주 문화유산 답사기",R.drawable.location5));
        schedules.add(new MyData("여름 평창 여행",R.drawable.location5));

        schedules.add(new MyData("여수 식도락 여행", R.drawable.location0 ));
        schedules.add(new MyData("데이식스 부산콘 겸 우정여행",R.drawable.location1));
        schedules.add(new MyData("TianJia와 함께 하는 서울 나들이",R.drawable.location3 ));
        schedules.add(new MyData("경주 문화유산 답사기",R.drawable.location5));
        schedules.add(new MyData("여름 평창 여행",R.drawable.location5));

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
            // 예시 trip 초기화
            //TODO : 날짜 지금은 일일히 넣었는데, 시작일로부터 하루씩 더하기 추가
            Date date1=new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/01");
            Date date2=new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/02");
            Date date3=new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/03");
            Date date4=new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/04");
            Trip thisTrip = new Trip("여수 식도락 여행", date1, 4);
            List<Place> firstDayPlaces = new ArrayList<>();
            firstDayPlaces.add(place1);
            firstDayPlaces.add(place2);
            firstDayPlaces.add(place3);
            List<Place> secondDayPlaces = new ArrayList<>();
            List<Place> thirdDayPlaces = new ArrayList<>();
            thirdDayPlaces.add(new Place("유명한 산"));
            thirdDayPlaces.add(new Place("큰 산"));
            thirdDayPlaces.add(new Place("멋진 공원"));
            thirdDayPlaces.add(new Place("작은 공원"));
            thirdDayPlaces.add(new Place("다양한 동물원"));
            thirdDayPlaces.add(new Place("멋진 동물원"));
            thirdDayPlaces.add(new Place("멋진 박물관"));
            thirdDayPlaces.add(new Place("큰 박물관"));
            List<Place> fourthDayPlaces = new ArrayList<>();
            fourthDayPlaces.add(new Place("작은 동물원"));
            fourthDayPlaces.add(new Place("작은 박물관"));
            fourthDayPlaces.add(new Place("멋진 휴양림"));
            fourthDayPlaces.add(new Place("거대한 휴양림"));

            Day day = new Day(0, firstDayPlaces);
            thisTrip.addDay(0, day);
            day = new Day(1, secondDayPlaces);
            thisTrip.addDay(1, day);
            day = new Day(2, thirdDayPlaces);
            thisTrip.addDay(2, day);
            day = new Day(3, fourthDayPlaces);
            thisTrip.addDay(3, day);


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

            for(int i=0; i<thisTrip.getPeriod(); i++){
                // 먼저 day 수만큼 HEADER 추가
                if(i==0){
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day "+String.valueOf(i+1), transFormat.format(date1)));
                }else if(i==1) {
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day " + String.valueOf(i+1), transFormat.format(date2)));
                }else if(i==2) {
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day " + String.valueOf(i+1), transFormat.format(date3)));
                }else if(i==3) {
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day " + String.valueOf(i+1), transFormat.format(date4)));
                }
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

        } catch (ParseException e) {
            e.printStackTrace();
        }



//        List<ExpandableListAdapter.Item> data = new ArrayList<>();
//
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day "+String.valueOf(1)));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Apple"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Orange"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Banana"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Cars"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Audi"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Aston Martin"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "BMW"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Cadillac"));
//
//        ExpandableListAdapter.Item places = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Places");
//        places.invisibleChildren = new ArrayList<>();
//        places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Kerala"));
//        places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Tamil Nadu"));
//        places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Karnataka"));
//        places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Maharashtra"));
//
//        data.add(places);
//
//        recyclerView.setAdapter(new ExpandableListAdapter(data));

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

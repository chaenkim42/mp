package com.example.myapplication.Schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;

import com.example.myapplication.Location;
import com.example.myapplication.R;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


//TODO: 스크롤 될 때 목록 자동 접고 펴기
//TODO: Location 실제 위, 경도 값 받아서 타임라인 표시
public class ScheduleForm extends AppCompatActivity {
    ViewGroup mapContainer;
    private RecyclerView recyclerView;

    private class Trip{
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

    private class Day{
        private int order;
        private List<Location> spots = new ArrayList<>();

        public Day(int order){
            this.order = order;
        }

        public Day(int order,  List<Location> locationList){
            this.order = order;
            this.spots = locationList;
        }

        public void addSpot(int onWhichIndex, Location location){
            this.spots.add(onWhichIndex, location);
//            int newSpotLength = spots.length + 1;
//            Location newSpots[] = new Location[newSpotLength];
//            for(int i=0; i<onWhichOrder; i++){
//                if(i == onWhichOrder-1){
//                    newSpots[i] = location;
//                }else{
//                    newSpots[i] = this.spots[i];
//                }
//            }
//            for(int i=onWhichOrder; i<newSpotLength; i++){
//                newSpots[i] = this.spots[i-1];
//            }
//            this.spots = newSpots;
        }

        public List<Location> getSpots() {
            return spots;
        }
    }

    private ViewGroup mapViewContainer;
    private MapView mapView;
//    private MapPoint mapPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);

//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.add(R.id.scheduleForm_mapContainer, new MapFragment());
//        fragmentTransaction.commit();

        mapViewContainer = findViewById(R.id.scheduleForm_mapContainer);
        mapView = new MapView(this);
        mapViewContainer.addView(mapView);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(34.746487,127.748342),false);
        mapView.setZoomLevel(5, false);
//        mapView.setMapViewEventListener(this);


        recyclerView = findViewById(R.id.scheduleForm_planRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<ExpandableListAdapter.Item> data = new ArrayList<>();

        try {
            // 예시 trip 초기화
            //TODO : 날짜 지금은 일일히 넣었는데, 시작일로부터 하루씩 더하기 추가
            Date date1=new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/01");
            Date date2=new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/02");
            Date date3=new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/03");
            Date date4=new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/04");
            Trip thisTrip = new Trip("여수 식도락 여행", date1, 4);
            List<Location> firstDayLocations = new ArrayList<>();
            firstDayLocations.add(new Location("아름다운 해변"));
            firstDayLocations.add(new Location("유명한 해변"));
            firstDayLocations.add(new Location("아름다운 산"));
            List<Location> secondDayLocations = new ArrayList<>();
            secondDayLocations.add(new Location("유명한 산"));
            secondDayLocations.add(new Location("큰 산"));
            secondDayLocations.add(new Location("멋진 공원"));
            secondDayLocations.add(new Location("작은 공원"));
            List<Location> thirdDayLocations = new ArrayList<>();
            thirdDayLocations.add(new Location("다양한 동물원"));
            thirdDayLocations.add(new Location("멋진 동물원"));
            thirdDayLocations.add(new Location("멋진 박물관"));
            thirdDayLocations.add(new Location("큰 박물관"));
            List<Location> fourthDayLocations = new ArrayList<>();
            fourthDayLocations.add(new Location("작은 동물원"));
            fourthDayLocations.add(new Location("작은 박물관"));
            fourthDayLocations.add(new Location("멋진 휴양림"));
            fourthDayLocations.add(new Location("거대한 휴양림"));

            Day day = new Day(0, firstDayLocations);
            thisTrip.addDay(0, day);
            day = new Day(1, secondDayLocations);
            thisTrip.addDay(1, day);
            day = new Day(2, thirdDayLocations);
            thisTrip.addDay(2, day);
            day = new Day(3, fourthDayLocations);
            thisTrip.addDay(3, day);


            SimpleDateFormat transFormat = new SimpleDateFormat("MM/dd E");
            for(int i=0; i<thisTrip.getPeriod(); i++){
                // 먼저 day 수만큼 HEADER 추가
                if(i==0){
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day "+String.valueOf(i), transFormat.format(date1)));
                }else if(i==1) {
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day " + String.valueOf(i), transFormat.format(date2)));
                }else if(i==2) {
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day " + String.valueOf(i), transFormat.format(date3)));
                }else if(i==3) {
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day " + String.valueOf(i), transFormat.format(date4)));
                }
                // 각 날짜별로 포함된 location 을 CHILD로 추가
                List<Location> spots = thisTrip.getDay(i).getSpots();
                for(int j=0; j<spots.size(); j++){
                    data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, spots.get(j).getName()));
                }
            }

            recyclerView.setAdapter(new ExpandableListAdapter(data));

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
}

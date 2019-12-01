package com.example.myapplication.Search;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.NewPlace;
import com.example.myapplication.Database.Place;
import com.example.myapplication.R;
import com.example.myapplication.Schedule.Schedule;
import com.example.myapplication.Schedule.ScheduleAdapter;
import com.example.myapplication.Schedule.ScheduleForm;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

//지도로 검색결과 나오는 액티비티

public class SearchMap extends AppCompatActivity implements MapView.CurrentLocationEventListener, Button.OnClickListener, MapView.POIItemEventListener,
        MapView.MapViewEventListener, PlaceInfoBoxFragment.OnFragmentInteractionListener, PlaceInfoBoxFragment.OnInputListener{

    private static final String LOG_TAG = "HomeFragment";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
//    public static String[] locationInformation = {"국립해양박물관","부산 영도구 해양로 301번길 45", "051-309-1500", "평일 09:00-18:00","무료"};

    public static MapView mapView;
    ViewGroup mapViewContainer;
    Button gps_btn;
    MapPoint current_mapPoint;
    ImageButton downArrow, tjBtn;
    ToggleButton starBtn;
    ConstraintLayout infoContainer, mapContainer;
    Boolean markerIsSelected = false;

    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    PlaceInfoBoxFragment placeInfoBoxFragment = new PlaceInfoBoxFragment();

    ArrayList<Place> placeList = new ArrayList<Place>(); //전체 placelist (1~6)
    ArrayList<Place> placeList1 = new ArrayList<Place>(); //관광지
    ArrayList<Place> placeList2 = new ArrayList<Place>(); //전시관람
    ArrayList<Place> placeList3 = new ArrayList<Place>(); //자연휴양
    ArrayList<Place> placeList4 = new ArrayList<Place>(); //캠핑
    ArrayList<Place> placeList5 = new ArrayList<Place>(); //역사유적
    ArrayList<Place> placeList6 = new ArrayList<Place>(); //지역특화거리

    String participants[] = new String[100];
    StringBuffer sb;

    int version = 1;
    int count = 0;
    String sql;
    Cursor cursor;
    MyDatabaseOpenHelper helper;
    SQLiteDatabase database;

    public static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    ArrayList poiData = new ArrayList();
    //index ... 0.name 1.lat 2.lon 3.address 4.tel 5.time 6.fee

    int poiDataIndex=-1;
    String poiName = "";

    static Place selectedPlace = null;
    static int selectedDay = -1;
    static String selectedTripName = "";
    public static Place getSelectedPlace(){
        return selectedPlace;
    }
    public static int getSelectedDay(){
        return selectedDay;
    }
    public static String getSelectedTripName(){
        return selectedTripName;
    }

    private EditText searchBox;
    private ImageButton searchBtn;
    private Button clearBtn;
    private ArrayList<Place> searchResult = new ArrayList();
    private ArrayList<Place> allPlace = new ArrayList();

    InputMethodManager imm;

    boolean[] checkBoolean;
    LinearLayout filterBox;
    boolean filterChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        //setDrawer();

        checkBoolean = SearchFilter.getCheckBoolean();
//        Log.d("filter test map", String.valueOf(checkBoolean[0])+","+
//                String.valueOf(checkBoolean[1])+ ","+
//                String.valueOf(checkBoolean[2])+ ","+
//                String.valueOf(checkBoolean[3])+ ","+
//                String.valueOf(checkBoolean[4])+ ","+
//                String.valueOf(checkBoolean[5]));

        mapContainer = findViewById(R.id.searchmap);
        gps_btn = findViewById(R.id.gps_btn);
        tjBtn = findViewById(R.id.serachmap_tjBtn);
        tjBtn.setOnClickListener(this);
        mapViewContainer = findViewById(R.id.map_view);
        mapView = new MapView(this);
        mapView.setCurrentLocationEventListener(this);
        mapView.setZoomLevel(11, false);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        searchBox = findViewById(R.id.searchmap_searchBox);
        searchBtn = findViewById(R.id.searchmap_searchBtn);
        searchBtn.setOnClickListener(this);
        clearBtn = findViewById(R.id.searchmap_clear);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        filterBox = findViewById(R.id.searchmap_filterLinearLayout);

        setUp();

        for(int i=0; i<6; i++){
            if(checkBoolean[i]){
                filterChecked = true;
                if(i==0){
                    String infService = this.LAYOUT_INFLATER_SERVICE;
                    LayoutInflater li = (LayoutInflater) this.getSystemService(infService);
                    View v = li.inflate(R.layout.custom_button_searchfilter, filterBox, false);
                    Button b = v.findViewById(R.id.filterbox);
                    b.setText("관광지");
                    filterBox.addView(v);
                    for(int j=0; j<placeList1.size(); j++){
                        allPlace.add(placeList1.get(j));
                    }
                }
                if(i==1){
                    String infService = this.LAYOUT_INFLATER_SERVICE;
                    LayoutInflater li = (LayoutInflater) this.getSystemService(infService);
                    View v = li.inflate(R.layout.custom_button_searchfilter, filterBox, false);
                    Button b = v.findViewById(R.id.filterbox);
                    b.setText("전시관람");
                    filterBox.addView(v);
                    for(int j=0; j<placeList2.size(); j++){
                        allPlace.add(placeList2.get(j));
                    }
                }
                if(i==2){
                    String infService = this.LAYOUT_INFLATER_SERVICE;
                    LayoutInflater li = (LayoutInflater) this.getSystemService(infService);
                    View v = li.inflate(R.layout.custom_button_searchfilter, filterBox, false);
                    Button b = v.findViewById(R.id.filterbox);
                    b.setText("자연휴양");
                    filterBox.addView(v);
                    for(int j=0; j<placeList3.size(); j++){
                        allPlace.add(placeList3.get(j));
                    }
                }
                if(i==3){
                    String infService = this.LAYOUT_INFLATER_SERVICE;
                    LayoutInflater li = (LayoutInflater) this.getSystemService(infService);
                    View v = li.inflate(R.layout.custom_button_searchfilter, filterBox, false);
                    Button b = v.findViewById(R.id.filterbox);
                    b.setText("캠핑");
                    filterBox.addView(v);
                    for(int j=0; j<placeList4.size(); j++){
                        allPlace.add(placeList4.get(j));
                    }
                }
                if(i==4){
                    String infService = this.LAYOUT_INFLATER_SERVICE;
                    LayoutInflater li = (LayoutInflater) this.getSystemService(infService);
                    View v = li.inflate(R.layout.custom_button_searchfilter, filterBox, false);
                    Button b = v.findViewById(R.id.filterbox);
                    b.setText("역사유적");
                    filterBox.addView(v);
                    for(int j=0; j<placeList5.size(); j++){
                        allPlace.add(placeList5.get(j));
                    }
                }
                if(i==5){
                    String infService = this.LAYOUT_INFLATER_SERVICE;
                    LayoutInflater li = (LayoutInflater) this.getSystemService(infService);
                    View v = li.inflate(R.layout.custom_button_searchfilter, filterBox, false);
                    Button b = v.findViewById(R.id.filterbox);
                    b.setText("지역특화거리");
                    filterBox.addView(v);
                    for(int j=0; j<placeList6.size(); j++){
                        allPlace.add(placeList6.get(j));
                    }
                }
            }
        }

        if(!filterChecked){
            allPlace = placeList;
        }

        clearBtn.setOnClickListener(this);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if(s.length() == 0 && !filterChecked){
                    searchResult.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        searchBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String input = String.valueOf(searchBox.getText());
                    fm = getSupportFragmentManager();
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.remove(placeInfoBoxFragment);
                    fragmentTransaction.commit();
                    mapView.removeAllPOIItems();
                    if(input.length() != 0 || filterChecked){
                        searchResult.clear();
                        for(int i=0; i<allPlace.size(); i++){
                            if(allPlace.get(i).getName().contains(input)){
                                searchResult.add(allPlace.get(i));
                            }
                        }
                        for(int i=0; i<searchResult.size(); i++){
                            MapPOIItem marker = new MapPOIItem();
                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                            marker.setItemName(searchResult.get(i).getName());
                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(searchResult.get(i).getLatitude(),searchResult.get(i).getLongitude()));
                            marker.setTag(i);
                            mapView.addPOIItem(marker);
                        }
                    }
                    return true;
                }
                return false;
            }
        });


        mapView.setPOIItemEventListener(this);
        gps_btn.setOnClickListener(this);

        infoContainer = findViewById(R.id.search_map_info_container);

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();

        }
    }


    @Override
    //Button.OnClickListener
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.gps_btn:
//                Toast.makeText(this, String.valueOf(mapView.getZoomLevel()), Toast.LENGTH_SHORT).show();
//                if(mapView.getZoomLevel()>=2){
//                    mapView.setZoomLevel(2,false);
//                }
                mapView.setMapCenterPoint(current_mapPoint, false);
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                break;
            case R.id.serachmap_tjBtn:
                Intent intent = new Intent(SearchMap.this, SearchFilter.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
                break;
            case R.id.search_map_info_container:
//                Log.e("infocontainer","clicked");
                break;
            case R.id.searchmap_searchBtn:
                //키보드 내리기
                imm.hideSoftInputFromWindow(searchBox.getWindowToken(),0);
                fm = getSupportFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.remove(placeInfoBoxFragment);
                fragmentTransaction.commit();
                mapView.removeAllPOIItems();
                String input = String.valueOf(searchBox.getText());
                searchResult.clear();
                if(input.length() != 0 || filterChecked) {
                    for (int i = 0; i < allPlace.size(); i++) {
                        if (allPlace.get(i).getName().contains(input)) {
                            searchResult.add(allPlace.get(i));
                        }
                    }
                    for (int i = 0; i < searchResult.size(); i++) {
                        MapPOIItem marker = new MapPOIItem();
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                        marker.setItemName(searchResult.get(i).getName());
                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(searchResult.get(i).getLatitude(), searchResult.get(i).getLongitude()));
                        marker.setTag(i);
                        mapView.addPOIItem(marker);
                    }
                }
                break;
            case R.id.searchmap_clear:
                searchResult.clear();
                searchBox.setText("");
                // 키보드 올리기
                imm.showSoftInput(searchBox,0);
                break;
        }

    }



    private void setUp() {
        helper = new MyDatabaseOpenHelper(SearchMap.this, MyDatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();
        String json = null;
        JSONObject obj = null;
        JSONArray recordArray = null;
        try{
            InputStream is = getAssets().open("전국관광지정보표준데이터.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String (buffer, "UTF-8");
            obj = new JSONObject(json);
            recordArray = obj.getJSONArray("records");
            for(int i=0; i<recordArray.length(); i++){
                JSONObject placeObj = recordArray.getJSONObject(i);
                Place place = new Place(placeObj.getString("관광지명"),
                        Double.parseDouble(placeObj.getString("위도")),
                        Double.parseDouble(placeObj.getString("경도")),
                        "tmp-소재지 도로명주소",
                        placeObj.getString("관리기관전화번호"),
                        "관광지");
                placeList.add(place);
                placeList1.add(place);
            }
            InputStream is2 = getAssets().open("전국박물관미술관정보표준데이터.json");
            int size2 = is2.available();
            byte[] buffer2 = new byte[size2];
            is2.read(buffer2);
            is2.close();
            json = new String (buffer2, "UTF-8");
            obj = new JSONObject(json);
            recordArray = obj.getJSONArray("records");
            for(int i=0; i<200; i++){
                JSONObject placeObj = recordArray.getJSONObject(i);
                Place place = new Place(placeObj.getString("시설명"),
                        Double.parseDouble(placeObj.getString("위도")),
                        Double.parseDouble(placeObj.getString("경도")),
                        "tmp-소재지 도로명주소",
                        placeObj.getString("운영기관전화번호"),
                        "전시관람");
                placeList.add(place);
                placeList2.add(place);
            }
            InputStream is6 = getAssets().open("전국야영(캠핑)장표준데이터.json");
            int size6 = is6.available();
            byte[] buffer6 = new byte[size6];
            is6.read(buffer6);
            is6.close();
            json = new String (buffer6, "UTF-8");
            obj = new JSONObject(json);
            recordArray = obj.getJSONArray("records");
            for(int i=0; i<200; i++){
                JSONObject placeObj = recordArray.getJSONObject(i);
                Place place = new Place(placeObj.getString("야영(캠핑)장명"),
                        Double.parseDouble(placeObj.getString("위도")),
                        Double.parseDouble(placeObj.getString("경도")),
                        "tmp-소재지 도로명주소",
                        placeObj.getString("관리기관전화번호"),
                        "캠핑");
                placeList.add(place);
                placeList4.add(place);
            }
            InputStream is3 = getAssets().open("전국휴양림표준데이터.json");
            int size3 = is3.available();
            byte[] buffer3 = new byte[size3];
            is3.read(buffer3);
            is3.close();
            json = new String (buffer3, "UTF-8");
            obj = new JSONObject(json);
            recordArray = obj.getJSONArray("records");
            for(int i=0; i<recordArray.length(); i++){
                JSONObject placeObj = recordArray.getJSONObject(i);
                Place place = new Place(placeObj.getString("휴양림명"),
                        Double.parseDouble(placeObj.getString("위도")),
                        Double.parseDouble(placeObj.getString("경도")),
                        "tmp-소재지 도로명주소",
                        placeObj.getString("휴양림전화번호"),
                        "자연휴양");
                placeList.add(place);
                placeList3.add(place);
            }
            InputStream is4 = getAssets().open("전국향토문화유적표준데이터.json");
            int size4 = is4.available();
            byte[] buffer4 = new byte[size4];
            is4.read(buffer4);
            is4.close();
            json = new String (buffer4, "UTF-8");
            obj = new JSONObject(json);
            recordArray = obj.getJSONArray("records");
            for(int i=0; i<200; i++){
                JSONObject placeObj = recordArray.getJSONObject(i);
                Place place = new Place(placeObj.getString("향토문화유적명"),
                        Double.parseDouble(placeObj.getString("위도")),
                        Double.parseDouble(placeObj.getString("경도")),
                        "tmp-소재지 도로명주소",
                        placeObj.getString("관리기관전화번호"),
                        "역사유적");
                placeList.add(place);
                placeList5.add(place);
            }
            InputStream is5 = getAssets().open("전국지역특화거리표준데이터.json");
            int size5 = is5.available();
            byte[] buffer5 = new byte[size5];
            is5.read(buffer5);
            is5.close();
            json = new String (buffer5, "UTF-8");
            obj = new JSONObject(json);
            recordArray = obj.getJSONArray("records");
            for(int i=0; i<recordArray.length(); i++){
                JSONObject placeObj = recordArray.getJSONObject(i);
                if(placeObj.getString("위도") != null && placeObj.getString("위도").length() != 0) {
                    Place place = new Place(placeObj.getString("거리명"),
                            Double.parseDouble(placeObj.getString("위도")),
                            Double.parseDouble(placeObj.getString("경도")),
                            "tmp-소재지 도로명주소",
                            placeObj.getString("관리기관전화번호"),
                            "지역특화거리");
                    placeList.add(place);
                    placeList6.add(place);
                }
            }
//            Log.d("sqlite JSONObject ", String.valueOf(obj));
        }catch (IOException e){
            e.printStackTrace();
            Log.d("sqlite exception", "IOException");
        }catch (JSONException e){
            e.printStackTrace();
            Log.d("sqlite exception", "JSONException");
        }
//        Log.d("sqlite JSON ", json);
        sb = new StringBuffer();
        if(database != null){
//            helper.deleteAll(database);
//            helper.insertName(database, "example_chaenkim");
//            helper.insertName(database, "example_clairesong");
        }
    }

//    private void nameList(){
//        sql = "select name from "+ helper.tableName;
//        cursor = database.rawQuery(sql, null);
//        if(cursor != null){
//            count = cursor.getCount();
//            for(int i=0; i<count; i++){
//                cursor.moveToNext();
//                String participant = cursor.getString(0);
//                participants[i] = participant;
//                sb.append("\n"+participants[i]);
//            }
//            cursor.close();
//        }
//    }

    public ArrayList<Place> getData(){
        // 전체 place 가 담긴 placeList 전달
        return placeList;
    }


    public int getDataIndex(){
        // 클릭한 데이터의 placList 상의 인덱스 전달
        return poiDataIndex;
    }

    public String getPoiName(){
        return poiName;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setShowCurrentLocationMarker(false);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        current_mapPoint = mapPoint;
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }
    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }
    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving);
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
//                    finish();
                }else {
                    Toast.makeText(this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        // 2. 이미 퍼미션을 가지고 있다면
        // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            // 3.  위치 값을 가져올 수 있음
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }


    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



    @Override //MapView.POIItemEventListener
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        mapView.setMapCenterPoint(mapPOIItem.getMapPoint(),true);
//        mapView.setZoomLevel(2,false);
    }

    @Override //MapView.POIItemEventListener
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        markerIsSelected = true;
//        infoContainer.setVisibility(View.VISIBLE);
        //Fragment part
        fm = getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        if(placeInfoBoxFragment.isAdded()){
            fragmentTransaction.remove(placeInfoBoxFragment);
            placeInfoBoxFragment = new PlaceInfoBoxFragment();
        }
        placeInfoBoxFragment.setOnItemClickListener(new PlaceInfoBoxFragment.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Place selectedPlace, int selectedDay, String selectedTripName) {
                NewPlace newPlace = NewPlace.getInstance();
                newPlace.setSelectedDay(selectedDay);
                newPlace.setSelectedPlace(selectedPlace);
                newPlace.setSelectedTripName(selectedTripName);
                Intent intent = new Intent(SearchMap.this, ScheduleForm.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                mapContainer = findViewById(R.id.searchmap);
                mapContainer.removeAllViews();
                startActivity(intent);
            }
        });
        fragmentTransaction.add(R.id.search_map_info_container, placeInfoBoxFragment);
        fragmentTransaction.commit();
        poiDataIndex = mapPOIItem.getTag();
        poiName = mapPOIItem.getItemName();
//        for(int i=0; i<placeList.size(); i++){
//            Log.d("sqlite getall", String.valueOf(places.get(i).getName()));
//        }
//        poiName = mapPOIItem.getItemName();
    }

    @Override //MapView.POIItemEventListener
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }
    @Override //View.OnDragListener
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
    @Override //MapView.MapViewEventListener
    public void onMapViewInitialized(MapView mapView) {

    }
    @Override //MapView.MapViewEventListener
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }
    @Override //MapView.MapViewEventListener
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override //MapView.MapViewEventListener
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        if(markerIsSelected){
            fm = getSupportFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.remove(placeInfoBoxFragment);
            fragmentTransaction.commit();
//            infoContainer.setVisibility(View.INVISIBLE);
            markerIsSelected = false;
        }
    }

    @Override //MapView.MapViewEventListener
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }
    @Override //MapView.MapViewEventListener
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }
    @Override //MapView.MapViewEventListener
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }
    @Override //MapView.MapViewEventListener
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }
    @Override //MapView.MapViewEventListener
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving);
    }

    @Override
    //PlaceInfoBoxFragment.OnFragmentInteractionListener
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    //PlaceInfoBoxFragment.OnInputListener
    public void sendInput(int inputId) {
        switch (inputId){
            case R.id.fragment_place_info_box_downArrowBtn:
                fm = getSupportFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.remove(placeInfoBoxFragment);
                fragmentTransaction.commit();
//                infoContainer.setVisibility(View.INVISIBLE);
                break;
        }
    }

//    public void setDrawer(){
//        final DrawerLayout drawerLayout = findViewById(R.id.drawerlayout_map);
//        final NavigationView navigationView = findViewById(R.id.navigationview_map);
//
//        //drawer toggle 세트로 drawerlayout, toolbar 등 해서 생성 - 드로어 여닫기 완성
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.closed);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//
//        //
//        ImageView drawer_opener = findViewById(R.id.drawer_opener_map);
//        drawer_opener.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawerLayout.openDrawer(navigationView);
//            }
//        });
//
//
//        //drawer recyclerview
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
//        RecyclerView drawer_recyclerview = findViewById(R.id.drawer_recyclerview_map);
//        drawer_recyclerview.setLayoutManager(layoutManager);
//        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this, 2);
//        drawer_recyclerview.setAdapter(scheduleAdapter);
//
//
//    }
}



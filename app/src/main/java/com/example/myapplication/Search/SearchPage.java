package com.example.myapplication.Search;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.Place;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SearchPage extends AppCompatActivity implements View.OnClickListener {

    private ImageView tree;
    int version = 1;
    int count = 0;
    String sql;
    Cursor cursor;
    MyDatabaseOpenHelper helper;
    SQLiteDatabase database;

    ArrayList<Place> placeList = new ArrayList<Place>();
    StringBuffer sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        setUp();

    }

    private void setUp() {
        placeList = new ArrayList<Place>();
        helper = new MyDatabaseOpenHelper(SearchPage.this, MyDatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();
        String json = null;
        JSONObject obj = null;
        JSONArray recordArray = null;
        try{
//            InputStream is = getAssets().open("전국관광지정보표준데이터.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String (buffer, "UTF-8");
//            obj = new JSONObject(json);
//            recordArray = obj.getJSONArray("records");
//            for(int i=0; i<recordArray.length(); i++){
//                JSONObject placeObj = recordArray.getJSONObject(i);
//                Place place = new Place(placeObj.getString("관광지명"),
//                        Double.parseDouble(placeObj.getString("위도")),
//                        Double.parseDouble(placeObj.getString("경도")),
//                        "tmp-소재지 도로명주소",
//                        placeObj.getString("관리기관전화번호"),
//                        "관광지");
//                placeList.add(place);
////                Log.d("sqlite PLACE LIST", String.valueOf(place.getName()));
//            }
//            InputStream is2 = getAssets().open("전국박물관미술관정보표준데이터.json");
//            int size = is2.available();
//            byte[] buffer = new byte[size];
//            is2.read(buffer);
//            is2.close();
//            json = new String (buffer, "UTF-8");
//            obj = new JSONObject(json);
//            recordArray = obj.getJSONArray("records");
//            for(int i=0; i<recordArray.length(); i++){
//                JSONObject placeObj = recordArray.getJSONObject(i);
//                Place place = new Place(placeObj.getString("시설명"),
//                        Double.parseDouble(placeObj.getString("위도")),
//                        Double.parseDouble(placeObj.getString("경도")),
//                        "tmp-소재지 도로명주소",
//                        placeObj.getString("관리기관전화번호"),
//                        "전시관람");
//                placeList.add(place);
//                Log.d("sqlite PLACE LIST", String.valueOf(place.getName()));
//            }
//            InputStream is3 = getAssets().open("전국야영(캠핑)장표준데이터.json");
//            int size = is3.available();
//            byte[] buffer = new byte[size];
//            is3.read(buffer);
//            is3.close();
//            json = new String (buffer, "UTF-8");
//            obj = new JSONObject(json);
//            recordArray = obj.getJSONArray("records");
//            for(int i=0; i<recordArray.length(); i++){
//                JSONObject placeObj = recordArray.getJSONObject(i);
//                Place place = new Place(placeObj.getString("야영(캠핑)장명"),
//                        Double.parseDouble(placeObj.getString("위도")),
//                        Double.parseDouble(placeObj.getString("경도")),
//                        "tmp-소재지 도로명주소",
//                        placeObj.getString("관리기관전화번호"),
//                        "캠핑");
//                placeList.add(place);
//                Log.d("sqlite PLACE LIST", String.valueOf(place.getName()));
//            }
//            InputStream is3 = getAssets().open("전국휴양림표준데이터.json");
//            int size = is3.available();
//            byte[] buffer = new byte[size];
//            is3.read(buffer);
//            is3.close();
//            json = new String (buffer, "UTF-8");
//            obj = new JSONObject(json);
//            recordArray = obj.getJSONArray("records");
//            for(int i=0; i<recordArray.length(); i++){
//                JSONObject placeObj = recordArray.getJSONObject(i);
//                Place place = new Place(placeObj.getString("휴양림명"),
//                        Double.parseDouble(placeObj.getString("위도")),
//                        Double.parseDouble(placeObj.getString("경도")),
//                        "tmp-소재지 도로명주소",
//                        placeObj.getString("휴양림전화번호"),
//                        "자연휴양");
//                placeList.add(place);
//                Log.d("sqlite PLACE LIST", String.valueOf(place.getName()));
//            }
//            InputStream is4 = getAssets().open("전국향토문화유적표준데이터.json");
//            int size = is4.available();
//            byte[] buffer = new byte[size];
//            is4.read(buffer);
//            is4.close();
//            json = new String (buffer, "UTF-8");
//            obj = new JSONObject(json);
//            recordArray = obj.getJSONArray("records");
//            for(int i=0; i<recordArray.length(); i++){
//                JSONObject placeObj = recordArray.getJSONObject(i);
//                Place place = new Place(placeObj.getString("향토문화유적명"),
//                        Double.parseDouble(placeObj.getString("위도")),
//                        Double.parseDouble(placeObj.getString("경도")),
//                        "tmp-소재지 도로명주소",
//                        placeObj.getString("관리기관전화번호"),
//                        "역사유적");
//                placeList.add(place);
//                Log.d("sqlite PLACE LIST", String.valueOf(place.getName()));
//            }
            InputStream is5 = getAssets().open("전국지역특화거리표준데이터.json");
            int size = is5.available();
            byte[] buffer = new byte[size];
            is5.read(buffer);
            is5.close();
            json = new String (buffer, "UTF-8");
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
                    Log.d("sqlite PLACE LIST", String.valueOf(place.getName()));
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

    private void nameList(){
//        sql = "select name from "+ helper.tableName;
//        sql = "update "
        cursor = database.rawQuery(sql, null);
        if(cursor != null){
//            count = cursor.getCount();
//            for(int i=0; i<count; i++){
//                cursor.moveToNext();
//                String participant = cursor.getString(0);
////                participants[i] = participant;
////                sb.append("\n"+participants[i]);
//            }
            cursor.close();
        }
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.search_page_tree_img:
                startActivity(new Intent(SearchPage.this, SearchMap.class));

                break;
        }
    }
}

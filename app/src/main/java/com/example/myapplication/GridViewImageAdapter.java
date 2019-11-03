package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ToggleButton;
import com.example.myapplication.AskLocation;

class GridViewImageAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    private Context mContext;
    private int displayWidth; //화면크기
    private int size; //이미지 크기
    private int pad; //패딩

    public String[] placeName = {
            "서울","경주","통영",
            "부산","대왕릉","제주도",
            "강릉","인천","서울","경주","통영",
            "부산","대왕릉","제주도",
            "강릉","인천"
    };

    int[] selectedBoolean = new int[placeName.length];

    //출력될 이미지 데이터셋(res/drawable 폴더)
    private Integer[] imageIds = {
            R.drawable.location0,
            R.drawable.location1,
            R.drawable.location2,
            R.drawable.location3,
            R.drawable.location4,
            R.drawable.location5,
            R.drawable.location6,
            R.drawable.location7,
            R.drawable.location0,
            R.drawable.location1,
            R.drawable.location2,
            R.drawable.location3,
            R.drawable.location4,
            R.drawable.location5,
            R.drawable.location6,
            R.drawable.location7
    };

    public GridViewImageAdapter(Context c){
        mContext = c;
//        this.displayWidth = displayWidth;
//        size = displayWidth/3 ;  //화면크기를 / 3으로 나누어서 이미지 사이즈를 구한다.
//        pad = 4;
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //처음엔 모두 0(비선택)
        for(int i=0; i< selectedBoolean.length; i++){
            selectedBoolean[i] = 0;
        }
        //주어진 위치(position)에 출력할 이미지를 반환함
        ToggleButton tb;
//        ImageView iv;
        if(convertView == null){
//            iv = new ImageView(mContext);
            tb = new ToggleButton(mContext);
//            toggleButton.setLayoutParams(new GridView.LayoutParams(size, size)); //85,85
//            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            toggleButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            toggleButton.setPadding(pad,pad,pad,pad); //4,4,4,4
        }else{
            tb = (ToggleButton) convertView;
//            iv = (ImageView) convertView;
        }
        //이미지뷰에 주어진 위치의 이미지를 설정함
//        tb.setBackgroundResource(imageIds[position]);
//        tb.set
//        tb.setWidth(170);
//        tb.setHeight(170);
        //tb.setBackgroundDrawable(mContext.getResources().getDrawable(imageIds[position]));
//        tb.setBackgroundColor(mContext.getResources().getColor(R.color.black));
//        tb.setPadding(10,10,10,10);
//        tb.setButtonDrawable(imageIds[position]);
        tb.setText(placeName[position]);
        tb.setTextOn(placeName[position]);
        tb.setTextOff(placeName[position]);
        tb.setOnCheckedChangeListener(this);
        tb.setId(position+200);
        tb.setChecked(false);
        tb.setTextSize(20);
        tb.setTextColor(mContext.getResources().getColor(R.color.white));
        return tb;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            buttonView.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            selectedBoolean[Integer.valueOf(buttonView.getId())-200] = 1;
        }else{
            buttonView.setTextColor(mContext.getResources().getColor(R.color.white));
            selectedBoolean[Integer.valueOf(buttonView.getId())-200] = 0;
        }
        //test 용 코드
        String s = "";
        for(int i=0; i< selectedBoolean.length; i++){
            s+= String.valueOf(selectedBoolean[i]);
        }
        Log.e("select",s);
    }
}

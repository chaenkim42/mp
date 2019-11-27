package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.myapplication.Database.User;

import static com.example.myapplication.AskPreference.askPref_nextBtn;

public class GridViewTextAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener{
    public static int selectedHash = 0;
    Button nextBtn;
    private Context mContext;
    private int displayWidth; //화면크기
    private int size; //이미지 크기
    private int pad; //패딩
    public User user = User.getInstance();

    public String[] hashtags = {
            "드라이브","해수욕장","미술관","수산물시장","역사탐방",
            "성지순례","등산","목장","계곡","박물관","동물원","놀이공원",
            "체험학습","식물원","농산물시장","역사탐방","산책","절","휴양림"
    };

    int[] selectedBoolean = new int[hashtags.length];


    public GridViewTextAdapter(Context c){
        mContext = c;
        nextBtn = askPref_nextBtn;
//        this.displayWidth = displayWidth;
//        size = displayWidth/3 ;  //화면크기를 / 3으로 나누어서 이미지 사이즈를 구한다.
//        pad = 4;
    }

    @Override
    public int getCount() {
        return hashtags.length;
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
        if(convertView == null){
            tb = new ToggleButton(mContext);
//            toggleButton.setLayoutParams(new GridView.LayoutParams(size, size)); //85,85
//            toggleButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            toggleButton.setPadding(pad,pad,pad,pad); //4,4,4,4
        }else{
            tb = (ToggleButton) convertView;
        }
        //이미지뷰에 주어진 위치의 이미지를 설정함
        tb.setText("#"+hashtags[position]);
        tb.setTextOn("#"+hashtags[position]);
        tb.setTextOff("#"+hashtags[position]);
        tb.setWidth(172);
        tb.setHeight(45);
        tb.setOnCheckedChangeListener(this);
        tb.setId(position+100);
        tb.setChecked(false);
        tb.setTextSize(18);
        tb.setTextColor(mContext.getResources().getColor(R.color.black));
        tb.setBackground(mContext.getDrawable(R.drawable.box_unselected));
        return tb;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //클릭될 때마다 background resource(버튼 배경) 바꾸고 selectedBoolean에 반영
        if(isChecked){
            selectedHash ++;
            buttonView.setBackgroundResource(R.drawable.box_selected);
            buttonView.setTextColor(mContext.getResources().getColor(R.color.white));
            selectedBoolean[Integer.valueOf(buttonView.getId())-100] = 1;

            // user preference 더해줌
            user.preferences.add(buttonView.getText().toString());
//            Log.d("preferences: - add ", buttonView.getText().toString());
        }else{
            selectedHash --;
            buttonView.setBackgroundResource(R.drawable.box_unselected);
            buttonView.setTextColor(mContext.getResources().getColor(R.color.black));
            selectedBoolean[Integer.valueOf(buttonView.getId())-100] = 0;
//            Log.d("preferences: - remove ", buttonView.getText().toString());

            // user preference 빼줌
            user.preferences.remove(buttonView.getText().toString());
        }

        if(selectedHash >=5){
//            nextBtn.setBackgroundColor(mContext.getResources().getColor(R.color.design_default_color_primary));
        }else{
//            nextBtn.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }

        //test 용 코드
//        String s = "";
//        for(int i=0; i< selectedBoolean.length; i++){
//            s+= String.valueOf(selectedBoolean[i]);
//        }
//        Log.e("select",s);
    }
}

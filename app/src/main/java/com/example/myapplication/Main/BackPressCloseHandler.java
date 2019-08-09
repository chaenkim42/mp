package com.example.myapplication.Main;

import android.app.Activity;
import android.widget.Toast;

//뒤로가기 버튼 눌렸을 때 바로 종료가 되지 않도록 하는 컨트롤러

public class BackPressCloseHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.moveTaskToBack(true);
            activity.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity,
                "한번 더 누르면 종료", Toast.LENGTH_SHORT);
        toast.show();
    }

}

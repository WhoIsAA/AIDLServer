package com.aa.aidlserver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAlarmManager();
    }

    /**
     * 初始化定时任务
     */
    private void initAlarmManager() {
        Intent intent = new Intent();
        // 设置Intent的Action 属性
        intent.setAction("com.aa.aidlserver.AIDLService");
        // Android 5.0以上需要设置包名
        intent.setPackage("com.aa.aidlserver");

        //创建一个PendingIntent对象，包含了startService操作
        PendingIntent pi = PendingIntent.getService(this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        long intervalMillis = 60 * 1000;
        //创建一个系统级定时器，即使熄屏状态也会每隔一分钟启动一次Service添加书本（注：部分定制系统定时器不准）
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), intervalMillis, pi);
    }
}

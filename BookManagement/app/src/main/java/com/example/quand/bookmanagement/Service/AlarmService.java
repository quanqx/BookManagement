package com.example.quand.bookmanagement.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.quand.bookmanagement.Activity.AlarmingActivity;

public class AlarmService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent1 = new Intent(AlarmService.this,
                AlarmingActivity.class);
        intent1.putExtras(intent.getExtras());
        startActivity(intent1);
        return START_NOT_STICKY;
    }
}

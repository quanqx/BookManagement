package com.example.quand.bookmanagement.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.quand.bookmanagement.Service.AlarmService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, AlarmService.class);
        Bundle bundle = intent.getExtras();
        intent1.putExtras(bundle);
        context.startService(intent1);
    }
}

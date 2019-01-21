package com.example.quand.bookmanagement.Utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.quand.bookmanagement.Entities.Alarm;
import com.example.quand.bookmanagement.Receiver.AlarmReceiver;

import java.util.Calendar;

public class AlarmHelper {

    public static void setAlarms(Context context, Alarm alarm){
        boolean []dayOfWeek = alarm.getDayOfWeek();
        for(int i = 0; i < 7; i++){
            if(dayOfWeek[i]){
                setAlarm(context, alarm, (i + 2) % 7);
            }
        }
    }

    private static void setAlarm(Context context, Alarm alarm, int dayOfWeek){
        android.app.AlarmManager alarmManager =
                (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", alarm.getId());
        bundle.putInt("hour", alarm.getHour());
        bundle.putInt("minute", alarm.getMinute());
        bundle.putBoolean("on", alarm.isOn());
        bundle.putBoolean("vibrate", alarm.isVibrate());
        bundle.putInt("ring", alarm.getRing());
        bundle.putInt("idBook", alarm.getId_book());
        bundle.putBooleanArray("dayOfWeek", alarm.getDayOfWeek());
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                alarm.getId() * 10 + dayOfWeek,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);
        long time = calendar.getTimeInMillis();
        if(time < Calendar.getInstance().getTimeInMillis()){
            time += 604800000;// 7 * 24 * 60 * 60 * 1000;
        }
        alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public static void cancelAlarms(Context context, Alarm alarm){
        boolean []dayOfWeek = alarm.getDayOfWeek();
        for(int i = 0; i < 7; i++){
            if(dayOfWeek[i]){
                cancelAlarm(context, alarm, (i + 2) % 7);
            }
        }
    }

    public static void cancelAlarm(Context context, Alarm alarm, int dayOfWeek){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                alarm.getId() * 10 + dayOfWeek, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        android.app.AlarmManager alarmManager =
                (android.app.AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}

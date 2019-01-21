package com.example.quand.bookmanagement.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.quand.bookmanagement.Database.DBManager;
import com.example.quand.bookmanagement.Entities.Alarm;
import com.example.quand.bookmanagement.Entities.Book;
import com.example.quand.bookmanagement.R;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmingActivity extends Activity {

    private SeekBar skCancel;
    private TextView tvTitle;
    private Button btnCancel;
    private DBManager dbManager;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarming_activity);
        turnOnScreen();
        bindView();
        dbManager = new DBManager(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Alarm alarm = new Alarm(bundle.getInt("id"),
                bundle.getInt("hour"),
                bundle.getInt("minute"),
                bundle.getBoolean("on"),
                bundle.getBoolean("vibrate"),
                bundle.getInt("ring"),
                bundle.getInt("idBook"),
                bundle.getBooleanArray("dayOfWeek"));
        setContent(alarm);
        setMediaPlayer(alarm.getRing());
        setVibrator(alarm.isVibrate());
    }

    private void setVibrator(boolean isVibrate) {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        timer = new Timer();
        if(isVibrate){
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    vibrator.vibrate(500);
                }
            }, 0, 3000);
        }
    }

    private void setMediaPlayer(int ringResId){
        mediaPlayer = MediaPlayer.create(this, ringResId);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    private void setContent(Alarm alarm) {
        Book book = dbManager.getBookById(alarm.getId_book());
        tvTitle.setText("Đọc " + book.getTitle());
        int h = alarm.getHour();
        int m = alarm.getMinute();
        String time = (h < 10 ? "0" + h : "" + h) + ":" + (m < 10 ? "0" + m : "" + m);
        btnCancel.setText(time);
    }

    private void turnOnScreen() {
        PowerManager mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wl = mPowerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK|
                        PowerManager.ACQUIRE_CAUSES_WAKEUP|
                        PowerManager.ON_AFTER_RELEASE, "tag"
        );
        wl.acquire();
    }

    private void bindView() {
        skCancel = findViewById(R.id.sk_turn_off);
        tvTitle = findViewById(R.id.tv_title);
        btnCancel = findViewById(R.id.btn_cancel_alarm);
        skCancel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(skCancel.getProgress() >= 97){
                    finish();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void finish(){
        timer.cancel();
        mediaPlayer.stop();
        mediaPlayer.reset();
        super.finish();
    }
}

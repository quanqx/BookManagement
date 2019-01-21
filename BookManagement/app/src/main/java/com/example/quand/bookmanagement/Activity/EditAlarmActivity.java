package com.example.quand.bookmanagement.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.quand.bookmanagement.Database.DBManager;
import com.example.quand.bookmanagement.Entities.Alarm;
import com.example.quand.bookmanagement.R;
import com.example.quand.bookmanagement.Utils.AlarmHelper;
import com.example.quand.bookmanagement.Utils.Helper;

public class EditAlarmActivity extends Activity {

    private TextView tvSelectedDay;
    private TimePicker timePicker;
    private ImageButton btnCancel;
    private ImageButton btnOk;
    private Switch swVibrate;
    private DBManager dbManager;
    private Dialog dialog;
    private Alarm alarm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_alarm);
        dbManager = new DBManager(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        alarm = new Alarm(bundle.getInt("id"),
                bundle.getInt("hour"),
                bundle.getInt("minute"),
                bundle.getBoolean("on"),
                bundle.getBoolean("vibrate"),
                bundle.getInt("ring"),
                bundle.getInt("idBook"),
                bundle.getBooleanArray("dayOfWeek"));
        bindView();
        setTime();
    }

    public void showDialog (Context context) {
        dialog = new Dialog(context);
        dialog.setTitle("Chọn ngày");
        dialog.setContentView(R.layout.select_day);

        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        final CheckBox chkMonday = dialog.findViewById(R.id.chk_monday);
        final CheckBox chkTueday = dialog.findViewById(R.id.chk_tueday);
        final CheckBox chkWednesday = dialog.findViewById(R.id.chk_wednesday);
        final CheckBox chkThursday = dialog.findViewById(R.id.chk_thursday);
        final CheckBox chkFriday = dialog.findViewById(R.id.chk_friday);
        final CheckBox chkSaturday = dialog.findViewById(R.id.chk_saturday);
        final CheckBox chkSunday = dialog.findViewById(R.id.chk_sunday);
        chkMonday.setChecked(alarm.getDayOfWeek()[0]);
        chkTueday.setChecked(alarm.getDayOfWeek()[1]);
        chkWednesday.setChecked(alarm.getDayOfWeek()[2]);
        chkThursday.setChecked(alarm.getDayOfWeek()[3]);
        chkFriday.setChecked(alarm.getDayOfWeek()[4]);
        chkSaturday.setChecked(alarm.getDayOfWeek()[5]);
        chkSunday.setChecked(alarm.getDayOfWeek()[6]);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarm.getDayOfWeek()[0] = chkMonday.isChecked();
                alarm.getDayOfWeek()[1] = chkTueday.isChecked();
                alarm.getDayOfWeek()[2] = chkWednesday.isChecked();
                alarm.getDayOfWeek()[3] = chkThursday.isChecked();
                alarm.getDayOfWeek()[4] = chkFriday.isChecked();
                alarm.getDayOfWeek()[5] = chkSaturday.isChecked();
                alarm.getDayOfWeek()[6] = chkSunday.isChecked();
                setSelectedText(alarm.getDayOfWeek());
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setSelectedText(boolean []dayOfWeek){
        tvSelectedDay.setText(Helper.getDaySelectedText(dayOfWeek) + " >> ");
    }

    private void setTime() {
        timePicker.setHour(alarm.getHour());
        timePicker.setMinute(alarm.getMinute());
    }

    private void bindView() {
        tvSelectedDay = findViewById(R.id.edit_selected_day);
        timePicker = findViewById(R.id.edit_time_picker);
        btnCancel = findViewById(R.id.edit_img_btn_cancel);
        btnOk = findViewById(R.id.edit_img_btn_ok);
        swVibrate = findViewById(R.id.sw_vibrate_edit);
        timePicker.setIs24HourView(true);
        tvSelectedDay.setText(Helper.getDaySelectedText(alarm.getDayOfWeek()));
        swVibrate.setChecked(alarm.isVibrate());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAlarm();
                AlarmHelper.setAlarms(EditAlarmActivity.this, alarm);
                finish();
            }
        });
        findViewById(R.id.edit_select_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(EditAlarmActivity.this);
            }
        });
    }

    private void updateAlarm(){
        alarm.setOn(true);
        alarm.setVibrate(swVibrate.isChecked());
        alarm.setHour(timePicker.getHour());
        alarm.setMinute(timePicker.getMinute());
        dbManager.editAlarm(alarm);
    }

}

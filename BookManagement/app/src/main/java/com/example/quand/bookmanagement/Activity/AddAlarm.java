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
import com.example.quand.bookmanagement.Entities.Book;
import com.example.quand.bookmanagement.R;
import com.example.quand.bookmanagement.Utils.AlarmHelper;
import com.example.quand.bookmanagement.Utils.Helper;


public class AddAlarm extends Activity {

    private Dialog dialog;
    private TextView selectedDay;
    private Alarm alarm;
    private TimePicker timePicker;
    private Switch swVibrate;
    private ImageButton btnCancel;
    private ImageButton btnOk;
    private DBManager dbManager;

    private boolean []dayOfWeek = new boolean[]{true, true, true, true, true, true, true};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);
        alarm = new Alarm();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Book book = (Book)bundle.getSerializable("book");
        bindView(book.getId());
        dbManager = new DBManager(this);

    }

    private void bindView(final int id) {
        selectedDay = findViewById(R.id.selected_day);
        findViewById(R.id.select_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(AddAlarm.this);
            }
        });
        timePicker = findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        swVibrate = findViewById(R.id.sw_vibrate);
        btnCancel = findViewById(R.id.img_btn_cancel);
        btnOk = findViewById(R.id.img_btn_ok);
        selectedDay = findViewById(R.id.selected_day);
        selectedDay.setText(Helper.getDaySelectedText(dayOfWeek));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAlarm(id);
                AlarmHelper.setAlarms(AddAlarm.this, alarm);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        chkMonday.setChecked(dayOfWeek[0]);
        chkTueday.setChecked(dayOfWeek[1]);
        chkWednesday.setChecked(dayOfWeek[2]);
        chkThursday.setChecked(dayOfWeek[3]);
        chkFriday.setChecked(dayOfWeek[4]);
        chkSaturday.setChecked(dayOfWeek[5]);
        chkSunday.setChecked(dayOfWeek[6]);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayOfWeek[0] = chkMonday.isChecked();
                dayOfWeek[1] = chkTueday.isChecked();
                dayOfWeek[2] = chkWednesday.isChecked();
                dayOfWeek[3] = chkThursday.isChecked();
                dayOfWeek[4] = chkFriday.isChecked();
                dayOfWeek[5] = chkSaturday.isChecked();
                dayOfWeek[6] = chkSunday.isChecked();
                setSelectedText(dayOfWeek);
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
        selectedDay.setText(Helper.getDaySelectedText(dayOfWeek));
    }

    private void addAlarm(int id){
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        boolean vibrate = swVibrate.isChecked();
        alarm.setHour(hour);
        alarm.setMinute(minute);
        alarm.setOn(true);
        alarm.setVibrate(vibrate);
        alarm.setRing(R.raw.kalimba);
        alarm.setId_book(id);
        alarm.setDayOfWeek(dayOfWeek);
        dbManager.addAlarm(alarm);
    }

}

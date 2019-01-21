package com.example.quand.bookmanagement.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.quand.bookmanagement.Activity.AddAlarm;
import com.example.quand.bookmanagement.Activity.EditAlarmActivity;
import com.example.quand.bookmanagement.Adapter.AlarmAdapter;
import com.example.quand.bookmanagement.Database.DBManager;
import com.example.quand.bookmanagement.Entities.Alarm;
import com.example.quand.bookmanagement.Entities.Book;
import com.example.quand.bookmanagement.ItemListener;
import com.example.quand.bookmanagement.R;
import com.example.quand.bookmanagement.Utils.AlarmHelper;

import java.util.List;

public class AlarmFragment extends Fragment implements ItemListener{

    private RecyclerView recyclerView;
    private DBManager db;
    private List<Alarm> alarms;
    private Dialog dialog;
    private AlarmAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_fragment, container, false);
        initData();
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        alarms = db.getAllAlarm();
        adapter.setAlarms(alarms);
        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.alarm_recycler_view);
        LinearLayoutManager linear = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linear);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), linear.getOrientation());
        decoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_recycler_view));
        recyclerView.addItemDecoration(decoration);
        adapter = new AlarmAdapter(getContext(), alarms, db);
        adapter.setItemListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        db = new DBManager(getContext());
        alarms = db.getAllAlarm();
    }

    @Override
    public void onClickDelete(View v, int position) {
        Alarm alarm = alarms.get(position);
        db.deleteAlarm(alarm.getId());
        AlarmHelper.cancelAlarms(getContext(), alarm);
        notifyData();
    }

    @Override
    public void onChangeOnOff(CompoundButton compoundButton, boolean b, int position) {
        Alarm alarm = alarms.get(position);
        if(b){
            alarm.setOn(true);
            AlarmHelper.setAlarms(getContext(), alarm);
        }
        else{
            alarm.setOn(false);
            AlarmHelper.cancelAlarms(getContext(), alarm);
        }
        db.editAlarm(alarm);
        notifyData();
    }

    @Override
    public void onChangeVibrate(CompoundButton compoundButton, boolean b, int position) {
        Alarm alarm = alarms.get(position);
        alarm.setVibrate(b);
        db.editAlarm(alarm);
        AlarmHelper.setAlarms(getContext(), alarm);
        notifyData();
    }

    private void notifyData(){
        alarms = db.getAllAlarm();
        adapter.setAlarms(alarms);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v, int position, boolean isLongClick) {
        if(!isLongClick){
            Intent intent = new Intent(getContext(), EditAlarmActivity.class);
            Alarm alarm = alarms.get(position);
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
            startActivity(intent);
        }
        else{
            showDialog(getContext(), position);
        }
    }

    private void showDialog (Context context, final int pos) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.delete_alarm);
        dialog.findViewById(R.id.btn_delete_alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog(pos);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showConfirmDialog(final int pos) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(false);

        builder.setPositiveButton("hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.setNegativeButton("xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.deleteAlarm(alarms.get(pos).getId());
                notifyData();
                dialogInterface.dismiss();
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

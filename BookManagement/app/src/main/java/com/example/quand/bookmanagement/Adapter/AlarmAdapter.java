package com.example.quand.bookmanagement.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.quand.bookmanagement.Database.DBManager;
import com.example.quand.bookmanagement.Entities.Alarm;
import com.example.quand.bookmanagement.Entities.Book;
import com.example.quand.bookmanagement.ItemListener;
import com.example.quand.bookmanagement.R;
import com.example.quand.bookmanagement.Utils.Helper;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    
    private Context context;
    private List<Alarm> alarms;
    private DBManager db;
    private ItemListener itemListener;

    public AlarmAdapter(Context context, List<Alarm> alarms, DBManager db) {
        this.context = context;
        this.alarms = alarms;
        this.db = db;
    }

    public void setItemListener(ItemListener itemListener){
        this.itemListener = itemListener;
    }

    public void setAlarms(List<Alarm> alarms){
        this.alarms = alarms;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.row_alarm, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        setDataView(holder, position);
    }

    private void setDataView(ViewHolder holder, int pos){
        Alarm alarm = alarms.get(pos);
        Book book = db.getBookById(alarm.getId_book());
        String title = "Đọc: ";
        if(book != null) title += book.getTitle();
        holder.title.setText(title);
        int h = alarm.getHour();
        int m = alarm.getMinute();
        holder.time.setText((h < 10 ? "0" + h : "" + h) + ":" + (m < 10 ? "0" + m : "" + m));
        holder.swOnOff.setChecked(alarm.isOn());
        holder.swOnOff.setText(alarm.isOn() ? "Bật " : "Tắt ");
        holder.swVibrate.setChecked(alarm.isVibrate());
        holder.tvSelectedDay.setText(Helper.getDaySelectedText(alarm.getDayOfWeek()));
        setAction(holder, pos);
    }

    private void setAction(ViewHolder holder, final int pos){
        holder.swOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                itemListener.onChangeOnOff(compoundButton, b, pos);
            }
        });
        holder.swVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                itemListener.onChangeVibrate(compoundButton, b, pos);
            }
        });
        holder.setItemListener(new ItemListener() {
            @Override
            public void onClickDelete(View v, int position) {

            }

            @Override
            public void onChangeOnOff(CompoundButton compoundButton, boolean b, int position) {

            }

            @Override
            public void onChangeVibrate(CompoundButton compoundButton, boolean b, int position) {

            }

            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                itemListener.onClick(v, pos, isLongClick);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView title;
        TextView time;
        Switch swOnOff;
        Switch swVibrate;
        TextView tvSelectedDay;
        ItemListener itemListener;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            swOnOff = itemView.findViewById(R.id.sw_on_off);
            swVibrate = itemView.findViewById(R.id.sw_vibrate);
            title = itemView.findViewById(R.id.tv_content);
            time = itemView.findViewById(R.id.tv_time);
            tvSelectedDay = itemView.findViewById(R.id.tv_selected_day);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemListener(ItemListener itemListener){
            this.itemListener = itemListener;
        }

        @Override
        public void onClick(View view) {
            itemListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }

}


package com.example.quand.bookmanagement;

import android.view.View;
import android.widget.CompoundButton;

public interface ItemListener {
    void onClickDelete(View v, int position);
    void onChangeOnOff(CompoundButton compoundButton, boolean b, int position);
    void onChangeVibrate(CompoundButton compoundButton, boolean b, int position);
    void onClick(View v, int position, boolean isLongClick);
}

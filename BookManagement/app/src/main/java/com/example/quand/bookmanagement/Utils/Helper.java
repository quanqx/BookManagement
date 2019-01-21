package com.example.quand.bookmanagement.Utils;

public class Helper {

    public static String getDaySelectedText(boolean []dayOfWeek){
        int count = 0;
        boolean monToFri = false;
        String res = "";
        for (int i = 0; i < 7; i++){
            if(dayOfWeek[i]){
                count ++;
                if(i == 4 && count == 5){
                    monToFri = true;
                }
                if(i == 6){
                    res += "CN";
                    break;
                }
                res += "T" + (i + 2) + " ";
            }
        }
        if(count == 7){
            return "Hàng ngày";
        }
        else{
            if(count == 0) return "Chọn ngày";
            if(monToFri) return "Thứ 2 đến thứ 6";
            else return res;
        }
    }



}

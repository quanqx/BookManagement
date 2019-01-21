package com.example.quand.bookmanagement.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.quand.bookmanagement.Entities.Alarm;
import com.example.quand.bookmanagement.Entities.Book;
import com.example.quand.bookmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public static String NAME = "BookManagement0";

    public DBManager(Context context) {
        super(context, NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table if not exists Book(" +
                "id integer primary key autoincrement," +
                "title text," +
                "author text," +
                "publisher text," +
                "category text," +
                "image blob," +
                "description text)";
        sqLiteDatabase.execSQL(sql);
        sql = "Create table if not exists Alarm(" +
                "id integer primary key autoincrement," +
                "hour integer," +
                "minute integer," +
                "turnOn boolean," +
                "vibrate boolean," +
                "ring integer," +
                "idBook integer," +
                "dayOfWeek text)";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL("Insert into Book values(null, 'truyện kiều', 'Nguyễn Du', 'nxb 1', ' category 1', null, 'đay là mô tả sách')");
        sqLiteDatabase.execSQL("Insert into Book values(null, 'cấu trúc dữ liệu', 'Nguyễn A', 'nxb 2', ' category 2', null, 'des 2')");
        sqLiteDatabase.execSQL("Insert into Book values(null, 'sao xa xôi', 'Minh Khuê', 'nxb 3', ' category 3', null, 'des 3')");
////        sqLiteDatabase.execSQL("Insert into Alarm values(null, 10, 30, 0, 0, " + R.raw.ring + ", 0, 'false false true false true false false')");
////        sqLiteDatabase.close();
    }

    public List<Alarm> getAllAlarm(){
        List<Alarm> result = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "Select * from Alarm";
        Cursor cur = sqLiteDatabase.rawQuery(sql, null);
        if(cur != null && cur.moveToFirst()){
            do {
                Alarm alarm = new Alarm();
                alarm.setId(cur.getInt(0));
                alarm.setHour(cur.getInt(1));
                alarm.setMinute(cur.getInt(2));
                alarm.setOn(cur.getInt(3) == 1 ? true : false);
                alarm.setVibrate(cur.getInt(4) == 1 ? true : false);
                alarm.setRing(cur.getInt(5));
                alarm.setId_book(cur.getInt(6));
                alarm.setDayOfWeek(stringToBooleanArray(cur.getString(7)));
                result.add(alarm);
            }
            while(cur.moveToNext());
        }
        cur.close();
        sqLiteDatabase.close();
        return result;
    }

    private boolean[] stringToBooleanArray(String str){
        String []arr = str.split(" ");
        boolean []res = new boolean[7];
        for (int i = 0; i < 7; i++){
            res[i] = Boolean.parseBoolean(arr[i]);
        }
        return res;
    }

    private String booleanArrayToString(boolean []arr){
        StringBuilder str = new StringBuilder("");
        for (int i = 0; i < 7; i++){
            str.append(arr[i]).append(" ");
        }
        return new String(str).trim();
    }

    public Alarm getAlarmById(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "Select * from Alarm where id = " + id;
        Cursor cur = sqLiteDatabase.rawQuery(sql, null);
        Alarm alarm = null;
        if(cur != null && cur.moveToFirst()){
            alarm = new Alarm();
            alarm.setId(cur.getInt(0));
            alarm.setHour(cur.getInt(1));
            alarm.setMinute(cur.getInt(2));
            alarm.setOn(cur.getInt(3) == 1 ? true : false);
            alarm.setVibrate(cur.getInt(4) == 1 ? true : false);
            alarm.setRing(cur.getInt(5));
            alarm.setId_book(cur.getInt(6));
            alarm.setDayOfWeek(stringToBooleanArray(cur.getString(7)));
        }
        cur.close();
        sqLiteDatabase.close();
        return alarm;
    }

    public void addAlarm(Alarm alarm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hour", alarm.getHour());
        values.put("minute", alarm.getMinute());
        values.put("ring", alarm.getRing());
        values.put("turnOn", alarm.isOn());
        values.put("vibrate", alarm.isVibrate());
        values.put("dayOfWeek", booleanArrayToString(alarm.getDayOfWeek()));
        values.put("idBook", alarm.getId_book());
        db.insert("Alarm",null,values);
        db.close();
    }

    public int editAlarm(Alarm alarm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hour", alarm.getHour());
        values.put("minute", alarm.getMinute());
        values.put("ring", alarm.getRing());
        values.put("turnOn", alarm.isOn());
        values.put("vibrate", alarm.isVibrate());
        values.put("dayOfWeek", booleanArrayToString(alarm.getDayOfWeek()));
        values.put("idBook", alarm.getId_book());
        int res = db.update("Alarm",values,"id = ?",new String[] { String.valueOf(alarm.getId())});
        db.close();
        return res;
    }

    public void deleteAlarm(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from Alarm where id = " + id );
        sqLiteDatabase.close();
    }
    public List<Book> getAllBook(){
        List<Book> result = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "Select * from Book";
        Cursor cur = sqLiteDatabase.rawQuery(sql, null);
        while (cur.moveToNext())
        {
            Book book = new Book();
            book.setId(cur.getInt(0));
            book.setTitle(cur.getString(1));
            book.setAuthor(cur.getString(2));
            book.setPublisher(cur.getString(3));
            book.setCategory(cur.getString(4));
            book.setImage(cur.getBlob(5));
            book.setDescription(cur.getString(6));
            result.add(book);
        }

        cur.close();
        sqLiteDatabase.close();
        return result;
    }

    public Book getBookById(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "Select * from Book where id = " + id;
        Log.i("id_get", "id: " + id);
        Cursor cur = sqLiteDatabase.rawQuery(sql, null);
        Log.i("id_get", "id: " + cur.getCount());
        Book book = null;
        if(cur.moveToNext()){
            book = new Book();
            book.setId(cur.getInt(0));
            book.setTitle(cur.getString(1));
            book.setAuthor(cur.getString(2));
            book.setPublisher(cur.getString(3));
            book.setCategory(cur.getString(4));
            book.setImage(cur.getBlob(5));
            book.setDescription(cur.getString(6));
        }
        cur.close();
        sqLiteDatabase.close();
        return book;
    }

    public long addBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put("publisher", book.getPublisher());
        values.put("category", book.getCategory());
        values.put("image", book.getImage());
        values.put("description", book.getDescription());
        long re  = db.insert("Book",null,values);
        db.close();
        return re;
    }

    public int editBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put("publisher", book.getPublisher());
        values.put("category", book.getCategory());
        values.put("image", book.getImage());
        values.put("description", book.getDescription());
        int re = db.update("Book",values,"id = ?",new String[] { String.valueOf(book.getId())});
        db.close();
        return re;
    }


    public void deleteBook(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from Book where id = " + id );
        sqLiteDatabase.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists Book");
        sqLiteDatabase.execSQL("Drop table if exists Alarm");
    }
}

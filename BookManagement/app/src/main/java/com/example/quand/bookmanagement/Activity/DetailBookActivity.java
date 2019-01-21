package com.example.quand.bookmanagement.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quand.bookmanagement.Entities.Book;
import com.example.quand.bookmanagement.R;

import java.io.ByteArrayOutputStream;

public class DetailBookActivity extends Activity {

    private ImageView imgBookDetail;
    private TextView txtTenSach;
    private TextView txtTacGia;
    private TextView txtNXB;
    private TextView txtTheLoai;
    private TextView txtMoTa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        imgBookDetail = (ImageView) findViewById(R.id.imgBookDetail);
        txtTenSach = (TextView) findViewById(R.id.txtTenSach);
        txtTacGia = (TextView) findViewById(R.id.txtTacGia);
        txtNXB = (TextView) findViewById(R.id.txtNXB);
        txtTheLoai = (TextView) findViewById(R.id.txtTheLoai);
        txtMoTa = (TextView) findViewById(R.id.txtMoTa);
        Bundle bundle = getIntent().getExtras();
        Book book = new Book();
        book = (Book) bundle.getSerializable("book");
        txtTenSach.setText(book.getTitle());
        txtTacGia.setText(book.getAuthor());
        txtNXB.setText(book.getPublisher());
        txtTheLoai.setText(book.getCategory());
        txtMoTa.setText(book.getDescription());
        byte[] byteImg = book.getImage();
        try {
            Bitmap bitmap = getImage(byteImg);
            imgBookDetail.setImageBitmap(bitmap);
        } catch (Exception e) {

        }
    }

    public byte[] getBytes() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgBookDetail.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}


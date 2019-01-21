package com.example.quand.bookmanagement.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quand.bookmanagement.Entities.Book;
import com.example.quand.bookmanagement.Fragment.BookFragment;
import com.example.quand.bookmanagement.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class EditBookActivity extends Activity {
    private final int REQUEST_CODE_SELECT_IMG = 100;
    private final int REQUEST_CODE_CAMERA = 101;
    private Button btnSelect, btnSave, btnCancel , btnCamera;
    private ImageView img;
    private EditText editNameBook, editAuthor, editPublisher, editCategory;
    private byte[] byteImg;
   // private Bitmap bitmap ;
    private Book book;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        init();

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFromCamera();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book.setAuthor(editAuthor.getText().toString().trim());
                book.setCategory(editCategory.getText().toString().trim());
                book.setPublisher(editPublisher.getText().toString().trim());
                book.setTitle(editNameBook.getText().toString().trim());
                book.setImage(getBytes());
                if (BookFragment.db.editBook(book) != -1)
                    Toast.makeText(EditBookActivity.this,"Successfully",Toast.LENGTH_SHORT).show();
                else Toast.makeText(EditBookActivity.this,"Fail",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMG && resultCode == RESULT_OK && data != null) {

            try {
                Uri selectedimg = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(selectedimg);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);

        }
    }

    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Choose Picture"), REQUEST_CODE_SELECT_IMG);
    }

    public void selectImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public void init() {
        btnSelect = (Button) findViewById(R.id.btnSelectImg);
        btnCamera = (Button) findViewById(R.id.btnCamera);
        btnSave = (Button) findViewById(R.id.btnOkBook);
        btnCancel = (Button) findViewById(R.id.btnCancelBook);
        img = (ImageView) findViewById(R.id.imgSelectBook);
        editNameBook = (EditText) findViewById(R.id.editNameBook);
        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editPublisher = (EditText) findViewById(R.id.editPublisher);
        editCategory = (EditText) findViewById(R.id.editCategory);

        Bundle bundle = getIntent().getExtras();
        book = (Book) bundle.getSerializable("dulieune");
        editNameBook.setText(book.getTitle());
        editAuthor.setText(book.getAuthor());
        editPublisher.setText(book.getPublisher());
        editCategory.setText(book.getCategory());
        byteImg = book.getImage();
        try {
            Bitmap bitmap = getImage(byteImg) ;
            img.setImageBitmap(bitmap);
        } catch (Exception e) {

        }

    }

    public byte[] getBytes() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}



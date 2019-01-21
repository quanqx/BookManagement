package com.example.quand.bookmanagement.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.quand.bookmanagement.Activity.AddAlarm;
import com.example.quand.bookmanagement.Activity.AddBookActivity;
import com.example.quand.bookmanagement.Activity.DetailBookActivity;
import com.example.quand.bookmanagement.Activity.EditBookActivity;
import com.example.quand.bookmanagement.Adapter.BookAdapter;
import com.example.quand.bookmanagement.Database.DBManager;
import com.example.quand.bookmanagement.Entities.Book;
import com.example.quand.bookmanagement.R;

import java.util.ArrayList;

public class BookFragment extends Fragment {

    private ListView listView;
    private EditText edtSearch;
    private ImageView imageSearch;
    private BookAdapter bookAdapter;
    private ArrayList<Book> listBook;
    public static DBManager db;
    private Dialog dialog;
    private int position;
    private FloatingActionButton fabAdd;
    private boolean isItemLongClick = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBManager(getContext());
//        db.addBook(new Book("Lập trình cơ bản", "Nguyễn A", "nxb 1", "category 1", null, "des 1"));
//        db.addBook(new Book("android application", "Lê B", "nxb 2", "category 2", null, "des 2"));
//        db.addBook(new Book("Toán 1", "Trần C", "nxb 3", "category 3", null, "des 3"));

    }

    @Override
    public void onResume() {
        super.onResume();
        listBook = (ArrayList<Book>) db.getAllBook();
        dataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_book, container, false);
        Mapping(view);

        listBook = (ArrayList<Book>) db.getAllBook();

        bookAdapter = new BookAdapter(getContext(), listBook, db);

        listView.setAdapter(bookAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isItemLongClick) {
                    return;
                }
                Intent intent = new Intent(getContext(), DetailBookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", listBook.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {
                isItemLongClick = true;
                showDialog(i);
                onPause();
                position = i;
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bookAdapter.getFilter().filter(charSequence);
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , AddBookActivity.class) ;
                startActivity(intent);
            }
        });
        return view;
    }

    private void Mapping(View view) {
        fabAdd = view.findViewById(R.id.fab_add_book);
        listView = (ListView) view.findViewById(R.id.listBook);
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        imageSearch = (ImageView) view.findViewById(R.id.imageSearch);
        //edtSearch.setFocusable(false);
    }

    public void showDialog(final int i) {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_book);

        dialog.findViewById(R.id.btnSetReminder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddAlarm.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", listBook.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook();
            }
        });
        dialog.findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBook();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isItemLongClick = false;
            }
        });

        dialog.show();
    }

    public void deleteBook() {
        dialog.dismiss();
        showConfirmDialog();

    }
    public void showConfirmDialog() {
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
                db.deleteBook(listBook.get(position).getId());
                listBook.remove(position);
                dataSetChanged();
                dialogInterface.dismiss();
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void editBook() {
        dialog.dismiss();
        Intent intent = new Intent(getContext(), EditBookActivity.class);
        Bundle bundle = new Bundle();
        Book book = listBook.get(position);
        bundle.putSerializable("dulieune", book);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void dataSetChanged() {
        bookAdapter.setData(listBook);
        bookAdapter.notifyDataSetChanged();
    }
}

package com.example.quand.bookmanagement.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.quand.bookmanagement.Database.DBManager;
import com.example.quand.bookmanagement.Entities.Book;
import com.example.quand.bookmanagement.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<Book> books;
    private List<Book> dataBackup;
    private DBManager db;
    private LayoutInflater layoutInflater;

    public void setData(List<Book> books) {
        this.books = books;
    }

    public BookAdapter(Context context, List<Book> books, DBManager db) {
        this.context = context;
        this.books = books;
        this.db = db;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_book, null);
            holder = new ViewHolder();
            // holder.flagView = (ImageView) view.findViewById(R.id.imageView_flag);
            holder.txtNameOfBook = (TextView) view.findViewById(R.id.txtNameOfBook);
            holder.txtNameOfAuthor = (TextView) view.findViewById(R.id.txtNameOfAuthor);
            holder.imgBook = (ImageView) view.findViewById(R.id.imgBook);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Book book = books.get(i);
        holder.txtNameOfBook.setText(book.getTitle());
        holder.txtNameOfAuthor.setText(book.getAuthor());
        holder.imgBook.setImageResource(R.drawable.book_cover);
        try {
            holder.imgBook.setImageBitmap(getImage(book.getImage()));
        }
        catch (Exception e) {
            holder.imgBook.setImageResource(R.drawable.book_cover);
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults fr = new FilterResults();
                if(dataBackup==null) dataBackup=new ArrayList<>(books);
                if(charSequence==null || charSequence.length()==0){
                    fr.values=dataBackup;
                    fr.count=dataBackup.size();
                }
                else{
                    ArrayList<Book> newdata = new ArrayList<>();
                    for(Book b:books)
                        if(b.getFilter().toLowerCase().contains(charSequence.toString().toLowerCase()))
                            newdata.add(b);
                    fr.values=newdata;
                    fr.count=newdata.size();
                }
                return fr;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                books = (ArrayList<Book>)filterResults.values;
                notifyDataSetChanged();
            }
        };
        return f;
    }

    static class ViewHolder {
        TextView txtNameOfBook;
        TextView txtNameOfAuthor;
        ImageView imgBook;
    }

    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}

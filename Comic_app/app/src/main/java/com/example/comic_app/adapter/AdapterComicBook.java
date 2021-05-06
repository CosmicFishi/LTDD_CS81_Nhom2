package com.example.comic_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comic_app.R;
import com.example.comic_app.model.ComicBook;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AdapterComicBook extends ArrayAdapter<ComicBook> {
    private int resourceLayout;
    private Context mContext;

    public AdapterComicBook(@NonNull Context context, int resource, @NonNull List<ComicBook> objects) {
        super(context, resource, objects);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        ComicBook comicBook = getItem(position);

        if (comicBook != null) {
            ImageView img_comic = (ImageView) v.findViewById(R.id.img_comic);
            TextView txt_author = (TextView) v.findViewById(R.id.txt_author);
            TextView txt_chapters = (TextView) v.findViewById(R.id.txt_chapters);
            TextView txt_status = (TextView) v.findViewById(R.id.txt_status);
            TextView txt_view = (TextView) v.findViewById(R.id.txt_view);
            TextView txt_book_name = (TextView) v.findViewById(R.id.txt_book_name);

            if (img_comic != null) {}
            if (txt_view != null) {}

            if (txt_author != null) {
                txt_author.setText(comicBook.getAuthor());
            }
            if (txt_chapters != null) {
                txt_chapters.setText(comicBook.getLength());
            }
            if (txt_status != null) {
                txt_status.setText(comicBook.getStatus());
            }
            if (txt_book_name != null) {
                txt_book_name.setText(comicBook.getTitle());
            }
        }

        return v;
    }
}

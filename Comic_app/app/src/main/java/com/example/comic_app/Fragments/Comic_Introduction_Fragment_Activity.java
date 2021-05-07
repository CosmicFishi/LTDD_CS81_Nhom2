package com.example.comic_app.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.comic_app.R;
import com.example.comic_app.Utils;
import com.example.comic_app.model.ComicBook;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Comic_Introduction_Fragment_Activity extends Fragment {

    private Bundle bundle;
    private ArrayList<String> currentChapterList = new ArrayList<>();
    FirebaseFirestore db;
    FirebaseStorage storage;
    Button btn_add_fav, btn_view, btn_view_first_chapter;
    ImageView imageView;
    TextView txt_name, txt_author, txt_view, txt_status, txt_chapters, txt_description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View comic_introduction_view = inflater.inflate(R.layout.comic_introduction_page,container,false);

        bindUI(comic_introduction_view);
        bundle = this.getArguments();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        populateUI(bundle.getString("comic_id"));

        //thêm vào fav
        btn_add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //đọc truyện
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("comic_id", bundle.getString("comic_id"));
                b.putStringArrayList("chapterList", bundle.getStringArrayList("chapter_list"));

                Fragment chapter_list_page = new Comic_ChapterList_Fragment_Activity();
                chapter_list_page.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, chapter_list_page).addToBackStack(null).commit();
            }
        });

        btn_view_first_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("comic_id", bundle.getString("comic_id"));
                b.putStringArrayList("chapter_list", bundle.getStringArrayList("chapter_list"));
                b.putInt("chapter_list_length", bundle.getStringArrayList("chapter_list").size());
                b.putInt("comic_num", 0);

                Fragment read_page = new Comic_Read_Fragment_Activity();
                read_page.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, read_page).addToBackStack(null).commit();
            }
        });


        return comic_introduction_view;
    }

    private void populateUI(String comic_document) {
        DocumentReference dr = db.collection("comic_book").document(comic_document);
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //Ngừa trường hợp người dùng chuyển fragment khác
                if(getActivity() == null) {
                    return;
                } else {
                    if(task.isSuccessful()) {
                        ComicBook comicBook = new ComicBook();
                        DocumentSnapshot document = task.getResult();
//                        comicBook = task.getResult().toObject(ComicBook.class);
                        comicBook.setChapterList((List<String>) document.get("chapterList"));
                        comicBook.setAuthor((String)document.get("author"));
                        comicBook.setCategory((List<Integer>)document.get("category"));
                        comicBook.setImage((String)document.get("image"));
                        comicBook.setLength((String)document.get("length"));
                        comicBook.setStatus((String)document.get("status"));
                        comicBook.setSummary((String)document.get("summary"));
                        comicBook.setTitle((String)document.get("title"));

                        bundle.putStringArrayList("chapter_list",new ArrayList(comicBook.getChapterList()));

                        StorageReference sr = storage.getReference();
                        sr.child("images/" + comicBook.getImage()).getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(getContext()).load(uri).into(imageView);
                                    }
                                });
                        setData(comicBook);
                    }
                }

            }
        });
    }
    private void bindUI(View view) {
        imageView = (ImageView)view.findViewById(R.id.img_c_thumbnail);
        txt_author = (TextView) view.findViewById(R.id.txt_c_author);
        txt_name = (TextView) view.findViewById(R.id.txt_c_name);
        txt_view = (TextView) view.findViewById(R.id.txt_c_views);
        txt_chapters = (TextView)view.findViewById(R.id.txt_c_chapters);
        txt_status = (TextView)view.findViewById(R.id.txt_c_status);
        txt_description = (TextView)view.findViewById(R.id.txt_c_description);
        btn_add_fav = (Button)view.findViewById(R.id.btn_add_fav);
        btn_view = (Button)view.findViewById(R.id.btn_view_chapter);
        btn_view_first_chapter = (Button)view.findViewById(R.id.btn_view_first_chapter);
    }
    private void setData(ComicBook comicBook) {
        txt_name.setText(comicBook.getTitle());
        txt_author.setText(comicBook.getAuthor());
        txt_description.setText(comicBook.getSummary());
        txt_status.setText(comicBook.getStatus());
        txt_chapters.setText(comicBook.getLength());
    }
}
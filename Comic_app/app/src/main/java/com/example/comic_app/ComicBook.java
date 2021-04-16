package com.example.comic_app;

import java.util.ArrayList;
import java.util.List;

public class ComicBook {
    private List<String> chapterList = new ArrayList<>();
    private String author;
    private int category;
    private String image;
    private String status;
    private String summary;
    private String title;

    public List getChapterList() {
        return chapterList;
    }

    public void setChapterList(List chapterList) {
        this.chapterList = chapterList;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
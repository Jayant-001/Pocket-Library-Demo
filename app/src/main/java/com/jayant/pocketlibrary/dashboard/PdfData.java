package com.jayant.pocketlibrary.dashboard;

public class PdfData {

    String name;
    String url;
    int view;

    public PdfData() {
    }

    public PdfData(String name, String url, int view) {
        this.name = name;
        this.url = url;
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}

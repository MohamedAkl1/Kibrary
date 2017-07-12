package com.example.akl.kibrary;

/**
 * Created by Mohamed Akl on 7/1/2017.
 */

public class Book {
    private String name;
    private String mAssetPath;
    private Integer mBookId;

    public Book(String assetPath){
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length-1];
        name = filename.replace(".pdf","");
    }

    public String getAssetPath(){
        return mAssetPath;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getmBookId() {
        return mBookId;
    }

    public void setmBookId(Integer mBookId) {
        this.mBookId = mBookId;
    }
}

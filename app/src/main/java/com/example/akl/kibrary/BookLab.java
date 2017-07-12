package com.example.akl.kibrary;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mohamed Akl on 7/9/2017.
 */

public class BookLab {

    private static final String TAG = "BeatBox";
    private static final String SAMPLE_BOOKS = "sample_books";

    private static BookLab sBookLab;
    private List<Book> mBooks;
    AssetManager assetManager;

    public static BookLab get(Context context) throws IOException {
        if(sBookLab == null){
            sBookLab = new BookLab(context);
        }
        return sBookLab;
    }

    public BookLab(Context context) throws IOException {
        mBooks = new ArrayList<>();

        assetManager = context.getAssets();
        loadBooks(context);
        }

    public Book getBook(String name){
        for(Book book : mBooks){
            if(book.getName().equals(name)){
                return book;
            }
        }
        return null;
    }

    private void loadBooks(Context c){
        String[] bookNames;
        try {
            bookNames = assetManager.list(SAMPLE_BOOKS);
            Log.i(TAG,"found "+bookNames.length +" books");
        }catch (IOException ioe){
            Log.e(TAG,"Could not list assets",ioe);
            return;
        }

        for(String filename : bookNames){
            String assetPath = SAMPLE_BOOKS + "/" + filename;
            Book book = new Book(assetPath);
            mBooks.add(book);
        }
    }

    private void load(Book book) throws IOException{
        AssetFileDescriptor afd = assetManager.openFd(book.getAssetPath());
    }

    public List<Book> getBooks(){
        return mBooks;
    }
}

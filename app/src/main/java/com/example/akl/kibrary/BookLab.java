package com.example.akl.kibrary;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mohamed Akl on 7/9/2017.
 */

public class BookLab {
    private static BookLab sBookLab;
    private List<Book> mBooks;

    public static BookLab get(Context context) throws IOException {
        if(sBookLab == null){
            sBookLab = new BookLab(context);
        }
        return sBookLab;
    }

    public BookLab(Context context) throws IOException {
        mBooks = new ArrayList<>();
        String[] files = context.getAssets().list("");
        }
    }

    public List<Book> getBooks(){
        return mBooks;
    }

    public Book getBook(String name){
        for(Book book : mBooks){
            if(book.name.equals(name)){
                return book;
            }
        }
        return null;
    }
}

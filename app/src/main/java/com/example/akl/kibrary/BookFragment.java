package com.example.akl.kibrary;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Mohamed Akl on 7/10/2017.
 */

public class BookFragment extends Fragment {

    private Book currentBook;
    private ParcelFileDescriptor mFileDescriptor;
    private PDFView pdfView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_view,container,false);
        try {
            pdfView.fromFile(new BookLab(getActivity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v;
    }
}

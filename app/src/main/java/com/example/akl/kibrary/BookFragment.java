package com.example.akl.kibrary;

import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private PdfRenderer mPdfRenderer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_view,container,false);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            currentBook.name = bundle.getString("BOOK_NAME");
        }
        File file = new File(getContext().getCacheDir(),currentBook.name);
        if(!file.exists()) {
            try{

                InputStream asset = getContext().getAssets().open(currentBook.name);
                FileOutputStream output = new FileOutputStream(file);
                final byte[] buffer = new byte[1024];
                int size;
                while ((size = asset.read(buffer)) != -1){
                output.write(buffer,0,size);
                }
                asset.close();
                output.close();

                mFileDescriptor = ParcelFileDescriptor.open(file,ParcelFileDescriptor.MODE_READ_ONLY);
                if(mFileDescriptor != null){
                 mPdfRenderer = new PdfRenderer(mFileDescriptor);
                }
            }catch (Exception e){

            }



        }


        return v;
    }
}

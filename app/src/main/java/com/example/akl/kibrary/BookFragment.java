package com.example.akl.kibrary;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;

import static android.content.ContentValues.TAG;

/**
 * Created by Mohamed Akl on 7/10/2017.
 */

public class BookFragment extends Fragment {

    private Book currentBook;
    private ParcelFileDescriptor mFileDescriptor;
    private PDFView pdfView;
    private File book;
    private Bundle bundle;
    private String bookName;
    private String bookPath;
    private PdfRenderer pdfRenderer;
    ParcelFileDescriptor parcelFileDescriptor;
    private Integer pageNumber = 0;

    private final static int REQUEST_CODE = 42;
    public static final int PERMISSION_CODE = 42042;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources resources = getResources();
        XmlPullParser xmlPullParser = resources.getXml(R.xml.fragment_book_view);
        AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);

        bundle = new Bundle();
        bundle = getArguments();
        bookName = bundle.getString("BOOK_NAME");
        bookPath = bundle.getString("BOOK_PATH");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_view,container,false);
        book = new File(bookPath);

        pdfView = (PDFView) v.findViewById(R.id.pdfView);
        pdfView.fromAsset(bookName).defaultPage(pageNumber).onPageChange(new OnPageChangeListener() {
            @Override
            public void onPageChanged(int page, int pageCount) {
                pageNumber = page;

            }
        }).enableAnnotationRendering(true).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {
                com.shockwave.pdfium.PdfDocument.Meta meta = pdfView.getDocumentMeta();
                Log.e(TAG, "title = " + meta.getTitle());
                Log.e(TAG, "author = " + meta.getAuthor());
                Log.e(TAG, "subject = " + meta.getSubject());
                Log.e(TAG, "keywords = " + meta.getKeywords());
                Log.e(TAG, "creator = " + meta.getCreator());
                Log.e(TAG, "producer = " + meta.getProducer());
                Log.e(TAG, "creationDate = " + meta.getCreationDate());
                Log.e(TAG, "modDate = " + meta.getModDate());


            }
        }).onRender(new OnRenderListener() {
            @Override
            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                pdfView.fitToWidth();
            }
        }).scrollHandle(new DefaultScrollHandle(getContext())).load();
        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                launchPicker();
            }
        }
    }

    void launchPicker(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        try {
            startActivityForResult(intent,REQUEST_CODE);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
        }
    }


}

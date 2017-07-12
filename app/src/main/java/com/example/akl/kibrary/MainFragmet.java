package com.example.akl.kibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

/**
 * Created by Mohamed Akl on 7/1/2017.
 */

public class MainFragmet extends Fragment {

    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private Book publicBook;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            BookLab bookLab = new BookLab(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main,container,false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            updateUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v;
    }

    public class BookHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        private Book mBook;

        public BookHolder(View itemView){
            super(itemView);
            mTextView = (TextView)itemView;
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Fragment fragmento;
                    fragmento = new BookFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("BOOK_NAME", (String) mTextView.getText());
                    fragmento.setArguments(bundle);
                    fm.beginTransaction().replace(R.id.main_fragment_container,fragmento).addToBackStack(null).commit();
                }
            });
        }

        public void bindBook(Book book){
            mBook = book;
            mTextView.setText(mBook.getName());
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<BookHolder>{
        private List<Book> mBooks;
        public ListAdapter(List<Book> books){
            mBooks = books;
        }

        @Override
        public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            return new BookHolder(view);
        }

        @Override
        public void onBindViewHolder(BookHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.mTextView.setText(book.getName());
            
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }
    }

    private void updateUI() throws IOException {
        BookLab bookLab = BookLab.get(getActivity());
        List<Book> books = bookLab.getBooks();

        mAdapter = new ListAdapter(books);
        mRecyclerView.setAdapter(mAdapter);
    }
}

package com.example.akl.kibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main,container,false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    public class ListHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;

        public ListHolder(View itemView){
            super(itemView);
            mTextView = (TextView)itemView;
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new BookFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("BOOK_NAME",mTextView.getText().toString());
                    fragment.setArguments(bundle);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.main_fragment_container,fragment).addToBackStack(null).commit();
                }
            });
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<ListHolder>{
        private List<Book> mBooks;
        public ListAdapter(List<Book> books){
            mBooks = books;
        }

        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(ListHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.mTextView.setText(book.name);
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }
    }

    private void updateUI(){
        BookLab bookLab = BookLab.get(getActivity());
        List<Book> books = bookLab.getBooks();

        mAdapter = new ListAdapter(books);
        mRecyclerView.setAdapter(mAdapter);
    }
}

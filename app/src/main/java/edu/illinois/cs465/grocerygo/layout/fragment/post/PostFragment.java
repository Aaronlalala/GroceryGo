package edu.illinois.cs465.grocerygo.layout.fragment.post;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs465.grocerygo.R;

public class PostFragment extends Fragment {

    public RecyclerView postRecyclerView;
    public RecyclerView.LayoutManager myLayoutManager;
    public PostAdapter myPostAdapter;
    public List<PostData> postList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.post_fragment, container, false);
        initDataset();
//        Drawable clock = getResources().getDrawable(R.drawable.clock);
//        clock.setBounds(0,  0, 10, 10);
        View rootView = inflater.inflate(R.layout.post_fragment, container, false);
        postRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        myLayoutManager = new LinearLayoutManager(getActivity());
        postRecyclerView.setLayoutManager(myLayoutManager);
        myPostAdapter = new PostAdapter(this.postList);
        postRecyclerView.setAdapter(myPostAdapter);

        return  rootView;
    }

    private void initDataset() {
        this.postList = new ArrayList<>();
        PostData pd = new PostData("iris", "11:00 am Oct. 23rd", "I want somebody to join me", "Walmart", "3.3 m");
        this.postList.add(pd);
    }
}

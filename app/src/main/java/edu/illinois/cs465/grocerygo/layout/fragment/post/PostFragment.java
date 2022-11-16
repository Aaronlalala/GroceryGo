package edu.illinois.cs465.grocerygo.layout.fragment.post;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.activity.MainActivity;
import edu.illinois.cs465.grocerygo.layout.activity.OngoingActivity;

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
        View rootView = inflater.inflate(R.layout.post_fragment, container, false);
        Button button = (Button) postRecyclerView.findViewById(R.id.theTime);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, OngoingActivity.class);
        });

        postRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        myLayoutManager = new LinearLayoutManager(getActivity());

        myPostAdapter = new PostAdapter(this.postList, getContext());

        postRecyclerView.setAdapter(myPostAdapter);
        postRecyclerView.setLayoutManager(myLayoutManager);

        return  rootView;
    }

    private void initDataset() {
        this.postList = new ArrayList<>();
        //PostData pd2 = new PostData(R.drawable.girl, "12:12 pm Oct. 2rd", "Plan to go tomorrow", "Costco", " 5.9 m");
        PostData pd2 = new PostData(R.drawable.man, "Jake", "12:12 pm Oct. 2rd", "Plan to go tomorrow", "Costco", " 5.3 m");
        PostData pd = new PostData(R.drawable.girl, "iris", "11:00 am Oct. 23rd", "I want somebody to join me", "Walmart", " 3.3 m");
        this.postList.add(pd2);
        this.postList.add(pd);
    }
}

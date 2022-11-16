package edu.illinois.cs465.grocerygo.layout.fragment.post;

import static edu.illinois.cs465.grocerygo.constant.Constant.POST_FRAGMENT_TAG;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.activity.PostActivity;
import edu.illinois.cs465.grocerygo.layout.activity.PostDetailActivity;

public class PostFragment extends Fragment {

    public RecyclerView postRecyclerView;
    public RecyclerView.LayoutManager myLayoutManager;
    public PostAdapter myPostAdapter;
    public List<PostData> postList;
    String[] sortOptions = { "Sort by distance", "Sort by time"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.post_fragment, container, false);
        initDataset();
        View rootView = initView(inflater,container);
        return  rootView;
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.post_fragment, container, false);

        postRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        myLayoutManager = new LinearLayoutManager(getActivity());

        myPostAdapter = new PostAdapter(this.postList, getContext());

        postRecyclerView.setAdapter(myPostAdapter);
        postRecyclerView.setLayoutManager(myLayoutManager);

        myPostAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.d("tag","msg");
            }
        });

        Spinner sortDropdown = rootView.findViewById(R.id.sortDropdown);
        ArrayAdapter ad = new ArrayAdapter(
                            getContext(),
                            android.R.layout.simple_spinner_item,
                            this.sortOptions);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortDropdown.setAdapter(ad);

        sortDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if(pos==0){
                    postList.sort((a,b) -> {
                        int res;
                        if(a.distanceDouble - b.distanceDouble<0) res = -1;
                        else res = 1;
                        return res;
                    });
                    myPostAdapter.notifyDataSetChanged();
                    System.out.println("ssssssqqqqqqq"+postList.get(0).distanceDouble);
                }
                else{
                    System.out.println("ssssssqqqqqqq"+postList.get(0).distanceDouble);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        return  rootView;
    }

    private void initDataset() {
        this.postList = new ArrayList<>();
        //PostData pd2 = new PostData(R.drawable.girl, "12:12 pm Oct. 2rd", "Plan to go tomorrow", "Costco", " 5.9 m");
        PostData pd2 = new PostData(5.3, R.drawable.man, "Jake", "12:12 pm Oct. 2rd", "Plan to go tomorrow", "Costco", " 5.3 m");
        PostData pd = new PostData(3.3, R.drawable.girl, "iris", "11:00 am Oct. 23rd", "I want somebody to join me", "Walmart", " 3.3 m");
        this.postList.add(pd2);
        this.postList.add(pd);
    }
}

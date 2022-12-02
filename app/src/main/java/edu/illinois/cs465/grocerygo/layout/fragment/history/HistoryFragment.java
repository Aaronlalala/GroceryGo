package edu.illinois.cs465.grocerygo.layout.fragment.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.activity.ChatActivity;
import edu.illinois.cs465.grocerygo.layout.activity.RatingActivity;
import edu.illinois.cs465.grocerygo.layout.fragment.mail.MailRecyclerViewInterface;
import edu.illinois.cs465.grocerygo.layout.fragment.post.PostData;

public class HistoryFragment extends Fragment implements HistoryRecyclerViewInterface {
    public RecyclerView historyRecyclerView;
    public RecyclerView.LayoutManager myLayoutManager;
    public HistoryAdapter myHistoryAdapter;
    public ArrayList<HistoryData> historyData;

    String[] sortOptions = { "Sort by time", "Sort by distance"};
    private String activityType;
    private PostData myPost;
    public int month;
    public int day;

    public HistoryFragment() {};
    // FIXME: It seems we don't need a constructor if we don't reuse post fragment because we can
    //  directly send information. (l:91)
    public HistoryFragment(String activityType) {
        this.activityType = activityType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDataset();
        View rootView = inflater.inflate(R.layout.history_fragment, container, false);
        historyRecyclerView = rootView.findViewById(R.id.historyRecyclerView);
        myLayoutManager = new LinearLayoutManager(getActivity());
        myHistoryAdapter = new HistoryAdapter(this.historyData, getContext(), this);
        historyRecyclerView.setAdapter(myHistoryAdapter);
        historyRecyclerView.setLayoutManager(myLayoutManager);

        return rootView;
    }


    private void initDataset() {
        this.historyData = new ArrayList<>();
        HistoryData d1 = new HistoryData(R.drawable.man, "0.5", "Jake", "10-11 11:10", "Costco", 5);
        HistoryData d2 = new HistoryData(R.drawable.man, "1.0", "Iris", "10-19 13:12", "Walmart", 4);
        HistoryData d3 = new HistoryData(R.drawable.man, "`1.2", "Lucy", "11-20 13:12", "Walmart", 4);
        // Not rated history items
        HistoryData d4 = new HistoryData(R.drawable.man , "Oven", "09-25 13:12", "Walmart");
        HistoryData d5 = new HistoryData(R.drawable.man , "john", "12-08 13:12", "Walmart");
        this.historyData.add(d1);
        this.historyData.add(d2);
        this.historyData.add(d3);
        this.historyData.add(d4);
        this.historyData.add(d5);
    }

    @Override
    public void onItemClick(int position) { // Override onClick handler from recyclerView interface
        Intent intent = new Intent(getContext(), RatingActivity.class);
        intent.putExtra("activity", "history");
        startActivity(intent);
    }


}

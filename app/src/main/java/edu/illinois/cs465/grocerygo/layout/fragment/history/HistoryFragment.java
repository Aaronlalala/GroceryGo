package edu.illinois.cs465.grocerygo.layout.fragment.history;

import static edu.illinois.cs465.grocerygo.constant.Constant.NO_STARS;
import static edu.illinois.cs465.grocerygo.constant.Constant.UNRATED;

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
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.event.RateEvent;
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
        EventBus.getDefault().register(this);
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
        // Not rated history items
        HistoryData d1 = new HistoryData(R.drawable.profile , UNRATED, "Jake", "12-01 13:12", "Walmart", NO_STARS, false);
        HistoryData d2 = new HistoryData(R.drawable.man , UNRATED, "john", "11-30 13:12", "Walmart", NO_STARS, false);
        HistoryData d3 = new HistoryData(R.drawable.man, "0.5", "Oven", "10-11 11:10", "Costco", 5, true);
        HistoryData d4 = new HistoryData(R.drawable.man, "1.0", "Iris", "10-19 13:12", "Walmart", 3, true);
        HistoryData d5 = new HistoryData(R.drawable.man, "1.2", "Lucy", "11-20 13:12", "Walmart", 4, true);
        this.historyData.add(d1);
        this.historyData.add(d2);
        this.historyData.add(d3);
        this.historyData.add(d4);
        this.historyData.add(d5);
    }

    @Subscribe
    public void onRated(RateEvent rateEvent) {
        int position = rateEvent.position;
        float tips = rateEvent.tips;
        float rating = rateEvent.rating;
        historyData.get(position).isRated = true;
        historyData.get(position).tips = String.valueOf(tips);
        historyData.get(position).stars = rating;
        myHistoryAdapter.notifyItemChanged(position);
    }

    @Override
    public void onItemClick(int position) { // Override onClick handler from recyclerView interface
        Intent intent = new Intent(getContext(), RatingActivity.class);
        intent.putExtra("activity", "history");
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}

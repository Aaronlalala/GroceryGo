package edu.illinois.cs465.grocerygo.layout.fragment.post;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.event.PostEvent;
import edu.illinois.cs465.grocerygo.layout.activity.PostDetailActivity;
import edu.illinois.cs465.grocerygo.layout.dialog.TimePickerDialog;

public class PostFragment extends Fragment {

    public RecyclerView postRecyclerView;
    public RecyclerView.LayoutManager myLayoutManager;
    public PostAdapter myPostAdapter;
    public List<PostData> postList;
    String[] sortOptions = { "Sort by time", "Sort by distance"};
    private String isHistory;

    public PostFragment() {};
    public PostFragment(String isHistory) {
        this.isHistory = isHistory;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.post_fragment, container, false);
        initDataset();
        EventBus.getDefault().register(this);
        return initView(inflater,container);
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
                if (isHistory == null) {
                    intent.putExtra("activity", "post");
                } else {
                    intent.putExtra("activity", "history");
                }
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
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if(pos==0){
                    DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
                    postList.sort((a,b) -> {
                        Date aDate = null;
                        Date bDate = null;
                        try {
                            aDate = df.parse(a.time);
                            bDate = df.parse(b.time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return aDate.compareTo(bDate);
                    });
                    myPostAdapter.notifyDataSetChanged();
                }
                else{
                    postList.sort((a,b) -> {
                        int res;
                        if(a.distanceDouble - b.distanceDouble<0) res = -1;
                        else res = 1;
                        return res;
                    });
                    myPostAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        Button filterButton = rootView.findViewById(R.id.filter);
        filterButton.setOnClickListener(view -> {
            showFilterDialog();
        });

        return  rootView;
    }

    private void showFilterDialog(){
        AlertDialog.Builder customizeDialog = new AlertDialog.Builder(getContext());
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.filter_dialog,null);
        TextView datePicker = dialogView.findViewById(R.id.date_picker);
        TextView timePickerFrom = dialogView.findViewById(R.id.time_picker_from);
        TextView timePickerTo = dialogView.findViewById(R.id.time_picker_to);

        datePicker.setOnClickListener(view -> {
            //current date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            datePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        timePickerFrom.setOnClickListener(view -> {
            Dialog dialog = new TimePickerDialog(getContext(), R.style.myDialog, timePickerFrom);
            dialog.show();
        });
        timePickerTo.setOnClickListener(view -> {
            Dialog dialog = new TimePickerDialog(getContext(), R.style.myDialog, timePickerTo);
            dialog.show();
        });
//        customizeDialog.setTitle("Filter posts");
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        EditText destination = (EditText) dialogView.findViewById(R.id.destination);
                    }
                });
        customizeDialog.show();
    }

    private void initDataset() {
        this.postList = new ArrayList<>();
        //PostData pd2 = new PostData(R.drawable.girl, "12:12 pm Oct. 2rd", "Plan to go tomorrow", "Costco", " 5.9 m");
        PostData pd2 = new PostData(5.3, R.drawable.man, "Jake", "10-11 11:10", "Plan to go tomorrow", "Costco", " 5.3 m");
        PostData pd = new PostData(3.3, R.drawable.girl, "Iris", "10-12 13:12", "I want somebody to join me", "Walmart", " 3.3 m");
        PostData pd3 = new PostData(1.2, R.drawable.man, "John", "11-20 13:12", "Anyone want to join?", "Walmart", " 1.2 m");
        PostData pd4 = new PostData(6.0, R.drawable.girl, "Lucy", "09-25 13:12", "I prefer tips > <", "Walmart", " 6.0 m");
        PostData pd5 = new PostData(4.5, R.drawable.man, "Oven", "12-08 13:12", "Hang out with me!", "Walmart", " 4.5 m");

        this.postList.add(pd2);
        this.postList.add(pd);
        this.postList.add(pd3);
        this.postList.add(pd4);
        this.postList.add(pd5);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPostMsg(PostEvent postEvent) {
        PostData postData = new PostData(postEvent.distance, R.drawable.img, postEvent.name, postEvent.time, postEvent.remark, postEvent.destination, postEvent.distance + " m");
        this.postList.add(0,postData);
        myPostAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}

package edu.illinois.cs465.grocerygo.layout.fragment.post;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import androidx.annotation.ColorInt;
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
import java.util.stream.Collectors;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.constant.Constant;
import edu.illinois.cs465.grocerygo.event.DeleteEvent;
import edu.illinois.cs465.grocerygo.event.FilterEvent;
import edu.illinois.cs465.grocerygo.event.PostEvent;
import edu.illinois.cs465.grocerygo.layout.activity.PostDetailActivity;
import edu.illinois.cs465.grocerygo.layout.dialog.TimePickerDialog;

public class PostFragment extends Fragment {

    public RecyclerView postRecyclerView;
    public RecyclerView.LayoutManager myLayoutManager;
    public PostAdapter myPostAdapter;
    public List<PostData> postList;
    String[] sortOptions = { "Sort by time", "Sort by distance"};
    private String activityType;
    private PostData myPost;
    public int month = 0;
    public int day;
    public String filterDestination = "";
    public String filterDate = "";
    public String filterTime = "";
    public String filterDuration = "";

    public PostFragment() {};
    public PostFragment(String activityType) {
        this.activityType = activityType;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.post_fragment, container, false);
        initDataset();
        Log.d("tagCreate", "cccc");
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
                if (activityType == null) {
                    intent.putExtra("activity", "post");
                }
                else {
                    intent.putExtra("activity", "history");
                }
               if(postList.get(position).isMypost){
                    intent.putExtra("activity", "myPost");
                }
                PostData pd = postList.get(position);
                intent.putExtra("userName", pd.name);
                intent.putExtra("destination", pd.destination);
                intent.putExtra("startTime", pd.time);
                intent.putExtra("imageId", pd.imageId);
                intent.putExtra("distanceToDriver", pd.distance);
                intent.putExtra("remark", pd.remark);

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
                        if(a.isMypost || b.isMypost) return a.isMypost==true? -1 : 1;
                        return aDate.compareTo(bDate);
                    });
                    myPostAdapter.notifyDataSetChanged();
                }
                else{
                    postList.sort((a,b) -> {
                        int res;
                        if(a.distanceDouble - b.distanceDouble<0) res = -1;
                        else res = 1;
                        if(a.isMypost || b.isMypost) return a.isMypost==true? -1 : 1;
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
        EditText des = (EditText) dialogView.findViewById(R.id.destination);
        EditText dur = (EditText) dialogView.findViewById(R.id.duration);

        if(!filterDestination.isEmpty()) des.setText(filterDestination);
        if(!filterDate.isEmpty())datePicker.setText(filterDate);
        if(!filterTime.isEmpty())timePickerFrom.setText(filterTime);
        if(!filterDuration.isEmpty())dur.setText(filterDuration);
        //TextView timePickerTo = dialogView.findViewById(R.id.time_picker_to);

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
                            datePicker.setText( (monthOfYear + 1)+ "-" + dayOfMonth + "-" + year);
                            month = monthOfYear+1;
                            day = dayOfMonth;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        
        timePickerFrom.setOnClickListener(view -> {
            Dialog dialog = new TimePickerDialog(getContext(), R.style.myDialog, timePickerFrom);
            dialog.show();
        });


        Button clearBtn = dialogView.findViewById(R.id.clear);
        Button OKBtn = dialogView.findViewById(R.id.ok);
        Dialog d = customizeDialog.setView(dialogView).create();
        OKBtn.setOnClickListener(view -> {
            EditText destination = (EditText) dialogView.findViewById(R.id.destination);
            EditText duration = (EditText) dialogView.findViewById(R.id.duration);
            filterDestination = destination.getText().toString();
            filterDate = datePicker.getText().toString();
            filterTime = timePickerFrom.getText().toString();
            filterDuration = duration.getText().toString();
            String dateStr = "";
            String dateTimeStr = "";
            if(!timePickerFrom.getText().toString().isEmpty()){
                if(month != 0){
                    dateStr = month + "-" + day;
                    dateTimeStr = month + "-" + day + " " + timePickerFrom.getText();
                }
                else{
                    dateTimeStr = "10-10 " + timePickerFrom.getText();
                }
            }
            else if(month != 0){
                dateStr = month + "-" + day;
            }


            Log.d("tag5", dateTimeStr);
            EventBus.getDefault().post(new FilterEvent(dateStr, dateTimeStr, destination.getText().toString()));
            d.dismiss();
        });

        clearBtn.setOnClickListener(view -> {
            EditText destination = (EditText) dialogView.findViewById(R.id.destination);
            EditText duration = (EditText) dialogView.findViewById(R.id.duration);
            timePickerFrom.setText("");
            datePicker.setText("");
            destination.setText("");
            duration.setText("");
        });
        d.show();
    }

    private void initDataset() {
            this.postList = new ArrayList<>();
            //PostData pd2 = new PostData(R.drawable.girl, "12:12 pm Oct. 2rd", "Plan to go tomorrow", "Costco", " 5.9 m");
            PostData pd2 = new PostData(5.3, R.drawable.man, "Jake", "10-11 16:10", "Plan to go tomorrow", "Costco", " 5.3 m", false);
            PostData pd = new PostData(3.3, R.drawable.girl, "Iris", "10-12 13:12", "I want somebody to join me", "Walmart", " 3.3 m", false);
            PostData pd3 = new PostData(1.2, R.drawable.man, "John", "11-20 13:12", "Anyone want to join?", "Walmart", " 1.2 m", false);
            PostData pd4 = new PostData(6.0, R.drawable.girl, "Lucy", "10-12 15:12", "I prefer tips > <", "Walmart", " 6.0 m", false);
            PostData pd5 = new PostData(4.5, R.drawable.man, "Oven", "12-08 13:12", "Hang out with me!", "Walmart", " 4.5 m",false);
            PostData pd6 = new PostData(3.5, R.drawable.man, "Jake", "11-18 09:12", "Prepare to buy something for Thanksgiving", "Walmart", " 3.5 m",false);
            PostData pd7 = new PostData(2.5, R.drawable.girl, "Annie", "10-18 19:20", "Want to buy things for hot pot!", "Costco", " 2.5 m",false);

            this.postList.add(pd2);
            this.postList.add(pd);
            this.postList.add(pd3);
            this.postList.add(pd4);
            this.postList.add(pd5);
            this.postList.add(pd6);
            this.postList.add(pd7);

        if(Constant.myPost != null){
                this.postList.add(Constant.myPost);
            }
        Constant.postList = this.postList;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFilterMessage(FilterEvent fe) {
        DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
        List<PostData> result = new ArrayList<>();
        if(!fe.dateAndTime.isEmpty()){
            try {
                String targetDate = fe.date;
                Date targetTime = df.parse(fe.dateAndTime);
                String targetDes = fe.destination;

                if(!targetDate.isEmpty() && !targetDes.isEmpty()){
                    for (int i = 0; i < this.postList.size(); i++) {
                        PostData cur = this.postList.get(i);
                        String curDate = cur.time.split(" ")[0];
                        Date curDateAndTime = df.parse(cur.time);
                        int res = curDateAndTime.compareTo(targetTime);
                        if(res>0 && cur.destination.equals(targetDes) && curDate.equals(targetDate)){
                            result.add(this.postList.get(i));
                        }
                    }
                }
                else if(targetDate.isEmpty() && !targetDes.isEmpty()){
                    for (int i = 0; i < this.postList.size(); i++) {
                        PostData cur = this.postList.get(i);
                        String curDate = cur.time.split(" ")[0];
                        Date curDateAndTime = df.parse(cur.time);
                        int res = curDateAndTime.compareTo(targetTime);
                        if(res>0 && cur.destination.equals(targetDes)){
                            result.add(this.postList.get(i));
                        }
                    }
                }
                else if(!targetDate.isEmpty() && targetDes.isEmpty()){
                    for (int i = 0; i < this.postList.size(); i++) {
                        PostData cur = this.postList.get(i);
                        String curDate = cur.time.split(" ")[0];
                        Date curDateAndTime = df.parse(cur.time);
                        int res = curDateAndTime.compareTo(targetTime);
                        if(res>0 && curDate.equals(targetDate)){
                            result.add(this.postList.get(i));
                        }
                    }
                }

            }catch(ParseException e){
                e.printStackTrace();
            }
        }
        else{
            String targetDate = fe.date;
            String targetDes = fe.destination;
            if(!targetDate.isEmpty() && !targetDes.isEmpty()){
                for (int i = 0; i < this.postList.size(); i++) {
                    PostData cur = this.postList.get(i);
                    String curDate = cur.time.split(" ")[0];
                    if(cur.destination.equals(targetDes) && curDate.equals(targetDate)){
                        result.add(this.postList.get(i));
                    }
                }
            }
            else if(!targetDate.isEmpty() && targetDes.isEmpty()){
                for (int i = 0; i < this.postList.size(); i++) {
                    PostData cur = this.postList.get(i);
                    String curDate = cur.time.split(" ")[0];
                    if(curDate.equals(targetDate)){
                        result.add(this.postList.get(i));
                    }
                }
            }
            else if(targetDate.isEmpty() && !targetDes.isEmpty()){
                for (int i = 0; i < this.postList.size(); i++) {
                    PostData cur = this.postList.get(i);
                    String curDate = cur.time.split(" ")[0];
                    if(cur.destination.equals(targetDes)){
                        result.add(this.postList.get(i));
                    }
                }
            }
        }
        if(fe.destination.isEmpty() && fe.dateAndTime.isEmpty() && fe.date.isEmpty()){
            this.postList = Constant.postList;
            myPostAdapter.postList = Constant.postList;
            myPostAdapter.notifyDataSetChanged();
            return;
        }
        int a = result.size();
        Log.d("tagRes" , String.valueOf(a));
        this.postList = new ArrayList<>();
        for(int j=0; j< result.size(); j++){
            postList.add(result.get(j));
        }
        int b = this.postList.size();
        Log.d("tagResB" , String.valueOf(b));
        myPostAdapter.postList = this.postList;
        myPostAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPostMsg(PostEvent postEvent) {
        PostData postData = new PostData(postEvent.distance, R.drawable.img, postEvent.name, postEvent.time, postEvent.remark, postEvent.destination, postEvent.distance + " m", true);
        this.postList.add(0,postData);
        myPostAdapter.postList = this.postList;
        Constant.myPost = postData;

        myPostAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteMyPostMsg(DeleteEvent de) {
        if(de.deleteMypost){
            this.postList.remove(Constant.myPost);
            myPostAdapter.postList = this.postList;
            Constant.myPost = null;

            myPostAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}

package edu.illinois.cs465.grocerygo.layout.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.dialog.TimePickerDialog;

public class PostActivity extends AbstractActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        initView();
    }

    private void initView() {
        ImageView dismissBtn = findViewById(R.id.btn_dismiss);
        TextView postBtn = findViewById(R.id.post);
        TextView datePicker = findViewById(R.id.date_picker);
        TextView timePickerFrom = findViewById(R.id.time_picker_from);
        TextView timePickerTo = findViewById(R.id.time_picker_to);
        dismissBtn.setOnClickListener(view -> this.finish());
        postBtn.setOnClickListener(view -> {
            String fromTime = (String) timePickerFrom.getText();
            String toTime = (String) timePickerTo.getText();
            if (check(fromTime, toTime)) {
                this.finish();
            } else {
                Toast.makeText(this, R.string.time_toast, Toast.LENGTH_SHORT).show();
            }
        });

        datePicker.setOnClickListener(view -> {
            //current date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            datePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        timePickerFrom.setOnClickListener(view -> {
            Dialog dialog = new TimePickerDialog(this, R.style.myDialog, timePickerFrom);
            dialog.show();
        });
        timePickerTo.setOnClickListener(view -> {
            Dialog dialog = new TimePickerDialog(this, R.style.myDialog, timePickerTo);
            dialog.show();
        });
    }

    private boolean check(String from, String to) {
        if (from == null || to == null || from.equals("") || to.equals("")) {
            return false;
        }
        String[] fromTime = from.split(":");
        String[] toTime = to.split(":");
        int t1 = Integer.parseInt(fromTime[0]) * 60 + Integer.parseInt(fromTime[1]);
        int t2 = Integer.parseInt(toTime[0]) * 60 + Integer.parseInt(toTime[1]);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int curTime = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
        return t1 < t2 && t2 > curTime;
    }
}

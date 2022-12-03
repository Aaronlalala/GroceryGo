package edu.illinois.cs465.grocerygo.layout.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.event.PostEvent;
import edu.illinois.cs465.grocerygo.layout.dialog.TimePickerDialog;

public class PostActivity extends AbstractActivity {
    public String date;
    public String theDate;
    private String[] items={
            "Costco 2020 N Neil St",
            "Kohl's 109 Convenience Center Rd",
            "Walmart 2610 N Prospect Ave",
            "Walmart 100 S High Cross Rd",
            "Fresh Internation Market 505 S Neil St",
            "Meijer 2401 N Prospect Ave"
    };
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
        EditText remark = findViewById(R.id.remark);
        RadioGroup radioGroup = findViewById(R.id.buttons);
//        EditText dest = findViewById(R.id.destination);
        AutoCompleteTextView dest = findViewById(R.id.destination);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
        dest.setAdapter(adapter);
        dest.setThreshold(1);
        dismissBtn.setOnClickListener(view -> this.finish());
        postBtn.setOnClickListener(view -> {
            String fromTime = (String) timePickerFrom.getText();
            String toTime = (String) timePickerTo.getText();
            String date = (String) datePicker.getText();
            String destination = dest.getText().toString();
            int checked = -1;
            int count = radioGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                RadioButton rb = (RadioButton)radioGroup.getChildAt(i);
                if (rb.isChecked()) {
                    checked = i;
                    break;
                }
            }
            if (checked == -1) {
                Toast.makeText(this, "please select your post type", Toast.LENGTH_SHORT).show();
            } else if (checked == 0) {
                Toast.makeText(this, "You have not verified as a Driver!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    if (check(fromTime, toTime, theDate) && !destination.equals("")) {
                        String name = dest.getText().toString().split(" ")[0];
                        EventBus.getDefault().post(new PostEvent(1.1, "Elysia",
                                theDate + " " + fromTime,
                                remark.getText().toString(), name));
                        this.finish();
                    } else if(destination.equals("")) {
                        Toast.makeText(this, "please input your destination!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.time_toast, Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
                            datePicker.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            theDate = (monthOfYear + 1) + "-" + dayOfMonth;
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

    private boolean check(String from, String to, String date) throws ParseException {
        if (from == null || to == null || from.equals("") || to.equals("") || date.equals("")) {
            return false;
        }
        DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
        //current date and time
        Date cur = new Date();
        Date curDateTime = df.parse(df.format(cur));
        String fromStr = date + " " + from;
        String toStr = date + " " + to;
        Date fromDateTime = df.parse(fromStr);
        Date toDateTime = df.parse(toStr);

        int res1 = toDateTime.compareTo(fromDateTime);
        int res2 = toDateTime.compareTo(curDateTime);
        int res3 = fromDateTime.compareTo(curDateTime);

        return res1 > 0 && res2 > 0 && res3 > 0;
        
    }
}

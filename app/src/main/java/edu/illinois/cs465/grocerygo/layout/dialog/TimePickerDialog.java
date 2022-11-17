package edu.illinois.cs465.grocerygo.layout.dialog;

import android.content.Context;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import edu.illinois.cs465.grocerygo.R;


public class TimePickerDialog extends BottomSheetDialog {
    private TextView timeView;
    public TimePickerDialog(@NonNull Context context, int theme, TextView view) {
        super(context, theme);
        initView(view);
    }
    private void initView(TextView view) {
        setContentView(R.layout.timepicker_dialog);
        timeView = view;
        TimePicker timePicker = findViewById(R.id.time_picker);
        if (timePicker != null) {
            timePicker.setIs24HourView(true);
        }
        TextView confirmBtn = findViewById(R.id.confirm_button);
        if (confirmBtn != null) {
            confirmBtn.setOnClickListener(btnView -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int hour = timePicker.getHour();
                    int minute = timePicker.getMinute();
                    timeView.setText(hour + ":" + minute);
                }
                this.dismiss();
            });
        }
    }
}

package com.mango.heartbeatmonitor.views.popups;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mango.heartbeatmonitor.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewReminderDialog {

    private static final String TIME_FORMAT = "hh:mm a";
    private static final String DATE_FORMAT = "dd MMM yyyy";
    private Context context;
    private Dialog dialog;
    private TextView setDate, setTime;
    private CheckBox repeat;
    private ImageButton saveAlert, cancelAlert;
    private RadioButton hourly, daily, monthly, yearly;
    private TextView alertInfo;

    Calendar myCalendar;
    private boolean timeGotSet = false;
    private boolean dateGotSet = false;

    public NewReminderDialog(Context context) {
        this.context = context;
        myCalendar = Calendar.getInstance();
    }

    /**
     * Creating the required dialog
     */
    public void createNewDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.create_new_reminder_alert);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        setDate = dialog.findViewById(R.id.set_date);
        setTime = dialog.findViewById(R.id.set_time);
        repeat = dialog.findViewById(R.id.repeat);
        hourly = dialog.findViewById(R.id.hourly);
        daily = dialog.findViewById(R.id.daily);
        monthly = dialog.findViewById(R.id.monthly);
        yearly = dialog.findViewById(R.id.yearly);
        alertInfo = dialog.findViewById(R.id.alert_info);

        cancelAlert = dialog.findViewById(R.id.cancel_alert);
        saveAlert = dialog.findViewById(R.id.save_alert);

        // set the click listeners
        setDate.setOnClickListener(initiateDatePicker);
        setTime.setOnClickListener(initiateTimePicker);
        cancelAlert.setOnClickListener(finishAction);
        saveAlert.setOnClickListener(finishAction);

        dialog.show();
    }

    /**
     * To initiate the default date picker for the user to make him choose the alert date
     */
    private View.OnClickListener initiateDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(context, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    };

    /**
     * To initiate the default time picker for the user to make him choose the alert time
     */
    private View.OnClickListener initiateTimePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar currentTime = Calendar.getInstance();
            int hour = currentTime.get(Calendar.HOUR_OF_DAY);
            int minute = currentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker = new TimePickerDialog(context, onTimeSetListener, hour, minute, true);
            mTimePicker.setTitle(context.getResources().getString(R.string.set_time));
            mTimePicker.show();
        }
    };

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }

    };

    /**
     * updating the date value with the new value
     */
    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        setDate.setText(sdf.format(myCalendar.getTime()));
        dateGotSet = true;
    }

    /**
     * The onTimeSet Listener for the time picker
     */
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            String pickedTime = selectedHour + ":" + selectedMinute;
            SimpleDateFormat currentFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat requiredFormat = new SimpleDateFormat(TIME_FORMAT);
            try {
                pickedTime = requiredFormat.format(currentFormat.parse(pickedTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setTime.setText(pickedTime);
            timeGotSet = true;
        }
    };

    /**
     * Tp perform finish action for the dialog either with saving the reminder or with cancelling it
     */
    private View.OnClickListener finishAction = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            v.setAlpha(0.3f);
            dialog.dismiss();
            if (v == saveAlert) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setAlpha(1.0f);
                    }
                }, 300);
                if (!timeGotSet || !dateGotSet) {
                    Toast.makeText(context, context.getResources().getString(R.string.date_and_time_set_error), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // TODO
                }
            }
        }
    };

}

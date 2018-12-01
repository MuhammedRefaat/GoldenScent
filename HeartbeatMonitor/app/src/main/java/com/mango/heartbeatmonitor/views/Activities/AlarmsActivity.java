package com.mango.heartbeatmonitor.views.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.mango.heartbeatmonitor.R;
import com.mango.heartbeatmonitor.views.popups.NewReminderDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.footer_view)
    View footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the view layout
        setContentView(R.layout.activity_alarms);
        // binding layout views
        ButterKnife.bind(this);
        // set the onClickListener
        fab.setOnClickListener(this);
    }

    /**
     * To display popupDialog for creating a new reminder
     */
    private void showReminderCreationDialog() {
        new NewReminderDialog(this).createNewDialog();
    }

    @Override
    public void onClick(final View v) {
        // give press feeling
        v.setAlpha(0.3f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setAlpha(1.0f);
            }
        }, 300);
        if (v == fab) {
            Snackbar.make(footerView, getResources().getString(R.string.new_alart), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            showReminderCreationDialog();
        }
    }
}

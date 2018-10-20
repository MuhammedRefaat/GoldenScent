package com.imagine.goldenscent;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.productsListView)
    ExpandableListView productsListView;

    ExpandableListAdapter productsListAdapter;

    public static Map<Integer, ImageView> groupsIndicator = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // views declaration
        ButterKnife.bind(this);

        // prepare data to be displayed for the listView
        PrepareListData.prepareData(MainActivity.this);

        // initiating the adapter with the data retrieved
        productsListAdapter = new ExpandableListAdapter(this,
                PrepareListData.headers, PrepareListData.children);

        // assigning the adapter to the view
        productsListView.setAdapter(productsListAdapter);

        // removing the list default indicator
        productsListView.setGroupIndicator(null);

        // set the Listeners for the ListView
        productsListView.setOnGroupExpandListener(onGroupExpand);
        productsListView.setOnGroupCollapseListener(onGroupCollapse);

        // handle opened groups
        productsListView.expandGroup(0);
        productsListView.expandGroup(1);
    }

    /**
     * The group expand listener for the ExpandableListView
     */
    private ExpandableListView.OnGroupExpandListener onGroupExpand = new ExpandableListView.OnGroupExpandListener() {
        @Override
        public void onGroupExpand(int groupPosition) {
            try {
                MainActivity.groupsIndicator.get(groupPosition).setImageResource(R.drawable.arrow_up);
            } catch (NullPointerException npe) { // as the view may be called before the adapter initialization finished
                npe.printStackTrace();
            }
        }
    };

    /**
     * The group collapse listener for the ExpandableListView
     */
    private ExpandableListView.OnGroupCollapseListener onGroupCollapse = new ExpandableListView.OnGroupCollapseListener() {
        @Override
        public void onGroupCollapse(int groupPosition) {
            try {
                MainActivity.groupsIndicator.get(groupPosition).setImageResource(R.drawable.arrow_down);
            } catch (NullPointerException npe) { // as the view may be called before the adapter initialization finished
                npe.printStackTrace();
            }
        }
    };

}

package com.imagine.goldenscent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.productsListView)
    ExpandableListView productsListView;

    ExpandableListAdapter productsListAdapter;

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

    }

}

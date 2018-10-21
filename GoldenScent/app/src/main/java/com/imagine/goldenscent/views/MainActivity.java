package com.imagine.goldenscent.views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.imagine.goldenscent.adapters.ExpandableListAdapter;
import com.imagine.goldenscent.models.PrepareCategoriesListData;
import com.imagine.goldenscent.models.PrepareProductsListData;
import com.imagine.goldenscent.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_categories_list)
    LinearLayout mainCategoriesList;
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

        // prepare the data to be displayed for the categories list
        PrepareCategoriesListData.prepareData(MainActivity.this);

        // handling the categories retrieve, set and display
        displayCategories();

        // prepare data to be displayed for the products listView
        PrepareProductsListData.prepareData(MainActivity.this);

        // initiating the adapter with the data retrieved
        productsListAdapter = new ExpandableListAdapter(this,
                PrepareProductsListData.headers, PrepareProductsListData.children);

        // assigning the adapter to the view
        productsListView.setAdapter(productsListAdapter);

        // removing the list default indicator adn children separators
        productsListView.setGroupIndicator(null);
        productsListView.setChildDivider(getResources().getDrawable(R.color.colorTransparent));

        // set the Listeners for the ListView
        productsListView.setOnGroupExpandListener(onGroupExpand);
        productsListView.setOnGroupCollapseListener(onGroupCollapse);

        // handle opened groups
        productsListView.expandGroup(0);
        productsListView.expandGroup(1);
    }

    private void displayCategories() {
        View view;
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.single_category_selection, null);
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

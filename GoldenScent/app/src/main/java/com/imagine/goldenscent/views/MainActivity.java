package com.imagine.goldenscent.views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imagine.goldenscent.adapters.ExpandableListAdapter;
import com.imagine.goldenscent.models.CategoriesListData;
import com.imagine.goldenscent.models.ProductsListData;
import com.imagine.goldenscent.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        /*** for categories list ***/

        // prepare the data to be displayed for the categories list
        CategoriesListData.prepareData(MainActivity.this);

        // handling the categories retrieve, set and display
        displayCategories();

        /*** for products list ***/

        // prepare data to be displayed for the products listView
        ProductsListData.prepareData(MainActivity.this);

        // initiating the adapter with the data retrieved
        productsListAdapter = new ExpandableListAdapter(this,
                ProductsListData.headers, ProductsListData.children);

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
     * This method is responsible for handling categories regarding getting, displaying, handling click events
     */
    private void displayCategories() {
        // define the inflater
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // declare the required views
        View view;
        ImageView catImage;
        TextView catName;
        List<String> categories = CategoriesListData.categories;
        ArrayList<Integer> catImagesResourcesIds = CategoriesListData.catImagesResourcesIds;
        // adjust the view layout params
        int viewHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 90, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, viewHeight);
        lp.setMargins(20, 20, 20, 20);
        // adding the views
        for (int i = 0; i < categories.size(); i++) {
            view = inflater.inflate(R.layout.single_category_selection, null);
            view.setLayoutParams(lp);
            // declare the fields
            catImage = view.findViewById(R.id.cat_image);
            catName = view.findViewById(R.id.cat_name);
            // set the values
            catName.setText(categories.get(i));
            catImage.setImageResource(catImagesResourcesIds.get(i));
            // adding the view to the list
            mainCategoriesList.addView(view);
            // set the view click listener
            view.setTag(Integer.toString(i));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCategory(Integer.parseInt(v.getTag().toString().trim()));
                }
            });
        }
        // now to select the default category
        selectCategory(1);
    }

    /**
     * To set selection criteria for the selected category
     *
     * @param index the index of the selected category
     */
    private void selectCategory(int index) {
        RelativeLayout singleCategory;
        ImageView catImage;
        TextView catName;
        View footer;
        for (int i = 0; i < mainCategoriesList.getChildCount(); i++) {
            singleCategory = (RelativeLayout) ((CardView) mainCategoriesList.getChildAt(i)).getChildAt(0);
            catImage = singleCategory.findViewById(R.id.cat_image);
            catName = singleCategory.findViewById(R.id.cat_name);
            footer = singleCategory.findViewById(R.id.footer);
            if (i == index) {
                catImage.setAlpha(1.0f);
                catName.setAlpha(1.0f);
                footer.setVisibility(View.VISIBLE);
            } else {
                catImage.setAlpha(0.3f);
                catName.setAlpha(0.5f);
                footer.setVisibility(View.GONE);
            }
        }
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

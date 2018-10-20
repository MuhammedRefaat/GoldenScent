package com.imagine.goldenscent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> headers;
    private HashMap<String, List<String>> allChildrenData;

    public ExpandableListAdapter(Context context, List<String> headers,
                                 HashMap<String, List<String>> allChildrenData) {
        this.context = context;
        this.headers = headers;
        this.allChildrenData = allChildrenData;
        MainActivity.groupsIndicator = new HashMap<>();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.allChildrenData.get(this.headers.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View view, ViewGroup viewGroup) {

        final String childName = (String) getChild(groupPosition, childPosition);

        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (groupPosition == 0)
            view = inflater.inflate(R.layout.bs_child_listing, null);
        else
            view = inflater.inflate(R.layout.single_lips_item, null);

        try {
            if (groupPosition == 0) {
                for (int i = 0; i < 3; i++) { // for adding the column view 3 times
                    handleBestSellerItem(view, childName);
                }
            } else {
                handleLipsItem(view, childName);
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        return view;
    }

    /**
     * To handle the child view in case of a bestSeller Item
     *
     * @param view      the holding view
     * @param childName that child name
     */
    private void handleBestSellerItem(View view, String childName) {
        // define the views
        LinearLayout bsScrollItems = view.findViewById(R.id.bsScrollItems);
        // define layout inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        // setting the views parameters
        // first, defining the column of products
        LinearLayout column = new LinearLayout(context);
        column.setLayoutParams(new LinearLayout.LayoutParams
                ((int) (getScreenWidth() * 0.73), LinearLayout.LayoutParams.WRAP_CONTENT));
        column.setGravity(Gravity.CENTER);
        column.setOrientation(LinearLayout.VERTICAL);
        // now, adding the products to the column
        String[] allColumnChildren = childName.split(",,");
        for (int i = 0; i < allColumnChildren.length; i++) {
            column.addView(addItemView(i, allColumnChildren[i], inflater));
        }
        // finally, adding the column to the main horizontalScrollView
        bsScrollItems.addView(column);
    }

    /**
     * to get the screen physical width
     *
     * @return the required width value
     */
    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * Adding a BestSeller view item to the column layout
     *
     * @param index     the current child index
     * @param childName the child name
     * @param inflater  he layout inflater to inflate the view layout
     * @return the created view with the required parameters set
     */
    private View addItemView(int index, String childName, LayoutInflater inflater) {
        // get the view and set the correct dimensions
        View singleBsItemListing = inflater.inflate(R.layout.single_bs_item, null);
        int height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());
        singleBsItemListing.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height));
        // define the views within that single view
        ImageView productImage = singleBsItemListing.findViewById(R.id.product_image);
        TextView productName = singleBsItemListing.findViewById(R.id.product_name);
        TextView productDesc = singleBsItemListing.findViewById(R.id.product_description);
        TextView productPrice = singleBsItemListing.findViewById(R.id.product_price);
        TextView productOrgPrice = singleBsItemListing.findViewById(R.id.product_org_price);
        TextView productCutPrice = singleBsItemListing.findViewById(R.id.product_cut_price);
        Button productAdd = singleBsItemListing.findViewById(R.id.product_add);
        View separator = singleBsItemListing.findViewById(R.id.separator);

        productName.setText(childName);
        int itemImageResourceId = context.getResources().getIdentifier
                (childName.replaceAll("&", "").replaceAll("\\s+", "").toLowerCase(), "drawable", context.getPackageName());
        productImage.setImageResource(itemImageResourceId);
        int itemDescriptionResourceId = context.getResources().getIdentifier
                (childName.replaceAll("&", "").replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName());
        productDesc.setText(itemDescriptionResourceId);

        if (index == 0) {
            productPrice.setVisibility(View.GONE);
            productOrgPrice.setVisibility(View.VISIBLE);
            productCutPrice.setVisibility(View.VISIBLE);
            productOrgPrice.setText(context.getResources().getString(R.string.org_price_value));
            productCutPrice.setText(context.getResources().getString(R.string.cut_price_value));
            // StrikeThrough the original price
            productOrgPrice.setPaintFlags(productOrgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            productCutPrice.setVisibility(View.GONE);
            productOrgPrice.setVisibility(View.GONE);
            productPrice.setVisibility(View.VISIBLE);
            productPrice.setText(context.getResources().getString(R.string.price_value));
        }
        if (index == 2) {
            separator.setVisibility(View.INVISIBLE);
        }

        productAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                // give click feeling
                view.setAlpha(0.3f);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setAlpha(1.0f);
                    }
                }, 300);
            }
        });

        return singleBsItemListing;
    }

    /**
     * To handle the child view in case of a lips Item
     *
     * @param view      the holding view
     * @param childName that child name
     */
    private void handleLipsItem(View view, String childName) {
        // define the views
        LinearLayout item1 = view.findViewById(R.id.item1);
        LinearLayout item2 = view.findViewById(R.id.item2);
        LinearLayout item3 = view.findViewById(R.id.item3);
        // setting the views parameters
        String[] allRowChildren = childName.split(",,");
        LinearLayout[] items = new LinearLayout[]{item1, item2, item3};
        ImageView itemImage;
        TextView itemName;
        String itemNameValue;
        for (int i = 0; i < allRowChildren.length; i++) {
            itemImage = (ImageView) items[i].getChildAt(0);
            itemName = (TextView) items[i].getChildAt(1);
            itemNameValue = allRowChildren[i];
            itemName.setText(itemNameValue);
            int itemImageResourceId = context.getResources().getIdentifier
                    (itemNameValue.replaceAll("\\s+", "").toLowerCase(), "drawable", context.getPackageName());
            itemImage.setImageResource(itemImageResourceId);
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.allChildrenData.get(this.headers.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headers.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.headers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View view, ViewGroup viewGroup) {

        // get the group title
        String headerTitle = (String) getGroup(groupPosition);

        // get the view
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.single_category_group_listing, null);

        /* define the views */
        TextView groupTitle = view.findViewById(R.id.group_title);
        ImageView groupIndicator = view.findViewById(R.id.group_list_indicator);
        TextView textViewAll = view.findViewById(R.id.text_view_all);

        // set the views
        groupTitle.setText(headerTitle);
        if (groupPosition == 0) {
            groupIndicator.setVisibility(View.GONE);
            textViewAll.setVisibility(View.VISIBLE);
        } else {
            if (isExpanded)
                groupIndicator.setImageResource(R.drawable.arrow_up);
            else
                groupIndicator.setImageResource(R.drawable.arrow_down);
        }

        MainActivity.groupsIndicator.put(groupPosition, groupIndicator);

        // return the group corresponding view
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

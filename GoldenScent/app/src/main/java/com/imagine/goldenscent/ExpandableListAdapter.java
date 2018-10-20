package com.imagine.goldenscent;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
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
import java.util.zip.Inflater;

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
                             boolean isLastChild, View view, ViewGroup parent) {

        final String childName = (String) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (groupPosition == 0)
                view = infalInflater.inflate(R.layout.bs_child_listing, null);
            else
                view = infalInflater.inflate(R.layout.single_lips_item, null);
        }

        try {
            if (groupPosition == 0) {
                handleBestSellerItem(view, childName);
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
        String[] allColumnChildren = childName.split(",,");
        for (int i = 0; i < allColumnChildren.length; i++) {
            LinearLayout column = new LinearLayout(context);
            column.setLayoutParams(new LinearLayout.LayoutParams
                    ((int) (bsScrollItems.getWidth() * 0.9), LinearLayout.LayoutParams.WRAP_CONTENT));
            column.setGravity(Gravity.CENTER);
            column.setOrientation(LinearLayout.VERTICAL);
            column.setPadding(10, 10, 10, 10);
            bsScrollItems.addView(column);
            for (int j = 0; j < 3; j++) { // for adding the column view 3 times
                column.addView(addItemView(i, allColumnChildren[i], inflater));
            }
        }
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
        View singleBsItemListing = inflater.inflate(R.layout.single_bs_item, null);
        // define the views within that single view
        ImageView productImage = singleBsItemListing.findViewById(R.id.product_image);
        TextView productName = singleBsItemListing.findViewById(R.id.product_name);
        TextView productDesc = singleBsItemListing.findViewById(R.id.product_description);
        TextView productPrice = singleBsItemListing.findViewById(R.id.product_price);
        TextView productOrgPrice = singleBsItemListing.findViewById(R.id.product_org_price);
        TextView productCutPrice = singleBsItemListing.findViewById(R.id.product_cut_price);
        Button productAdd = singleBsItemListing.findViewById(R.id.product_add);

        productName.setText(childName);
        int itemImageResourceId = context.getResources().getIdentifier
                (childName.replaceAll("&amp;", "").replaceAll("\\s+", "").toLowerCase(), "drawable", context.getPackageName());
        productImage.setImageResource(itemImageResourceId);
        int itemDescriptionResourceId = context.getResources().getIdentifier
                (childName.replaceAll("&", "").replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName());
        productDesc.setText(itemDescriptionResourceId);

        if (index == 0) {
            productPrice.setVisibility(View.GONE);
            productCutPrice.setVisibility(View.VISIBLE);
            productOrgPrice.setVisibility(View.VISIBLE);
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
                             View view, ViewGroup parent) {

        // get the group title
        String headerTitle = (String) getGroup(groupPosition);

        // get the view
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.single_category_group_listing, null);
        }

        /* define the views */
        TextView groupTitle = view.findViewById(R.id.group_title);
        ImageView groupIndicator = view.findViewById(R.id.group_list_indicator);
        TextView textViewAll = view.findViewById(R.id.text_view_all);

        // set the views
        groupTitle.setText(headerTitle);
        if (groupPosition == 0) {
            groupIndicator.setVisibility(View.GONE);
            textViewAll.setVisibility(View.VISIBLE);
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

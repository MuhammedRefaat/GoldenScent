package com.imagine.goldenscent.models;

import android.content.Context;

import com.imagine.goldenscent.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ProductsListData {

    private static String CHILDREN_ARRAY_NAME = "childrenOfHeader_";

    public static ArrayList<String> headers;
    public static HashMap<String, List<String>> children;

    public static void prepareData(Context context) {

        headers = new ArrayList<>();
        children = new HashMap<>();

        // Adding child data
        String[] headersArray = context.getResources().getStringArray(R.array.headers);
        int childrenArrayResourceId;
        String[] childrenArray;
        List<String> childrenData;
        for (int i = 0; i < headersArray.length; i++) {
            // adding the Header to the array
            headers.add(headersArray[i]);
            // getting the corresponding header children
            childrenArrayResourceId = context.getResources().getIdentifier
                    (CHILDREN_ARRAY_NAME + i, "array", context.getPackageName());
            childrenArray = context.getResources().getStringArray(childrenArrayResourceId);
            // convert the String Array to String List
            childrenData = Arrays.asList(childrenArray);
            // finally, add the complete data
            children.put(headersArray[i], childrenData);
        }

    }

}

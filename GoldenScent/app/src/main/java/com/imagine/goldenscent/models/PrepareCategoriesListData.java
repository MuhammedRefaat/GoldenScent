package com.imagine.goldenscent.models;

import android.content.Context;

import com.imagine.goldenscent.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrepareCategoriesListData {

    public static List<String> categories;
    public static ArrayList<Integer> catImagesResourcesIds;

    private static Context context;

    public static void prepareData(Context activityContext) {

        context = activityContext;

        // getting the categories array
        String[] categoriesArray = context.getResources().getStringArray(R.array.categories);
        categories = Arrays.asList(categoriesArray);
        // getting the categories images resources id
        for (int i = 0; i < categoriesArray.length; i++) {
            catImagesResourcesIds.add(i, context.getResources().getIdentifier
                    ("cat" + i, "drawable", context.getPackageName()));
        }

    }

}

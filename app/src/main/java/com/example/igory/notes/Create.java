package com.example.igory.notes;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.TabHost;

/**
 * Created by igory on 26.03.2018.
 */

public class Create {

    Bitmap CreateBitmap(View view)
    {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    TabHost.TabSpec CreateTabHost(TabHost tabHost, String tag, String name, int id)
    {
        TabHost.TabSpec tabSpec;
        tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(name);
        tabSpec.setContent(id);

        return tabSpec;
    }
}

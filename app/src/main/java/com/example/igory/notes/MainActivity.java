package com.example.igory.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.example.igory.notes.ListView.CustomAdapter;
import com.example.igory.notes.ListView.ListItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    List<ListItem> items;
    int positionItem;
    TabHost.TabSpec tabSpec;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabHost tabHost = findViewById(R.id.tab_host);
        tabHost.setup();

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Заметки");
        tabSpec.setContent(R.id.listView);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Выбор цвета");
        tabSpec.setContent(R.id.tab2);
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("tag1");

        listView = findViewById(R.id.listView);
        items = new ArrayList<>();


        final LinearLayout layout = findViewById(R.id.circles);

        ImageView imageView10 = findViewById(R.id.imageView10);

        int px = (int)getApplicationContext().getResources().getDisplayMetrics().density;

        int width = px * 80;
        int margin = 24 * px;

        int length = width + margin / 2;


        layout.setDrawingCacheEnabled(true);
        layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        layout.layout(0, 0, layout.getMeasuredWidth(), layout.getMeasuredHeight());
        layout.buildDrawingCache(true);
        Bitmap bitmap3 = Bitmap.createBitmap(layout.getDrawingCache());
        layout.setDrawingCacheEnabled(false);


        int centr;

        for (int i = R.id.imageView10, j = 0; j < 16; i++, j++ )
        {
            centr = bitmap3.getPixel(length - width / 2, width / 2);

            (findViewById(i)).setBackgroundColor(centr);
            (findViewById(i)).setOnClickListener(this);

            length += width + margin;
        }

        findViewById(R.id.selected_color).setBackgroundColor(Color.BLACK);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ListItem changed = (ListItem) parent.getItemAtPosition(position);

                Intent start = new Intent(MainActivity.this, AddActivity.class);
                start.putExtra("head", changed.getHead());
                start.putExtra("description", changed.getDescription());

                positionItem = position;

                startActivityForResult(start, 2);
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(start, 1);
            }
        });

        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                if(tabId == "tag2")
                {
                    fab.hide();
                }
                else
                    fab.show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode == 1)
            {
                items.add(new ListItem(data.getStringExtra("head"),
                        data.getStringExtra("description"),
                        data.getStringExtra("date")));
            }
            else
            {
                items.set(positionItem, new ListItem(
                        data.getStringExtra("head"),
                        data.getStringExtra("description"),
                        items.get(positionItem).getDate(),
                        items.get(positionItem).getColor()
                ));
            }

            CustomAdapter customAdapter = new CustomAdapter(this, items);

            listView.setAdapter(customAdapter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("items", (ArrayList<ListItem>) items);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        items = savedInstanceState.getParcelableArrayList("items");

        CustomAdapter customAdapter = new CustomAdapter(this, items);

        listView.setAdapter(customAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        ImageView selected = findViewById(v.getId());

        Bitmap screenshot;
        selected.setDrawingCacheEnabled(true);
        screenshot = Bitmap.createBitmap(selected.getDrawingCache());
        selected.setDrawingCacheEnabled(false);

        int pixel = screenshot.getPixel(10, 10);

        int red = Color.red(pixel);
        int green = Color.green(pixel);
        int blue = Color.blue(pixel);

        ((ImageView) findViewById(R.id.selected_color)).setBackgroundColor(pixel);

        ((TextView) findViewById(R.id.RGB)).setText(String.format("RGB: %s,%s,%s",
                String.valueOf(red),
                String.valueOf(green),
                String.valueOf(blue)));

        float[] hsv = new float[3];
        Color.colorToHSV(pixel, hsv);

        ((TextView) findViewById(R.id.HSV)).setText(String.format("HSV: %s,%s,%s",
                String.valueOf((int)hsv[0]),
                String.valueOf((int)hsv[1]),
                String.valueOf((int)hsv[2])));

    }
}

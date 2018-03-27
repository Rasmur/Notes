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

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<ListItem> items;
    int positionItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.include);
        items = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                ListItem changed = (ListItem) parent.getItemAtPosition(position);

                Intent start = new Intent(MainActivity.this, AddActivity.class);
                start.putExtra("head", changed.getHead());
                start.putExtra("description", changed.getDescription());
                start.putExtra("color", changed.getColor());

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode == 1)
            {
                Log.d("Main", "1111");
                items.add(new ListItem(data.getStringExtra("head"),
                        data.getStringExtra("description"),
                        data.getIntExtra("color", 0),
                        data.getStringExtra("date"))
                        );
            }
            else
            {
                items.set(positionItem, new ListItem(
                        data.getStringExtra("head"),
                        data.getStringExtra("description"),
                        data.getIntExtra("color", 0),
                        items.get(positionItem).getDate()

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


}

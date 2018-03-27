package com.example.igory.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.igory.notes.ListView.CustomAdapter;
import com.example.igory.notes.ListView.ListItem;

import java.util.Random;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    EditText head;
    EditText description;
    ImageView colorView;

    int color;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        head = findViewById(R.id.head);
        description = findViewById(R.id.description);
        colorView = findViewById(R.id.color);

        colorView.setOnClickListener(this);

        Intent intent = getIntent();

        head.setText(intent.getStringExtra("head"));
        description.setText(intent.getStringExtra("description"));
        color = intent.getIntExtra("color", 0);

        if (color == 0)
        {
            Log.d("Main", "SET");
            color = Color.argb(255, rand.nextInt(255), rand.nextInt(255),
                    rand.nextInt(255));
        }

        colorView.setBackgroundColor(color);
    }

    @Override
    public void onClick(View v) {

        Intent start = new Intent(AddActivity.this, ColorPicker.class);
        start.putExtra("color", color);

        startActivityForResult(start, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            color = data.getIntExtra("color", 0);
            colorView.setBackgroundColor(color);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("head", head.getText().toString());
        outState.putString("description", description.getText().toString());
        outState.putInt("color", color);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        head.setText(savedInstanceState.getString("head"));
        description.setText(savedInstanceState.getString("description"));

        color = savedInstanceState.getInt("color");
        colorView.setBackgroundColor(color);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) description.getLayoutParams();

            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50 , getResources().getDisplayMetrics());

            description.setLayoutParams(params);
        }
        else
        {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) description.getLayoutParams();

            params.matchConstraintDefaultHeight = 244;

            description.setLayoutParams(params);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (head.getText().length() > 0)
        {
            Intent answerIntent = new Intent(this, MainActivity.class);

            answerIntent.putExtra("head", head.getText().toString());
            answerIntent.putExtra("description", description.getText().toString());
            answerIntent.putExtra("color", color);

            Time time = new Time(Time.getCurrentTimezone());
            time.setToNow();
            answerIntent.putExtra("date", time.format("%d.%m.%Y"));

            setResult(RESULT_OK, answerIntent);
            finish();
        }
        else
        {
            Toast toast = Toast.makeText(this, "Введите заголовок",Toast.LENGTH_LONG);
            toast.show();
        }

        return super.onOptionsItemSelected(item);
    }
}

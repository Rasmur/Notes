package com.example.igory.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ColorPicker extends AppCompatActivity implements View.OnClickListener{

    int mainColor;
    TextView RGB;
    TextView HSV;
    ImageView selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        RGB = findViewById(R.id.RGB);
        HSV = findViewById(R.id.HSV);
        selectedColor = findViewById(R.id.selected_color);

        Create create = new Create();

        final LinearLayout layout = findViewById(R.id.circles);

        int px = (int)getApplicationContext().getResources().getDisplayMetrics().density;

        int width = px * 80;
        int margin = 24 * px;

        int length = width + margin / 2;

        Bitmap bitmap = create.CreateBitmap(layout);

        int centr;

        for (int i = R.id.imageView10, j = 0; j < 16; i++, j++ )
        {
            centr = bitmap.getPixel(length - width / 2, width / 2);

            (findViewById(i)).setBackgroundColor(centr);
            (findViewById(i)).setOnClickListener(this);

            length += width + margin;
        }

        Intent intent = getIntent();

        mainColor = intent.getIntExtra("color", 0);

        int red = Color.red(mainColor);
        int green = Color.green(mainColor);
        int blue = Color.blue(mainColor);

        RGB.setText(String.format("RGB: %s,%s,%s",
                String.valueOf(red),
                String.valueOf(green),
                String.valueOf(blue)));

        float[] hsv = new float[3];
        Color.colorToHSV(mainColor, hsv);

        HSV.setText(String.format("HSV: %s,%s,%s",
                String.valueOf((int)hsv[0]),
                String.valueOf((int)hsv[1]),
                String.valueOf((int)hsv[2])));

        selectedColor.setBackgroundColor(mainColor);
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

        selectedColor.setBackgroundColor(pixel);

        RGB.setText(String.format("RGB: %s,%s,%s",
                String.valueOf(red),
                String.valueOf(green),
                String.valueOf(blue)));

        float[] hsv = new float[3];
        Color.colorToHSV(pixel, hsv);

        HSV.setText(String.format("HSV: %s,%s,%s",
                String.valueOf((int)hsv[0]),
                String.valueOf((int)hsv[1]),
                String.valueOf((int)hsv[2])));

        mainColor = pixel;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("mainColor", mainColor);
        outState.putString("RGB", RGB.getText().toString());
        outState.putString("HSV", HSV.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mainColor = savedInstanceState.getInt("mainColor");
        selectedColor.setBackgroundColor(mainColor);
        RGB.setText(savedInstanceState.getString("RGB"));
        HSV.setText(savedInstanceState.getString("HSV"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent answerIntent = new Intent(this, AddActivity.class);

        answerIntent.putExtra("color", mainColor);

        setResult(RESULT_OK, answerIntent);
        finish();

        return super.onOptionsItemSelected(item);
    }
}

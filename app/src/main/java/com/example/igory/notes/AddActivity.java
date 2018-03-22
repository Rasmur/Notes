package com.example.igory.notes;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    Button save;
    EditText head;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        save = findViewById(R.id.button);
        save.setOnClickListener(this);

        head = findViewById(R.id.head);
        description = findViewById(R.id.description);

        Intent intent = getIntent();

        head.setText(intent.getStringExtra("head"));
        description.setText(intent.getStringExtra("description"));
    }

    @Override
    public void onClick(View v) {

        if (head.getText().length() > 0)
        {
            Intent answerIntent = new Intent(this, MainActivity.class);

            answerIntent.putExtra("head", head.getText().toString());
            answerIntent.putExtra("description", description.getText().toString());

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
    }
}

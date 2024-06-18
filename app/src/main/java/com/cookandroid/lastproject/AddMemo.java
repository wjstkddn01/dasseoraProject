package com.cookandroid.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class AddMemo extends AppCompatActivity {

    EditText editMainText, editTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        editMainText = findViewById(R.id.editMemo);
        editTitle = findViewById(R.id.editTitle);

        findViewById(R.id.btnWrite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                String mainText = editMainText.getText().toString();
                String title = editTitle.getText().toString();

                if(mainText.length() > 0 && title.length() > 0) {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String subText = sdf.format(date);

                    Intent intent = new Intent();
                    intent.putExtra("title", title);
                    intent.putExtra("sub", subText);
                    intent.putExtra("main", mainText);
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }

        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);

                finish();

            }
        });
    }
}
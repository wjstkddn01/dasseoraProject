package com.cookandroid.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateMemo extends AppCompatActivity {

    EditText editTitle, editMainText;
    Button btnUpdate, btnCancel;
    int seq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_memo);

        editTitle = findViewById(R.id.editTitle);
        editMainText = findViewById(R.id.editMemo);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        seq = intent.getIntExtra("seq", -1);
        String Title = intent.getStringExtra("title");
        String Maintext = intent.getStringExtra("maintext");

        // EditText에 데이터 설정
        editTitle.setText(Title);
        editMainText.setText(Maintext);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedTitle = editTitle.getText().toString();
                String updatedMainText = editMainText.getText().toString();

                if(updatedTitle.length() > 0 && updatedMainText.length() > 0) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String updatedSubText = sdf.format(new Date());

                    Intent intent = new Intent();
                    intent.putExtra("seq", seq);
                    intent.putExtra("title", updatedTitle);
                    intent.putExtra("main", updatedMainText);
                    intent.putExtra("sub", updatedSubText);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }
}

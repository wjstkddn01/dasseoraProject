package com.cookandroid.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReadMemo extends AppCompatActivity {

    TextView readTitle, readMaintext, readSubtext;
    Button btnOK, btnUpdate;
    int seq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_memo);

        readTitle = (TextView) findViewById(R.id.readTitle);
        readMaintext = (TextView) findViewById(R.id.readMainText);
        readSubtext = (TextView) findViewById(R.id.readSubText);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnOK = (Button) findViewById(R.id.btnOk);

        Intent intent = getIntent();
        seq = intent.getIntExtra("seq", -1);

        String Title = intent.getStringExtra("title");
        String Subtext = intent.getStringExtra("subtext");
        String Maintext = intent.getStringExtra("maintext");

        // TextView에 데이터 설정
        readTitle.setText(Title);
        readSubtext.setText(Subtext);
        readMaintext.setText(Maintext);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                setResult(RESULT_CANCELED, intent1);
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent(ReadMemo.this, UpdateMemo.class);
                updateIntent.putExtra("seq", seq);
                updateIntent.putExtra("title", Title);
                updateIntent.putExtra("maintext", Maintext);
                startActivityForResult(updateIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String updatedTitle = data.getStringExtra("title");
            String updatedMainText = data.getStringExtra("main");
            String updatedSubText = data.getStringExtra("sub");

            // 업데이트된 데이터를 TextView에 설정
            readTitle.setText(updatedTitle);
            readMaintext.setText(updatedMainText);
            readSubtext.setText(updatedSubText);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("seq", seq);
            resultIntent.putExtra("title", updatedTitle);
            resultIntent.putExtra("main", updatedMainText);
            resultIntent.putExtra("sub", updatedSubText);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }
}

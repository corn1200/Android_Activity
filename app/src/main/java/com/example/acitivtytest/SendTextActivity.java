package com.example.acitivtytest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SendTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);

        String textMessage = "";
        TextView SendText = findViewById(R.id.SendText);
        // String 에 전달 받은 메세지를 저장
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
        // View 에 전달 받은 메세지를 set 하고 화면을 보여줌
        SendText.setText(textMessage);
        startActivity(sendIntent);
    }
}
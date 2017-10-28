package com.tbuonomo.jawgmapsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

public class SendActivity extends AppCompatActivity {

    private Button sendBtn;
    private Button resetBtn;
    private ImageView img;
    private EditText description;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        img = (ImageView)findViewById(R.id.image);
        description = (EditText)findViewById(R.id.description);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        mProgressBar = (ProgressBar)findViewById(R.id.send_progress);


        File mFile = new File (getExternalFilesDir(null), "currentPicture.jpg");
        Glide.with(this).load(mFile).into(img);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendTask(img, description.getText().toString(), mProgressBar, new SendTask.OnSendListener() {
                    @Override
                    public void OnSuccess() {
                        Intent intent = new Intent(SendActivity.this, BottomNavActivity.class);
                        startActivity(intent);
                    }

                    public void OnFailure(){
                        Toast.makeText(getBaseContext(), R.string.send_fail, Toast.LENGTH_LONG).show();
                    }
                }).execute();
            }
        });

        resetBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

    }
}
package com.example.javakurssi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity2";
    private Button helloButton;
    private Button startButton;
    private TextView helloView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        helloView = (TextView) findViewById(R.id.helloView);
        helloButton = (Button) findViewById(R.id.helloButton);

        helloButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

    }

    public void handleOnClickEvents(View v) {
        //if else kokeile?
        switch(v.getId()){
            case R.id.helloButton:
                Log.d(TAG, "User tapped the Hello button");
                if(helloView.getVisibility() == View.GONE )
                { //ei ole constraintteja helloViewvissä joten muut elementit eivät liiku, gone toimii
                    helloView.setVisibility(View.VISIBLE);
                } else {
                    helloView.setVisibility(View.GONE);
                }
                break;
            case R.id.startButton:
                Log.d(TAG, "User tapped the Start Game button");
                startActivity(new Intent(MainActivity2.this, GameActivity.class));
                break;
        }
    }


}
package com.huzaifa.wallahabibi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Intermediate extends AppCompatActivity {

    Button letsGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate);

        letsGo=findViewById(R.id.continue_button_AI);
        letsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letsGo.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rounded_clicked_button_background));
                Intent intent=new Intent(Intermediate.this,homeScreen.class);
                startActivity(intent);
            }
        });

    }
}
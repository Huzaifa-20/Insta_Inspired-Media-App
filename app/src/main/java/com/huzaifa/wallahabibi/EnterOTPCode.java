package com.huzaifa.wallahabibi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EnterOTPCode extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_o_t_p_code);

        tv=findViewById(R.id.tv);
        tv.setText(getIntent().getStringExtra("PhoneNumber"));
    }
}
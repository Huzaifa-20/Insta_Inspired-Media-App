package com.huzaifa.wallahabibi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button continueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectViews();
        createClickableText();
    }

    //FUNCTION THAT ENABLES 'PRIVACY POLICY' & 'TERMS OF SERVICE' TEXT TO BE CLICKABLE//
    private void createClickableText() {
        String text="to the Privacy Policy and Terms of Service";
        SpannableString ss=new SpannableString(text);               //Convert simple string to spannable string first//

        ClickableSpan clickableSpan1=new ClickableSpan() {
            //Override on click method to do something once text is clicked//
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent=new Intent(MainActivity.this,PrivacyPolicyActivity.class);
                startActivity(intent);
            }

            //Override updateDrawState function to change text color and underline of text//
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan clickableSpan2=new ClickableSpan() {
            //Override on click method to do something once text is clicked//
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent=new Intent(MainActivity.this,TermsOfServiceActivity.class);
                startActivity(intent);
            }

            //Override updateDrawState function to change text color and underline of text//
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan1,7,14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2,26,42, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    //FUNCTION THAT CONNECTS XML TO JAVA FILE//
    private void connectViews() {
        tv=findViewById(R.id.textView_AM);
        continueButton=findViewById(R.id.continue_button_AM);
    }
}
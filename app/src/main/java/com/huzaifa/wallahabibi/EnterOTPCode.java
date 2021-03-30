package com.huzaifa.wallahabibi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterOTPCode extends AppCompatActivity {

    TextView numberHeading;
    TextView numberSubHeading;
    TextView resendCode;
    EditText otpCode;
    Button next;

    String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_o_t_p_code);

        connectViews();
        setListeners();
        createClickableText();

    }

    //SET ON CLICK LISTENERS//
    private void setListeners() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rounded_clicked_button_background));

                String code=otpCode.getText().toString();
                if(code.equals("")){
                    Toast.makeText(EnterOTPCode.this, "Enter OTP Code First", Toast.LENGTH_SHORT).show();
                }
                else if(code.length()!=6){
                    Toast.makeText(EnterOTPCode.this, "Incorrect OTP code length", Toast.LENGTH_SHORT).show();
                }
                else{
                    PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(EnterOTPCode.this, "Entering Home Screen", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(EnterOTPCode.this,MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(EnterOTPCode.this, "Invalid OTP code entered", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });
    }

    //FUNCTION THAT ENABLES 'PRIVACY POLICY' & 'TERMS OF SERVICE' TEXT TO BE CLICKABLE//
    private void createClickableText() {
        String text1=getIntent().getStringExtra("PhoneNumber")+". Wrong number?";
        SpannableString ss1=new SpannableString(text1);               //Convert simple string to spannable string first//

        String text2="Resend SMS";
        SpannableString ss2=new SpannableString(text2);               //Convert simple string to spannable string first//

        ClickableSpan clickableSpan1=new ClickableSpan() {
            //Override on click method to do something once text is clicked//
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent=new Intent(EnterOTPCode.this,EnterPhoneNumberActivity.class);
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
                //RESEND OTP CODE//
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        getIntent().getStringExtra("PhoneNumber"),
                        60,
                        TimeUnit.SECONDS,
                        EnterOTPCode.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(EnterOTPCode.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Toast.makeText(EnterOTPCode.this, "Sending OTP Code via SMS", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }

            //Override updateDrawState function to change text color and underline of text//
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        ss1.setSpan(clickableSpan1,15,28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(clickableSpan2,0,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        numberSubHeading.setText(ss1);
        resendCode.setText(ss2);
        numberSubHeading.setMovementMethod(LinkMovementMethod.getInstance());
        resendCode.setMovementMethod(LinkMovementMethod.getInstance());
    }

    //FUNCTION THAT CONNECTS XML TO JAVA FILE//
    private void connectViews() {
        numberHeading=findViewById(R.id.numberHeading_AEOTPC);
        numberHeading.setText(getIntent().getStringExtra("PhoneNumber"));
        numberSubHeading=findViewById(R.id.phoneNumberSubHeading_AEOTPC);
        numberSubHeading.setText(getIntent().getStringExtra("PhoneNumber")+". Wrong number?");
        otpCode=findViewById(R.id.otpCode_AEOTPC);
        resendCode=findViewById(R.id.resendCode_AEOTPC);
        next=findViewById(R.id.next_AEOTPC);

        verificationId=getIntent().getStringExtra("verificationId");
    }
}
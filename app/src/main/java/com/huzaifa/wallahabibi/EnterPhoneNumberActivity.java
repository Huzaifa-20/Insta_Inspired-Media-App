package com.huzaifa.wallahabibi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterPhoneNumberActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    TextView countryCode;
    EditText phoneNumber;
    Button next;

    String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone_number);

        connectViews();
        setListeners();
    }

    //SET ON CLICK LISTENERS//
    private void setListeners() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rounded_clicked_button_background));

                num=phoneNumber.getText().toString();
                if(num.length()!=10)
                {
                    Toast.makeText(EnterPhoneNumberActivity.this, "Enter Complete Phone Number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    num=countryCode.getText().toString()+phoneNumber.getText().toString();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            num,
                            60,
                            TimeUnit.SECONDS,
                            EnterPhoneNumberActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(EnterPhoneNumberActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Toast.makeText(EnterPhoneNumberActivity.this, "Sending OTP Code via SMS", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(EnterPhoneNumberActivity.this,EnterOTPCode.class);
                                    intent.putExtra("PhoneNumber",num);
                                    intent.putExtra("verificationId",verificationId);
                                    startActivity(intent);
                                }
                            }
                    );
                }
            }
        });
    }

    //FUNCTION THAT CONNECTS XML TO JAVA FILE//
    private void connectViews() {
        countryCode=findViewById(R.id.countryCode_AEPN);
        phoneNumber=findViewById(R.id.phoneNumber_AEPN);
        next=findViewById(R.id.next_AEPN);
        spinner=findViewById(R.id.countryName_AEPN);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.countries, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) {
            countryCode.setText("+93");
        }
        else if(position==1) {
            countryCode.setText("+86");
        }
        else if(position==2) {
            countryCode.setText("+20");
        }
        else if(position==3) {
            countryCode.setText("+91");
        }
        else if(position==4) {
            countryCode.setText("+92");
        }
        else if(position==5) {
            countryCode.setText("+1");
        }
        else if(position==6) {
            countryCode.setText("+44");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
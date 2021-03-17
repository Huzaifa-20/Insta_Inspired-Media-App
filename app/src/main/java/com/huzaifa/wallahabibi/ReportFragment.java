package com.huzaifa.wallahabibi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static android.app.Activity.RESULT_OK;

public class ReportFragment extends Fragment {

    ImageButton backArrow;
    Button report;
    Button feedback;
    EditText userInput;
    TextView attachments;
    EditText email_phone;

    Context c;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_report,container,false);
        c=container.getContext();

        connectViews(v);
        setListeners();
        return v;
    }

    private void connectViews(View v) {
        backArrow=v.findViewById(R.id.back_arrow_FR);
        report=v.findViewById(R.id.reportButton_FR);
        feedback=v.findViewById(R.id.feedbackButton_FR);
        userInput=v.findViewById(R.id.input_FR);
        attachments=v.findViewById(R.id.attachFiles_FR);
        email_phone=v.findViewById(R.id.email_phone_FR);
    }

    private void setListeners() {

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutFragment aboutFragment=new AboutFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_AHS,aboutFragment).commit();
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report.setTextColor(Color.parseColor("#000000"));
                feedback.setTextColor(Color.parseColor("#818181"));
                userInput.setHint("Details of your important feedback...");
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback.setTextColor(Color.parseColor("#000000"));
                report.setTextColor(Color.parseColor("#818181"));
                userInput.setHint("Details of your trouble as much as you can describe");
            }
        });

        attachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick an image"), 03);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 03 && resultCode == RESULT_OK && data != null) {
            Uri imageDataUri = data.getData();
            Toast.makeText(c, "Item Chosen", Toast.LENGTH_SHORT).show();
        }
    }
}

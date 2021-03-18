package com.huzaifa.wallahabibi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatScreen extends AppCompatActivity {

    ImageView backArrow;
    TextView name;
    CircleImageView contactImage;
    ImageView camera;
    EditText inputMessage;
    ImageView sendMessage;


    RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        connectViews();
        setListeners();
    }

    private void connectViews()
    {
        backArrow=findViewById(R.id.back_arrow_ACS);
        name=findViewById(R.id.username_ACS);
        contactImage=findViewById(R.id.profile_image_ACS);
        camera=findViewById(R.id.cameraIcon_ACS);
        inputMessage=findViewById(R.id.write_message_ACS);
        rv=findViewById(R.id.recycler_view_ACS);
        sendMessage=findViewById(R.id.sendMessageIcon_ACS);
    }

    private void setListeners()
    {
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChatScreen.this,homeScreen.class);
                startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick an image"), 03);
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageToSend=inputMessage.getText().toString();
                if(messageToSend!="" ){
                    inputMessage.setText("");
                    //sendingMessage(fuser.getUid(),userId,messageToSend,imageToSend);
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 03 && resultCode == RESULT_OK && data != null) {
            Uri imageDataUri = data.getData();
            //TODO Send picture as a message now//
            Toast.makeText(this, "send picture as a message now", Toast.LENGTH_SHORT).show();
        }
    }
}
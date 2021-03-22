package com.huzaifa.wallahabibi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatScreen extends AppCompatActivity {

    ImageView backArrow;
    TextView name;
    CircleImageView contactImage;
    EditText inputMessage;
    ImageView sendMessage;
    int position;

    private DatabaseReference reference;

    RecyclerView rv;
    private MessageAdapter messageAdapter;
    private List<Chat> mChat;

    ValueEventListener seenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        Intent intent=getIntent();
        position=intent.getIntExtra("contactNumber",0);

        connectViews();
        setListeners();
        readMessage(MainActivity.currentUser.getMyId(),MainActivity.chatContacts.get(position).getMyId());
        seenMessage(MainActivity.chatContacts.get(position).getMyId());
    }

    private void connectViews()
    {
        backArrow=findViewById(R.id.back_arrow_ACS);
        name=findViewById(R.id.username_ACS);
        contactImage=findViewById(R.id.profile_image_ACS);
        inputMessage=findViewById(R.id.write_message_ACS);
        sendMessage=findViewById(R.id.sendMessageIcon_ACS);

        rv=findViewById(R.id.recycler_view_ACS);
        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);

        name.setText(MainActivity.chatContacts.get(position).getName());
        Picasso.get().load(MainActivity.chatContacts.get(position).getProfileImage()).fit().centerCrop().into(contactImage);
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

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageToSend=inputMessage.getText().toString();
                if(messageToSend!="" )
                {
                    inputMessage.setText("");
                    sendingMessage(MainActivity.currentUser.getMyId(),MainActivity.chatContacts.get(position).getMyId(),messageToSend);
                }
            }
        });

    }

    private void sendingMessage(final String myId, final String userId, final String message){

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

        final HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",myId);
        hashMap.put("receiver",userId);
        hashMap.put("message",message);
        hashMap.put("seen",false);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(final String myId, final String userId){
        mChat=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren() ){
                    Chat chat=snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
                        mChat.add(chat);
                    }
                    messageAdapter=new MessageAdapter(ChatScreen.this,mChat);
                    rv.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void seenMessage(final String userId){
        reference=FirebaseDatabase.getInstance().getReference("Chats");
        seenListener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat=snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(MainActivity.currentUser.getMyId()) && chat.getSender().equals(userId)){
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("seen",true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
    }
}
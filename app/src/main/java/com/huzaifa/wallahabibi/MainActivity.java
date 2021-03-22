package com.huzaifa.wallahabibi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //<--------GLOBAL VARIABLES-------->//
    public static Profile currentUser;
    public static ArrayList<String> followers;
    public static ArrayList<String> following;
    public static List<ProfilePosts> posts;
    public static List<Profile> chatContacts;
    //<-------------------------------->//

    //<----FIREBASE VARIABLES---->//
    FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    //<-------------------------->//

    //<--------XML VIEWS-------->//
    TextView tv;
    Button continueButton;
    //<------------------------->//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectViews();
        setListeners();
        createClickableText();
    }

    //SET ON CLICK LISTENERS//
    private void setListeners() {
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,EnterPhoneNumberActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public void onStart() {
        super.onStart();

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        if(user!=null)
        {
            currentUser=new Profile();
            followers=new ArrayList<>();
            following=new ArrayList<>();
            posts=new ArrayList<>();
            chatContacts=new ArrayList<>();

            database=FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
            reference=database.getReference("Profiles");

            ExampleRunnable exampleRunnable=new ExampleRunnable();
            new Thread(exampleRunnable).start();

            Toast.makeText(MainActivity.this, " You're already signed in!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,homeScreen.class);
            startActivity(intent);
        }
    }

    //IMPLEMENTED FOR THREADING PART//
    class ExampleRunnable implements Runnable{

        ExampleRunnable(){

        }

        @Override
        public void run() {
            fetchData();
        }
    }

    private void fetchData() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Profile tempUser=new Profile(dataSnapshot.getValue(Profile.class));
                if(tempUser.getMyId().equals(user.getUid()))
                {
                    currentUser=new Profile(tempUser);

                    if(currentUser.getFollowers()!=null)
                    {
                        Iterator it = currentUser.getFollowers().entrySet().iterator();
                        while (it.hasNext())
                        {
                            Map.Entry pair = (Map.Entry)it.next();
                            followers.add(pair.getValue().toString());
                            it.remove(); // avoids a ConcurrentModificationException
                        }
                    }
                    if(currentUser.getFollowing()!=null)
                    {
                        Iterator it = currentUser.getFollowing().entrySet().iterator();
                        while (it.hasNext())
                        {
                            Map.Entry pair = (Map.Entry)it.next();
                            following.add(pair.getValue().toString());
                            it.remove(); // avoids a ConcurrentModificationException
                        }
                    }
                    if(currentUser.getPosts()!=null)
                    {
                        ArrayList<String> postUrls=new ArrayList<>();
                        Iterator it = currentUser.getPosts().entrySet().iterator();
                        while (it.hasNext())
                        {
                            Map.Entry pair = (Map.Entry)it.next();
                            postUrls.add(pair.getValue().toString());
                            it.remove(); // avoids a ConcurrentModificationException
                        }

                        for(int i=0;i<postUrls.size();i++)
                        {
                            if( i+2<=(postUrls.size()-1) )
                            {
                                posts.add(new ProfilePosts(postUrls.get(i), postUrls.get(i+2), postUrls.get(i+2)));
                                i+=2;
                            }
                            else if( i+1==(postUrls.size()-1) )
                            {
                                posts.add(new ProfilePosts(postUrls.get(i),postUrls.get(i+1)));
                                i++;
                            }
                            else if( i==(postUrls.size()-1) )
                            {
                                posts.add(new ProfilePosts(postUrls.get(i)));
                            }
                        }
                    }
                }
                else
                {
                    chatContacts.add(new Profile(tempUser));
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
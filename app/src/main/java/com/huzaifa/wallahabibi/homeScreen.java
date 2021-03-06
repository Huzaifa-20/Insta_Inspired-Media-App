package com.huzaifa.wallahabibi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class homeScreen extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        clearDataProfiles();
        Toast.makeText(this, "Loading Data...", Toast.LENGTH_SHORT).show();
        connectViews();
    }

    //FUNCTION THAT CONNECTS XML TO JAVA FILE//
    private void connectViews() {
        bottomNavigationView=findViewById(R.id.bottom_navigation_AHS);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_AHS,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;
            switch(item.getItemId())
            {
                case R.id.nav_home:
                    selectedFragment=new HomeFragment();
                    break;
                case R.id.nav_stories:
                    selectedFragment=new StoriesFragment();
                    break;
                case R.id.nav_chat:
                    selectedFragment=new ChatFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment=new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_AHS,selectedFragment).commit();
            return true;
        }
    };

    public static void clearDataProfiles()
    {
        for(int i=0;i<MainActivity.allChatContacts.size();i++)
        {
            if(MainActivity.followers.contains(MainActivity.allChatContacts.get(i).getMyId()) ||
                    MainActivity.following.contains(MainActivity.allChatContacts.get(i).getMyId()))
            {
                if(!MainActivity.chatContacts.contains(MainActivity.allChatContacts.get(i)))
                {
                    MainActivity.chatContacts.add(MainActivity.allChatContacts.get(i));
                    if(MainActivity.allChatContacts.get(i).getStories()!=null)
                    {
                        Iterator it =MainActivity.allChatContacts.get(i).getStories().entrySet().iterator();
                        while (it.hasNext())
                        {
                            Map.Entry pair = (Map.Entry)it.next();
                            MainActivity.allStories.add((Story) pair.getValue());
                            MainActivity.images.add(((Story) pair.getValue()).getStory());
                            MainActivity.users.add(((Story) pair.getValue()).getUserName());
                            it.remove(); // avoids a ConcurrentModificationException
                        }
                    }
                }
            }
        }
        String tempUrl="";
        String tempThumbnail="";

        for(int i=0;i<MainActivity.allPosts.size();i++)
        {
            if(MainActivity.allPosts.get(i).getType().equals("video"))
            {
                tempUrl=MainActivity.allPosts.get(i).getUrl();
                tempThumbnail=getThumbNail(tempUrl);

                MainActivity.videoPosts.add(new VideoPost());
            }
        }
    }

    public static String getThumbNail(String url){
        String thumbnail="";


        return thumbnail;
    }
}
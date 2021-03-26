package com.huzaifa.wallahabibi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.model.MyStory;

import static android.app.Activity.RESULT_OK;

public class StoriesFragment extends Fragment implements StoriesRvAdapter.OnStoryListener {


    Context c;
    View v;

    TextView stories;
    ImageButton add;
    Button update;

    private ArrayList<MyStory> myStories;
    private ArrayList<String> frontImages;
    private ArrayList<String> frontImagesUsers;
    private Uri imageDataUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_stories, container, false);
        c = container.getContext();

        connectViews(v);
        setListeners(v);
        initRV(v, c);
        return v;
    }

    private void setListeners(View view) {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 01);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.INVISIBLE);
                initRV(view,c);
            }
        });
    }

    private void initRV(View v, Context c) {
        RecyclerView rv = v.findViewById(R.id.stories_rv);

        getFrontImages();
        StoriesRvAdapter sada = new StoriesRvAdapter(c, frontImages, this);
        rv.setAdapter(sada);

        StaggeredGridLayoutManager stag = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(stag);
    }

    private void getFrontImages()
    {
        if(MainActivity.images.size()==1)
        {
            frontImages=MainActivity.images;
            frontImagesUsers=MainActivity.users;
        }
        else if(MainActivity.images.size()>1) {
            for (int i = 0; i < MainActivity.images.size() - 1; i++) {
                if (!MainActivity.users.get(i).equals(MainActivity.users.get(i + 1))) {
                    frontImages.add(MainActivity.images.get(i));
                    frontImagesUsers.add(MainActivity.users.get(i));
                    if (i == MainActivity.images.size() - 2) {
                        frontImages.add(MainActivity.images.get(i + 1));
                        frontImagesUsers.add(MainActivity.users.get(i + 1));
                    }
                }
                if(i==MainActivity.images.size()-2 && frontImages.size()==0)
                {
                    frontImages.add(MainActivity.images.get(i));
                    frontImagesUsers.add(MainActivity.users.get(i));
                }
            }
        }
    }

    private void connectViews(View v) {
        stories = v.findViewById(R.id.stories_stories);
        add = v.findViewById(R.id.stories_add_btn);
        update = v.findViewById(R.id.updateButton_FS);

        frontImages=new ArrayList<>();
        frontImagesUsers=new ArrayList<>();
    }

    @Override
    public void onStoryClick(int pos) {
        initStoryView(v, c, pos);
    }

    private void initStoryView(View v, Context c, int pos) {
        myStories = new ArrayList<>();
        myStories=getMyStories(pos);

        //TODO so our images will be like an obj of a class
        //      where class holds a thumbnail and ref to actual user's stories
        //      currently, the thumbnail itslef is being provided as story but later
        //      implement such that the myStories list is populated with correct stories.


        String name=frontImagesUsers.get(pos);
        String url="";
        if(name.equals(MainActivity.currentUser.getName()))
        {
            url=MainActivity.currentUser.getProfileImage();
        }
        else
        {
            for(int i=0;i<MainActivity.chatContacts.size();i++)
            {
                if(MainActivity.chatContacts.get(i).getName().equals(name))
                {
                    url=MainActivity.chatContacts.get(i).getProfileImage();
                }
            }
        }

        new StoryView.Builder(((FragmentActivity) c).getSupportFragmentManager())
                .setStoriesList(myStories)
                .setStoryDuration(5000)
                .setTitleText(name)
                .setSubtitleText("Dummy sub title")
                .setTitleLogoUrl(url)
                .build()
                .show();
    }

    private ArrayList<MyStory> getMyStories(int pos)
    {
        ArrayList<MyStory> myStories=new ArrayList<>();
        ArrayList<Integer> allIndexes=new ArrayList<>();
        String tappedName=frontImagesUsers.get(pos);

        for(int i=0;i<MainActivity.users.size();i++)
        {
            if(MainActivity.users.get(i).equals(tappedName))
            {
                allIndexes.add(i);
            }
        }

        for(int i=0;i<allIndexes.size();i++)
        {
            myStories.add(new MyStory(MainActivity.images.get(allIndexes.get(i))));
        }
        return myStories;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 01) {
            imageDataUri = data.getData();
//            Log.d("cheq1", "onActivityResult data imgDatauri: "+imageDataUri.toString());
            System.out.println("Working"+imageDataUri.toString()+" or not?");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("StoryImages/" + MainActivity.currentUser.getMyId()
                    +"_"+currentDateandTime+ ".jpg");
            storageReference.putFile(imageDataUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Profiles").
                                            child(MainActivity.currentUser.getMyId());

                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("userName", MainActivity.currentUser.getName());
                                    hashMap.put("story", uri.toString());
                                    hashMap.put("time", currentDateandTime);

                                    reference.child("stories").push().setValue(hashMap);
                                }
                            });
                        }
                    });
            Toast.makeText(c, "Story Posted!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchStories();
    }

    private void fetchStories()
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Profiles");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren() ){
                    Profile profile=snapshot.getValue(Profile.class);

                    if(profile.getStories()!=null)
                    {
                        Iterator it = profile.getStories().entrySet().iterator();
                        while (it.hasNext())
                        {
                            Map.Entry pair = (Map.Entry)it.next();
                            if(!(MainActivity.images.contains(((Story) pair.getValue()).getStory())))
                            {
                                MainActivity.images.add(((Story) pair.getValue()).getStory());
                                MainActivity.users.add(((Story) pair.getValue()).getUserName());
                                update.setVisibility(View.VISIBLE);
                            }
                            it.remove(); // avoids a ConcurrentModificationException
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

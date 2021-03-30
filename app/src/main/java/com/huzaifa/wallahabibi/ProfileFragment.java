package com.huzaifa.wallahabibi;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    ImageButton settings;
    ImageButton editProfile;
    ImageButton addImage;
    ImageButton addVideo;
    CircleImageView profilePicture;
    TextView name;
    TextView bio;
    TextView followers;
    TextView following;

    Uri imageUri;
    Uri videoUri;

    //<-----RECYCLER VIEW VARIABLES----->//
    RecyclerView rv;
    MyRvAdapter adapter;
    Context c;
    //<-------------------------------->//

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile,container,false);
        c=container.getContext();

        connectViews(v);
        setListeners();
        return v;
    }

    //SET ON CLICK LISTENERS//
    private void setListeners() {
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment=new SettingsFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_AHS,settingsFragment).commit();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditFragment editFragment=new EditFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_AHS,editFragment).commit();
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick an image"), 101);
            }
        });

        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 102);
            }
        });
    }

    //FUNCTION THAT CONNECTS XML TO JAVA FILE//
    private void connectViews(View v) {
        settings=v.findViewById(R.id.settings_FP);
        editProfile=v.findViewById(R.id.edit_FP);
        addImage=v.findViewById(R.id.addImage_FP);
        addVideo=v.findViewById(R.id.addVideo_FP);
        profilePicture=v.findViewById(R.id.profileImage_FP);
        name=v.findViewById(R.id.name_FP);
        bio=v.findViewById(R.id.bio_FP);
        followers=v.findViewById(R.id.followers_FP);
        following=v.findViewById(R.id.following_FP);

        Picasso.get().load(MainActivity.currentUser.getProfileImage()).fit().centerCrop().into(profilePicture);
        name.setText(MainActivity.currentUser.getName());
        bio.setText(MainActivity.currentUser.getBio());
        followers.setText(Integer.toString(MainActivity.currentUser.getTotalFollowers()));
        following.setText(Integer.toString(MainActivity.currentUser.getTotalFollowing()));

        rv=v.findViewById(R.id.rv_FP);
        RecyclerView.LayoutManager lm= new LinearLayoutManager(c);
        rv.setLayoutManager(lm);
        adapter = new MyRvAdapter(MainActivity.myProfilePosts,c);
        rv.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode== Activity.RESULT_OK && data.getData()!=null)
        {
            imageUri=data.getData();
            addPostToDatabase("image");

        }

        else if(requestCode==102 && resultCode== Activity.RESULT_OK && data.getData()!=null)
        {
            videoUri=data.getData();
            addPostToDatabase("video");
        }

    }

    private void addPostToDatabase(String postType)
    {
        String extension="";
        Uri uri=null;
        if(postType.equals("image")){
            extension=".jpg";
            uri=imageUri;
        }
        else if(postType.equals("video")){
            extension=".mp4";
            uri=videoUri;
        }
        String userId=MainActivity.currentUser.getMyId();

        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss", Locale.getDefault());
        String date = sdf.format(new Date());
        String likes="0";
        String shares="0";
        String views="0";

        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("posts/"+userId+"_"+date+extension);
        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                        System.out.println("\n<====================================== 2 =====================================>\n");

                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                System.out.println("\n<====================================== 3 =====================================>\n");
                                String url=uri.toString();
                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Profiles").child(userId);

                                HashMap<String,String> hashMap=new HashMap<>();
                                hashMap.put("url", url);
                                hashMap.put("type", postType);
                                hashMap.put("userId",userId);
                                hashMap.put("date",date);
                                hashMap.put("likes",likes);
                                hashMap.put("shares",shares);
                                hashMap.put("vews",views);

                                reference.child("posts").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        System.out.println("\n<====================================== 4 =====================================>\n");

                                        Toast.makeText(c, "Posted!", Toast.LENGTH_SHORT).show();
                                        MainActivity.myPosts.add(new Post(url,postType,userId,date,likes,shares,views));
                                        addToProfilePosts();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println("\n<====================================== -1 =====================================>\n");
                                        Toast.makeText(c, "Failed to post!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("\n<====================================== -2 =====================================>\n");
                        Toast.makeText(c, "Failed uploading to storage", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addToProfilePosts()
    {
        int i=MainActivity.myProfilePosts.size()-1;
        int j=MainActivity.myPosts.size()-1;
        System.out.println("\n<====================================== i: "+i+"=====================================>\n");
        System.out.println("\n<====================================== j: "+j+"=====================================>\n");
        if(i==-1)
        {
            MainActivity.myProfilePosts.add(new ProfilePosts(MainActivity.myPosts.get(j).getUrl(), MainActivity.myPosts.get(j).getType()));
        }
        else if(MainActivity.myProfilePosts.get(i).getSecondType().equals(""))
        {
            MainActivity.myProfilePosts.get(i).setSecondPost(MainActivity.myPosts.get(j).getUrl());
            MainActivity.myProfilePosts.get(i).setSecondType(MainActivity.myPosts.get(j).getType());
        }
        else if(MainActivity.myProfilePosts.get(i).getThirdType().equals(""))
        {
            MainActivity.myProfilePosts.get(i).setThirdPost(MainActivity.myPosts.get(j).getUrl());
            MainActivity.myProfilePosts.get(i).setThirdType(MainActivity.myPosts.get(j).getType());
        }
        else
        {
            MainActivity.myProfilePosts.add(new ProfilePosts(MainActivity.myPosts.get(j).getUrl(), MainActivity.myPosts.get(j).getType()));
        }
        adapter.notifyDataSetChanged();

    }
}

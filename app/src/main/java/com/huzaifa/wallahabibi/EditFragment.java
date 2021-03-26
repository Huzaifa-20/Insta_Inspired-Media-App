package com.huzaifa.wallahabibi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class EditFragment extends Fragment {

    ImageButton backArrow;
    CircleImageView profilePicture;
    private Uri imageDataUri;
    EditText name;
    EditText bio;
    EditText phoneNum;
    TextView update;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    Context c;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_edit,container,false);
        c=container.getContext();

        connectViews(v);
        setListeners();
        return v;
    }

    private void setListeners() {
        backArrow.setOnClickListener(v -> {
            ProfileFragment profileFragment=new ProfileFragment();
            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_AHS,profileFragment).commit();
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick an image"), 02);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=user.getUid();
                String userName=name.getText().toString();
                String shortBio=bio.getText().toString();
                String phone=phoneNum.getText().toString();

                if(imageDataUri!=null & userName!=null & shortBio!=null & phoneNum!=null){
                    StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("profileImages/"+userName+".jpg");
                    storageReference.putFile(imageDataUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                                    System.out.println("\n<====================================== 2 =====================================>\n");

                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            System.out.println("\n<====================================== 3 =====================================>\n");
                                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Profiles").child(user.getUid());
                                            String dp=uri.toString();

                                            HashMap<String,String> hashMap=new HashMap<>();
                                            hashMap.put("myId",id);
                                            hashMap.put("profileImage",dp);
                                            hashMap.put("name",userName);
                                            hashMap.put("Bio",shortBio);
                                            hashMap.put("phoneNumber",phone);

                                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    System.out.println("\n<====================================== 4 =====================================>\n");

                                                    Toast.makeText(c, "Profile edited!", Toast.LENGTH_SHORT).show();
                                                    ProfileFragment profileFragment=new ProfileFragment();
                                                    FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                                                    fragmentTransaction.replace(R.id.fragment_container_AHS,profileFragment).commit();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    System.out.println("\n<====================================== -1 =====================================>\n");
                                                    Toast.makeText(c, "Profile not edited!", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(c, "Failed uploading photo", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    Toast.makeText(c, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void connectViews(View v) {
        backArrow=v.findViewById(R.id.back_arrow_FE);
        profilePicture=v.findViewById(R.id.profileImage_FE);
        name=v.findViewById(R.id.name_FE);
        bio=v.findViewById(R.id.shortBio_FE);
        phoneNum=v.findViewById(R.id.phoneNumber_FE);
        update=v.findViewById(R.id.update_FE);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 02 && resultCode == RESULT_OK && data != null) {
            imageDataUri = data.getData();
            profilePicture.setImageURI(imageDataUri);
        }
    }
}

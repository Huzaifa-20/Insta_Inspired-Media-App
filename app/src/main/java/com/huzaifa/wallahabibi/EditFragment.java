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

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class EditFragment extends Fragment {

    ImageButton backArrow;
    CircleImageView profilePicture;
    EditText name;
    EditText bio;
    EditText phoneNum;
    TextView update;

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

        update.setOnClickListener(v -> Toast.makeText(c, "Update information on firebase", Toast.LENGTH_SHORT).show());
    }

    private void connectViews(View v) {
        backArrow=v.findViewById(R.id.back_arrow_FE);
        profilePicture=v.findViewById(R.id.profileImage_FE);
        name=v.findViewById(R.id.name_FE);
        bio=v.findViewById(R.id.shortBio_FE);
        phoneNum=v.findViewById(R.id.phoneNumber_FE);
        update=v.findViewById(R.id.update_FE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 02 && resultCode == RESULT_OK && data != null) {
            Uri imageDataUri = data.getData();
            profilePicture.setImageURI(imageDataUri);
        }
    }
}

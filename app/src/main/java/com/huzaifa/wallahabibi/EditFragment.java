package com.huzaifa.wallahabibi;

import android.content.Context;
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

public class EditFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ImageButton backArrow;
    CircleImageView profilePicture;
    EditText name;
    EditText bio;
    EditText phoneNum;
    Spinner spinner;
    TextView update;

    Context c;
    String newCountry;
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

        update.setOnClickListener(v -> Toast.makeText(c, "Update information on firebase", Toast.LENGTH_SHORT).show());
    }

    private void connectViews(View v) {
        backArrow=v.findViewById(R.id.back_arrow_FE);
        profilePicture=v.findViewById(R.id.profileImage_FE);
        name=v.findViewById(R.id.name_FE);
        bio=v.findViewById(R.id.shortBio_FE);
        phoneNum=v.findViewById(R.id.phoneNumber_FE);
        spinner=v.findViewById(R.id.countryName_FE);
        update=v.findViewById(R.id.update_FE);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(c,R.array.countries, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //set this as country on firebase//
        if(position==0) {
            newCountry="Afghanistan";
        }
        else if(position==1) {
            newCountry="China";       
        }
        else if(position==2) {
            newCountry="Egypt";      
        }
        else if(position==3) {
            newCountry="India";        
        }
        else if(position==4) {
            newCountry="Pakistan";     
        }
        else if(position==5) {
            newCountry="USA";
        }
        else if(position==6) {
            newCountry="UK";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

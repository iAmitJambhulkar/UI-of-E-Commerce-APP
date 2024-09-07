package com.example.innoval.Activity.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.innoval.Activity.LoginActivity;
import com.example.innoval.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView logOutTxt, name , email, Sname;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    public ProfileFragment() {}


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);




       }
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        ListView listView1 = rootView.findViewById(R.id.listView);


        String arr[] = {"Orders", "Wishlist", "Coupon Quest", "Help Centre", "Customer Care", "Address","Manage Account", "Settings"};



        ArrayAdapter ad = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1 , arr);
        listView1.setAdapter(ad);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "clicked:"+position, Toast.LENGTH_SHORT).show();
            }
        });




        // logout button
        email = rootView.findViewById(R.id.email);
        name = rootView.findViewById(R.id.name);
        Sname = rootView.findViewById(R.id.Sname);
        logOutTxt = rootView.findViewById(R.id.logOutTxt);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        gsc = GoogleSignIn.getClient(getContext(),gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if(account != null){
            String Name = account.getDisplayName();
            String Mail = account.getEmail();

            name.setText(Name);
            email.setText(Mail);
        }

        logOutTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOut();
            }
        });

        String fullName = name.getText().toString().trim();
        String[] nameParts = fullName.split("\\s+");
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        String initials = firstName.substring(0,1) + (lastName.isEmpty() ? "" : lastName.substring(0,1));
        Sname.setText(initials.toUpperCase());


        return rootView;
    }

    private void SignOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getActivity().finish();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }
}
package com.example.innoval.Activity.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.innoval.Adapter.CartListAdapter;
import com.example.innoval.Adapter.PopularListAdapter;
import com.example.innoval.Domain.PopularDomain;
import com.example.innoval.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {
    private RecyclerView.Adapter adapterPopular;
    private RecyclerView recyclerViewPopular;
    ArrayList<PopularDomain> itemList;
    private SearchView searchView;
    private TextView username;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
     private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ExploreFragment() {
        // Required empty public constructor
    }

    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);

        username = rootView.findViewById(R.id.username);
        itemList = new ArrayList<>(); // Initialize itemList here

        searchView = rootView.findViewById(R.id.searchView);

        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapterPopular instanceof CartListAdapter) {
                    filterList(newText, (CartListAdapter) adapterPopular);
                }
                return true;
            }

        });



        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        gsc = GoogleSignIn.getClient(getContext(),gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if(account != null){
            String Name = account.getDisplayName();
             username.setText(Name);
        }


        initRecyclerView(rootView);

        return rootView;
    }



    private void filterList(String text, CartListAdapter adapter) {
        List<PopularDomain> filteredList = new ArrayList<>();

        for (PopularDomain item : itemList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }





    private void initRecyclerView(View rootView) {
        itemList = new ArrayList<>();
        itemList.add(new PopularDomain("Apple 2022 MacBook Air Laptop M2 chip","Improved chip: The M2 chip is 1.4 times faster than the M1 chip, with a 18% faster CPU, 35% more powerful GPU, and 40% faster Neural Engine.\n" +
                "Better display: The 2022 MacBook Air M2 has an upgraded display with a larger size, brighter display, and smaller bezels.\n" +
                "Improved webcam: The 2022 MacBook Air M2 has an improved webcam.\n"
                ,"c",11,4.0,77,1));
        itemList.add(new PopularDomain("PS-5 Digital","What are the features of PlayStation 5?\n" +
                "PlayStation®5 | Play Has No Limits | PlayStation\n" +
                "Experience lightning-fast loading with an ultra-high speed SSD, deeper immersion with support for haptic feedback1, " +
                "adaptive triggers1 and 3D Audio*, and an all-new generation of incredible PlayStation games." +
                "*3D audio via built-in TV speakers or analog/USB stereo headphones.", "ps",18,4.3,69,2));
        itemList.add(new PopularDomain("iphone 15 pro max","The iPhone 15 has an upgraded dual-camera system with a 48-megapixel main camera and a 12-megapixel ultra wide camera. The main camera takes 24 MP images by default," +
                " and produces more detailed images in different lighting conditions.\n" +
                "The iPhone 15 has a battery made from 100% recycled cobalt.\n" +
                "The iPhone 15 is splash, water, and dust resistant with an IP68 rating. " +
                "It can be submerged in water up to 6 meters deep for up to 30 minutes.\n" +
                "The iPhone 15 has USB-C instead of Lightning.\n", "iphone", 15, 4.9,33,3));

        // Initialize RecyclerView
        recyclerViewPopular = rootView.findViewById(R.id.view1); // Assuming the RecyclerView is in the fragment's layout
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        adapterPopular = new PopularListAdapter(itemList);
        recyclerViewPopular.setAdapter(adapterPopular);
    }
}
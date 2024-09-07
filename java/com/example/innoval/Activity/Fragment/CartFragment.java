package com.example.innoval.Activity.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.innoval.Adapter.CartListAdapter;
import com.example.innoval.Helper.ChangeNumberItemsListener;
import com.example.innoval.Helper.ManagementCart;
import com.example.innoval.R;

public class CartFragment extends Fragment {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ManagementCart managementCart;
    private TextView subTotalFeeTxt, totalTaxTxt, deliveryTxt, OtotalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;
    private ImageView backBtn;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CartFragment() {}
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        managementCart = new ManagementCart(getContext()); // or requireContext() if outside lifecycle methods

        initView();
        setVariable();
        initList();
        calculateCart();
    }

    private void initView() {
        subTotalFeeTxt = getView().findViewById(R.id.subtotalrate);
        totalTaxTxt = getView().findViewById(R.id.totaltaxrate);
        deliveryTxt = getView().findViewById(R.id.deliveryrate);
        OtotalTxt = getView().findViewById(R.id.totalrate);
        recyclerView = getView().findViewById(R.id.rView);
        scrollView = getView().findViewById(R.id.scrollView2);
        backBtn = getView().findViewById(R.id.backBtn); // Correct initialization
        emptyTxt = getView().findViewById(R.id.emptyTxt);
    }


    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), getContext(), new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calculateCart();
            }
        });

        recyclerView.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calculateCart() {
        double percentTax = 0.02;
        double delivery = 50;
        tax = Math.round((managementCart.getTotalFee()* percentTax *100.0)) / 100.0;

        double total = Math.round((managementCart.getTotalFee() + tax + delivery)* 100)/ 100;
        double itemTotal = Math.round(managementCart.getTotalFee()*100) / 100 ;

        subTotalFeeTxt.setText("$"+ itemTotal);
        totalTaxTxt.setText("$"+tax);
        deliveryTxt.setText("$"+delivery);
        OtotalTxt.setText("$"+total);
    }
    private void setVariable() {

    }

}

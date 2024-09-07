package com.example.innoval.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.innoval.Adapter.CartListAdapter;
import com.example.innoval.Helper.ChangeNumberItemsListener;
import com.example.innoval.Helper.ManagementCart;
import com.example.innoval.R;

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ManagementCart managementCart;
    private TextView subTotalFeeTxt, totalTaxTxt, deliveryTxt, OtotalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managementCart = new ManagementCart(this);

        initView();
        setVariable();
        initList();
        calculateCart();
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calculateCart();
            }
        });

        recyclerView.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()){
                emptyTxt.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
        }else{
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
        backBtn.setOnClickListener(v -> finish());
    }
    private void initView() {
        subTotalFeeTxt = findViewById(R.id.subtotalrate);
        totalTaxTxt = findViewById(R.id.totaltaxrate);
        deliveryTxt = findViewById(R.id.deliveryrate);
        OtotalTxt = findViewById(R.id.totalrate);
        recyclerView = findViewById(R.id.rView);
        scrollView = findViewById(R.id.scrollView2);
        backBtn = findViewById(R.id.backBtn);
        emptyTxt = findViewById(R.id.emptyTxt);
    }
}
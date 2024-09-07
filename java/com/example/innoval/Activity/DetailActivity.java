package com.example.innoval.Activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.innoval.Domain.PopularDomain;
import com.example.innoval.Helper.ManagementCart;
import com.example.innoval.R;

public class DetailActivity extends AppCompatActivity {
    private TextView titleText, feeTxt, descriptionTxt,reviewTxt,scoreTxt;
    private ImageView picFood, backBtn;
    private Button addToCartBtn;
    private PopularDomain object;
    private ImageView shareBtn;
    private int numberOrder = 1;
    private ManagementCart managementCart;
    private Context context;
//    private boolean flag= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        managementCart = new ManagementCart(DetailActivity.this);
        getBundle();

    }

    private void getBundle() {
        object = (PopularDomain) getIntent().getSerializableExtra("object");
        int drawableResourceId = this.getResources().getIdentifier(object.getPicUrl(), "drawable",this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picFood);

        titleText.setText(object.getTitle());
        feeTxt.setText("$"+ object.getPrice());
        descriptionTxt.setText(object.getDescription());
        reviewTxt.setText(object.getReview()+"");
        scoreTxt.setText(object.getScore()+"");

        addToCartBtn.setOnClickListener(v -> {
            object.setNumberInCart(numberOrder);
            managementCart.insertFood(object);
        });
        backBtn.setOnClickListener(v -> finish());

    }

    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleText = findViewById(R.id.titleTxtD);
        feeTxt = findViewById(R.id.priceText);
        descriptionTxt = findViewById(R.id.descriptiontext);
        reviewTxt = findViewById(R.id.reviewTxt);
        scoreTxt = findViewById(R.id.scoreTxt);
        picFood = findViewById(R.id.productimg);
        backBtn = findViewById(R.id.backBtn);
        shareBtn = findViewById(R.id.shareBtn);

    }


}





//textView = findViewById(R.id.about);
//        textView.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        if(flag ){
//        textView.setMaxLines(2);
//        flag=false;
//        }else {
//        textView.setMaxLines(200);
//        flag=true;
//        }
//        }
//        });
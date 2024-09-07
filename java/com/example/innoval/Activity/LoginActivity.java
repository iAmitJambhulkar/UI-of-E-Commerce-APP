package com.example.innoval.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.innoval.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView signUpBtn, signInBtn; // top view switch button

    private ViewSwitcher viewSwitcher;

    // signup page
    TextInputEditText signupEmail, signupPassword; // singUp edit text

    TextView getStarted, loginRedirectText;



    //sign in page
    TextInputEditText signInEmail, signInPassword;
    TextView login, signupRedirectText;


    TextView continueWithGoogleBtn, continueWithGoogleBtn2 ;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//------------------------------------------------------------------------------------------------------------------//
        //VIEW SWITCHER CONDITIONS

        // view switcher find view by id
        signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);
        viewSwitcher = findViewById(R.id.viewSwitcher);

        final int[] currentViewId = {R.id.signInLayout};


        Animation enterAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        enterAnimation.setDuration(280);

        Animation exitAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        exitAnimation.setDuration(280);

        viewSwitcher.setInAnimation(enterAnimation);
        viewSwitcher.setOutAnimation(exitAnimation);

        signUpBtn.setBackgroundColor(Color.rgb(227, 230, 237));


        signInBtn.setOnClickListener(v -> {
            if (currentViewId[0] != R.id.signUpLayout) {
                viewSwitcher.showNext();
                currentViewId[0] = R.id.signUpLayout;
            }
            signInBtn.setBackgroundColor(Color.rgb(227, 230, 237));
            signUpBtn.setBackgroundColor(Color.TRANSPARENT);



        });

        signUpBtn.setOnClickListener(v -> {
            if (currentViewId[0] != R.id.signInLayout) {
                viewSwitcher.showPrevious();
                currentViewId[0] = R.id.signInLayout;
            }
            signUpBtn.setBackgroundColor(Color.rgb(227, 230, 237));
            signInBtn.setBackgroundColor(Color.TRANSPARENT);



        });
//----------------------------------------------------------------------------------------------------------//
        //SIGN UP CONDITIONS

        // sign up find view by id
        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        getStarted = findViewById(R.id.getStarted);
        loginRedirectText = findViewById(R.id.loginRedirectText);



        loginRedirectText.setOnClickListener(v -> {
            if (currentViewId[0] != R.id.signUpLayout) {
                viewSwitcher.showNext();
                currentViewId[0] = R.id.signUpLayout;
            }
        });





//---------------------------------------------------------------------------------------------------------------//
        //SIGN IN CONDITIONS

        // sign in find view by id
        signInEmail = findViewById(R.id.signInEmail);
        signInPassword = findViewById(R.id.signInPassword);
        login = findViewById(R.id.login);
        signupRedirectText = findViewById(R.id.signupRedirectText);



        signupRedirectText.setOnClickListener(v -> {
            if (currentViewId[0] != R.id.signInLayout) {
                viewSwitcher.showPrevious();
                currentViewId[0] = R.id.signInLayout;
            }
        });

       

//---------------------------------------------------------------------------------------------------------------//
        //GOOGLE AUTHENTICATIONS
        continueWithGoogleBtn = findViewById(R.id.continueWithGoogleBtn);
        continueWithGoogleBtn2 = findViewById(R.id.continueWithGoogleBtn2);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        gsc = GoogleSignIn.getClient(this,gso);
        gsc = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        continueWithGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
        continueWithGoogleBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });












//--------------------------------------------------------------------------------------------------------------------//
    }

    // GOOGLE AUTHENTICATION
    private void SignIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent,100);

    }
    // this is i have created
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                MainActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void MainActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}



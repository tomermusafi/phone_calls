package com.musafi.phone_calls.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.musafi.phone_calls.R;
import com.musafi.phone_calls.Utils.MySharedPreferences;

public class loginActivity extends AppCompatActivity {

    private EditText login_edt_name;
    private EditText login_edt_number;
    private Button login_btn_login;
    private MySharedPreferences msp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        msp = new MySharedPreferences(this);


        if(!msp.getString("name", "none").equals("none")){
            gotoMainActivity();
        }

        findViews();

        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msp.putString("name", login_edt_name.getText().toString());
                msp.putString("number", login_edt_number.getText().toString());
                msp.putInt("counter", 0);
                gotoMainActivity();
            }
        });
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void findViews(){
        login_edt_number = findViewById(R.id.login_edt_number);
        login_edt_name = findViewById(R.id.login_edt_name);
        login_btn_login = findViewById(R.id.login_btn_login);
    }



}

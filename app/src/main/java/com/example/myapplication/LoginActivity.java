package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    public static final String USER_NAME = "com.example.myapplication.DATA";

    public String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        // EditText editUserName = findViewById(R.id.userName);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView editUserName = findViewById(R.id.userName);
                TextView nameView = findViewById(R.id.nameView);
                nameView.setText("Login ID?");

                String stringEditUserName = editUserName.getText().toString();
                SharedPreferences preferences = getSharedPreferences("USERDATA", Context.MODE_PRIVATE);

                // search for the user name typed in the USERDATA
                String nameString = preferences.getString("account", null);
                if (stringEditUserName.equals(nameString)) {
                    nameView.setText("Hello " + nameString);
                    username = nameString;
                    Intent intent = new Intent(getApplication(), ScrollingActivity.class);
                    intent.putExtra("USER_NAME", username);
                    startActivity(intent);
                }
                else {
                    nameView.setText("Can't find your account with the ID that you typed.\n Please input the correct one or sign up if you don't have your account.");
                }
            }
        });

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
                String nameString = preferences.getString("account", null);
                SharedPreferences.Editor editor = preferences.edit();

                TextView editUserName = findViewById(R.id.userName);
                TextView nameView = findViewById(R.id.nameView);
                String stringEditUserName = editUserName.getText().toString();

                if (stringEditUserName == null || stringEditUserName.equals("")) {
                    nameView.setText("Please enter your ID.");
                }
                else if (nameString.equals(stringEditUserName)) {
                    nameView.setText("This ID has already been used.");
                }
                else {
                    editor.putString("account", stringEditUserName);
                    editor.apply();

                    TextView textView = findViewById(R.id.messageLoginText);
                    nameView.setText("Hello " + stringEditUserName);
                    textView.setText("Please click the back button if it doesn't go back automatically.");
                    username = stringEditUserName;

                    Intent intent = new Intent(getApplication(), ScrollingActivity.class);
                    intent.putExtra("USER_NAME", username);
                    startActivity(intent);
                }
            }
        });


        final Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ScrollingActivity.class);
                // intent.putExtra("USER_NAME", username);
                startActivity(intent);
            }
        });
    }
}
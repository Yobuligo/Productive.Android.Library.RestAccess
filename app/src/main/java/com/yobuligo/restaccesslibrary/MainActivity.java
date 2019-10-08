package com.yobuligo.restaccesslibrary;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.yobuligo.restaccess.api.AuthorizationRequestConfig;
import com.yobuligo.restaccess.api.IAuthorizationRequestConfig;
import com.yobuligo.restaccess.api.RestAccess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                IAuthorizationRequestConfig authorizationRequestConfig = new AuthorizationRequestConfig(
                        "http://10.0.2.2:8080/auth/realms/demo/protocol/openid-connect/auth",
                        "http://10.0.2.2:8080/auth/realms/demo/protocol/openid-connect/token",
                        "login-app"
                );

                RestAccess restAccess = new RestAccess(authorizationRequestConfig, view.getContext());
                restAccess.login();
            }
        });
    }

}

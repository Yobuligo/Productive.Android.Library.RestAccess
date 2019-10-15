package com.yobuligo.restaccess.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import com.yobuligo.restaccess.internal.DataContext;
import com.yobuligo.restaccess.internal.IDataContext;
import com.yobuligo.restaccess.internal.Login;
import com.yobuligo.restaccess.internal.Logout;
import com.yobuligo.restaccess.internal.SendRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RestAccess implements IRestAccess {
    private IAuthorizationRequestConfig authorizationRequestConfig;
    private IWebserviceRequestConfig webserviceRequestConfig;
    private IDataContext dataContext;
    private Context context;
    private ArrayList<ILoginListener> loginListeners = new ArrayList<>();
    private ArrayList<ILogoutListener> logoutListeners = new ArrayList<>();

    public RestAccess(IAuthorizationRequestConfig authorizationRequestConfig, IWebserviceRequestConfig webserviceRequestConfig, Context context) {
        this.authorizationRequestConfig = authorizationRequestConfig;
        this.webserviceRequestConfig = webserviceRequestConfig;
        this.context = context;
    }

    @Override
    public void login() {
        Login login = new Login(getDataContext());
        registerLoginAsBroadcastReceiver(login);
        login.execute();
        onLoginCompleted();
    }

    @Override
    public void logout() {
        Logout logout = new Logout(getDataContext());
        logout.execute();
        onLogoutCompleted();
    }

    @Override
    public void sendRequest(String path) {
        SendRequest sendRequest = new SendRequest(getDataContext());
        sendRequest.execute(path);
    }

    @Override
    public void setOnLoginListener(ILoginListener loginListener) {
        loginListeners.add(loginListener);
    }

    @Override
    public void setOnLogoutListener(ILogoutListener logoutListener) {
        logoutListeners.add(logoutListener);
    }

    private IDataContext getDataContext() {
        if (dataContext == null) {
            dataContext = new DataContext(authorizationRequestConfig, webserviceRequestConfig, context);
        }

        return dataContext;
    }

    private void registerLoginAsBroadcastReceiver(BroadcastReceiver broadcastReceiver) {
        Context context = getDataContext().getContext();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IDataContext.HANDLE_AUTHORIZATION_RESPONSE);
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void onLoginCompleted() {
        for (ILoginListener loginListener : loginListeners) {
            loginListener.onLogin();
        }
    }

    private void onLogoutCompleted() {
        for (ILogoutListener logoutlistener : logoutListeners) {
            logoutlistener.onLogout();
        }
    }
}
package com.yobuligo.restaccess.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import com.yobuligo.restaccess.internal.DataContext;
import com.yobuligo.restaccess.internal.IDataContext;
import com.yobuligo.restaccess.internal.Login;
import com.yobuligo.restaccess.internal.Logout;

public class RestAccess implements IRestAccess {
    private IAuthorizationRequestConfig authorizationRequestConfig;
    private Context context;
    private IDataContext dataContext;

    public RestAccess(IAuthorizationRequestConfig authorizationRequestConfig, Context context) {
        this.authorizationRequestConfig = authorizationRequestConfig;
        this.context = context;
    }

    @Override
    public void login() {
        Login login = new Login(getDataContext());
        registerLoginAsBroadcastReceiver(login);
        login.execute();
    }

    @Override
    public void logout() {
        Logout logout = new Logout(getDataContext());
        logout.execute();
    }

    @Override
    public void sendRequest() {

    }

    private IDataContext getDataContext() {
        if (dataContext == null) {
            dataContext = new DataContext(authorizationRequestConfig, context);
        }

        return dataContext;
    }

    private void registerLoginAsBroadcastReceiver(BroadcastReceiver broadcastReceiver){
        Context context = getDataContext().getContext();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IDataContext.HANDLE_AUTHORIZATION_RESPONSE);
        context.registerReceiver(broadcastReceiver, intentFilter);
    }
}
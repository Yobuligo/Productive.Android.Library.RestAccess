package com.yobuligo.restaccess.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import com.yobuligo.restaccess.internal.DataContext;
import com.yobuligo.restaccess.internal.IDataContext;
import com.yobuligo.restaccess.internal.ILogout;
import com.yobuligo.restaccess.internal.Login;
import com.yobuligo.restaccess.internal.Logout;

public class RestAccess implements IRestAccess {
    private IAuthorizationRequestConfig authorizationRequestConfig;
    private IDataContext dataContext;
    private Context context;

    public RestAccess(IAuthorizationRequestConfig authorizationRequestConfig, Context context) {
        this.authorizationRequestConfig = authorizationRequestConfig;
        this.context = context;
    }

    public Login createLogin() {
        return new Login(getDataContext());
    }

    public void executeLogin(Login login) {
        login.execute();
    }

    @Override
    public void login() {
        Login login = new Login(getDataContext());
        registerLoginToBroadcastReceiver(login);
        login.execute();
    }

    @Override
    public void logout() {
        ILogout logout = new Logout(getDataContext());
        logout.execute();
    }

    @Override
    public void sendRequest() {

    }

    private void registerLoginToBroadcastReceiver(BroadcastReceiver broadcastReceiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IDataContext.HANDLE_AUTHORIZATION_RESPONSE);
        Context context = getDataContext().getContext();
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    private IDataContext getDataContext() {
        if (dataContext == null) {
            dataContext = new DataContext(authorizationRequestConfig, context);
        }

        return dataContext;
    }
}
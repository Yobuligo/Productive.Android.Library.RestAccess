package com.yobuligo.restaccess.api;

import android.content.Context;

import com.yobuligo.restaccess.internal.DataContext;
import com.yobuligo.restaccess.internal.IDataContext;
import com.yobuligo.restaccess.internal.ILogin;
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

    public Login createLogin(){
        return new Login(getDataContext());
    }

    public void executeLogin(Login login){
        login.execute();
    }

    @Override
    public void login() {
        ILogin login = new Login(getDataContext());
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

    private IDataContext getDataContext() {
        if (dataContext == null) {
            dataContext = new DataContext(authorizationRequestConfig, context);
        }

        return dataContext;
    }
}
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

    public RestAccess(IAuthorizationRequestConfig authorizationRequestConfig) {
        this.authorizationRequestConfig = authorizationRequestConfig;
    }

    public Login createLogin(Context context){
        return new Login(getDataContext(), context);
    }

    public void executeLogin(Login login){
        login.execute();
    }

    @Override
    public void login(Context context) {
        ILogin login = new Login(getDataContext(), context);
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
            dataContext = new DataContext(authorizationRequestConfig);
        }

        return dataContext;
    }
}
package com.yobuligo.restaccess.internal;

import android.content.Context;

import com.yobuligo.restaccess.api.IAuthorizationRequestConfig;

import net.openid.appauth.TokenResponse;

public class DataContext implements IDataContext {
    private IAuthorizationRequestConfig authorizationRequestConfig;
    private TokenResponse tokenResponse;
    private Context context;

    public DataContext(IAuthorizationRequestConfig authorizationRequestConfig, Context context) {
        this.authorizationRequestConfig = authorizationRequestConfig;
        this.context = context;
    }

    @Override
    public IAuthorizationRequestConfig getAuthorizationRequestConfig() {
        return authorizationRequestConfig;
    }

    @Override
    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    @Override
    public Context getContext() {
        return context;
    }
}
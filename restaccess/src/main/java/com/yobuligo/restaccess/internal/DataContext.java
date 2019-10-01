package com.yobuligo.restaccess.internal;

import com.yobuligo.restaccess.api.IAuthorizationRequestConfig;

public class DataContext implements IDataContext {
    private IAuthorizationRequestConfig authorizationRequestConfig;

    public DataContext(IAuthorizationRequestConfig authorizationRequestConfig) {
        this.authorizationRequestConfig = authorizationRequestConfig;
    }

    @Override
    public IAuthorizationRequestConfig getAuthorizationRequestConfig() {
        return authorizationRequestConfig;
    }
}
package com.yobuligo.restaccess.internal;

import android.content.Context;
import android.text.TextUtils;

import com.yobuligo.restaccess.api.IAuthorizationRequestConfig;
import com.yobuligo.restaccess.api.IWebserviceRequestConfig;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationService;

import org.json.JSONException;

public class DataContext implements IDataContext {
    private IAuthorizationRequestConfig authorizationRequestConfig;
    private IWebserviceRequestConfig webserviceRequestConfig;
    private Context context;
    private AuthorizationService authorizationService;
    private AuthState authState;

    public DataContext(IAuthorizationRequestConfig authorizationRequestConfig, IWebserviceRequestConfig webserviceRequestConfig, Context context) {
        this.authorizationRequestConfig = authorizationRequestConfig;
        this.webserviceRequestConfig = webserviceRequestConfig;
        this.context = context;
    }

    @Override
    public IAuthorizationRequestConfig getAuthorizationRequestConfig() {
        return authorizationRequestConfig;
    }

    @Override
    public IWebserviceRequestConfig getWebserverRequestConfig() {
        return webserviceRequestConfig;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public AuthorizationService getAuthorizationService() {
        if (authorizationService == null) {
            authorizationService = new AuthorizationService(getContext());
        }

        return authorizationService;
    }

    @Override
    public AuthState restoreAuthState() {
        if (authState != null) {
            return authState;
        }

        Context context = getContext();
        String jsonString = context.getSharedPreferences(IDataContext.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getString(IDataContext.AUTH_STATE, null);
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                return AuthState.fromJson(jsonString);
            } catch (JSONException jsonException) {
                // should never happen
            }
        }
        return null;
    }

    @Override
    public void setAuthState(AuthState authState) {
        this.authState = authState;
    }
}
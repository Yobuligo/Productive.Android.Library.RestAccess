package com.yobuligo.restaccess.internal;

import android.content.Context;

import com.yobuligo.restaccess.api.IAuthorizationRequestConfig;
import com.yobuligo.restaccess.api.IWebserviceRequestConfig;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.TokenResponse;

/**
 * Object that contains all relevant runtime data
 */
public interface IDataContext {
    String LOG_TAG = "AppAuth";
    String SHARED_PREFERENCES_NAME = "AuthStatePreference";
    String AUTH_STATE = "AUTH_STATE";
    String USED_INTENT = "USED_INTENT";
    String HANDLE_AUTHORIZATION_RESPONSE = "com.yobuligo.restaccess.internal.HANDLE_AUTHORIZATION_RESPONSE";

    IAuthorizationRequestConfig getAuthorizationRequestConfig();
    IWebserviceRequestConfig getWebserverRequestConfig();
    Context getContext();
    AuthorizationService getAuthorizationService();
    AuthState restoreAuthState();
    void setAuthState(AuthState authState);
}
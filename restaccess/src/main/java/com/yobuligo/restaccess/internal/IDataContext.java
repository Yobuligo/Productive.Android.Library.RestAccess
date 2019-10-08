package com.yobuligo.restaccess.internal;

import android.content.Context;

import com.yobuligo.restaccess.api.IAuthorizationRequestConfig;

import net.openid.appauth.TokenResponse;

/**
 * Object that contains all relevant runtime data
 */
public interface IDataContext {
    String LOG_TAG = "AppAuth";
    String SHARED_PREFERENCES_NAME = "AuthStatePreference";
    String AUTH_STATE = "AUTH_STATE";
    String USED_INTENT = "USED_INTENT";
    String HANDLE_AUTHORIZATION_RESPONSE = "HANDLE_AUTHORIZATION_RESPONSE";

    IAuthorizationRequestConfig getAuthorizationRequestConfig();
    TokenResponse getTokenResponse();
    Context getContext();
}
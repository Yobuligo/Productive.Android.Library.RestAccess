package com.yobuligo.restaccess.internal;

import com.yobuligo.restaccess.api.IAuthorizationRequestConfig;

/**
 * Object that contains all relevant runtime data
 */
public interface IDataContext {
    IAuthorizationRequestConfig getAuthorizationRequestConfig();
}
package com.yobuligo.restaccess.api;

import android.content.Context;

/**
 * IRestAccess is a facade to access all possible functions of this api
 */
public interface IRestAccess {
    /**
     * Method to display a login screen and login the user by user credentials
     */
    void login();

    /**
     * Method to logout the current user
     */
    void logout();

    /**
     * Method to send a rest request with the credentials of the logged in user
     */
    void sendRequest(String path);
}
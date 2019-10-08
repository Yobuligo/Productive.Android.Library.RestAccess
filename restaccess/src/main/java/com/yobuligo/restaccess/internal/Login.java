package com.yobuligo.restaccess.internal;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.TokenResponse;

import org.json.JSONException;

public class Login extends BroadcastReceiver implements ILogin {

    private AuthState mAuthState;
    private IDataContext dataContext;
    private Context context;

    public Login(){}

    public Login(IDataContext dataContext, Context context) {
        this.dataContext = dataContext;
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        checkIntent(intent);
    }

    @Override
    public void execute() {

        AuthorizationServiceConfiguration serviceConfiguration = new AuthorizationServiceConfiguration(
                Uri.parse(dataContext.getAuthorizationRequestConfig().getAuthEndpointUri()),
                Uri.parse(dataContext.getAuthorizationRequestConfig().getTokenEndpointUri())
        );

        String clientId = dataContext.getAuthorizationRequestConfig().getClientId();
        Uri redirectUri = Uri.parse("com.yobuligo.restaccess.internal:/oauth2callback");
        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(
                serviceConfiguration,
                clientId,
                AuthorizationRequest.RESPONSE_TYPE_CODE,
                redirectUri
        );

        builder.setScopes("openid profile email");
        AuthorizationRequest request = builder.build();

        AuthorizationService authorizationService = new AuthorizationService(context);
        String action = "com.yobuligo.restaccess.internal.HANDLE_AUTHORIZATION_RESPONSE";
        Intent postAuthorizationIntent = new Intent(action);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, request.hashCode(), postAuthorizationIntent, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request.hashCode(), postAuthorizationIntent, 0);
        authorizationService.performAuthorizationRequest(request, pendingIntent);
    }

    private void handleAuthorizationResponse(@NonNull Intent intent) {
        AuthorizationResponse response = AuthorizationResponse.fromIntent(intent);
        AuthorizationException error = AuthorizationException.fromIntent(intent);
        final AuthState authState = new AuthState(response, error);

        if (response != null) {
            Log.i(IDataContext.LOG_TAG, String.format("Handled Authorization Response %s ", authState.toString()));
            AuthorizationService service = new AuthorizationService(context);

            service.performTokenRequest(response.createTokenExchangeRequest(), new AuthorizationService.TokenResponseCallback() {
                @Override
                public void onTokenRequestCompleted(@Nullable TokenResponse tokenResponse, @Nullable AuthorizationException exception) {
                    if (exception != null) {
                        Log.w(IDataContext.LOG_TAG, "Token Exchange failed", exception);
                    } else {
                        if (tokenResponse != null) {
                            authState.update(tokenResponse, exception);
                            persistAuthState(authState);
                            Log.i(IDataContext.LOG_TAG, String.format("Token Response [ Access Token: %s, ID Token: %s ]", tokenResponse.accessToken, tokenResponse.idToken));
                        }
                    }
                }
            });
        }
    }

    private void checkIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            switch (action) {
                case "com.yobuligo.restaccess.internal.HANDLE_AUTHORIZATION_RESPONSE":
                    if (!intent.hasExtra(IDataContext.USED_INTENT)) {
                        handleAuthorizationResponse(intent);
                        intent.putExtra(IDataContext.USED_INTENT, true);
                    }
                    break;
                default:
                    // do nothing
            }
        }
    }

    private void persistAuthState(@NonNull AuthState authState) {
        context.getSharedPreferences(IDataContext.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putString(IDataContext.AUTH_STATE, authState.toJsonString())
                .commit();
        enablePostAuthorizationFlows();
    }

    @Nullable
    private AuthState restoreAuthState() {
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

    private void enablePostAuthorizationFlows() {
        mAuthState = restoreAuthState();
    }
}
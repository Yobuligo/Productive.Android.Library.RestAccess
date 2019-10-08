package com.yobuligo.restaccess.internal;

import android.content.Context;

public class Logout implements ILogout {
    private IDataContext dataContext;

    public Logout(IDataContext dataContext) {
        this.dataContext = dataContext;
    }

    @Override
    public void execute() {
        Context context = dataContext.getContext();
        context.getSharedPreferences(IDataContext.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove(IDataContext.AUTH_STATE)
                .apply();
    }
}
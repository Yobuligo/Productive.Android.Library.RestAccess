package com.yobuligo.restaccess.internal;

public class Logout implements ILogout {
    private IDataContext dataContext;

    public Logout(IDataContext dataContext) {
        this.dataContext = dataContext;
    }

    @Override
    public void execute() {

    }
}
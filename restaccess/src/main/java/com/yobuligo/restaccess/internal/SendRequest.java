package com.yobuligo.restaccess.internal;

public class SendRequest implements ISendRequest {
    private IDataContext dataContext;

    public SendRequest(IDataContext dataContext) {
        this.dataContext = dataContext;
    }

    @Override
    public void execute(String path) {

    }
}

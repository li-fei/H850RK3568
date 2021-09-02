package com.yuneec.command;

public class BaseResponse {

    public BaseResponse(int mCommandId) {
        this.commandId = mCommandId;
    }

    protected int commandId;
    public byte[] responseData;


    public void trans(byte[] data) {
        responseData = new byte[data.length];
        System.arraycopy(data, 0, responseData, 0, responseData.length);
    }

    public int getCommandId() {
        return commandId;
    }


}

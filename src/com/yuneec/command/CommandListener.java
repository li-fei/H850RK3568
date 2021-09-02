package com.yuneec.command;

public class CommandListener {
    private long sendTimeStamp;

    /**
     * Set the timestamp of send this command.
     *
     * @param timeStamp millisecond of System time.
     */
    public void setSendTimeStamp(long timeStamp) {
        sendTimeStamp = timeStamp;
    }

    /**
     * Get the timestamp of send this command.
     *
     * @return the timestamp of send this command.
     */
    public long getSendTimestamp() {
        return sendTimeStamp;
    }

    /**
     * Called when command execute success.
     *
     * @param response the response of command.
     */
    public void onSuccess(BaseResponse response) {

    }

    public void onStartSend() {

    }

    /**
     * Called when command execute timeout. the default value of timeout is 2 second.
     */
    public void onTimeout() {

    }

    /**
     * Occur some errors when command execute.
     *
     * @param errorCode The reason of error.
     */
    public void onError(int errorCode) {

    }
}

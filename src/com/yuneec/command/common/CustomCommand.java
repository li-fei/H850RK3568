package com.yuneec.command.common;

public class CustomCommand extends BaseCmd {

    private int cmd;

    public CustomCommand(int cmd){
        this.cmd = cmd;
    }

    @Override
    public int getCommandId() {
        return cmd;
    }

    @Override
    public byte[] toRawData() {
        return super.toRawData();
    }

    @Override
    public boolean needResend() {
        return true;
    }
}

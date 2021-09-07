package com.yuneec.command.common;

public class CustomCommand extends BaseCommand {

    private int funcID;

    public CustomCommand(int funcID){
        this.funcID = funcID;
    }

    @Override
    public int getFuncId() {
        return funcID;
    }

    @Override
    public int getCommandId() {
        return super.getCommandId();
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

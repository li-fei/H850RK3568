package com.yuneec.command.common;

import com.yuneec.command.FUNC;

public class TestStartCommand extends BaseCommand {

    @Override
    public byte[] getDataCont() {
        return new byte[0];
    }

    @Override
    public int getFuncId() {
        return FUNC.CMD_TEST_START;
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

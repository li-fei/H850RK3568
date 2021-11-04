package com.yuneec.command.common;

import com.yuneec.command.FUNC;

public class AllTestCommand extends BaseCommand {

    @Override
    public int getFuncId() {
        return FUNC.ALL_TEST;
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
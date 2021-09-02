package com.yuneec.command.common;

import com.yuneec.command.COMMAND;
import com.yuneec.command.common.BaseCmd;

public class WifiCommand extends BaseCmd {

    @Override
    public int getCommandId() {
        return COMMAND.WIFI;
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

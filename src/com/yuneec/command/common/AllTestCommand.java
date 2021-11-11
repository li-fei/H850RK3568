package com.yuneec.command.common;

import com.yuneec.command.FUNC;

public class AllTestCommand extends BaseCommand {

    byte[] dataCont;

    public AllTestCommand(String ip){
        byte[] ipData = ip.getBytes();
        dataCont = new byte[ipData.length + 1];
        System.arraycopy(ipData,0,dataCont,0,ipData.length);
    }

    public byte[] getDataCont(){
        return dataCont;
    }

    @Override
    public int getFuncId() {
        return FUNC.ALL_TEST;
    }

    @Override
    public int getTimeOut() {
        return 1000 * 180;
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

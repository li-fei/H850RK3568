package com.yuneec.utils;

import com.yuneec.Configs;
import com.yuneec.Global;
import com.yuneec.H850RK3568;
import com.yuneec.command.BaseResponse;
import com.yuneec.command.FUNC;
import com.yuneec.command.CommandListener;
import com.yuneec.views.InfoView;
import com.yuneec.views.LeftViewController;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class ADBUtils {

    private static ADBUtils instance;

    public static ADBUtils getInstance() {
        if (instance == null) {
            instance = new ADBUtils();
        }
        return instance;
    }

	public void startTest(){
		cmd_adbForward();
		SocketUtil.I().listening();
		H850RK3568.proSocket.setOpacity(1);
		InfoView.I().updateSocketStatus("正在通信链接...",Configs.yellow_color);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				SendPackage.I().sendCommand(FUNC.CMD_TEST_START,new CommandListener(){
					@Override
					public void onStartSend() {
						super.onStartSend();
					}
					@Override
					public void onSuccess(BaseResponse response) {
						super.onSuccess(response);
						LeftViewController.I().start();
						H850RK3568.proSocket.setOpacity(0);
						InfoView.I().updateSocketStatus("通信链接正常",Configs.green_color);
					}
					@Override
					public void onTimeout() {
						super.onTimeout();
						H850RK3568.proSocket.setOpacity(0);
						InfoView.I().updateSocketStatus("通信链接异常",Configs.red_color);
						LeftViewController.I().start();
					}
				});
			}
		}, 4000);
	}

	private class cmd_adbDevices_Runnable implements Runnable {
		@Override
		public void run() {
			cmd_adbDevices();
		}
	}

	public void listener_cmd_adbDevices(){
		ThreadPoolManage.I().startRunnable(new cmd_adbDevices_Runnable(),2000);
	}

	public String deviceName;
	public void cmd_adbDevices(){
		String command = "platform-tools/adb.exe devices";
		deviceName = null;
		String result = cmd(command);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (deviceName == null){
					Global.usbConnected = false;
					H850RK3568.labelusbStatus.setText("USB未连接!");
					H850RK3568.labelusbStatus.setTextFill(Color.web(Configs.white_color));
					LeftViewController.I().setNoTestInfo();
					InfoView.I().updateSocketStatus("通信未链接",Configs.white_color);
				}else {
					Global.usbConnected = true;
					H850RK3568.labelusbStatus.setText("USB已连接!");
					H850RK3568.labelusbStatus.setTextFill(Color.GREEN);
				}
			}
		});
	}

    public void cmd_adbForward() {
		String command = "platform-tools/adb.exe forward tcp:6666 tcp:6666";
		String result = cmd(command);
    }

    public String cmd(String cmd) {
        Process p;
		String line = null;
        try {
//			runCMD(cmd);
			/**/
            p = Runtime.getRuntime().exec(cmd);
//			p = Runtime.getRuntime().exec(new String[]{ "cmd", "/c", cmd});
//			String result = InputStream2String(p.getInputStream());
//			Log.I("cmd->" + cmd + "  ---> " + result);

            InputStream fis = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
				parseCMDInfo(line);
				Log.I("cmd->" + cmd + "  ---> " + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return line;
    }
	public static void runCMD(String cmd) {
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			SequenceInputStream sis = new SequenceInputStream(process.getInputStream(), process.getErrorStream());
			InputStreamReader isr = new InputStreamReader(sis, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			OutputStreamWriter osw = new OutputStreamWriter(process.getOutputStream());
			BufferedWriter bw = new BufferedWriter(osw);
			String line = null;
			while (null != (line = br.readLine())) {
				System.out.println(line);
			}
			process.destroy();
			br.close();
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String InputStream2String(InputStream inputStream){
		String result="";
		BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
		try {
			String temp="";
			while ((temp=br.readLine())!=null){
				result+=temp+"\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void parseCMDInfo(String line) {
		// System.out.println("cmd: "+line);
		if (line.endsWith("device")){
			deviceName = line;
		}
	}


}

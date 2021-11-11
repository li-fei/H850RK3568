package com.yuneec.utils;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Utils {

	public static boolean mouseLeftClick(MouseEvent e){
		return e.getButton().name().equals(MouseButton.PRIMARY.name());
	}
	
	public static boolean mouseMiddleClick(MouseEvent e){
		return e.getButton().name().equals(MouseButton.MIDDLE.name());
	}
	
	public static boolean mouseRightClick(MouseEvent e){
		return e.getButton().name().equals(MouseButton.SECONDARY.name());
	}

	public static String getHostIP(){
		creatFile();
		String tempIP = "127.0.0.1";
		try {
			tempIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		System.out.println(tempIP);
		try{
			Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			Enumeration<InetAddress> addrs;
			while (networks.hasMoreElements())
			{
				addrs = networks.nextElement().getInetAddresses();
				while (addrs.hasMoreElements())
				{
					ip = addrs.nextElement();
					if (ip != null
							&& ip instanceof Inet4Address
							&& ip.isSiteLocalAddress()
							&& !ip.getHostAddress().equals(tempIP))
					{
						return ip.getHostAddress();
					}
				}
			}
			return tempIP;
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	private static void creatFile(){
		File dir = new File("D:/log");
		if (!dir.exists()) {
			dir.mkdir();
		}
	}


}

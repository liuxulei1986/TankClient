package com.xulei.TankClient;

import java.io.IOException;
import java.util.Properties;


public class PropertyManager {   //Singleton Design Pattern
	
	static Properties props = new Properties();
	
	static{
		try {
			props.load(PropertyManager.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getProperty(String key)
	{	
		
		return props.getProperty(key);
	}

}

/*	private static PropertyMgr mgr = null;

	private PropertyMgr(){
	
							}

	public PropertyMgr getInstance(){
	if(mgr==null){
		mgr = new PropertyMgr();
	}
	return mgr;
}*/
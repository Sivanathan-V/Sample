package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public final class ConfigurationSupport {
	static Properties props = new Properties();
	String strFileName;
	String strValue;

	
	public String getProperty(String strKey) {
		try {
			File f = new File(strFileName);
			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				props.load(in);
				strValue = props.getProperty(strKey);
				in.close();
			} 
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("property file not found");
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("can not read property");
		}
		return strValue;
	}

	public void setProperty(String strKey, String strValue) throws Throwable {
		try {
			File f = new File(strFileName);
			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				props.load(in);
				props.setProperty(strKey, strValue);
				props.store(new FileOutputStream(strFileName), null);
				
				in.close();
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("property file not found");
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("can not read property");
		}
	}

	public void removeProperty(String strKey) {
		try {
			File f = new File(strFileName);
			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				props.load(in);
				props.remove(strKey);
				props.store(new FileOutputStream(strFileName), null);
				in.close();
			} else
				System.out.println("File not found!");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public ConfigurationSupport(String strFileName) {
		this.strFileName = strFileName;
	}
	
	public void clean()
	{
		props.clear();
	}
}
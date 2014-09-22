package com.example.ourblackbox2;


public class BEmergencySetting {
	
	protected static String emerNum;
	protected static String contactMethod;
	protected static String emerMessage;
	
	public BEmergencySetting(){
		
	}
	
	public void setEmerNum(String s)
	{
		emerNum = s; 
	}
	
	public void setContactMethod(String s)
	{
		contactMethod = s;
	}
	
	public void setMessage(String s)
	{
		emerMessage = s;
	}
	
	public String getContactMethod()
	{
		return contactMethod;
	}
	
	public String getEmerNum()
	{
		return emerNum; 
	}
	
	public String getMessage()
	{
		return emerMessage;
	}
}

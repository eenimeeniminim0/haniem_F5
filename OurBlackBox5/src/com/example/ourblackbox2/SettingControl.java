package com.example.ourblackbox2;

import android.util.Log;

public class SettingControl {
	
	Command slot;
	
	public SettingControl(){}
	
	public void setCommand(Command command){
		slot=command;
	}
	
	public void selected(){
		
		slot.execute();
	}
	
	public void unSelected(){
		slot.undo();
	}

}

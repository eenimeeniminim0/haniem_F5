package com.example.ourblackbox2;

import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

public class BTimer {
	
	Chronometer chron;
	
	public void start(){
		chron.start();
	}
	
	public void stop(){
		chron.stop();
		chron.setBase(SystemClock.elapsedRealtime());
	}
	
	public void print(){
		long current= SystemClock.elapsedRealtime()-chron.getBase();
		int time=(int)(current/1000);
		
		int hout=time/(60*60);
		int min=time%(60*60)/60;
		int sec=time*60;
	}

}

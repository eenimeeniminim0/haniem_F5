package com.example.ourblackbox2;

import android.os.Handler;
import android.util.Log;

public class BThreadRecorder  {
	
	int i=0;
	private Thread videotimerUpdate;
	private Handler videotimerUpdateHandler;
	private BRecorder bRecorder;
	protected static boolean isTimeChange;
	
	
	public BThreadRecorder()
	{
		bRecorder=new BRecorder();
		videotimerUpdateHandler=new Handler();
		
	}
	public void threadStart()
	{
		Log.v("스레드님제발요?","울고싶다?="+20000);
		videotimerUpdate= new Thread(new Runnable(){
			int i=0;
			public void run(){   
	
				if(BRecorder.videoCurrentTime < checkThreadTime(BSensor.isSensorDetected)){
					Log.v("충격 감지시 돌아가는 시간은요?","궁금합니당="+BRecorder.SECONDS_BETWEEN_VIDEO);
					Log.v("스레드님","몇번돌아갔나요="+i);

					if(BRecorder.videoCurrentTime==0){
						bRecorder.startRecorder();
						bRecorder.isVideotimerRunning=true;
					}
					Log.v("videoCurrentTime","time="+BRecorder.videoCurrentTime);
					BRecorder.videoCurrentTime++;
					videotimerUpdateHandler.postDelayed(videotimerUpdate, 1000);
					
				}else{
					bRecorder.resetRecorder();
					BSensor.isSensorDetected=false;
					BRecorder.isTimeChange=false;
					i++;
					videotimerUpdateHandler.post(videotimerUpdate);		
				}
			}
		});
		videotimerUpdate.start();
	}
	
	public void threadStop()
	{
		if(videotimerUpdate != null && videotimerUpdate.isAlive() ||bRecorder.isVideotimerRunning)//만약 비디오스레드가 돌아가고있으면
		{
			Log.v("여기로 가긴 하는거니??","울고싶다?="+20000);
			videotimerUpdateHandler.removeCallbacks(videotimerUpdate);//스레드강제중지	
			bRecorder.isVideotimerRunning=false;//비디오스레드가 종료되었음
			videotimerUpdate.interrupt();
			bRecorder.stopRecorder();
			BSensor.isSensorDetected=false;
		}

	}

	
	public int checkThreadTime(boolean isSensorDetected)
	{
		if(isSensorDetected && BRecorder.SECONDS_BETWEEN_VIDEO==15){
			BRecorder.isTimeChange=true;
			BSensor.isSensorDetected=false;
			BRecorder.SECONDS_BETWEEN_VIDEO=BRecorder.videoCurrentTime+15;
				return BRecorder.SECONDS_BETWEEN_VIDEO;
		}
		else if(isSensorDetected && BRecorder.SECONDS_BETWEEN_VIDEO!=15){
			BRecorder.isTimeChange=true;
			BSensor.isSensorDetected=false;
			BRecorder.SECONDS_BETWEEN_VIDEO=BRecorder.videoCurrentTime+15;
				return BRecorder.SECONDS_BETWEEN_VIDEO;
		}
		else if(!isSensorDetected && BRecorder.SECONDS_BETWEEN_VIDEO!=15){
			if(BRecorder.isTimeChange){
			//isTimeChange=false;
				return BRecorder.SECONDS_BETWEEN_VIDEO;
		}
		else{	
			BRecorder.SECONDS_BETWEEN_VIDEO=15;
				return BRecorder.SECONDS_BETWEEN_VIDEO;
		}
	}
	
	else 
		return BRecorder.SECONDS_BETWEEN_VIDEO;

	}
	
	public BRecorder getBRecorder(){
		return bRecorder;
	}
}




package com.example.ourblackbox2;

import android.os.Handler;
import android.util.Log;

public class BThreadRecorder  {
	
	int i=0;
	private Thread videotimerUpdate;
	private Handler videotimerUpdateHandler;
	private BRecorder bRecorder;
	private BIOstream biostream;
	static  boolean isTimeChange;
	protected  int SECONDS_BETWEEN_VIDEO=15;//동영상 녹화 간격
	protected  int videoCurrentTime;//처음 시작 시간
	protected  int REC_PERIOD;


	
	public BThreadRecorder()
	{
		initBThreadRecorder();
		
	}
	
	public synchronized void initBThreadRecorder(){
		bRecorder=new BRecorder();
		biostream=new BIOstream(); 
		videotimerUpdateHandler=new Handler();
		videoCurrentTime=0;
		isTimeChange=false;
	}
	

	//동영상 녹화시간 설정 메소드
	
	public void setRecPeriod()
	{
		if(BRecordingSetting.recPeriod.equals("1min"))
			REC_PERIOD=5;
		
		else if(BRecordingSetting.recPeriod.equals("3min"))
			REC_PERIOD=10;

		else
			REC_PERIOD=15;
		
	}
	public synchronized void threadStart()
	{
		setRecPeriod();
		Log.v("스레드님제발요?","울고싶다?="+20000);
		videotimerUpdate= new Thread(new Runnable(){
			
			int i=0;
			//bTimer.start();
	
			public void run(){   
	
				if(videoCurrentTime <= checkThreadTime(BSensor.isSensorDetected)){
					Log.v("충격 감지시 돌아가는 시간은요?","궁금합니당="+SECONDS_BETWEEN_VIDEO);
					Log.v("스레드님","몇번돌아갔나요="+i);

					if(videoCurrentTime==0){
						bRecorder.initRecorder();
						bRecorder.startRecorder();
						bRecorder.isVideotimerRunning=true;
					}
					Log.v("videoCurrentTime","time="+videoCurrentTime);
					videoCurrentTime++;
					videotimerUpdateHandler.postDelayed(videotimerUpdate, 1000);
					
					biostream.rename();
					

				}else{
					bRecorder.resetRecorder();
					videoCurrentTime=0;
					BSensor.isSensorDetected=false;
					isTimeChange=false;
					i++;
					videotimerUpdateHandler.post(videotimerUpdate);	
					
				}
			}
		});
		videotimerUpdate.start();
	}
	
	public synchronized void threadStop()
	{
		if(videotimerUpdate != null && videotimerUpdate.isAlive() ||bRecorder.isVideotimerRunning)//만약 비디오스레드가 돌아가고있으면
		{
			Log.v("여기로 가긴 하는거니??","울고싶다?="+20000);
			videotimerUpdateHandler.removeCallbacks(videotimerUpdate);//스레드강제중지	
			bRecorder.isVideotimerRunning=false;//비디오스레드가 종료되었음
			videotimerUpdate.interrupt();
			bRecorder.stopRecorder();
			videoCurrentTime=0;
			BSensor.isSensorDetected=false;
		}

	}
	
	public int checkThreadTime(boolean isSensorDetected)
	{
		if(isSensorDetected && SECONDS_BETWEEN_VIDEO==REC_PERIOD){
			isTimeChange=true;
			BSensor.isSensorDetected=false;
			SECONDS_BETWEEN_VIDEO=videoCurrentTime+REC_PERIOD;
				return SECONDS_BETWEEN_VIDEO;
		}
		else if(isSensorDetected && SECONDS_BETWEEN_VIDEO!=REC_PERIOD){
			isTimeChange=true;
			BSensor.isSensorDetected=false;
			SECONDS_BETWEEN_VIDEO=videoCurrentTime+REC_PERIOD;
				return SECONDS_BETWEEN_VIDEO;
		}
		else if(!isSensorDetected && SECONDS_BETWEEN_VIDEO!=REC_PERIOD){
			if(isTimeChange){
				return SECONDS_BETWEEN_VIDEO;
			}else{	
				SECONDS_BETWEEN_VIDEO=REC_PERIOD;
					return SECONDS_BETWEEN_VIDEO;
			}
		}
		else 
			return SECONDS_BETWEEN_VIDEO;
	}
	
	
	public BRecorder getBRecorder(){
		return bRecorder;
	}
	
}




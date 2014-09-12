package com.example.ourblackbox2;

import android.content.Intent;
import android.net.Uri;
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
	
	
	public BThreadRecorder()
	{
		bRecorder=new BRecorder();
		biostream=new BIOstream(); 
		videotimerUpdateHandler=new Handler();
		videoCurrentTime=0;
		isTimeChange=false;
		
	}
	public void threadStart()
	{
		Log.v("스레드님제발요?","울고싶다?="+20000);
		videotimerUpdate= new Thread(new Runnable(){
			int i=0;
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
					//fileScan();
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
	
	public void threadStop()
	{
		if(videotimerUpdate != null && videotimerUpdate.isAlive() ||bRecorder.isVideotimerRunning)//만약 비디오스레드가 돌아가고있으면
		{
			Log.v("여기로 가긴 하는거니??","울고싶다?="+20000);
			videotimerUpdateHandler.removeCallbacks(videotimerUpdate);//스레드강제중지	
			bRecorder.isVideotimerRunning=false;//비디오스레드가 종료되었음
			videotimerUpdate.interrupt();
			bRecorder.stopRecorder();
			//fileScan();
			videoCurrentTime=0;
			BSensor.isSensorDetected=false;
		}

	}

	
	public int checkThreadTime(boolean isSensorDetected)
	{
		if(isSensorDetected && SECONDS_BETWEEN_VIDEO==15){
			isTimeChange=true;
			BSensor.isSensorDetected=false;
			SECONDS_BETWEEN_VIDEO=videoCurrentTime+15;
				return SECONDS_BETWEEN_VIDEO;
		}
		else if(isSensorDetected && SECONDS_BETWEEN_VIDEO!=15){
			isTimeChange=true;
			BSensor.isSensorDetected=false;
			SECONDS_BETWEEN_VIDEO=videoCurrentTime+15;
				return SECONDS_BETWEEN_VIDEO;
		}
		else if(!isSensorDetected && SECONDS_BETWEEN_VIDEO!=15){
			if(isTimeChange){
				return SECONDS_BETWEEN_VIDEO;
			}else{	
				SECONDS_BETWEEN_VIDEO=15;
					return SECONDS_BETWEEN_VIDEO;
			}
		}
		else 
			return SECONDS_BETWEEN_VIDEO;
	}
	
	public BRecorder getBRecorder(){
		return bRecorder;
	}
	
	public Intent fileScan()
	{
		Intent intent =new Intent(Intent.ACTION_MEDIA_MOUNTED); //패스 선언을 이 클래스에서!!
		Uri uri= Uri.parse("file://"+BRecorder.Folder);
		intent.setData(uri);
		return intent;
		
		//sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		
	}
}




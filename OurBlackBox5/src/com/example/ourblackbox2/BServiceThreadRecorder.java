package com.example.ourblackbox2;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BServiceThreadRecorder {


	int i=0;
	private Thread videotimerUpdate;
	private Handler videotimerUpdateHandler;
	private BServiceRecorder bServiceRecorder;
	protected  boolean isTimeChange;
	protected  int SECONDS_BETWEEN_VIDEO=15;//동영상 녹화 간격
	protected  int videoCurrentTime;//처음 시작 시간
	
	private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
	
	public BServiceThreadRecorder(SurfaceView sv)
	{
		initServiceThreadRecorder(sv);
		
		
		
	}
	public synchronized void initServiceThreadRecorder(SurfaceView sv){
		
		mSurfaceView=sv;
		bServiceRecorder=new BServiceRecorder(mSurfaceView);
		videotimerUpdateHandler=new Handler();
		videoCurrentTime=0;
		isTimeChange=false;
		
	}
	
	public synchronized void threadStart()
	{
		Log.v("서비스쓰레드","스레드시작");
		videotimerUpdate= new Thread(new Runnable(){
			int i=0;
			public void run(){   
	
				if(videoCurrentTime <= checkThreadTime(BSensor.isSensorDetected)){
					//Log.v("충격 감지시 돌아가는 시간은요?","궁금합니당="+SECONDS_BETWEEN_VIDEO);
					//Log.v("스레드님","몇번돌아갔나요="+i);

					if(videoCurrentTime==0){
						bServiceRecorder.initRecorder();
						bServiceRecorder.startRecorder();
						bServiceRecorder.isVideotimerRunning=true;
					}
					Log.v("videoCurrentTime","time="+videoCurrentTime);
					videoCurrentTime++;
					videotimerUpdateHandler.postDelayed(videotimerUpdate, 1000);
					
				}else{
					bServiceRecorder.resetRecorder();
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
	
	public synchronized void threadStop()
	{
		if(videotimerUpdate != null && videotimerUpdate.isAlive() ||bServiceRecorder.isVideotimerRunning)//만약 비디오스레드가 돌아가고있으면
		{
			Log.v("서비스스레드","스레드스탑");
			videotimerUpdateHandler.removeCallbacks(videotimerUpdate);//스레드강제중지	
			bServiceRecorder.isVideotimerRunning=false;//비디오스레드가 종료되었음
			videotimerUpdate.interrupt();
			bServiceRecorder.stopRecorder();
			bServiceRecorder.destroyRecorder();
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
	
	public BServiceRecorder getBRecorder(){
		return bServiceRecorder;
	}
	
	public Intent fileScan()
	{
		Intent intent =new Intent(Intent.ACTION_MEDIA_MOUNTED); //패스 선언을 이 클래스에서!!
		Uri uri= Uri.parse("file://"+BRecorder.File);
		intent.setData(uri);
		return intent;
		
		//sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		
	}
	
}

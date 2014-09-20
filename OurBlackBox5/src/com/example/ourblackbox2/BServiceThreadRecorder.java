package com.example.ourblackbox2;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BServiceThreadRecorder {

	int i=0;
	private Thread videotimerUpdate;
	private Handler videotimerUpdateHandler;
	private BServiceRecorder bServiceRecorder;
	private BIOstream biostream;
	protected  static boolean isTimeChange;
	protected  int SECONDS_BETWEEN_VIDEO=15;//������ ��ȭ ����
	protected  int videoCurrentTime;//ó�� ���� �ð�
	
	private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
	
	public BServiceThreadRecorder(SurfaceView sv)
	{
		mSurfaceView=sv;
		bServiceRecorder=new BServiceRecorder(mSurfaceView);
		videotimerUpdateHandler=new Handler();
		biostream=new BIOstream();
		videoCurrentTime=0;
		isTimeChange=false;
		
		
	}
	public void threadStart(final Context appContext)
	{
		Log.v("���񽺾�����","���������");
		videotimerUpdate= new Thread(new Runnable(){
			int i=0;
			public void run(){   
	
				if(videoCurrentTime <= checkThreadTime(BSensor.isSensorDetected)){

					if(videoCurrentTime==0){
						bServiceRecorder.initRecorder();
						bServiceRecorder.startRecorder();
						bServiceRecorder.isVideotimerRunning=true;
					}
					Log.v("videoCurrentTime","time="+videoCurrentTime);
					videoCurrentTime++;
					videotimerUpdateHandler.postDelayed(videotimerUpdate, 1000);
					biostream.renameService();
					
				}else{
					bServiceRecorder.resetRecorder();
					videoCurrentTime=0;
					BSensor.isSensorDetected=false;
					isTimeChange=false;
					i++;
					videotimerUpdateHandler.post(videotimerUpdate);
					updateMediaScanMounted(appContext);
				}
			}
		});
		videotimerUpdate.start();
	}
	
	public void threadStop(Context appContext)
	{
		if(videotimerUpdate != null && videotimerUpdate.isAlive() ||bServiceRecorder.isVideotimerRunning)//���� ���������尡 ���ư���������
		{
			Log.v("���񽺽�����","�����彺ž");
			videotimerUpdateHandler.removeCallbacks(videotimerUpdate);//�����尭������	
			bServiceRecorder.isVideotimerRunning=false;//���������尡 ����Ǿ���
			videotimerUpdate.interrupt();
			bServiceRecorder.stopRecorder();
			bServiceRecorder.destroyRecorder();
			//fileScan();
			videoCurrentTime=0;
			BSensor.isSensorDetected=false;
			updateMediaScanMounted(appContext);
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
	
	
	public void updateMediaScanMounted(Context context) {
	    
		int version = android.os.Build.VERSION.SDK_INT;
		  
		  if (version > 17) {   
			    
		      
			  	File file = new File(BServiceRecorder.Path);
			    Uri uri = Uri.fromFile(file);
			    Intent scanFileIntent = new Intent(
			            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
			    context.sendBroadcast(scanFileIntent);
		      Log.v("���ο�Ƽ��Ƽ","����� �ȿ���?"+BRecorder.Path);
		  } else {
		   context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		  }
	}  
	
	
}

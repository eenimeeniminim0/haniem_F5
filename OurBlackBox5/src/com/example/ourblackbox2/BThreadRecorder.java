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
	protected  int SECONDS_BETWEEN_VIDEO=15;//������ ��ȭ ����
	protected  int videoCurrentTime;//ó�� ���� �ð�
	
	
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
		Log.v("����������߿�?","���ʹ�?="+20000);
		videotimerUpdate= new Thread(new Runnable(){
			int i=0;
			public void run(){   
	
				if(videoCurrentTime <= checkThreadTime(BSensor.isSensorDetected)){
					Log.v("��� ������ ���ư��� �ð�����?","�ñ��մϴ�="+SECONDS_BETWEEN_VIDEO);
					Log.v("�������","������ư�����="+i);

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
		if(videotimerUpdate != null && videotimerUpdate.isAlive() ||bRecorder.isVideotimerRunning)//���� ���������尡 ���ư���������
		{
			Log.v("����� ���� �ϴ°Ŵ�??","���ʹ�?="+20000);
			videotimerUpdateHandler.removeCallbacks(videotimerUpdate);//�����尭������	
			bRecorder.isVideotimerRunning=false;//���������尡 ����Ǿ���
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
		Intent intent =new Intent(Intent.ACTION_MEDIA_MOUNTED); //�н� ������ �� Ŭ��������!!
		Uri uri= Uri.parse("file://"+BRecorder.Folder);
		intent.setData(uri);
		return intent;
		
		//sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		
	}
}




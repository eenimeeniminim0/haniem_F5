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
		Log.v("����������߿�?","���ʹ�?="+20000);
		videotimerUpdate= new Thread(new Runnable(){
			int i=0;
			public void run(){   
	
				if(BRecorder.videoCurrentTime < checkThreadTime(BSensor.isSensorDetected)){
					Log.v("��� ������ ���ư��� �ð�����?","�ñ��մϴ�="+BRecorder.SECONDS_BETWEEN_VIDEO);
					Log.v("�������","������ư�����="+i);

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
		if(videotimerUpdate != null && videotimerUpdate.isAlive() ||bRecorder.isVideotimerRunning)//���� ���������尡 ���ư���������
		{
			Log.v("����� ���� �ϴ°Ŵ�??","���ʹ�?="+20000);
			videotimerUpdateHandler.removeCallbacks(videotimerUpdate);//�����尭������	
			bRecorder.isVideotimerRunning=false;//���������尡 ����Ǿ���
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




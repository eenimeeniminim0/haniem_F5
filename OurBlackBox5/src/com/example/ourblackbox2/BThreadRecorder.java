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
	protected  int SECONDS_BETWEEN_VIDEO=15;//������ ��ȭ ����
	protected  int videoCurrentTime;//ó�� ���� �ð�
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
	

	//������ ��ȭ�ð� ���� �޼ҵ�
	
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
		Log.v("����������߿�?","���ʹ�?="+20000);
		videotimerUpdate= new Thread(new Runnable(){
			
			int i=0;
			//bTimer.start();
	
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
		if(videotimerUpdate != null && videotimerUpdate.isAlive() ||bRecorder.isVideotimerRunning)//���� ���������尡 ���ư���������
		{
			Log.v("����� ���� �ϴ°Ŵ�??","���ʹ�?="+20000);
			videotimerUpdateHandler.removeCallbacks(videotimerUpdate);//�����尭������	
			bRecorder.isVideotimerRunning=false;//���������尡 ����Ǿ���
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




package com.example.ourblackbox2;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RecordingActivity extends ActionBarActivity implements OnClickListener, SensorEventListener  {
	
	private BRecorder bRecorder;
	private ToggleButton VideoCapture, SnapShot;
	private Button Tools;
	//private Thread videotimerUpdate;
	//private Handler videotimerUpdateHandler;
	private BSensor bSensor;
	private BThredRecorder bThread;

	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Ÿ��Ʋ ���ֱ�
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȭ��Ǯ��ũ��
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ȭ�� ���η� ����
        
        setContentView(R.layout.recording);//���ڵ� ���̾ƿ�
		Toast.makeText(getApplicationContext(), "���ڵ���Ƽ��Ƽ ����", Toast.LENGTH_SHORT).show();
		
		//videotimerUpdateHandler=new Handler();
		
		bRecorder=new BRecorder();
		bSensor=new BSensor();
		bThread=new BThredRecorder();


		
		BSurfaceView.bSurface = (BSurfaceView)findViewById(R.id.CameraPreview);		
		VideoCapture=(ToggleButton)findViewById(R.id.VideoCapture);
        SnapShot=(ToggleButton)findViewById(R.id.Snapshot);
        Tools=(Button)findViewById(R.id.Tools);

        VideoCapture.setOnClickListener(this);
        SnapShot.setOnClickListener(this);
        Tools.setOnClickListener(this);
        
      //��������
        BSensor.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        BSensor.accelerormeterSensor = BSensor.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
	}
	

    @Override
    public void onClick(View v) {
    	
    	switch(v.getId()){
    	
    	case R.id.VideoCapture:
    		
    		
    		if(VideoCapture.isChecked())
    		{ 
    			bThread.threadStart();
    			if (BSensor.accelerormeterSensor != null)
    	        	BSensor.sensorManager.registerListener(this, BSensor.accelerormeterSensor,SensorManager.SENSOR_DELAY_GAME);
    			Toast.makeText(this, "����ĸ��On", Toast.LENGTH_SHORT).show();
    			
    		}
    		
    		else
    		{
    			if(bRecorder.isRecording==true){
    				Toast.makeText(this, "����ĸ��Off", Toast.LENGTH_SHORT).show();
    				if (BSensor.sensorManager != null)
    		        	BSensor.sensorManager.unregisterListener(this);
    				bThread.threadStop();
    			}
    		}
      		break;
    		    		
    	case R.id.Snapshot:
    		Toast.makeText(this, "�������� �ɱ��?", Toast.LENGTH_SHORT).show();
    		break;
    		
    	case R.id.Tools:
    		Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
    
    	}
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	bRecorder.destroyRecorder();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {//������ �����Ǹ� �Ҹ���.
		// TODO Auto-generated method stub
		
		 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {//���ӵ��� Ÿ���� ������ �����Ǹ�
	            long currentTime = System.currentTimeMillis();
	            long gabOfTime = (currentTime - bSensor.lastTime);
	            if (gabOfTime > 100) {
	            	bSensor.lastTime = currentTime;
	                bSensor.x = event.values[SensorManager.DATA_X];
	                bSensor.y = event.values[SensorManager.DATA_Y];
	                bSensor.z = event.values[SensorManager.DATA_Z];
	 
	                bSensor.speed = Math.abs(bSensor.x + bSensor.y + bSensor.z - bSensor.lastX - bSensor.lastY - bSensor.lastZ) / gabOfTime * 10000;
	 
	                if (bSensor.speed > BSensor.SHAKE_THRESHOLD) {//�ӵ��� ������ �Ӱ�ġ���� ������
	                    // �̺�Ʈ�߻�!!
	                	Log.v("�𷡹��� ���߻���","��������="+20000000);
	                	BSensor.isSensorDetected=true;
	                	//Toast.makeText(getApplicationContext(),"���ӵ�����������", Toast.LENGTH_SHORT).show();
	                }
	 
	                bSensor.lastX = event.values[BSensor.DATA_X];
	                bSensor.lastY = event.values[BSensor.DATA_Y];
	                bSensor.lastZ = event.values[BSensor.DATA_Z];
	            }
	            
		 }
		
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	class BThredRecorder  {
		
		int i=0;
		private Thread videotimerUpdate;
		private Handler videotimerUpdateHandler;
		
		
		public BThredRecorder()
		{
			videotimerUpdateHandler=new Handler();
			
		}
		public void threadStart()
		{
			Log.v("����������߿�?","���ʹ�?="+20000);
			videotimerUpdate= new Thread(new Runnable(){
				int i=0;
				public void run(){   
		
					if(BRecorder.videoCurrentTime < bRecorder.checkRecordTime(BSensor.isSensorDetected)){
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
	}
    


}

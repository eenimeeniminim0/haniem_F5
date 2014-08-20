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
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 없애기
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);//화면풀스크린
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//화면 가로로 설정
        
        setContentView(R.layout.recording);//레코딩 레이아웃
		Toast.makeText(getApplicationContext(), "레코딩액티비티 시작", Toast.LENGTH_SHORT).show();
		
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
        
      //센서관련
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
    			Toast.makeText(this, "비디오캡쳐On", Toast.LENGTH_SHORT).show();
    			
    		}
    		
    		else
    		{
    			if(bRecorder.isRecording==true){
    				Toast.makeText(this, "비디오캡쳐Off", Toast.LENGTH_SHORT).show();
    				if (BSensor.sensorManager != null)
    		        	BSensor.sensorManager.unregisterListener(this);
    				bThread.threadStop();
    			}
    		}
      		break;
    		    		
    	case R.id.Snapshot:
    		Toast.makeText(this, "스냅샷이 될까요?", Toast.LENGTH_SHORT).show();
    		break;
    		
    	case R.id.Tools:
    		Toast.makeText(this, "도구", Toast.LENGTH_SHORT).show();
    
    	}
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	bRecorder.destroyRecorder();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {//센서가 감지되면 불린다.
		// TODO Auto-generated method stub
		
		 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {//가속도계 타입의 센서가 감지되면
	            long currentTime = System.currentTimeMillis();
	            long gabOfTime = (currentTime - bSensor.lastTime);
	            if (gabOfTime > 100) {
	            	bSensor.lastTime = currentTime;
	                bSensor.x = event.values[SensorManager.DATA_X];
	                bSensor.y = event.values[SensorManager.DATA_Y];
	                bSensor.z = event.values[SensorManager.DATA_Z];
	 
	                bSensor.speed = Math.abs(bSensor.x + bSensor.y + bSensor.z - bSensor.lastX - bSensor.lastY - bSensor.lastZ) / gabOfTime * 10000;
	 
	                if (bSensor.speed > BSensor.SHAKE_THRESHOLD) {//속도가 지정한 임계치보다 높으면
	                    // 이벤트발생!!
	                	Log.v("모래반지 빵야빵야","허허허허="+20000000);
	                	BSensor.isSensorDetected=true;
	                	//Toast.makeText(getApplicationContext(),"가속도센서감지됨", Toast.LENGTH_SHORT).show();
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
			Log.v("스레드님제발요?","울고싶다?="+20000);
			videotimerUpdate= new Thread(new Runnable(){
				int i=0;
				public void run(){   
		
					if(BRecorder.videoCurrentTime < bRecorder.checkRecordTime(BSensor.isSensorDetected)){
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
	}
    


}

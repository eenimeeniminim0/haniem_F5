package com.example.ourblackbox2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RecordingActivity extends ActionBarActivity implements OnClickListener, SensorEventListener {
	
	private ToggleButton VideoCapture;
	private ImageButton Home,Accident;
	private ImageButton parkingGuide;
	private BSensor bSensor;
	private TextView recordState;
	private BThreadRecorder bThread;
	private Context appContext;


	//public static String bRecordingState;
	//public static String a;

	//private final static int MESSAGE_ID = 1;
	//private NotificationManager mNotificationManager = null;
	
	boolean parkingbool=false;
	boolean homebool=false;

	private PowerManager bpm;
	private WakeLock bwl;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

        
        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 없애기
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//화면풀스크린
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//화면 가로로 설정
		
		bpm=(PowerManager)getSystemService(Context.POWER_SERVICE);
		bwl=bpm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "어둡게 겸");
		appContext=getApplicationContext();
        
        setContentView(R.layout.recording);//레코딩 레이아웃
		Toast.makeText(getApplicationContext(), "레코딩액티비티 시작", Toast.LENGTH_SHORT).show();
		

		
		bSensor=new BSensor();
		bThread=new BThreadRecorder();

		BSurfaceView.bSurface = (BSurfaceView)findViewById(R.id.CameraPreview);

		
		VideoCapture=(ToggleButton)findViewById(R.id.VideoCapture);
        Home=(ImageButton)findViewById(R.id.home);
        Accident=(ImageButton)findViewById(R.id.accident);
        recordState = (TextView)findViewById(R.id.record_state);
        parkingGuide = (ImageButton)findViewById(R.id.parkingGuide);
        
        
        VideoCapture.setOnClickListener(this);
        Home.setOnClickListener(this);
        Accident.setOnClickListener(this);
        parkingGuide.setOnClickListener(this);
        
        //mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        
		recordState.setText("촬영 준비");
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
    			bThread.threadStart(appContext);
    			recordState.setText("촬영 중");
    			if (BSensor.accelerormeterSensor != null)
    	        	BSensor.sensorManager.registerListener(this, BSensor.accelerormeterSensor,SensorManager.SENSOR_DELAY_GAME);
    			Toast.makeText(this, "비디오캡쳐On", Toast.LENGTH_SHORT).show();
    			//ledOn();
    			
    		}
    		
    		else
    		{
    				Toast.makeText(this, "비디오캡쳐Off", Toast.LENGTH_SHORT).show();
       				bThread.threadStop(appContext);	 
    				recordState.setText("촬영 준비");
    				if (BSensor.sensorManager != null)
    		        	BSensor.sensorManager.unregisterListener(this);
    		        				
    		}
      		break;
    		    		
    	case R.id.home:
    		homebool=true;
    		Toast.makeText(this, "뒤로 갈까요?", Toast.LENGTH_SHORT).show();
    		
    		Intent intent=new Intent(this,MainActivity.class);
	    	startActivity(intent);
	    	finish();
    		break;
    		
    	case R.id.accident:
    		recordState.setText("사고 모드");
    		Toast.makeText(this, "사고가 났나요?", Toast.LENGTH_SHORT).show();
    		//삼총사가 만든 ....?
    		break;
    		
    	case R.id.parkingGuide:
    		parkingbool=true;
    		Toast.makeText(this, "조심히 주차하세요", Toast.LENGTH_SHORT).show();
    		Intent pintent =new Intent(this,ParkingGuideActivity.class);
    		startActivity(pintent);
    		finish();
    
    	}
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	bThread.getBRecorder().destroyRecorder();
    	
    	//wakeLock.release();
    	Log.v("레코딩액티비티","ondestroy?");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {//센서가 감지되면 불린다.
		// TODO Auto-generated method stub
		
		 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {//가속도계 타입의 센서가 감지되면
			 	bSensor.setSensitivity();
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
	                	Toast.makeText(getApplicationContext(),"가속도센서감지됨", Toast.LENGTH_SHORT).show();
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
	
   
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
    	parkingbool=false;
    	homebool=false;
		super.onResume();
		bwl.acquire();
	}
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(bwl.isHeld()){
			bwl.release();
		}
	}


	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
    	Log.v("onRestart()", "다시시작...");
    	
		super.onRestart();
	}


}

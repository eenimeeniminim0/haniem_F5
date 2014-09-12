package com.example.ourblackbox2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RecordingActivity extends ActionBarActivity implements OnClickListener, SensorEventListener  {
	
	private ToggleButton VideoCapture;
	private Button Home,Accident;
	private BSensor bSensor;
	private TextView recordState;
	
	private BThreadRecorder bThread;
	public static String bRecordingState;
	public static String a;
	private final static int MESSAGE_ID = 1;
	private NotificationManager mNotificationManager = null;
	

	PowerManager pm;
	PowerManager.WakeLock wakeLock;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        		
        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 없애기
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);//화면풀스크린
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//화면 가로로 설정
		
		//pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
		
		//wakeLock=pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "MY TAG");
		//wakeLock.acquire();
        
        setContentView(R.layout.recording);//레코딩 레이아웃
        
		Toast.makeText(getApplicationContext(), "레코딩액티비티 시작", Toast.LENGTH_SHORT).show();
		
		bSensor=new BSensor();
		bThread=new BThreadRecorder();

		BSurfaceView.bSurface = (BSurfaceView)findViewById(R.id.CameraPreview);
		
		
		VideoCapture=(ToggleButton)findViewById(R.id.VideoCapture);
        Home=(Button)findViewById(R.id.home);
        Accident=(Button)findViewById(R.id.accident);
        recordState = (TextView)findViewById(R.id.record_state);
        
        
        VideoCapture.setOnClickListener(this);
        Home.setOnClickListener(this);
        Accident.setOnClickListener(this);
		mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        
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
    			bThread.threadStart();
    			recordState.setText("촬영 중");
    			if (BSensor.accelerormeterSensor != null)
    	        	BSensor.sensorManager.registerListener(this, BSensor.accelerormeterSensor,SensorManager.SENSOR_DELAY_GAME);
    			Toast.makeText(this, "비디오캡쳐On", Toast.LENGTH_SHORT).show();
    			ledOn();
    			
    		}
    		
    		else
    		{
    				Toast.makeText(this, "비디오캡쳐Off", Toast.LENGTH_SHORT).show();
    				recordState.setText("촬영 준비");
    				bThread.threadStop();	
       				//sendBroadcast(bFileScan.fileScan());
    				if (BSensor.sensorManager != null)
    		        	BSensor.sensorManager.unregisterListener(this);
    		        				
    		}
      		break;
    		    		
    	case R.id.home:
    		Toast.makeText(this, "뒤로 갈까요?", Toast.LENGTH_SHORT).show();
    		Intent intent=new Intent(this,MainActivity.class);
	    	startActivity(intent);
    		break;
    		
    	case R.id.accident:
    		recordState.setText("사고 모드");
    		Toast.makeText(this, "사고가 났나요?", Toast.LENGTH_SHORT).show();
    		//삼총사가 만든 ....?
    
    	}
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	bThread.getBRecorder().destroyRecorder();
    	//wakeLock.release();
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
	

	public void scanFile(String path)
	{
		Intent intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); //패스 선언을 이 클래스에서!!
		Uri uri= Uri.parse("file://"+BRecorder.Path);
		intent.setData(uri);
		sendBroadcast(intent);
	}
		
	/////LED//////////
	
	
    public void ledOn(){
    	
    	String ticker = "OurblackBox가 실행중입니다";
    	String title = "OurblackBox";
    	String text = "실행중";
    	String ns = Context.NOTIFICATION_SERVICE;
    	NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);
    	
    	Intent intent = new Intent(this, RecordingActivity.class);
    	PendingIntent pendingIntent = PendingIntent.getActivity(RecordingActivity.this, 0, intent, 0);
    	
    	int icon = android.R.drawable.ic_input_add;
    	CharSequence tickerText = ticker;
    	long when = System.currentTimeMillis();
    	Notification.Builder builder = new Notification.Builder(RecordingActivity.this);
    	builder.setSmallIcon(icon).setTicker(tickerText).setWhen(when);
    	builder.setContentTitle(title);
    	builder.setContentText(text);
    	builder.setLights(Color.GREEN,500,500);
    	builder.build();
    	
    	Notification notification = builder.getNotification();
    	notification.flags |= Notification.FLAG_SHOW_LIGHTS;
    	notification.flags |= Notification.FLAG_INSISTENT;
    	//notification.flags |= Notification.FLAG_AUTO_CANCEL; //알림 클릭시 자동으로 알림 취소
    	
    	notification.setLatestEventInfo(this, title, text, pendingIntent);
    	mNotificationManager.notify(MESSAGE_ID, notification);
    	
    	
    	
    	
    	
    }
    
    public void ledOff(){
    	
    	mNotificationManager.cancel(MESSAGE_ID);
    
    }
	
	

}

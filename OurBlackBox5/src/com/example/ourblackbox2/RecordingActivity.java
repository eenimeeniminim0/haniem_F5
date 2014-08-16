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
	
	//public BSurfaceView bSurface;
	private BRecorder bRecorder;
	private ToggleButton VideoCapture, SnapShot;
	private Button Tools;
	private Thread videotimerUpdate;
	private Handler videotimerUpdateHandler;
	
	private long lastTime; // 센서변화를 감지한 가장 최근시간 
    private float speed; // 속력
    private float lastX; // 기존 x갑
    private float lastY; // ,,
    private float lastZ; //,,
    private float x, y, z;// 지금받은 x값
 
    private static final int SHAKE_THRESHOLD = 1000;//충격의 임계치, 임계치 넘어가면 작동
    private static final int DATA_X = SensorManager.DATA_X; 
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;
    private SensorManager sensorManager;//센서를 불러오기위한 매니져
    private Sensor accelerormeterSensor;//가속도계 센서를 받는 객체
    //public static BSurfaceView bSurface;

	
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
		
		videotimerUpdateHandler=new Handler();
		
		bRecorder=new BRecorder();
		
		BSurfaceView.bSurface = (BSurfaceView)findViewById(R.id.CameraPreview);		
		VideoCapture=(ToggleButton)findViewById(R.id.VideoCapture);
        SnapShot=(ToggleButton)findViewById(R.id.Snapshot);
        Tools=(Button)findViewById(R.id.Tools);

        VideoCapture.setOnClickListener(this);
        SnapShot.setOnClickListener(this);
        Tools.setOnClickListener(this);
        
      //센서관련
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
	}
	
	@Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
        	sensorManager.registerListener(this, accelerormeterSensor,SensorManager.SENSOR_DELAY_GAME);
    }
 
    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
        	sensorManager.unregisterListener(this);
    }
	

    @Override
    public void onClick(View v) {
    	
    	switch(v.getId()){
    	
    	case R.id.VideoCapture:
    		if(VideoCapture.isChecked())
    		{  		
    			videotimerUpdate= new Thread(new Runnable(){
    				int i=0;
    				public void run(){    
    					if(bRecorder.videoCurrentTime < BRecorder.SECONDS_BETWEEN_VIDEO){
    						Log.v("스레드님","몇번돌아갔나요="+i);

    						if(bRecorder.videoCurrentTime==0){
    							bRecorder.startRecorder();
    						}
    						bRecorder.videoCurrentTime++;
    						Log.v("videoCurrentTime","time="+bRecorder.videoCurrentTime);
    						videotimerUpdateHandler.postDelayed(videotimerUpdate, 1000);
    					}else if(bRecorder.videoCurrentTime == BRecorder.SECONDS_BETWEEN_VIDEO){
    						
    						bRecorder.resetRecorder();
    						i++;
    						videotimerUpdateHandler.post(videotimerUpdate);		
    					}
    				}
    			});
    			videotimerUpdate.start();
    			Toast.makeText(this, "비디오캡쳐On", Toast.LENGTH_SHORT).show();
    			
    		}
    		
    		else
    		{
    			if(bRecorder.isRecording==true){
    				Toast.makeText(this, "비디오캡쳐Off", Toast.LENGTH_SHORT).show();
    				bRecorder.stopRecorder();
        			if(bRecorder.isVideotimerRunning==true)//만약 비디오스레드가 돌아가고있으면
        			{
        				videotimerUpdateHandler.removeCallbacks(videotimerUpdate);
        				bRecorder.isVideotimerRunning=false;//비디오스레드가 종료되었음
        			}

    				
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

    public void onSensorChanged(SensorEvent event) {//센서가 감지되면 불린다.
		// TODO Auto-generated method stub
		
		 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {//가속도계 타입의 센서가 감지되면
	            long currentTime = System.currentTimeMillis();
	            long gabOfTime = (currentTime - lastTime);
	            if (gabOfTime > 100) {
	                lastTime = currentTime;
	                x = event.values[SensorManager.DATA_X];
	                y = event.values[SensorManager.DATA_Y];
	                z = event.values[SensorManager.DATA_Z];
	 
	                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;
	 
	                if (speed > SHAKE_THRESHOLD) {//속도가 지정한 임계치보다 높으면
	                    // 이벤트발생!!
	                	Log.v("모래반지 빵야빵야","허허허허="+20000000);
	                	//Toast.makeText(getApplicationContext(),"가속도센서감지됨", Toast.LENGTH_SHORT).show();
	                }
	 
	                lastX = event.values[DATA_X];
	                lastY = event.values[DATA_Y];
	                lastZ = event.values[DATA_Z];
	            }
	            
		 }
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
    


}


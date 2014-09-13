package com.example.ourblackbox2;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RecordingActivity extends ActionBarActivity implements OnClickListener, SensorEventListener {
	
	private ToggleButton VideoCapture;
	private Button Home,Accident;
	private BSensor bSensor;
	private TextView recordState;
	private BFileScan bFileScan;
	private BThreadRecorder bThread;

	private Context context; 

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

        
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Ÿ��Ʋ ���ֱ�
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȭ��Ǯ��ũ��
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ȭ�� ���η� ����
		
		//pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
		
		//wakeLock=pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "MY TAG");
		//wakeLock.acquire();
        
        setContentView(R.layout.recording);//���ڵ� ���̾ƿ�
        
		Toast.makeText(getApplicationContext(), "���ڵ���Ƽ��Ƽ ����", Toast.LENGTH_SHORT).show();
		

		
		bSensor=new BSensor();
		bThread=new BThreadRecorder();
		bFileScan=new BFileScan();

		BSurfaceView.bSurface = (BSurfaceView)findViewById(R.id.CameraPreview);

		
		VideoCapture=(ToggleButton)findViewById(R.id.VideoCapture);
        Home=(Button)findViewById(R.id.home);
        Accident=(Button)findViewById(R.id.accident);
        recordState = (TextView)findViewById(R.id.record_state);
        
        
        VideoCapture.setOnClickListener(this);
        Home.setOnClickListener(this);
        Accident.setOnClickListener(this);
		mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        
		recordState.setText("�Կ� �غ�");
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
    			recordState.setText("�Կ� ��");
    			if (BSensor.accelerormeterSensor != null)
    	        	BSensor.sensorManager.registerListener(this, BSensor.accelerormeterSensor,SensorManager.SENSOR_DELAY_GAME);
    			Toast.makeText(this, "����ĸ��On", Toast.LENGTH_SHORT).show();
    			ledOn();
    			
    		}
    		
    		else
    		{
    				Toast.makeText(this, "����ĸ��Off", Toast.LENGTH_SHORT).show();

       				bThread.threadStop();	
       				//sendBroadcast(bThread.fileScan());

    				recordState.setText("�Կ� �غ�");
    				bThread.threadStop();	
       				sendBroadcast(bFileScan.fileScan());

    				if (BSensor.sensorManager != null)
    		        	BSensor.sensorManager.unregisterListener(this);
    		        				
    		}
      		break;
    		    		
    	case R.id.home:
    		Toast.makeText(this, "�ڷ� �����?", Toast.LENGTH_SHORT).show();
    		Intent intent=new Intent(this,MainActivity.class);
	    	startActivity(intent);
    		break;
    		
    	case R.id.accident:
    		recordState.setText("��� ���");
    		Toast.makeText(this, "��� ������?", Toast.LENGTH_SHORT).show();
    		//���ѻ簡 ���� ....?
    
    	}
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	bThread.getBRecorder().destroyRecorder();
    	//wakeLock.release();
    	Log.v("���ڵ���Ƽ��Ƽ","ondestroy?");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {//������ �����Ǹ� �Ҹ���.
		// TODO Auto-generated method stub
		
		 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {//���ӵ��� Ÿ���� ������ �����Ǹ�
			 	bSensor.setSensitivity();
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
	                	Toast.makeText(getApplicationContext(),"���ӵ�����������", Toast.LENGTH_SHORT).show();
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
		Intent intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); //�н� ������ �� Ŭ��������!!
		Uri uri= Uri.parse("file://"+BRecorder.Path);
		intent.setData(uri);
		sendBroadcast(intent);
	}
		

    public void ledOn(){
    	
    	String ticker = "OurblackBox�� �������Դϴ�";
    	String title = "OurblackBox";
    	String text = "������";
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
    	//notification.flags |= Notification.FLAG_AUTO_CANCEL; //�˸� Ŭ���� �ڵ����� �˸� ���
    	
    	notification.setLatestEventInfo(this, title, text, pendingIntent);
    	mNotificationManager.notify(MESSAGE_ID, notification);
    	   	
    }
       
    @Override
	protected void onRestart() {
		// TODO Auto-generated method stub
    	/*
    	if(isRecordServiceRunning(RecordingActivity.this, "com.example.ourblackbox2.RecordingService")){
    		
    		Toast.makeText(getApplicationContext(), "���ڵ�����", Toast.LENGTH_SHORT).show();
            stopService(new Intent(RecordingActivity.this, RecordingService.class));
    	}
    	
    	*/
    	Log.v("onRestart()", "�ٽý���...");
    	
		super.onRestart();
	}


	@Override
	protected void onUserLeaveHint() {
		// TODO Auto-generated method stub
    	
    	bThread.threadStop();
			//sendBroadcast(bThread.fileScan());
		if (BSensor.sensorManager != null)
        	BSensor.sensorManager.unregisterListener(this);
		
		if(BSurfaceView.bSurface.getCamera()!=null)
		{
			Log.v("onUserLeaveHint()", "��........��ī�޶�");
			BSurfaceView.bSurface.bCamera.stopPreview();
			BSurfaceView.bSurface.bCamera.release();
			BSurfaceView.bSurface.bCamera=null;
		}
    	
		if(!isRecordServiceRunning(RecordingActivity.this, "com.example.ourblackbox2.RecordingService")){
    		
    		Toast.makeText(getApplicationContext(), "���ڵ�����", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RecordingActivity.this, RecordingService.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startService(intent);
            
            
		}
		
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.example.ourblackbox2.servicedestroyrecorder");

		
		super.onUserLeaveHint();
		
		finish();
	}

    
  //���񽺽����������ƴ��� Ȯ��
  	private boolean isRecordServiceRunning(Context ctx, String s_service_name) {

      	ActivityManager manager = (ActivityManager) ctx.getSystemService(Activity.ACTIVITY_SERVICE);

      	for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {

      	    if (s_service_name.equals(service.service.getClassName())) {

      	        return true;
      	    }
      	}

      	return false;
  }


	public void ledOff(){
    	
    	mNotificationManager.cancel(MESSAGE_ID);
    
    }
	

}

package com.example.ourblackbox2;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

public class RecordingService extends Service  implements SensorEventListener, SurfaceHolder.Callback{

	
   

    private BSensor bSensor;
	private BServiceThreadRecorder bThread;
	
	private Context context; 
	private final static int MESSAGE_ID = 1;
	private NotificationManager mNotificationManager = null;

	 private WindowManager windowManager;
	    private SurfaceView surfaceView;
	   

    
    @Override
    public void onCreate() {
   
    	

		
    
        initService();
        
        bSensor=new BSensor();
		bThread=new BServiceThreadRecorder(surfaceView);
        
        bThread.threadStart();
        super.onCreate();

    }
    
    public synchronized void initService(){
        
    		  //��������
            BSensor.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            BSensor.accelerormeterSensor = BSensor.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    		
            
    		if (BSensor.accelerormeterSensor != null)
            	BSensor.sensorManager.registerListener(this, BSensor.accelerormeterSensor,SensorManager.SENSOR_DELAY_GAME);
    		Toast.makeText(this, "����ĸ��On", Toast.LENGTH_SHORT).show();

            
            
            Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();
            
            
            // Start foreground service to avoid unexpected kill
            Notification notification = new Notification.Builder(this)
                .setContentTitle("Background Video Recorder")
                .setContentText("")
                .setSmallIcon(R.drawable.ic_launcher)
                .build();
            startForeground(1234, notification);

            // Create new SurfaceView, set its size to 1x1, move it to the top left corner and set this service as a callback
            windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            surfaceView = new SurfaceView(this);
            LayoutParams layoutParams = new WindowManager.LayoutParams(
                1, 1,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
            );
            layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            windowManager.addView(surfaceView, layoutParams);
            surfaceView.getHolder().addCallback(this);
    	
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
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
	public void onDestroy() {
		// TODO Auto-generated method stub
		   bThread.threadStop();
		   
	        windowManager.removeView(surfaceView);
	        Intent intent = new Intent();
			intent.setAction("com.example.ourblackbox2.servicedestroyrecorder");
			sendBroadcast(intent);

		super.onDestroy();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	

}

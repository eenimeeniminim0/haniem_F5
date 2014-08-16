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
	
	private long lastTime; // ������ȭ�� ������ ���� �ֱٽð� 
    private float speed; // �ӷ�
    private float lastX; // ���� x��
    private float lastY; // ,,
    private float lastZ; //,,
    private float x, y, z;// ���ݹ��� x��
 
    private static final int SHAKE_THRESHOLD = 1000;//����� �Ӱ�ġ, �Ӱ�ġ �Ѿ�� �۵�
    private static final int DATA_X = SensorManager.DATA_X; 
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;
    private SensorManager sensorManager;//������ �ҷ��������� �Ŵ���
    private Sensor accelerormeterSensor;//���ӵ��� ������ �޴� ��ü
    //public static BSurfaceView bSurface;

	
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
		
		videotimerUpdateHandler=new Handler();
		
		bRecorder=new BRecorder();
		
		BSurfaceView.bSurface = (BSurfaceView)findViewById(R.id.CameraPreview);		
		VideoCapture=(ToggleButton)findViewById(R.id.VideoCapture);
        SnapShot=(ToggleButton)findViewById(R.id.Snapshot);
        Tools=(Button)findViewById(R.id.Tools);

        VideoCapture.setOnClickListener(this);
        SnapShot.setOnClickListener(this);
        Tools.setOnClickListener(this);
        
      //��������
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
    						Log.v("�������","������ư�����="+i);

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
    			Toast.makeText(this, "����ĸ��On", Toast.LENGTH_SHORT).show();
    			
    		}
    		
    		else
    		{
    			if(bRecorder.isRecording==true){
    				Toast.makeText(this, "����ĸ��Off", Toast.LENGTH_SHORT).show();
    				bRecorder.stopRecorder();
        			if(bRecorder.isVideotimerRunning==true)//���� ���������尡 ���ư���������
        			{
        				videotimerUpdateHandler.removeCallbacks(videotimerUpdate);
        				bRecorder.isVideotimerRunning=false;//���������尡 ����Ǿ���
        			}

    				
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

    public void onSensorChanged(SensorEvent event) {//������ �����Ǹ� �Ҹ���.
		// TODO Auto-generated method stub
		
		 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {//���ӵ��� Ÿ���� ������ �����Ǹ�
	            long currentTime = System.currentTimeMillis();
	            long gabOfTime = (currentTime - lastTime);
	            if (gabOfTime > 100) {
	                lastTime = currentTime;
	                x = event.values[SensorManager.DATA_X];
	                y = event.values[SensorManager.DATA_Y];
	                z = event.values[SensorManager.DATA_Z];
	 
	                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;
	 
	                if (speed > SHAKE_THRESHOLD) {//�ӵ��� ������ �Ӱ�ġ���� ������
	                    // �̺�Ʈ�߻�!!
	                	Log.v("�𷡹��� ���߻���","��������="+20000000);
	                	//Toast.makeText(getApplicationContext(),"���ӵ�����������", Toast.LENGTH_SHORT).show();
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


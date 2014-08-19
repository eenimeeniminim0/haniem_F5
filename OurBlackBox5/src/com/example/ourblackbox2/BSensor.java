package com.example.ourblackbox2;

import android.hardware.Sensor;
import android.hardware.SensorManager;

public class BSensor {
	protected long lastTime; // 센서변화를 감지한 가장 최근시간 
	protected float speed; // 속력
	protected float lastX; // 기존 x갑
	protected float lastY; // ,,
	protected float lastZ; //,,
	protected float x, y, z;// 지금받은 x값
 
	protected static final int SHAKE_THRESHOLD = 1000;//충격의 임계치, 임계치 넘어가면 작동
	protected static final int DATA_X = SensorManager.DATA_X; 
	protected static final int DATA_Y = SensorManager.DATA_Y;
	protected static final int DATA_Z = SensorManager.DATA_Z;
    public static SensorManager sensorManager;//센서를 불러오기위한 매니져
    public static Sensor accelerormeterSensor;//가속도계 센서를 받는 객체
    
    /*public void onSensorChanged(SensorEvent event) {//센서가 감지되면 불린다.
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
		
	}*/

}

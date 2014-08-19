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
   

}

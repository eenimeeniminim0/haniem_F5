package com.example.ourblackbox2;

import android.hardware.Sensor;
import android.hardware.SensorManager;

public class BSensor {
	protected long lastTime; // ������ȭ�� ������ ���� �ֱٽð� 
	protected float speed; // �ӷ�
	protected float lastX; // ���� x��
	protected float lastY; // ,,
	protected float lastZ; //,,
	protected float x, y, z;// ���ݹ��� x��
	protected boolean isSensorDetected;
 
	protected static  int SHAKE_THRESHOLD;//����� �Ӱ�ġ, �Ӱ�ġ �Ѿ�� �۵�
	protected static  int DATA_X; 
	protected static  int DATA_Y;
	protected static  int DATA_Z;
    public static SensorManager sensorManager;//������ �ҷ��������� �Ŵ���
    public static Sensor accelerormeterSensor;//���ӵ��� ������ �޴� ��ü
    
    public BSensor()
    {
    	isSensorDetected=false;
    	SHAKE_THRESHOLD = 1000;
    	DATA_X = SensorManager.DATA_X; 
    	DATA_Y = SensorManager.DATA_Y;
    	DATA_Z = SensorManager.DATA_Z;
    	
    }
   

}

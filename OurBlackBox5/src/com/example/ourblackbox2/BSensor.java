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
	protected static boolean isSensorDetected;
 
	protected static  int SHAKE_THRESHOLD;//����� �Ӱ�ġ, �Ӱ�ġ �Ѿ�� �۵�
	protected static  int DATA_X; 
	protected static  int DATA_Y;
	protected static  int DATA_Z;
    public static SensorManager sensorManager;//������ �ҷ��������� �Ŵ���
    public static Sensor accelerormeterSensor;//���ӵ��� ������ �޴� ��ü
    
    public BSensor()
    {
    	isSensorDetected=false;
    	//setSensitivity();
    	DATA_X = SensorManager.DATA_X; 
    	DATA_Y = SensorManager.DATA_Y;
    	DATA_Z = SensorManager.DATA_Z;
    	
    }
    
    public void setSensitivity()
    {
    		if(BSensorSetting.sensitivity.equals("high"))
    			SHAKE_THRESHOLD=500;
    		
    		else if(BSensorSetting.sensitivity.equals("normal"))
    			SHAKE_THRESHOLD=1000;

    		else
    			SHAKE_THRESHOLD=1500;;
    }
   

}

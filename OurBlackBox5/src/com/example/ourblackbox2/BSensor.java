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
 
	protected static final int SHAKE_THRESHOLD = 1000;//����� �Ӱ�ġ, �Ӱ�ġ �Ѿ�� �۵�
	protected static final int DATA_X = SensorManager.DATA_X; 
	protected static final int DATA_Y = SensorManager.DATA_Y;
	protected static final int DATA_Z = SensorManager.DATA_Z;
    public static SensorManager sensorManager;//������ �ҷ��������� �Ŵ���
    public static Sensor accelerormeterSensor;//���ӵ��� ������ �޴� ��ü
   

}

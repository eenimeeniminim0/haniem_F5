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
    
    /*public void onSensorChanged(SensorEvent event) {//������ �����Ǹ� �Ҹ���.
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
		
	}*/

}

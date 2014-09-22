package com.example.ourblackbox2;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RecordingActivity extends ActionBarActivity implements
		OnClickListener, SensorEventListener, LocationListener {

	private ToggleButton VideoCapture;
	private ImageButton Home, Accident;
	private ImageButton parkingGuide;
	private BSensor bSensor;
	private TextView recordState;
	private BThreadRecorder bThread;

	private BEmergencySetting bEmergencySetting;
	private Uri numberCall;
	private String numberSms;
	
	private double lat;
	private double lng;
	
	private LocationManager lm = null;
	private String provider = null;

	public static String bRecordingState;
	public static String a;

	private final static int MESSAGE_ID = 1;
	private NotificationManager mNotificationManager = null;
	private Context appContext;

	
	boolean parkingbool = false;
	boolean homebool = false;

	PowerManager pm;
	PowerManager.WakeLock wakeLock;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);// Ÿ��Ʋ ���ֱ�
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȭ��Ǯ��ũ��
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// ȭ��
																			// ���η�
																			// ����


		setContentView(R.layout.recording);// ���ڵ� ���̾ƿ�
		appContext = getApplicationContext();

		Toast.makeText(getApplicationContext(), "���ڵ���Ƽ��Ƽ ����",
				Toast.LENGTH_SHORT).show();

		bSensor = new BSensor();
		bThread = new BThreadRecorder();
		bEmergencySetting = new BEmergencySetting();

		BSurfaceView.bSurface = (BSurfaceView) findViewById(R.id.CameraPreview);

		VideoCapture = (ToggleButton) findViewById(R.id.VideoCapture);
		Home = (ImageButton) findViewById(R.id.home);
		Accident = (ImageButton) findViewById(R.id.accident);
		recordState = (TextView) findViewById(R.id.record_state);
		parkingGuide = (ImageButton) findViewById(R.id.parkingGuide);

		VideoCapture.setOnClickListener(this);
		Home.setOnClickListener(this);
		Accident.setOnClickListener(this);
		parkingGuide.setOnClickListener(this);

		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		recordState.setText("�Կ� �غ�");
		// ��������
		BSensor.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		BSensor.accelerormeterSensor = BSensor.sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		numberCall = Uri.parse("tel:" + bEmergencySetting.getEmerNum());
	    numberSms = bEmergencySetting.getEmerNum();
		
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		/** ���� ��밡���� ��ġ ���� ��ġ �˻�*/
		Criteria c = new Criteria();
		provider = lm.getBestProvider(c, true);
		
		if(provider == null || !lm.isProviderEnabled(provider)){
			List<String> list = lm.getAllProviders();
			
			for(int i = 0; i < list.size(); i++){
				String temp = list.get(i);
				
				if(lm.isProviderEnabled(temp)){
					provider = temp;
					break;
				}
			}
		}
		
		/**����������  ��ȸ�ߴ� ��ġ ���*/
		Location location = lm.getLastKnownLocation(provider);
		
		if(location == null){
			Toast.makeText(this, "��밡���� ��ġ ���� �����ڰ� �����ϴ�.", Toast.LENGTH_SHORT).show();
		}else{
			//���� ��ġ���� ���� �̾ GPS ����...
			onLocationChanged(location);
			
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.VideoCapture:

			if (VideoCapture.isChecked()) {
				bThread.threadStart(appContext);
				recordState.setText("�Կ� ��");
				if (BSensor.accelerormeterSensor != null)
					BSensor.sensorManager.registerListener(this,
							BSensor.accelerormeterSensor,
							SensorManager.SENSOR_DELAY_GAME);
				Toast.makeText(this, "����ĸ��On", Toast.LENGTH_SHORT).show();

			}

			else {
				Toast.makeText(this, "����ĸ��Off", Toast.LENGTH_SHORT).show();
				bThread.threadStop(appContext);
				updateMediaScanMounted();
				recordState.setText("�Կ� �غ�");
				if (BSensor.sensorManager != null)
					BSensor.sensorManager.unregisterListener(this);

			}
			break;

		case R.id.home:
			homebool = true;
			Toast.makeText(this, "�ڷ� �����?", Toast.LENGTH_SHORT).show();

			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
			break;

		case R.id.accident:
			recordState.setText("��� ���");
			Toast.makeText(this, "��� ������?", Toast.LENGTH_SHORT).show();
			
			bThread.threadStop(appContext);
			updateMediaScanMounted();
			recordState.setText("�Կ� �غ�");
			
			new AlertDialog.Builder(RecordingActivity.this)
			.setTitle("��޿��� ����")
			.setMessage("��޿����� �����Ͻðڽ��ϱ�?")
			.setPositiveButton("����",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(
								DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							
							emercontact();
							finish();
						}
					})
			.setNegativeButton("���",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
								DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
						}
					}).show();

			break;
	

		case R.id.parkingGuide:
			parkingbool = true;
			Toast.makeText(this, "������ �����ϼ���", Toast.LENGTH_SHORT).show();
			Intent pintent = new Intent(this, ParkingGuideActivity.class);
			startActivity(pintent);
			finish();

		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		bThread.getBRecorder().destroyRecorder();

		// wakeLock.release();
		Log.v("���ڵ���Ƽ��Ƽ", "ondestroy?");
	}

	@Override
	public void onSensorChanged(SensorEvent event) {// ������ �����Ǹ� �Ҹ���.
		// TODO Auto-generated method stub

		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {// ���ӵ��� Ÿ����
																	// ������ �����Ǹ�
			bSensor.setSensitivity();
			long currentTime = System.currentTimeMillis();
			long gabOfTime = (currentTime - bSensor.lastTime);
			if (gabOfTime > 100) {
				bSensor.lastTime = currentTime;
				bSensor.x = event.values[SensorManager.DATA_X];
				bSensor.y = event.values[SensorManager.DATA_Y];
				bSensor.z = event.values[SensorManager.DATA_Z];

				bSensor.speed = Math.abs(bSensor.x + bSensor.y + bSensor.z
						- bSensor.lastX - bSensor.lastY - bSensor.lastZ)
						/ gabOfTime * 10000;
				if (bSensor.speed > BSensor.SHAKE_THRESHOLD) {// �ӵ��� ������ �Ӱ�ġ����
																// ������
					// �̺�Ʈ�߻�!!

					Log.v("�𷡹��� ���߻���", "��������=" + 20000000);
					BSensor.isSensorDetected = true;
					Toast.makeText(getApplicationContext(), "���ӵ�����������",
							Toast.LENGTH_SHORT).show();
					
					//
					if(bSensor.speed>100000){
					
					bThread.threadStop(appContext);
					updateMediaScanMounted();
					recordState.setText("�Կ� �غ�");

					emercontact();
					finish();
				}

				}	
					
			}

			bSensor.lastX = event.values[BSensor.DATA_X];
			bSensor.lastY = event.values[BSensor.DATA_Y];
			bSensor.lastZ = event.values[BSensor.DATA_Z];
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void updateMediaScanMounted() {

		int version = android.os.Build.VERSION.SDK_INT;

		if (version > 17) {
			Intent mediaScanIntent = new Intent(
					Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri contentUri = Uri.parse("file://"
					+ Environment.getExternalStorageDirectory());
			mediaScanIntent.setData(contentUri);
			sendBroadcast(mediaScanIntent);
		} else {
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse("file://"
							+ Environment.getExternalStorageDirectory())));
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		parkingbool = false;
		homebool = false;
		super.onResume();
		lm.requestLocationUpdates(provider, 500, 1, this);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.v("onRestart()", "�ٽý���...");

		super.onRestart();
	}


	// ���񽺽����������ƴ��� Ȯ��
	private boolean isRecordServiceRunning(Context ctx, String s_service_name) {

		ActivityManager manager = (ActivityManager) ctx
				.getSystemService(Activity.ACTIVITY_SERVICE);

		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {

			if (s_service_name.equals(service.service.getClassName())) {

				return true;
			}
		}

		return false;
	}

	public void emercontact(){
		
		if (bEmergencySetting.getContactMethod().equals("call")) {
			Intent phoneIntent = new Intent(
					Intent.ACTION_CALL, numberCall);
			startActivity(phoneIntent);
			
		} else if (bEmergencySetting.getContactMethod().equals("message")) {
			Toast.makeText(getApplicationContext(), "���� �浵 : " + lat + " " + lng,
					Toast.LENGTH_SHORT).show();
		      Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		      String t = "<���߻� ����>" + "\n" + getAddress(lat, lng) + "\n" + bEmergencySetting.getMessage();
		      smsIntent.putExtra("sms_body", t); 
		      smsIntent.putExtra("address", numberSms); // �޴»�� ��ȣ
		      smsIntent.setType("vnd.android-dir/mms-sms");
		      startActivity(smsIntent);
		      
		}else{
			Toast.makeText(getApplicationContext(), "���������� �������� �ʾҽ��ϴ�.",
							Toast.LENGTH_SHORT).show();
		}
  	}
	
	/** �ٸ� ȭ������ �Ѿ ��, �Ͻ����� ó��*/
	@Override
	public void onPause(){
		//Activity LifrCycle ���� �޼���� ������ ���� �޼��� ȣ�� �ʿ�
		super.onPause();
		lm.removeUpdates(this);
	}

	/** ��ġ�� ������ ��� ȣ��ȴ�.*/
	@Override
	public void onLocationChanged(Location location) {
		// ����, �浵
		lat = location.getLatitude();
		lng = location.getLongitude();

	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	/** ������ �浵 ������� �ּҸ� �����ϴ� �޼���*/
	public String getAddress(double lat, double lng){
		String address = null;
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		List<Address> list = null;
		
		try{
			list = geocoder.getFromLocation(lat, lng, 1);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		if(list == null){
			Log.e("getAddress", "�ּ� ������ ��� ����");
			return null;
		}
		
		if(list.size() > 0){
			Address addr = list.get(0);
			address = addr.getCountryName() + " "
					+ addr.getAdminArea() + " "
					+ addr.getLocality() + " "
					+ addr.getThoroughfare() + " "
					+ addr.getFeatureName();
		}
		return address;
	}
}

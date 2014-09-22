package com.example.ourblackbox2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class SettingActivity extends Activity implements OnClickListener{

	ImageButton sensorButton, recordingButton, storageButton, gpsButton,
			emergencyButton, noticeButton;
	
	private BEmergencySetting bEmergencySetting;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		setContentView(R.layout.setting);
		// TODO Auto-generated method stub

		sensorButton = (ImageButton) findViewById(R.id.sensor_button);
		recordingButton = (ImageButton) findViewById(R.id.recording_button);
		storageButton = (ImageButton) findViewById(R.id.storage_button);
		gpsButton = (ImageButton) findViewById(R.id.gps_button);
		emergencyButton = (ImageButton) findViewById(R.id.emergency_button);
		noticeButton = (ImageButton) findViewById(R.id.notice_button);
		
		sensorButton.setOnClickListener(this);
		recordingButton.setOnClickListener(this);
		storageButton.setOnClickListener(this);
		gpsButton.setOnClickListener(this);
		emergencyButton.setOnClickListener(this);
		noticeButton.setOnClickListener(this);
		
		bEmergencySetting = new BEmergencySetting();

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == sensorButton) {
			Intent intent = new Intent(this, SensorSetting.class);
			startActivity(intent);

		} else if (v == recordingButton) {
			Intent intent = new Intent(this, RecordingSetting.class);
			startActivity(intent);

		} else if (v == storageButton) {
			Intent intent = new Intent(this, StorageSetting.class);
			startActivity(intent);

		} else if (v == gpsButton) {
			Intent intent = new Intent(this, GpsSetting.class);
			startActivity(intent);
		}else if (v == emergencyButton) {
			Intent intent = new Intent(this, EmergencySetting.class);
			startActivity(intent);
		}else if (v == noticeButton) {
			Intent intent = new Intent(this, NoticeSetting.class);
			startActivity(intent);
		}

	}
	
	public void onResume() {
		super.onResume();
		
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this); // �̷������� ���� �����;���....
		

		/*--------------------��ȭ����--------------------------------------*/
		Log.v("��ȭ ǰ���� �ٲ��?","�ñ��մϴ�="+prefs.getString("recQuality", "<unset>"));
		BRecordingSetting.recQuality=prefs.getString("recQuality", "<unset>");
		Log.v("���� ������?","�ñ��մϴ�="+BRecordingSetting.recQuality);
		
		Log.v("��ȭ �ð��� �ٲ��?","�ñ��մϴ�="+prefs.getString("recPeriod", "<unset>"));
		BRecordingSetting.recPeriod=prefs.getString("recPeriod", "<unset>");
		Log.v("���� ������?","�ñ��մϴ�="+BRecordingSetting.recPeriod);
		
		Log.v("��� ������ �ٲ��?","�ñ��մϴ�="+prefs.getString("sensitivity", "<unset>"));
		BSensorSetting.sensitivity=prefs.getString("sensitivity", "<unset>");
		Log.v("���� ������?","�ñ��մϴ�="+BSensorSetting.sensitivity);
		
		Log.v("������� ��ġ�� �ٲ��?","�ñ��մϴ�="+prefs.getString("storageLocation", "<unset>"));
		BStorageSetting.storageLocation=prefs.getString("storageLocation", "<unset>");
		Log.v("���� ������?","�ñ��մϴ�="+BStorageSetting.storageLocation);
		
		Log.v("��ȭ��ȣ�� �ٲ��?", "�ñ��մϴ�=" + prefs.getString("emerPhonenum", "<unset>"));
	    bEmergencySetting.setEmerNum(prefs.getString("emerPhonenum", "<unset>"));
	    Log.v("������ �ٲ��?", "�ñ��մϴ�=" + prefs.getString("emerOccur", "<unset>"));
	    bEmergencySetting.setContactMethod(prefs.getString("emerOccur", "<unset>"));
	    Log.v("���� ������?", "�ñ��մϴ�=" + this.bEmergencySetting.getContactMethod().toString());
	    Log.v("�޼����� �ٲ��?", "�ñ��մϴ�=" + prefs.getString("emerMessage", "<unset>"));
	    bEmergencySetting.setMessage(prefs.getString("emerMessage", "<unset>"));
		
	}

}

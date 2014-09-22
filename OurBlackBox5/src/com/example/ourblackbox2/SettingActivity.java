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
		
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this); // 이런식으로 값을 가져와야함....
		

		/*--------------------녹화설정--------------------------------------*/
		Log.v("녹화 품질이 바뀌나요?","궁금합니당="+prefs.getString("recQuality", "<unset>"));
		BRecordingSetting.recQuality=prefs.getString("recQuality", "<unset>");
		Log.v("값이 들어갔나용?","궁금합니당="+BRecordingSetting.recQuality);
		
		Log.v("녹화 시간이 바뀌나요?","궁금합니당="+prefs.getString("recPeriod", "<unset>"));
		BRecordingSetting.recPeriod=prefs.getString("recPeriod", "<unset>");
		Log.v("값이 들어갔나용?","궁금합니당="+BRecordingSetting.recPeriod);
		
		Log.v("충격 감도가 바뀌나요?","궁금합니당="+prefs.getString("sensitivity", "<unset>"));
		BSensorSetting.sensitivity=prefs.getString("sensitivity", "<unset>");
		Log.v("값이 들어갔나용?","궁금합니당="+BSensorSetting.sensitivity);
		
		Log.v("저장소의 위치가 바뀌나요?","궁금합니당="+prefs.getString("storageLocation", "<unset>"));
		BStorageSetting.storageLocation=prefs.getString("storageLocation", "<unset>");
		Log.v("값이 들어갔나용?","궁금합니당="+BStorageSetting.storageLocation);
		
		Log.v("전화번호가 바뀌나요?", "궁금합니당=" + prefs.getString("emerPhonenum", "<unset>"));
	    bEmergencySetting.setEmerNum(prefs.getString("emerPhonenum", "<unset>"));
	    Log.v("수단이 바뀌나요?", "궁금합니당=" + prefs.getString("emerOccur", "<unset>"));
	    bEmergencySetting.setContactMethod(prefs.getString("emerOccur", "<unset>"));
	    Log.v("값이 들어갔나용?", "궁금합니당=" + this.bEmergencySetting.getContactMethod().toString());
	    Log.v("메세지가 바뀌나요?", "궁금합니당=" + prefs.getString("emerMessage", "<unset>"));
	    bEmergencySetting.setMessage(prefs.getString("emerMessage", "<unset>"));
		
	}

}

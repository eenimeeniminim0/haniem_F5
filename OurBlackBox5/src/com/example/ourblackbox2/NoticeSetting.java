package com.example.ourblackbox2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class NoticeSetting extends PreferenceActivity{

	Button update_button;
	private BroadcastReceiver finishedReceiver=null;
	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		addPreferencesFromResource(R.xml.notice_prefer);
		finishedReceiver= new BroadcastReceiver(){
			@Override
			public void onReceive(Context context, Intent intent){
				NoticeSetting.this.finish();
			}
		};
		
		registerFinishedReceiver();
		
		//Intent intent= new Intent(NoticeSetting.this,MainActivity.class);
		Preference versionUpdate=findPreference("version_update");
		//versionUpdate.setIntent(intent);
		
		
	}
	
	private void registerFinishedReceiver(){
		IntentFilter filter= new IntentFilter("android.intent.action.Finish");
		registerReceiver(finishedReceiver,filter);
	}
	
	private void unregisterFinishedReceiver(){
		unregisterReceiver(finishedReceiver);
	}
	
	
	@Override
	protected void onDestroy(){	
		unregisterFinishedReceiver();
		Log.i("NottieSetting","온디스트로이?");
		super.onDestroy();
	}
	
	@Override
	protected void onPause(){	
		//unregisterFinishedReceiver();
		Log.i("NottieSetting","온퍼즈?");
		super.onPause();
		//finish();
	}
	
	@Override
	protected void onStop(){	
		//unregisterFinishedReceiver();
		Log.i("NottieSetting","온스탑?");
		super.onStop();
	}


}

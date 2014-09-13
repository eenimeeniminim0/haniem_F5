package com.example.ourblackbox2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class NoticeSetting extends PreferenceActivity{//Activity implements OnClickListener{

	Button update_button;
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
		
		Intent intent= new Intent(NoticeSetting.this,MainActivity.class);
		
		Preference latestVersion=findPreference("latest_version");
		
		Preference versionUpdate=findPreference("version_update");
		versionUpdate.setIntent(intent);
		
		
		/*super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);*/
	
	    // TODO Auto-generated method stub
	    //setContentView(R.layout.notice);
	    
	    //addPreferencesFromResource(R.xml.notice_prefer);

		//Button update_button = (Button) findViewById(R.id.update_button);
		//update_button.setOnClickListener(this);
	}

	//@Override
	/*public void onClick(View v) {

		// TODO Auto-generated method stub

		if (v == update_button) {
			//update
		}
	}*/

}

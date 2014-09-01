package com.example.ourblackbox2;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NoticeSetting extends Activity implements OnClickListener{

	Button update_button;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.notice);

		Button update_button = (Button) findViewById(R.id.update_button);
		update_button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub

		if (v == update_button) {
			//update
		}
	}

}

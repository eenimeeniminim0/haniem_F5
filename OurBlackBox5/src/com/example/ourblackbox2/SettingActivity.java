package com.example.ourblackbox2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class SettingActivity extends ActionBarActivity implements OnClickListener {
	
	private Button bQuality;
	private Button bResolution;
	private Button bTerm;
	private Button bLED;
	private Button bAudio;
	//private Button bBack;
	

	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 없애기
		//WindowManager.LayoutParams.FLAG_FULLSCREEN);//화면풀스크린
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//화면 가로로 설정
		
        setContentView(R.layout.setting_activity);//레코딩 레이아웃
		Toast.makeText(getApplicationContext(), "레코딩액티비티 시작", Toast.LENGTH_SHORT).show();
		
		bQuality=(Button)findViewById(R.id.button1);
		bResolution=(Button)findViewById(R.id.button2);
		bTerm=(Button)findViewById(R.id.button3);
		bLED=(Button)findViewById(R.id.button4);
		bAudio=(Button)findViewById(R.id.button5);
		
			}


    @Override
    public void onClick(View v) {
    	
    	switch(v.getId()){
    	
    	case R.id.button1:
    		    		
    	case R.id.button2:
    		break;
    		
    	case R.id.button3:
    	
    	case R.id.button4:
    		
    	case R.id.button5:
    
    	}
    }
    


}

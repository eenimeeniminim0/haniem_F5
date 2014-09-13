package com.example.ourblackbox2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ParkingGuideActivity extends ActionBarActivity implements OnClickListener {
	

	private Button Home;
	

	PowerManager pm;
	PowerManager.WakeLock wakeLock;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        		
        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 없애기
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);//화면풀스크린
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//화면 가로로 설정
        
        setContentView(R.layout.parking);
        
		Toast.makeText(getApplicationContext(), "주차가이드 시작", Toast.LENGTH_SHORT).show();

		BSurfaceView.bSurface = (BSurfaceView)findViewById(R.id.CameraPreview);
	
        Home=(Button)findViewById(R.id.home);        
        
        Home.setOnClickListener(this);
        	
	}
	

    @Override
    public void onClick(View v) {
    	
    	switch(v.getId()){
    		    		
    	case R.id.home:
    		Toast.makeText(this, "뒤로 갈까요?", Toast.LENGTH_SHORT).show();
    		Intent intent=new Intent(this,MainActivity.class);
	    	startActivity(intent);
    		break;
    
    	}
    }	
	

}
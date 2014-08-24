package com.example.ourblackbox2;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;


public class SettingActivity extends ActionBarActivity implements OnClickListener {
	
	private Button bQuality;
	private Button bResolution;
	private Button bTerm;
	private ToggleButton bLED;
	private Button bAudio;
	//private Button bBack;
	private Context context; 

	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Ÿ��Ʋ ���ֱ�
		//WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȭ��Ǯ��ũ��
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ȭ�� ���η� ����
		
        setContentView(R.layout.setting_activity);
		Toast.makeText(getApplicationContext(), "���ڵ���Ƽ��Ƽ ����", Toast.LENGTH_SHORT).show();
		
		bQuality=(Button)findViewById(R.id.button1);
		bResolution=(Button)findViewById(R.id.button2);
		bTerm=(Button)findViewById(R.id.button3);
		bLED=(ToggleButton)findViewById(R.id.button4);
		bAudio=(Button)findViewById(R.id.button5);
		
		bLED.setOnClickListener(this);

	}
	
//	public Notification.Builder setLights (int argb, int onMs, int offMs){
		
	//}

    @Override
    public void onClick(View v) {
    	
    	switch(v.getId()){
    	
    	case R.id.button1:
    		    		
    	case R.id.button2:
    		break;
    		
    	case R.id.button3:
    	
    	case R.id.button4:
    		
    		/*
    		if(bLED.isChecked())
    		{ 
    			NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	        Notification notif = new Notification();
    	        Notification.Builder noti = new Notification.Builder(context);
    	        setLights(Color.RED,1000,1000);
    	        notif.defaults = 0;
    	        notif.flags = Notification.FLAG_SHOW_LIGHTS;
    	        nm.notify(0, notif);
    	        Toast.makeText(getApplicationContext(), "LED ����", Toast.LENGTH_SHORT).show();
    		}
    		
    		else
    		{
    			
    		}
    		*/
      		break;
      		
    		
    	case R.id.button5:
    
    	}
    }
    public Notification.Builder  setLights (int argb, int onMs, int offMs){
		return null;
    	
    }




}

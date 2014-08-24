package com.example.ourblackbox2;

import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;


public class SettingActivity extends ActionBarActivity implements OnClickListener {
	
	private ToggleButton bQuality;
	private Button bResolution;
	private Button bTerm;
	private Button bLED;
	private Button bAudio;
	protected static boolean isButtonPushed=false;
	//private Button bBack;
	
	SettingControl remote= new SettingControl();
	BQualityCommand bQCommand= new BQualityCommand();
	

	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Ÿ��Ʋ ���ֱ�
		//WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȭ��Ǯ��ũ��
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ȭ�� ���η� ����
		
        setContentView(R.layout.setting_activity);//���ڵ� ���̾ƿ�
		Toast.makeText(getApplicationContext(), "���ڵ���Ƽ��Ƽ ����", Toast.LENGTH_SHORT).show();
		
		remote.setCommand(bQCommand);
		
		
		bQuality=(ToggleButton)findViewById(R.id.button1);
		bResolution=(Button)findViewById(R.id.button2);
		bTerm=(Button)findViewById(R.id.button3);
		bLED=(Button)findViewById(R.id.button4);
		bAudio=(Button)findViewById(R.id.button5);
		
		bQuality.setOnClickListener(this);
		
			}


    @Override
    public void onClick(View v) {
    	
    	switch(v.getId()){
    	
    	case R.id.button1:
    		//��ȭǰ��
    		 if(bQuality.isChecked())
    		 {
    			 Toast.makeText(this, "��ȭǰ��On", Toast.LENGTH_SHORT).show();
    	    		remote.selected();
    		 }
    		 else
    		 {
    			 Toast.makeText(this, "��ȭǰ��Off", Toast.LENGTH_SHORT).show();
    			 remote.unSelected();
    			 
    		 }
    		break;
    		    		
    	case R.id.button2:
    		//ȭ�� �ػ�
    		break;
    		
    	case R.id.button3:
    		//���弳��
    	
    	case R.id.button4:
    		//������Ʈ��ũ ����
    	case R.id.button5:
    
    	}
    }



}

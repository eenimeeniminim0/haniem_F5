package com.example.ourblackbox2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;


public class SettingActivity extends ActionBarActivity implements OnClickListener {
	
	private ToggleButton bQuality;
	private ImageButton bResolution;
	private ImageButton bTerm;
	private ToggleButton bLED;
	private ImageButton bAudio;
	protected static boolean isButtonPushed=false;
	//private Button bBack;

	private Context context; 
	private final static int MESSAGE_ID = 1;
	private NotificationManager mNotificationManager = null;

	SettingControl remote= new SettingControl();
	BQualityCommand bQCommand= new BQualityCommand();	

	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Ÿ��Ʋ ���ֱ�
		//WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȭ��Ǯ��ũ��
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ȭ�� ���η� ����
		
        setContentView(R.layout.setting_activity);
		Toast.makeText(getApplicationContext(), "������Ƽ��Ƽ ����", Toast.LENGTH_SHORT).show();
		
		remote.setCommand(bQCommand);		
		mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		bQuality=(ToggleButton)findViewById(R.id.button01);
		bResolution=(ImageButton)findViewById(R.id.button02);
		bTerm=(ImageButton)findViewById(R.id.button03);
		bLED=(ToggleButton)findViewById(R.id.button04);
		bAudio=(ImageButton)findViewById(R.id.button05);
		
		bQuality.setOnClickListener(this);
		bLED.setOnClickListener(this);

	}
	
//	public Notification.Builder setLights (int argb, int onMs, int offMs){
		
	//}

    @Override
    public void onClick(View v) {
    	
    	switch(v.getId()){
    	
    	case R.id.button01:
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
    		    		
    	case R.id.button02:
    		//ȭ�� �ػ�
    		break;
    		
    	case R.id.button03:
    		//���弳��
    	
    	case R.id.button04:
    		//LED
    		if(bLED.isChecked()){
    			
    			ledOn();
    			
    		}
    		else{
    			
    			ledOff();
    		
    		}
      		break;
      		
    		
    		//������Ʈ��ũ ����

    	case R.id.button05:
    
    	}
    }
    
/*
	public void ledOn(){
    	String ticker = "OurblackBox�� �������Դϴ�";
    	String title = "OurblackBox";
    	String text = "������";
    	
    	Notification noti = new Notification();
    	
    	//noti.flags |= Notification.FLAG_AUTO_CANCEL; //�˸� Ŭ���� �ڵ����� �˸� ���
    	//noti.flags |= Notification.FLAG_SHOW_LIGHTS;
    	
    	Intent intent = new Intent(this, RecordingActivity.class);
    	PendingIntent pendingIntent = PendingIntent.getActivity(SettingActivity.this, 0, intent, 0);
    	
    	noti.flags = Notification.DEFAULT_LIGHTS|Notification.FLAG_AUTO_CANCEL|Notification.FLAG_ONGOING_EVENT;
    	
    	
    	noti.icon = android.R.drawable.ic_input_add;
    	noti.tickerText = ticker;
    	noti.when = System.currentTimeMillis(); 
    	noti.defaults = 0;
    	noti.ledARGB = 0xff0000ff;
    	noti.ledOnMS = 300;
    	noti.ledOffMS = 300;
    	
    	
    	noti.setLatestEventInfo(this, title, text, pendingIntent);
    
    	bNotificationManager.notify(MESSAGE_ID, noti);
    }
 */
    public void ledOn(){
    	
    	String ticker = "OurblackBox�� �������Դϴ�";
    	String title = "OurblackBox";
    	String text = "������";
    	String ns = Context.NOTIFICATION_SERVICE;
    	NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);
    	
    	Intent intent = new Intent(this, RecordingActivity.class);
    	PendingIntent pendingIntent = PendingIntent.getActivity(SettingActivity.this, 0, intent, 0);
    	
    	int icon = android.R.drawable.ic_input_add;
    	CharSequence tickerText = ticker;
    	long when = System.currentTimeMillis();
    	Notification.Builder builder = new Notification.Builder(SettingActivity.this);
    	builder.setSmallIcon(icon).setTicker(tickerText).setWhen(when);
    	builder.setContentTitle(title);
    	builder.setContentText(text);
    	builder.setLights(Color.GREEN,500,500);
    	builder.build();
    	
    	Notification notification = builder.getNotification();
    	notification.flags |= Notification.FLAG_SHOW_LIGHTS;
    	notification.flags |= Notification.FLAG_INSISTENT;
    	
    	notification.setLatestEventInfo(this, title, text, pendingIntent);
    	mNotificationManager.notify(MESSAGE_ID, notification);
    	
    	
    	
    	
    	
    }
    
    public void ledOff(){
    	
    	mNotificationManager.cancel(MESSAGE_ID);
    
    }



}

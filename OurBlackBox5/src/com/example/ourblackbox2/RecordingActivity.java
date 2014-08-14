package com.example.ourblackbox2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RecordingActivity extends ActionBarActivity implements OnClickListener  {
	BSurfaceView bSurface;
	BRecorder bRecorder;
	ToggleButton VideoCapture, SnapShot;
	Button Tools;
	Thread videotimerUpdate;
	Handler videotimerUpdateHandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Ÿ��Ʋ ���ֱ�
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȭ��Ǯ��ũ��
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ȭ�� ���η� ����
        
        setContentView(R.layout.recording);//���ڵ� ���̾ƿ�
		Toast.makeText(getApplicationContext(), "���ڵ���Ƽ��Ƽ ����", Toast.LENGTH_SHORT).show();
		
		videotimerUpdateHandler=new Handler();
		
		bRecorder=new BRecorder();
		
		bSurface=(BSurfaceView)findViewById(R.id.CameraPreview);		
		VideoCapture=(ToggleButton)findViewById(R.id.VideoCapture);
        SnapShot=(ToggleButton)findViewById(R.id.Snapshot);
        Tools=(Button)findViewById(R.id.Tools);

        VideoCapture.setOnClickListener(this);
        SnapShot.setOnClickListener(this);
        Tools.setOnClickListener(this);

	
		
	}
	

    @Override
    public void onClick(View v) {
    	
    	switch(v.getId()){
    	
    	case R.id.VideoCapture:
    		if(VideoCapture.isChecked())
    		{  		
    			videotimerUpdate= new Thread(new Runnable(){
    				int i=0;
    				public void run(){    
    					if(bRecorder.videoCurrentTime < BRecorder.SECONDS_BETWEEN_VIDEO){
    						Log.v("�������","������ư�����="+i);

    						if(bRecorder.videoCurrentTime==0){
    							bRecorder.startRecorder(bSurface);
    	    					//bRecorder.startRecorder();
    						}
    						bRecorder.videoCurrentTime++;
    						Log.v("videoCurrentTime","time="+bRecorder.videoCurrentTime);
    						videotimerUpdateHandler.postDelayed(videotimerUpdate, 1000);
    					}else if(bRecorder.videoCurrentTime == BRecorder.SECONDS_BETWEEN_VIDEO){
    						
    						bRecorder.resetRecorder();
    						i++;
    						videotimerUpdateHandler.post(videotimerUpdate);		
    					}
    				}
    			});
    			videotimerUpdate.start();
    			Toast.makeText(this, "����ĸ��On", Toast.LENGTH_SHORT).show();
    			
    		}
    		
    		else
    		{
    			if(bRecorder.isRecording==true){
    				Toast.makeText(this, "����ĸ��Off", Toast.LENGTH_SHORT).show();
    				bRecorder.stopRecorder(bSurface);
        			if(bRecorder.isVideotimerRunning==true)//���� ���������尡 ���ư���������
        			{
        				videotimerUpdateHandler.removeCallbacks(videotimerUpdate);
        				bRecorder.isVideotimerRunning=false;//���������尡 ����Ǿ���
        			}

    				
    			}
    		}
      		break;
    		    		
    	case R.id.Snapshot:
    		Toast.makeText(this, "�������� �ɱ��?", Toast.LENGTH_SHORT).show();
    		break;
    		
    	case R.id.Tools:
    		Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
    
    	}
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	bRecorder.destroyRecorder();
    }
}


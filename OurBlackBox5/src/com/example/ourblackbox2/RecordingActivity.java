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
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 없애기
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);//화면풀스크린
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//화면 가로로 설정
        
        setContentView(R.layout.recording);//레코딩 레이아웃
		Toast.makeText(getApplicationContext(), "레코딩액티비티 시작", Toast.LENGTH_SHORT).show();
		
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
    						Log.v("스레드님","몇번돌아갔나요="+i);

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
    			Toast.makeText(this, "비디오캡쳐On", Toast.LENGTH_SHORT).show();
    			
    		}
    		
    		else
    		{
    			if(bRecorder.isRecording==true){
    				Toast.makeText(this, "비디오캡쳐Off", Toast.LENGTH_SHORT).show();
    				bRecorder.stopRecorder(bSurface);
        			if(bRecorder.isVideotimerRunning==true)//만약 비디오스레드가 돌아가고있으면
        			{
        				videotimerUpdateHandler.removeCallbacks(videotimerUpdate);
        				bRecorder.isVideotimerRunning=false;//비디오스레드가 종료되었음
        			}

    				
    			}
    		}
      		break;
    		    		
    	case R.id.Snapshot:
    		Toast.makeText(this, "스냅샷이 될까요?", Toast.LENGTH_SHORT).show();
    		break;
    		
    	case R.id.Tools:
    		Toast.makeText(this, "도구", Toast.LENGTH_SHORT).show();
    
    	}
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	bRecorder.destroyRecorder();
    }
}


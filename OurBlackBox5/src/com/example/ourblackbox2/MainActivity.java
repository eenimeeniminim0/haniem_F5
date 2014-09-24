package com.example.ourblackbox2;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	
	ImageButton recording,gallery, setting, exit;
	boolean controlbool=false;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        Log.v("메인엑티비티","onCreate?");
        
     
  
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    @Override
  	protected void onPause() {
  		// TODO Auto-generated method stub
  		super.onPause();
  	}


  	@Override
  	protected void onResume() {
  		// TODO Auto-generated method stub
        controlbool=false;
		if(isRecordServiceRunning(MainActivity.this, "com.example.ourblackbox2.RecordingService")){
    		Toast.makeText(getApplicationContext(), "레코딩중지", Toast.LENGTH_SHORT).show();
            stopService(new Intent(MainActivity.this, RecordingService.class));
    	}
  		super.onResume();
  		Log.v("메인엑티비티","onResume?");
  	}


  	@Override
  	protected void onStart() {
  		// TODO Auto-generated method stub
  		super.onStart();
  		   recording=(ImageButton)findViewById(R.id.button1);//
  	       gallery=(ImageButton)findViewById(R.id.button2);//
  	       setting=(ImageButton)findViewById(R.id.button3);//
  	       exit=(ImageButton)findViewById(R.id.button4);//
  	       
  	       recording.setOnClickListener(this);
  	       gallery.setOnClickListener(this);
  	       setting.setOnClickListener(this);
  	       exit.setOnClickListener(this);
  	       
  	       initSetting();
  		
  	}

    
    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==recording){
			controlbool=true;
			
	    	Intent intent=new Intent(this,RecordingActivity.class);
	    	startActivity(intent);
			
	    	finish();
		}else if(v==gallery){
			controlbool=true;
			Intent intent=new Intent(this,GalleryActivity.class);
	    	startActivity(intent);	
		}else if(v==setting){
			controlbool=true;
			Intent intent=new Intent(this,SettingActivity.class);
	    	startActivity(intent);
		}else if(v==exit){
			controlbool=true;
			//Intent intent =new Intent("android.intent.action.Finish");
			//sendBroadcast(intent);
			Toast.makeText(getApplicationContext(), "종료!", Toast.LENGTH_SHORT).show();
			System.exit(0);

	    	
		}
		
	}
	
	public void initSetting(){
		
	       SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
	       SharedPreferences.Editor editor= prefs.edit();
	       
	       if(prefs.getString("recQuality", "<unset>")=="<unset>"){   
  	    	   editor.putString("recQuality", "high");
  	    	   editor.commit();
  	       }
  	       
  	       if(prefs.getString("recPeriod", "<unset>")=="<unset>"){   
  	    	   	editor.putString("recPeriod","1min");
  	    	   	editor.commit();
	       }
  	       
  	       if(prefs.getString("sensitivity", "<unset>")=="<unset>"){   
	    	   	editor.putString("sensitivity","normal");
	    	   	editor.commit();
	       }
  	     
  	       if(prefs.getString("storageLocation", "<unset>")=="<unset>"){   
	    	   	editor.putString("storageLocation","internal");
	    	   	editor.commit();
	       }
  	       
  	     if(prefs.getString("emerPhonenum", "<unset>")=="<unset>"){   
	    	   	editor.putString("emerPhonenum","01052881019");
	    	   	editor.commit();
	       }
  	     
  	     if(prefs.getString("emerOccur", "<unset>")=="<unset>"){   
  	    	 editor.putString("emerOccur","call");
  	    	 editor.commit();
  	     }
  	     
  	    if(prefs.getString("emerMessage", "<unset>")=="<unset>"){   
	    	 editor.putString("emerMessage","살려주세요?");
	    	 editor.commit();
	     }
  	       
    	     /*--------------------녹화설정--------------------------------------*/
    			Log.v("메인 시작시 녹화 품질이 바뀌나요?","궁금합니당="+prefs.getString("recQuality", "<unset>"));
    			BRecordingSetting.recQuality=prefs.getString("recQuality", "<unset>");
    			Log.v("메인 시작시 값이 들어갔나용?","궁금합니당="+BRecordingSetting.recQuality);
    			
    			Log.v("메인 시작시 녹화 시간이 바뀌나요?","궁금합니당="+prefs.getString("recPeriod", "<unset>"));
    			BRecordingSetting.recPeriod=prefs.getString("recPeriod", "<unset>");
    			Log.v("메인 시작시 값이 들어갔나용?","궁금합니당="+BRecordingSetting.recPeriod);
    			
    			Log.v("충격 감도가 바뀌나요?","궁금합니당="+prefs.getString("sensitivity", "<unset>"));
    			BSensorSetting.sensitivity=prefs.getString("sensitivity", "<unset>");
    			Log.v("값이 들어갔나용?","궁금합니당="+BSensorSetting.sensitivity);
    			
    			Log.v("저장소의 위치가 바뀌나요?","궁금합니당="+prefs.getString("storageLocation", "<unset>"));
    			BStorageSetting.storageLocation=prefs.getString("storageLocation", "<unset>");
    			Log.v("값이 들어갔나용?","궁금합니당="+BStorageSetting.storageLocation);
    			
    			Log.v("메인 시작시 녹화 품질이 바뀌나요?","궁금합니당="+prefs.getString("recQuality", "<unset>"));
    			BEmergencySetting.emerNum=prefs.getString("recQuality", "<unset>");
    			Log.v("메인 시작시 값이 들어갔나용?","궁금합니당="+BEmergencySetting.emerNum);
    		  			
    			Log.v("전화번호가 바뀌나요?", "궁금합니당=" + prefs.getString("emerPhonenum", "<unset>"));
    		    BEmergencySetting.emerNum=prefs.getString("emerPhonenum", "<unset>");
    		    
    		    Log.v("수단이 바뀌나요?", "궁금합니당=" + prefs.getString("emerOccur", "<unset>"));
    		    BEmergencySetting.contactMethod=prefs.getString("emerOccur", "<unset>");
    		    Log.v("값이 들어갔나용?", "궁금합니당=" + BEmergencySetting.contactMethod.toString());
    		    
    		    BEmergencySetting.emerMessage=prefs.getString("emerMessage", "<unset>");
    		    Log.v("메세지가 바뀌나요?", "궁금합니당=" + prefs.getString("emerMessage", "<unset>"));
    		    
    
    			
    			/*----------------나중에 지우기----------------------------------------*/
  	       
		
	}
	
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	//wakeLock.release();
    	Log.v("메인엑티비티","ondestroy?");

    }
	
	
	@Override
	protected void onUserLeaveHint() {
		// TODO Auto-generated method stub
    	
		if(!controlbool){
		if(!isRecordServiceRunning(MainActivity.this, "com.example.ourblackbox2.RecordingService")){
    		
    		Toast.makeText(getApplicationContext(), "레코딩시작", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, RecordingService.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startService(intent);
            
            
		}
		
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.example.ourblackbox2.servicedestroyrecorder");

		
		super.onUserLeaveHint();
		
		finish();
			
		}
	}
    		
	private boolean isRecordServiceRunning(Context ctx, String s_service_name) {

      	ActivityManager manager = (ActivityManager) ctx.getSystemService(Activity.ACTIVITY_SERVICE);

      	for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {

      	    if (s_service_name.equals(service.service.getClassName())) {

      	        return true;
      	    }
      	}

      	return false;
  }

	

}

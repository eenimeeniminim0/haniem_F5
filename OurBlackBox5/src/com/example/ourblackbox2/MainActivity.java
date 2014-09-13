package com.example.ourblackbox2;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
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
	BIOstream sd;
	
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
        
     
  
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
		if(isRecordServiceRunning(MainActivity.this, "com.example.ourblackbox2.RecordingService")){
    		
    		Toast.makeText(getApplicationContext(), "���ڵ�����", Toast.LENGTH_SHORT).show();
            stopService(new Intent(MainActivity.this, RecordingService.class));
    	}
  		super.onResume();
  	}


  	@Override
  	protected void onStart() {
  		// TODO Auto-generated method stub
  		super.onStart();
  			
  		   sd=new BIOstream();
  		   recording=(ImageButton)findViewById(R.id.button1);//
  	       gallery=(ImageButton)findViewById(R.id.button2);//
  	       setting=(ImageButton)findViewById(R.id.button3);//
  	       exit=(ImageButton)findViewById(R.id.button4);//
  	       
  	       recording.setOnClickListener(this);
  	       gallery.setOnClickListener(this);
  	       setting.setOnClickListener(this);
  	       exit.setOnClickListener(this);
  	       
  	       
  	       SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
  	       SharedPreferences.Editor editor= prefs.edit();
  	       
  	       if(prefs.getString("recQuality", "<unset>")=="<unset>")
  	       {
  	       
  	       editor.putString("recQuality", "high");
  	      // editor.commit();
  	       
  	       editor.putString("recPeriod","1min");
	      // editor.commit();
	       
	       editor.putString("sensitivity", "normal");
  	       //editor.commit();
  	       
  	       editor.putString("storageLocation","internal");
	       editor.commit();
  	       }
  	       
  	     /*--------------------��ȭ����--------------------------------------*/
  			Log.v("���� ���۽� ��ȭ ǰ���� �ٲ��?","�ñ��մϴ�="+prefs.getString("recQuality", "<unset>"));
  			BRecordingSetting.recQuality=prefs.getString("recQuality", "<unset>");
  			Log.v("���� ���۽� ���� ������?","�ñ��մϴ�="+BRecordingSetting.recQuality);
  			
  			Log.v("���� ���۽� ��ȭ �ð��� �ٲ��?","�ñ��մϴ�="+prefs.getString("recPeriod", "<unset>"));
  			BRecordingSetting.recPeriod=prefs.getString("recPeriod", "<unset>");
  			Log.v("���� ���۽� ���� ������?","�ñ��մϴ�="+BRecordingSetting.recPeriod);
  			
  			Log.v("��� ������ �ٲ��?","�ñ��մϴ�="+prefs.getString("sensitivity", "<unset>"));
  			BSensorSetting.sensitivity=prefs.getString("sensitivity", "<unset>");
  			Log.v("���� ������?","�ñ��մϴ�="+BSensorSetting.sensitivity);
  			
  			Log.v("������� ��ġ�� �ٲ��?","�ñ��մϴ�="+prefs.getString("storageLocation", "<unset>"));
  			BStorageSetting.storageLocation=prefs.getString("storageLocation", "<unset>");
  			Log.v("���� ������?","�ñ��մϴ�="+BStorageSetting.storageLocation);
  			
  			
  			Log.v("����޸𸮸� �о�ü� ������?","�ñ��մϴ�="+sd.getExternalMounts());
  			
  			
  		
  	}

    
    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==recording){
		//	Toast.makeText(getApplicationContext(), "���ڵ���Ƽ��Ƽ��", Toast.LENGTH_SHORT).show();
	    	Intent intent=new Intent(this,RecordingActivity.class);
	    	startActivity(intent);
			
		}else if(v==gallery){
		//	Toast.makeText(getApplicationContext(), "ȯ�漳������", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(this,GalleryActivity.class);
	    	startActivity(intent);	
		}else if(v==setting){
		//	Toast.makeText(getApplicationContext(), "����������", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(this,SettingActivity.class);
	    	startActivity(intent);
		}else if(v==exit){
			Toast.makeText(getApplicationContext(), "����!", Toast.LENGTH_SHORT).show();

	    	
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

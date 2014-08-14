package com.example.ourblackbox2;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	
	Button recording, preference, photogallery, videogallery;
	
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
  		super.onResume();
  	}


  	@Override
  	protected void onStart() {
  		// TODO Auto-generated method stub
  		super.onStart();
  		  recording=(Button)findViewById(R.id.Recording);//레코딩액티비티로가는버튼
  	       preference=(Button)findViewById(R.id.Preference);//환경설정액티비티로가는버튼
  	       photogallery=(Button)findViewById(R.id.PhotoGallery);//갤러리액티비티로가는버튼
  	       videogallery=(Button)findViewById(R.id.VideoGallery);//비디오앨범액티비티로가는버튼
  	       
  	       recording.setOnClickListener(this);
  	       preference.setOnClickListener(this);
  	       photogallery.setOnClickListener(this);
  	       videogallery.setOnClickListener(this);
  		
  	}

    
    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==recording){
			Toast.makeText(getApplicationContext(), "레코딩액티비티로", Toast.LENGTH_SHORT).show();
	    	Intent intent=new Intent(this,RecordingActivity.class);
	    	startActivity(intent);
			
		}else if(v==preference){
			Toast.makeText(getApplicationContext(), "환경설정으로", Toast.LENGTH_SHORT).show();
			
		}else if(v==photogallery){
			Toast.makeText(getApplicationContext(), "사진갤러리", Toast.LENGTH_SHORT).show();
			
		}else if(v==videogallery){
			Toast.makeText(getApplicationContext(), "비디오갤러리", Toast.LENGTH_SHORT).show();
			
		}
		
	}

}

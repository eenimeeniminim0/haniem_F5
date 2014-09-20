package com.example.ourblackbox2;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import android.content.ContentValues;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.MediaStore.Video;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.app.Service;

public class BServiceRecorder
{
	
	private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private static Camera mServiceCamera;
	
	protected MediaRecorder bRecorder;
	protected String filePath;	
	protected File videoFile;
	protected BIOstream biostream;
	protected static String Path;
	protected static String File;
	//
	protected boolean isRecording;//현재녹화중인지나타내는것
	protected boolean isVideotimerRunning;//비디오 타이머가 작동중인지 아닌지

	
	
	
	public BServiceRecorder(SurfaceView sv)
	{
		//mSurfaceView=RecordingActivity.RCSurfaceview;
		//mSurfaceHolder=RecordingActivity.RCSurfaceHolder;
		mSurfaceView=sv;
		mSurfaceHolder=sv.getHolder();
		videoFile=null;
		isRecording=false;
		isVideotimerRunning=false;
		Path="";
		File="";
		bRecorder= new MediaRecorder();
		biostream=new BIOstream();
		mServiceCamera = Camera.open();
	}
	
	
	// 동영상 촬영 관련  메소드
	public void initRecorder()
	{
				
		   Camera.Parameters params = mServiceCamera.getParameters();
           mServiceCamera.setParameters(params);
           Camera.Parameters p = mServiceCamera.getParameters();

           final List<Size> listSize = p.getSupportedPreviewSizes();
           Size mPreviewSize = listSize.get(2);
           
           p.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
           p.setPreviewFormat(PixelFormat.YCbCr_420_SP);
           mServiceCamera.setParameters(p);

           try {
            /**/   mServiceCamera.setPreviewDisplay(mSurfaceHolder);
               mServiceCamera.startPreview();
           }
           catch (IOException e) {
              
               e.printStackTrace();
           }	
		
		File=biostream.createInternalFolder();
		Path=File+"/"+biostream.createName(System.currentTimeMillis());
		
		if(bRecorder==null){
			bRecorder= new MediaRecorder();
		}else{
			bRecorder.reset();
		}
		
		
		mServiceCamera.unlock();
		bRecorder.setCamera(mServiceCamera);
		
		bRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		bRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		
		bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_LOW));
		
		bRecorder.setMaxDuration(60000);//최대캡쳐시간 60초
		bRecorder.setMaxFileSize(10000000);//최대파일크기 10메가
		
		bRecorder.setOutputFile(Path);
		bRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
		
		
	}
	
	
	
	public void startRecorder()
	{	
		
		try{	
			bRecorder.prepare();
			bRecorder.start();
			Log.v("is playing?","really?="+20000);
		}catch(IllegalStateException e){
			e.printStackTrace();
			return;
		}catch(IOException e){
			e.printStackTrace();
			return;
		}catch(Exception e){
			e.printStackTrace();
			return;
		}	
		isRecording=true;
		isVideotimerRunning=true;

	}
	
	public void resetRecorder(){
	
		bRecorder.stop();
		bRecorder.reset();
		isRecording=false;
	}

	public void stopRecorder()
	{	
		bRecorder.stop();
		bRecorder.reset();
		isVideotimerRunning=false;
		bRecorder=null;
		isRecording=false;
		mServiceCamera.lock();//카메라객체 잠금
		
	}
	
	public void destroyRecorder()
	{
		
		if(bRecorder !=null)
		{
			bRecorder.release();
			bRecorder=null;
		}
		if(mServiceCamera!=null)
		{
		mServiceCamera.release();
		mServiceCamera=null;
		}
		Log.v("서비스레코더","카메라파괴");
		
		
		
	}
	
	
}
package com.example.ourblackbox2;

import java.io.File;
import java.io.IOException;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class BRecorder extends MediaRecorder
{
	
	BIOstream biostream;
	BRecorder bRecorder;
	String filePath;	
	File videoFile;
	boolean isRecording;//현재녹화중인지나타내는것
	String Path;
	//
	static int SECONDS_BETWEEN_VIDEO=15;//동영상 녹화 간격
	int videoCurrentTime;//처음 시작 시간
	//
	boolean isVideotimerRunning;//비디오 타이머가 작동중인지 아닌지
	
	
	public BRecorder()
	{
		videoFile=null;
		isRecording=false;
		isVideotimerRunning=false;
		Path="";
		videoCurrentTime=0;
		biostream = new BIOstream();
	}
	
	// 동영상촬영관련  메소드들.

	public void startRecorder(BSurfaceView bSurface)
	{	
		bSurface.getBSurfaceView().setVisibility(View.VISIBLE);		
		Path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+biostream.createName(System.currentTimeMillis());
		
		if(bRecorder==null){
			bRecorder= new BRecorder();
		}else{
			reset();
		}
		
		bSurface.getCamera().stopPreview();
		bSurface.getCamera().unlock();
		setCamera(bSurface.getCamera());
		
		setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		
		setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_HIGH));
		
		//bRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		//bRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		//bRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		setMaxDuration(50000);//최대캡쳐시간 50초
		setMaxFileSize(5000000);//최대파일크기 5메가
		
		setOutputFile(Path);
		setPreviewDisplay(bSurface.getSurfaceHolder().getSurface());
		
		
		try{	
			prepare();
			start();
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
	
		//registerVideo();
		reset();
		isRecording=false;
		videoCurrentTime=0;
	}

	public void stopRecorder(BSurfaceView bSurface)
	{	
		stop();
		reset();
		//registerVideo
		videoCurrentTime=0;
		bRecorder=null;
		isRecording=false;
		bSurface.getCamera().lock();//카메라객체 잠금

		try {
			bSurface.getCamera().setPreviewDisplay(bSurface.getSurfaceHolder());//카메라객체를 preview display에 할당함.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		bSurface.getCamera().startPreview();//카메라객체에서 받아들이는 화면에 서비스뷰에 프리뷰가 보이는것 시작.
	}
	
	public void destroyRecorder()
	{
		if(bRecorder !=null)
		{
			release();
			bRecorder=null;
		}
	}
	
	/*public boolean getIsRecording()
	{
		return this.isRecording;
	}*/
	

}


    
package com.example.ourblackbox2;

import java.io.File;
import java.io.IOException;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class BRecorder
{
	
	protected BIOstream biostream;
	protected MediaRecorder bRecorder;
	protected String filePath;	
	protected File videoFile;
	protected String Path;
	//
	protected static int SECONDS_BETWEEN_VIDEO=15;//동영상 녹화 간격
	protected static int videoCurrentTime;//처음 시작 시간
	//
	protected boolean isRecording;//현재녹화중인지나타내는것
	protected boolean isVideotimerRunning;//비디오 타이머가 작동중인지 아닌지
	protected static boolean isTimeChange;
	
	public BRecorder()
	{
		videoFile=null;
		isRecording=false;
		isVideotimerRunning=false;
		isTimeChange=false;
		Path="";
		videoCurrentTime=0;
		biostream = new BIOstream();
		bRecorder= new MediaRecorder();
		//bThread=new RecorderThread();
	}
	
	// 동영상촬영관련  메소드들.

	public void startRecorder()
	{	
		BSurfaceView.bSurface.setVisibility(View.VISIBLE);		
		Path=biostream.createFolder()+"/"+biostream.createName(System.currentTimeMillis());
		
		if(bRecorder==null){
			bRecorder= new MediaRecorder();
		}else{
			bRecorder.reset();
		}
		
		BSurfaceView.bSurface.getCamera().stopPreview();
		BSurfaceView.bSurface.getCamera().unlock();
		bRecorder.setCamera(BSurfaceView.bSurface.getCamera());
		
		bRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		bRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		
		bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_HIGH));
		
		//bRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		//bRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		//bRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		bRecorder.setMaxDuration(50000);//최대캡쳐시간 50초
		bRecorder.setMaxFileSize(5000000);//최대파일크기 5메가
		
		bRecorder.setOutputFile(Path);
		bRecorder.setPreviewDisplay(BSurfaceView.bSurface.getSurfaceHolder().getSurface());
		
		//biostream.createVideoPath(System.currentTimeMillis());
		
		
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
		//registerVideo();
		isRecording=false;
		videoCurrentTime=0;
	}

	public void stopRecorder()
	{	
		bRecorder.stop();
		bRecorder.reset();
		//registerVideo()
		
		isVideotimerRunning=false;
		videoCurrentTime=0;
		bRecorder=null;
		isRecording=false;
		BSurfaceView.bSurface.getCamera().lock();//카메라객체 잠금
		

		try {
			BSurfaceView.bSurface.getCamera().setPreviewDisplay(BSurfaceView.bSurface.getSurfaceHolder());//카메라객체를 preview display에 할당함.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		BSurfaceView.bSurface.getCamera().startPreview();//카메라객체에서 받아들이는 화면에 서비스뷰에 프리뷰가 보이는것 시작.
	}
	
	public int checkRecordTime(boolean isSensorDetected)
	{
		if(isSensorDetected && SECONDS_BETWEEN_VIDEO==15){
			isTimeChange=true;
			BSensor.isSensorDetected=false;
			SECONDS_BETWEEN_VIDEO=videoCurrentTime+15;
			return SECONDS_BETWEEN_VIDEO;
		}
		else if(isSensorDetected && SECONDS_BETWEEN_VIDEO!=15){
			isTimeChange=true;
			BSensor.isSensorDetected=false;
			SECONDS_BETWEEN_VIDEO=videoCurrentTime+15;
			return SECONDS_BETWEEN_VIDEO;
		}
		else if(!isSensorDetected && SECONDS_BETWEEN_VIDEO!=15){
			if(isTimeChange){
				//isTimeChange=false;
				return SECONDS_BETWEEN_VIDEO;
			}
			else{	
			SECONDS_BETWEEN_VIDEO=15;
			return SECONDS_BETWEEN_VIDEO;}
		}
		
		else 
			return SECONDS_BETWEEN_VIDEO;

	}
	
	public void destroyRecorder()
	{
		if(bRecorder !=null)
		{
			bRecorder.release();
			bRecorder=null;
		}
	}
	

}
	





    
    
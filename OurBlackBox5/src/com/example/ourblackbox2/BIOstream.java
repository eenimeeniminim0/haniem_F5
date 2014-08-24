package com.example.ourblackbox2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

public class BIOstream  {
	
	private String videoFileName = null;	
	private ContentValues videoValues = null;
	String dir = null;
	String name = "OurblackBox";
	
	public String createName(long dateTaken){
		Date date= new Date(dateTaken);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		return dateFormat.format(date)+".mp4";
	}
	
	public String createFolder(){
		
		String dir=Environment.getExternalStorageDirectory().getPath() + "/" + name;
		File folder = new File(dir);
		folder.mkdirs();
		return dir;
    	
	}
	
	public void fileScan()
	{
		Intent intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri= Uri.parse("file://"+createFolder());
		intent.setData(uri);
		//sendBroadcast(intent);
		
	}

	
	/*void createVideoPath(long dateTaken) {
		String dirName = "OurBlackBox";
		File cameraDir = new File(dirName);
    	cameraDir.mkdirs();
		String title = createName(dateTaken);
		String filename = title;
		ContentValues values = new ContentValues(8);
		values.put(Video.Media.TITLE, title);
		values.put(Video.Media.DISPLAY_NAME, title + ".mp4");
		values.put(Video.Media.DATE_TAKEN, dateTaken);
		values.put(Video.Media.MIME_TYPE, "video/mp4");
		values.put(Video.Media.DATA, filename);
		videoFileName = filename;
		videoValues = values;
	}*/
	
	/*	private void stopRecording() {
	Log.d(Config.TAG, toString() + ".stopRecording(): stop recording...");
	recorder.stop();
	Log.d(Config.TAG, toString() + ".stopRecording(): Recorded video filename:" + videoFileName);

	videoValues.put(Video.Media.DURATION, System.currentTimeMillis()
			- recordingStarted);
	videoValues.put(Video.Media.SIZE, new File(videoFileName).length());
	Uri videoUri = act_.getContentResolver().insert(
			MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoValues);
	if (videoUri == null) {
		Log.d(Config.TAG, toString() + ".stopRecording(): Content resolver failed");
		return;
	}
	Log.d(Config.TAG, toString() + ".stopRecording(): Video URI = " + videoUri.getPath());
	videoValues = null;

	// Force Media scanner to refresh now. Technically, this is
	// unnecessary, as the media scanner will run periodically but
	// helpful for testing.
	act_.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
			videoUri));
	Log.d(Config.TAG, toString() + ".stopRecording(): Video file published");
}*/
	
	
}

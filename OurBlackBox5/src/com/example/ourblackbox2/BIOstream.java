package com.example.ourblackbox2;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Environment;

public class BIOstream {
	
	private String videoFileName = null;	
	private ContentValues videoValues = null;
	public static Intent intent;
	String path;
	String dir = null;
	String Edir = null;
	String name = "OurblackBox";

	
	Vector<String> pathSaver= new Vector<String>(10,5);
	
	public String getExternalMounts(){

		   //final String out;

		    String reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";

		    String s = "";

		    try {

		        final Process process = new ProcessBuilder().command("mount")

		                .redirectErrorStream(true).start();

		        process.waitFor();

		        final InputStream is = process.getInputStream();

		        final byte[] buffer = new byte[1024];

		        while (is.read(buffer) != -1) {

		            s = s + new String(buffer);

		        }

		        is.close();

		    } catch (final Exception e) {

		        e.printStackTrace();

		    }

		    // parse output

		    final String[] lines = s.split("\n");

		    for (String line : lines) {

		        if (!line.toLowerCase(Locale.US).contains("asec")) {

		            if (line.matches(reg)) {

		                String[] parts = line.split(" ");

		                for (String part : parts) {

		                    if (part.startsWith("/"))

		                        if (!part.toLowerCase(Locale.US).contains("vold"))

		                            path=part;

		                }

		            }

		        }

		    }

		    return path;

		}

	
	public String createName(long dateTaken){
		Date date= new Date(dateTaken);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		return dateFormat.format(date)+".mp4";
	}
	
	public String createInternalFolder(){
		
		String dir=Environment.getExternalStorageDirectory().getPath() + "/" + name;
		File folder = new File(dir);
		folder.mkdirs();
		return dir;
    		
	}
	

	public String createExternalFolder(){
		String Edir= getExternalMounts() + "/" + name;
		File Efolder = new File(Edir);
		Efolder.mkdirs();
		//Log.v("외부 경로가 아디인가요1?","궁금합니당="+Edir);
		return Edir;

	}
	
	public void rename(){
		
		File before = new File(BRecorder.Path);
		File after = new File(BRecorder.Folder +"/"+"EVENT_"+ BRecorder.Name );
		
		if(BThreadRecorder.isTimeChange == true){
			before.renameTo(after);
		}
	}
    
	
	/*public void pathSave(String path){
		
		pathSaver.addElement(path);
	}*/
	
	/*public void fileScan()
	{
		intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); //패스 선언을 이 클래스에서!!
		Uri uri= Uri.parse("file://"+BRecorder.Path);
		intent.setData(uri);
		
	}*/
	
	

	
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
	

	
	/*public String exCreateFolder(){
		
		String dir=Environment.getExternalStorageDirectory().getPath() + "/mnt/sdcard" + "/" + name;
		File folder = new File(dir);
		folder.mkdirs();
		return dir;
    	
=======
>>>>>>> branch 'master' of https://github.com/haniemF5/haniem_F5.git
	}
	
	
    
	
	/*public void pathSave(String path){
		
		pathSaver.addElement(path);
	}*/
	
	/*public void fileScan()
	{
		intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); //패스 선언을 이 클래스에서!!
		Uri uri= Uri.parse("file://"+BRecorder.Path);
		intent.setData(uri);
		
	}*/
	
	

	
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
	

	
	/*public String exCreateFolder(){
		
		String dir=Environment.getExternalStorageDirectory().getPath() + "/mnt/sdcard" + "/" + name;
		File folder = new File(dir);
		folder.mkdirs();
		return dir;
    	
	}
	
	public String inCreateFolder(){
		
		String dir2=Environment.getExternalStorageDirectory().getPath() + "/" + name;
		File folder = new File(dir2);
		folder.mkdirs();
		return dir2;
    		
	}
	*/
	

	

	
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

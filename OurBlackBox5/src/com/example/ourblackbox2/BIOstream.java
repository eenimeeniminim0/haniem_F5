package com.example.ourblackbox2;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.os.Environment;

public class BIOstream {
	
	public static Intent intent;
	String path;
	String dir = null;
	String Edir = null;
	String name = "OurblackBox";

	
	public String getExternalMounts(){

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
		return Edir;

	}
	
	public void rename(){
		
		if(BThreadRecorder.isTimeChange == true){
			File before = new File(BRecorder.Path);
			File after = new File(BRecorder.Folder +"/"+"EVENT_"+ BRecorder.Name );
			BRecorder.Path=BRecorder.Folder +"/"+"EVENT_"+ BRecorder.Name; 
			before.renameTo(after);
		}
	}
	
	   public void renameService(){
		      
		 if(BServiceThreadRecorder.isTimeChange == true){
		    File before = new File(BServiceRecorder.Path);
			File after = new File(BServiceRecorder.Folder +"/"+"EVENT_"+ BServiceRecorder.Name);
			BServiceRecorder.Path=BServiceRecorder.Folder +"/"+"EVENT_"+ BServiceRecorder.Name; 
			before.renameTo(after);
		 }
	 }
    	
	
}

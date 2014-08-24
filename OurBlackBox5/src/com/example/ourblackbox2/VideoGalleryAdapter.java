package com.example.ourblackbox2;

import java.util.ArrayList;
import java.util.List;

import com.example.ourblackbox2.GalleryActivity.VideoViewInfo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class VideoGalleryAdapter extends BaseAdapter{
	private Context context;
	private List<VideoViewInfo> videoItems;
	
	LayoutInflater inflater;
	
	public VideoGalleryAdapter(Context _context, ArrayList<VideoViewInfo> _items) {
		context = _context;
		videoItems = _items;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public int getCount(){
		return videoItems.size();
	
    }
	
	public Object getItem(int position){
		return videoItems.get(position);
	}
	public long getItemId(int position){
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View videoRow = inflater.inflate(R.layout.list_item, null);
		
		ImageView videoThumb = (ImageView) videoRow.findViewById(R.id.ImageView);
		if(videoItems.get(position).thumbPath != null) {
			videoThumb.setImageURI(Uri.parse(videoItems.get(position).thumbPath));
		}
		TextView videoTitle = (TextView) videoRow.findViewById(R.id.TextView);
		videoTitle.setText(videoItems.get(position).title);
		
		return videoRow;
	}
	

}



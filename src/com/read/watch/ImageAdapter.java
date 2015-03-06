
package com.read.watch;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import org.taptwo.android.widget.ViewFlow;

import com.read.watch.main.NewModel;
import com.read.watch.main.SyncImageLoader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter {
    private TextView text;
	private Context mContext;
	private LayoutInflater mInflater;
	private ViewFlow viewflow;
	private Vector<NewModel> mModels = new Vector<NewModel>();
	SyncImageLoader syncImageLoader;
	HashMap<Integer, Object> map = new HashMap<Integer, Object>();
	
	private   int[] ids = {R.drawable.p1, R.drawable.p1, R.drawable.p1, R.drawable.p1, R.drawable.p1 };
	public ImageAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public ImageAdapter(Context context,ViewFlow listView){
		//mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater = LayoutInflater.from(context);
		syncImageLoader = new SyncImageLoader();
		mContext = context;
		viewflow=listView;
		
	}
	
	@Override
	public int getCount() {
		return Integer.MAX_VALUE;   
	}
	@Override
	public Object getItem(int position) {
		if(position >= mModels.size()){
			return null;
		}
		return mModels.get(position);
		//return position;
	}
   public void setmodel(Vector<NewModel> a)
   {
	   mModels=a;
	  
   }
   
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void addNew(String id,String title,String info,String pic,String time){
		NewModel model = new NewModel();
		model.id=id;
		model.title =title;
		model.info = info;
		model.pic = pic;
		model.time=time;
		mModels.add(model);
	}
	public void clean(){
		mModels.clear();
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.viewpager_item, null);
		}
		if(position%5>=mModels.size())
			{
			ImageView iv = (ImageView) convertView.findViewById(R.id.imgView);
		    iv.setBackgroundResource(R.drawable.p2);
		    TextView sItemTitle =  (TextView) convertView.findViewById(R.id.textt);
			sItemTitle.setText("");
			return convertView;
			}
		NewModel model = mModels.get(position%5);
		
		convertView.setTag(position);
		//Log.d("bug","测试");
		//Log.d("bug",viewflow.findViewWithTag(model).toString());
	//	Log.d("bug",convertView.toString());
		ImageView iv = (ImageView) convertView.findViewById(R.id.imgView);
		TextView sItemTitle =  (TextView) convertView.findViewById(R.id.textt);
		sItemTitle.setText(model.title);
		Log.d("bug",convertView.toString());
		iv.setBackgroundResource(R.drawable.p1);
		syncImageLoader.loadImage(position,model.pic,imageLoadListener);
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,DetailActivity.class);
				Bundle bundle = new Bundle();
				if(position%5<mModels.size())
				{
				NewModel model = mModels.get(position%5);
				bundle.putString("pic_url", model.pic);
				bundle.putString("new_id",model.id);
				bundle.putString("new_title", model.title);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
				}
			}
			
		});
		
		return convertView;
		
	}
	
	SyncImageLoader.OnImageLoadListener imageLoadListener = new SyncImageLoader.OnImageLoadListener(){

		@Override
		public void onImageLoad(Integer t, Drawable drawable) {
			//BookModel model = (BookModel) getItem(t);
			Log.d("bug","555555");
		//	View view = (View) map.get(t);
				//	
			View view =viewflow.findViewWithTag(t);
			//Log.d("bug",view.toString());
			if(view != null){
				Log.d("bug","5");
				ImageView iv = (ImageView) view.findViewById(R.id.imgView);
				if(iv!=null)
				iv.setBackgroundDrawable(drawable);
				
			}
		}
		@Override
		public void onError(Integer t) {
			Log.d("bug","a1");
			Log.d("bug",Integer.toString(t%5));

			NewModel model = (NewModel) getItem(t%5);
			View view = viewflow.findViewWithTag(model);
			if(view != null){
				ImageView iv = (ImageView) view.findViewById(R.id.imgView);
				iv.setBackgroundResource(R.drawable.p2);
			}
		}
		
	};
	

}

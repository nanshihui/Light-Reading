package com.read.watch;



import java.util.Vector;

import com.read.watch.main.DebugUtil;
import com.read.watch.main.NewModel;
import com.read.watch.main.SyncImageLoader;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Historyitem_adaptor extends BaseAdapter{
	
	private LayoutInflater mInflater;
	private Context mContext;
	private Vector<NewModel> mModels = new Vector<NewModel>();
	private ListView mListView;
	SyncImageLoader syncImageLoader;
	
	public Historyitem_adaptor(Context context,ListView listView){
		mModels.clear();
		mInflater = LayoutInflater.from(context);
		syncImageLoader = new SyncImageLoader();
		mContext = context;
		mListView = listView;
		
		mListView.setOnScrollListener(onScrollListener);
	}

	public void setModel(Vector<NewModel> a)
	{
		mModels=a;
	}

	public Vector<NewModel> getModel()
	{
		return mModels;
	}
	public void addNew(String username,String id,String title,String info,String pic,String time,int state,String photo2,String photo3,String video){
		NewModel model = new NewModel();
		model.author=username;
		model.title =title;
		model.info = info;
		model.pic = pic;
		model.id=id;
		model.time=time;
		model.new_state=state;
		model.photo2=photo2;
		model.photo3=photo3;
		model.video=video;
		mModels.add(model);
	}
	
	public void clean(){
		mModels.clear();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mModels.size();
	}

	@Override
	public Object getItem(int position) {
		if(position >= getCount()){
			return null;
		}
		return mModels.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.history_item, null);
		}
		if(mModels.size()<position)
			return null;
		NewModel model = mModels.get(position);
		convertView.setTag(position);
		ImageView iv = (ImageView) convertView.findViewById(R.id.infophoto);
		TextView sItemTitle =  (TextView) convertView.findViewById(R.id.infotitile);
		TextView sItemInfo =  (TextView) convertView.findViewById(R.id.infocontent);
		TextView username =  (TextView) convertView.findViewById(R.id.usernameshow);
		TextView sItemtime =  (TextView) convertView.findViewById(R.id.infotime);
		TextView sItemstate =  (TextView) convertView.findViewById(R.id.infostate);
		sItemTitle.setText(model.title);
		sItemInfo.setText(model.info);
		username.setText(model.author);
		switch(model.new_state)
		{
		case 1 :sItemstate.setText("未受理");break;
		case 2 :sItemstate.setText("已受理");break;
		case 3 :sItemstate.setText("已认定");break;
		default:sItemstate.setText("无效内容");break;
		}
		sItemtime.setText(model.time);
		iv.setBackgroundResource(R.drawable.p1);
		syncImageLoader.loadImage(position,model.pic,imageLoadListener);
		return  convertView;
	}

	SyncImageLoader.OnImageLoadListener imageLoadListener = new SyncImageLoader.OnImageLoadListener(){

		@Override
		public void onImageLoad(Integer t, Drawable drawable) {
			//BookModel model = (BookModel) getItem(t);
			
			View view = mListView.findViewWithTag(t);
			if(view != null&&drawable!=null){
				ImageView iv = (ImageView) view.findViewById(R.id.infophoto);
				if(iv!=null)
				iv.setBackgroundDrawable(drawable);
			}
		}
		@Override
		public void onError(Integer t) {
			NewModel model = (NewModel) getItem(t);
			View view = mListView.findViewWithTag(model);
			if(view != null){
				ImageView iv = (ImageView) view.findViewById(R.id.infophoto);
				iv.setBackgroundResource(R.drawable.p2);
			}
		}
		
	};
	
	public void loadImage(){
		int start = mListView.getFirstVisiblePosition();
		int end =mListView.getLastVisiblePosition();
		if(end >= getCount()){
			end = getCount() -1;
		}
		syncImageLoader.setLoadLimit(start, end);
		syncImageLoader.unlock();
	}
	
	AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
				case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
					DebugUtil.debug("SCROLL_STATE_FLING");
					syncImageLoader.lock();
					
					break;
				case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
					DebugUtil.debug("SCROLL_STATE_IDLE");
					loadImage();
					//loadImage();
					break;
				case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					syncImageLoader.lock();
				
					
					break;
		
				default:
					break;
			}
			
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			
		}
	};
}

package com.read.watch;




import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.taptwo.android.widget.CircleFlowIndicator;

import org.taptwo.android.widget.ViewFlow;


import com.read.watch.PullToRefreshView.OnFooterRefreshListener;
import com.read.watch.PullToRefreshView.OnHeaderRefreshListener;
import com.read.watch.main.ItemAdapter;
import com.read.watch.main.NewModel;





public class FirstActivity extends Activity 
implements OnHeaderRefreshListener,OnFooterRefreshListener,OnClickListener
{

	private final int NEWSCOUNT = 5; 
	private final int SUCCESS = 0;
	private final int NONEWS = 1;
	private final int NOMORENEWS = 2;
	private final int LOADERROR = 3;
	private Button btn;
	private ItemAdapter adapter;
	private ImageAdapter adapterh;
	private Context context;
	private int u=0;
	PullToRefreshView mPullToRefreshView;
	private ListView listView; 
	private ViewFlow viewFlow; 
	 private ArrayList<HashMap<String, Object>> listItem = null;  
	 private SimpleAdapter listItemAdapter;    
	private ProgressBar mLoadnewsProgress;
	private LoadNewsAsyncTask loadNewsAsyncTask;
	 CircleFlowIndicator indic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1);
		//
		mLoadnewsProgress = (ProgressBar)findViewById(R.id.progressBar1);
		btn=(Button)findViewById(R.id.button5);
		btn.setOnClickListener(this);
		 mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
		listView = (ListView) findViewById(R.id.listView1);
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		View header = inflater.inflate(R.layout.viewflow, null);
		viewFlow = (ViewFlow) header.findViewById(R.id.viewflow);// 获得viewFlow对象
		listView.addHeaderView(header); // 将viewFlow添加到listview�?
		adapter = new ItemAdapter(this,listView);
		
		 adapterh=new ImageAdapter(this,viewFlow);
//	     reload();
		
	       
		 adapterh.setmodel(adapter.getModel());
		 
		viewFlow.setAdapter(adapterh);
	
		 viewFlow.setmSideBuffer(5); // 实际图片张数
	        
		  indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
	        viewFlow.setFlowIndicator(indic);
	        viewFlow.setTimeSpan(5000);
	        viewFlow.setSelection(5*1000);//设置初始位置
	        viewFlow.startAutoFlowTimer();  //启动自动播放
	       
	       listView.setAdapter(adapter);
	 
        mPullToRefreshView.setOnHeaderRefreshListener(this);
       mPullToRefreshView.setOnFooterRefreshListener(this);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
			{
				  Log.d("bug 项目", Integer.toString(arg2));
				  Intent intent = new Intent(FirstActivity.this,DetailActivity.class);
					Bundle bundle = new Bundle();
				
					NewModel model = adapter.getModel().get(arg2-1);
					bundle.putString("new_id",model.id);
					bundle.putString("pic_url", model.pic);
					bundle.putString("new_title", model.title);
					intent.putExtras(bundle);
					startActivity(intent);
				
			}
		});
       
      //添加长按点击
        listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
											ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("����");   
				menu.add(0, 0, 0, "�ղ�");
				menu.add(0, 1, 0, "ת��");   
			}
		}); 
        mLoadnewsProgress.setVisibility(View.VISIBLE); 

        new Thread(new Runnable(){   

            public void run(){   

                try {
					Thread.sleep(2000);
					adapter.clean();
					
					loadNewsAsyncTask = new LoadNewsAsyncTask();
			        String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					loadNewsAsyncTask.execute(time,true);
				    

				   
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   

               

            }   

        }).start(); 

        
        
      
        
        
        
        
	}  
	
	 
		
	
	/*@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Toast.makeText(this, "positon = "+position, 1000).show();
	}
	*/
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
				
				loadNewsAsyncTask = new LoadNewsAsyncTask();
				String time=new String();
		      //  String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				if(adapter.getCount()>0)
				{NewModel model = adapter.getModel().get(adapter.getCount()-1);
				 time=model.time;
				 String []a=time.split("[ :-]");
				 String c=new String();
				 for(int i=0;i<a.length;i++)
					 c+=a[i];
				 time=c;
				}
				else
					 time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				Toast.makeText(FirstActivity.this, time, Toast.LENGTH_LONG).show();
				loadNewsAsyncTask.execute(time,false);
				  adapter.notifyDataSetChanged();
		
				  viewFlow.startAutoFlowTimer();
			
			    
			    
			   
		/*	try {
				Log.d("abc","已经触发");
				viewFlow.setAdapter(new ImageAdapter(a));
			} catch (Exception e) {
				Log.d("abc","哈哈");// TODO Auto-generated catch block
				e.printStackTrace();
			}
				for(int i=0;i<5;i++)
		        {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.p2);//图像资源的ID
        	map.put("ItemTitle", "刚添加标�?"+i);
        	map.put("ItemText", "123123");
            listItem.add(map);
            }
                listItemAdapter.notifyDataSetChanged();
			*/}
		}, 1000);
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				adapter.clean();
				loadNewsAsyncTask = new LoadNewsAsyncTask();
		        String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				loadNewsAsyncTask.execute(time,true);
			     adapter.notifyDataSetChanged();
			     viewFlow.startAutoFlowTimer();
				mPullToRefreshView.onHeaderRefreshComplete();
				
				
		
				
			}
		},1000);
				 
	}
	private void reload(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				 adapter.notifyDataSetChanged();
		        loadNewsAsyncTask = new LoadNewsAsyncTask();
		        String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				loadNewsAsyncTask.execute(time,true);
	           
				 adapter.notifyDataSetChanged();

			}
		}).start();
	}
	private void reload1(){
		
		new Thread(new Runnable(){
			@Override
			public void run() {
	
			}
		}).start();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.button5: 
			Toast.makeText(this, "��������", Toast.LENGTH_LONG).show();
			 adapter.clean();
			adapterh.clean();	
			viewFlow.setAdapter(adapterh);
			adapter.notifyDataSetChanged();
		default:
			Toast.makeText(this, "���������", Toast.LENGTH_LONG).show();break;
	}
	}

	public class LoadNewsAsyncTask extends AsyncTask<Object, Integer, Integer>
	{
		
		@Override
		protected void onPreExecute()
		{
			
			
		
		}

		@Override
		protected Integer doInBackground(Object... params)
		{
			return getSpeCateNews((String)params[0],adapter,(Boolean)params[1]);
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			//根据返回值显示相关的Toast
			switch (result)
			{
			case NONEWS:
				Toast.makeText(FirstActivity.this, "û����Ϣ", Toast.LENGTH_LONG).show();break;
			case NOMORENEWS:
				Toast.makeText(FirstActivity.this, "û�и�����Ϣ", Toast.LENGTH_LONG).show();break;
			case LOADERROR:
				Toast.makeText(FirstActivity.this, "��ȡ��Ϣʧ��", Toast.LENGTH_LONG).show();break;
		}
			
			adapter.notifyDataSetChanged();
			
			//隐藏进度�?
			mLoadnewsProgress.setVisibility(View.GONE); 
		
		
		}
	}
	

	private int getSpeCateNews(String time,ItemAdapter newsList,Boolean firstTimes)
	{
		if (firstTimes)
		{
			//如果是第�?��，则清空集合里数�?
			newsList.clean();
		}
		//请求URL和字符串
		String url = "http://192.168.100.100:8080/suiyipic/getSpecifyCategoryNews";
		String params = "time="+time;
		SyncHttp syncHttp = new SyncHttp();
		try
		{
			//以Get方式请求，并获得返回结果
			String retStr = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retStr);
			//获取返回码，0表示成功
			int retCode = jsonObject.getInt("ret");
			if (0==retCode)
			{
				JSONObject dataObject = jsonObject.getJSONObject("data");
				//获取返回数目
				int totalnum = dataObject.getInt("totalnum");
				if (totalnum>0)
				{
					//获取返回新闻集合
					JSONArray newslist = dataObject.getJSONArray("newslist");
					for(int i=0;i<newslist.length();i++)
					{
						JSONObject newsObject = (JSONObject)newslist.opt(i); 
					newsList.addNew(newsObject.getString("INFOR_ID"),newsObject.getString("TITLE"),newsObject.getString("REPORTTIME"),newsObject.getString("PHOTO1"),newsObject.getString("REPORTTIME"));
					}
					return SUCCESS;
				}
				else
				{
					if (firstTimes)
					{
						return NONEWS;
					}
					else
					{
						return NOMORENEWS;
					}
				}
			}
			else
			{
				return LOADERROR;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return LOADERROR;
		}
	}
}

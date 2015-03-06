package com.read.watch;



/**
 * 
 */


import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 搜索Activity
 
 */
public class SignActivity extends Activity implements OnClickListener{
	private SigninAsyncTask signintask;
	private EditText username;
	private EditText password;
	private ProgressBar mLoadnewsProgress;
	private Button signin;
	private Button signback;
	private Button more;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		mLoadnewsProgress = (ProgressBar)findViewById(R.id.progressBar1);
		signin = (Button)findViewById(R.id.signin);
		signin.setOnClickListener(this);
		more = (Button)findViewById(R.id.more);
		more.setOnClickListener(this);
		username=(EditText)findViewById(R.id.sign_edit_account);
		password=(EditText)findViewById(R.id.sign_edit_pwd);
		 
		 
		signback = (Button)findViewById(R.id.signback);
		signback.setOnClickListener(this);
		 
		
	    
	     
	}
	
	
	

	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.signin: 
			signintask=new SigninAsyncTask();
			signintask.execute(username.getText().toString(),password.getText().toString());
			
			break;
		case R.id.signback: 
			finish();
			break;
		case R.id.more:
			Toast.makeText(SignActivity.this, "你没有权限哦", Toast.LENGTH_LONG).show();break;

			default:break;
		}
		
	}
	
	
	public class SigninAsyncTask extends AsyncTask<Object, Integer, Integer>
	{
		
		@Override
		protected void onPreExecute()
		{
			
			//显示进度条
			mLoadnewsProgress.setVisibility(View.VISIBLE); 
		
		}

		@Override
		protected Integer doInBackground(Object... params)
		{
			return getsignin((String)params[0],(String)params[1]);
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			//根据返回值显示相关的Toast
			switch (result)
			{
			case 1:
				Toast.makeText(SignActivity.this, "注册成功", Toast.LENGTH_LONG).show();
				
				break;
			case 2:
				Toast.makeText(SignActivity.this, "已有人注册用户名", Toast.LENGTH_LONG).show();break;
			case 3:
				Toast.makeText(SignActivity.this, "网络无法连接", Toast.LENGTH_LONG).show();break;
			}
			
			
			//隐藏进度条
			mLoadnewsProgress.setVisibility(View.INVISIBLE); 
		
		
		}
	}
	private int getsignin(String username,String password)
	{
	
		//请求URL和字符串
		String url = "http://192.168.100.100:8080/suiyipic/signin";
		String params = "username="+username+"&password="+password;
		SyncHttp syncHttp = new SyncHttp();
		try
		{
			//以Get方式请求，并获得返回结果
			String retStr = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retStr);
			//获取返回码，0表示成功
			int retCode = jsonObject.getInt("ret");
			System.out.println(retCode);
			if (0==retCode)
			{
				JSONObject dataObject = jsonObject.getJSONObject("data");
				//获取返回数目
				Boolean bool = dataObject.getBoolean("bool");
				System.out.println(bool);
				if (bool)
				{
					
					
					return 1;
				}
				else
				{
					return 2;
				}
			}
			else
			{
				return 3;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return 3;
		}
	}
	
}

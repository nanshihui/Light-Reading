package com.read.watch;

/**
 * 
 */


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 我的资料Activity
 
 */
public class ThirdActivity extends Activity  implements OnClickListener{
	private String pathA;
	private String videopathname;
	private int USERTYPE;
	private MyTask mTask;
	private Button operatorupload;
	private Button infodetail;
	private Button historysearch;
	private Button loginbtn;
	private Button logout;
	private Button signin;
	private Button userhead;
	private Button button1;
	private Button more;
	private Button moviepic;
	
	private LinearLayout loginlayout;
	private LinearLayout userlayout;
	private LinearLayout loginlayoutpic;
	private LinearLayout logoutlayout;
	private SharedPreferences sharedpreferences;
	private SharedPreferences.Editor editor;
	private ProgressBar mLoadnewsProgress;
	private LoginAsyncTask logintask;

	private EditText username;
	private EditText password;
	private TextView textview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab3);
		sharedpreferences=getSharedPreferences("user",MODE_PRIVATE);
		username=(EditText)findViewById(R.id.login_edit_account);
		password=(EditText)findViewById(R.id.login_edit_pwd);
		editor=sharedpreferences.edit();
		infodetail=(Button)findViewById(R.id.infodetail);
		infodetail.setOnClickListener(this);
		textview=(TextView)findViewById(R.id.textView3);
		more=(Button)findViewById(R.id.more);
		more.setOnClickListener(this);
		
		userhead=(Button)findViewById(R.id.userhead);
		userhead.setOnClickListener(this);
		signin=(Button)findViewById(R.id.signin);
		signin.setOnClickListener(this);
		button1=(Button)findViewById(R.id.button1);
		historysearch=(Button)findViewById(R.id.historysearch);
		historysearch.setOnClickListener(this);
		operatorupload=(Button)findViewById(R.id.operatorupload);
		operatorupload.setOnClickListener(this);
		loginbtn=(Button)findViewById(R.id.login_btn_login);
		loginbtn.setOnClickListener(this);
		logout=(Button)findViewById(R.id.logout);
		logout.setOnClickListener(this);
		loginlayout=(LinearLayout)findViewById(R.id.loginlayout);
		userlayout=(LinearLayout)findViewById(R.id.userlayout);
		userlayout.setVisibility(View.GONE);
		userlayout.setOnClickListener(this);
		loginlayoutpic=(LinearLayout)findViewById(R.id.loginlayoutpic);
		loginlayoutpic.setVisibility(View.VISIBLE);
		logoutlayout=(LinearLayout)findViewById(R.id.logoutlayout);
		logoutlayout.setVisibility(View.GONE);
		if(sharedpreferences.getString("username", null)!=null)
		{
			textview.setText(sharedpreferences.getString("username", null));
			loginlayout.setVisibility(View.GONE);
			userlayout.setVisibility(View.VISIBLE);
			loginlayoutpic.setVisibility(View.GONE);
			logoutlayout.setVisibility(View.VISIBLE);
			if(sharedpreferences.getInt("usertype", 0)==2)
			{
				operatorupload.setVisibility(View.VISIBLE);
			}
		}
		mLoadnewsProgress=(ProgressBar)findViewById(R.id.progressBar1);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.infodetail: 
			Toast.makeText(this, "正在跳转", Toast.LENGTH_LONG).show();

			Intent intent=new Intent(ThirdActivity.this,DetailinfoActivity.class);
			startActivity(intent);
			break;
		
		case R.id.login_btn_login:
			
			logintask = new LoginAsyncTask();
	       
			logintask.execute(username.getText().toString(),password.getText().toString());
		
		break;
		case R.id.logout:
			editor.clear();
			editor.commit();
			loginlayout.setVisibility(View.VISIBLE);
			userlayout.setVisibility(View.GONE);
			loginlayoutpic.setVisibility(View.VISIBLE);
			logoutlayout.setVisibility(View.GONE);
			operatorupload.setVisibility(View.GONE);
			break;
		case R.id.userlayout:
			Intent intent11=new Intent(ThirdActivity.this,DetailinfoActivity.class);
			startActivity(intent11);
			break;
		case R.id.userhead:
			Intent intent111=new Intent(ThirdActivity.this,DetailinfoActivity.class);
			startActivity(intent111);
			break;
		case R.id.historysearch:		
			Intent intent1=new Intent(ThirdActivity.this,SearchActivity.class);
			startActivity(intent1);
		    Toast.makeText(this, "正在跳转", Toast.LENGTH_LONG).show();
		break;
		
		case R.id.signin:
			Intent newintent=new Intent(ThirdActivity.this,SignActivity.class);
			startActivity(newintent);
		break;
		
		case R.id.operatorupload:
			Intent newintent1=new Intent(ThirdActivity.this,OperateuploadActivity.class);
			startActivity(newintent1);
			break;
		case R.id.more:
			Toast.makeText(this, "你的权限不够哦", Toast.LENGTH_LONG).show();break;
		default:Toast.makeText(this, "没有触发", Toast.LENGTH_LONG).show();break;
		}
		
		}
	
	
	
	
	
	public class LoginAsyncTask extends AsyncTask<Object, Integer, Integer>
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
			return getlogin((String)params[0],(String)params[1]);
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			//根据返回值显示相关的Toast
			switch (result)
			{
			case 1:
				Toast.makeText(ThirdActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
				editor.putString("username", username.getText().toString());
				editor.putString("password", password.getText().toString());
				editor.putInt("usertype", USERTYPE);
				editor.commit();
				if(USERTYPE>1)
				{
					operatorupload.setVisibility(View.VISIBLE);
				}					
				textview.setText(username.getText().toString());
				username.setText("");
				password.setText("");
				
				loginlayout.setVisibility(View.GONE);
				userlayout.setVisibility(View.VISIBLE);
				loginlayoutpic.setVisibility(View.GONE);
				logoutlayout.setVisibility(View.VISIBLE);
				break;
			case 2:
				Toast.makeText(ThirdActivity.this, "密码或账号出错", Toast.LENGTH_LONG).show();break;
			case 3:
				Toast.makeText(ThirdActivity.this, "网络无法连接", Toast.LENGTH_LONG).show();break;
			}
			
			
			//隐藏进度条
			mLoadnewsProgress.setVisibility(View.INVISIBLE); 
		
		
		}
	}
	
	
	private int getlogin(String username,String password)
	{
	
		//请求URL和字符串
		String url = "http://192.168.100.100:8080/suiyipic/login";
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
					
					 USERTYPE = jsonObject.getInt("usertype");
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
	
	
	
	
	
	
	
	private class MyTask extends AsyncTask<String, Integer, String>{
	     private String path=new String();
			@Override
			protected void onPostExecute(String result) {
				//mTvProgress.setText(result);	
				button1.setText(result);
				mLoadnewsProgress.setVisibility(View.GONE); 
				Toast.makeText(ThirdActivity.this,result, Toast.LENGTH_LONG).show();
				button1.setVisibility(View.INVISIBLE);
			}

			@Override
			protected void onPreExecute() {
				
				Toast.makeText(ThirdActivity.this,"loading ...", Toast.LENGTH_LONG).show();
			
				mLoadnewsProgress.setVisibility(View.VISIBLE);
				button1.setVisibility(View.VISIBLE);
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				
			 	
                 button1.setText("loading..." + values[0] + "%");
			}

			@Override
			protected String doInBackground(String... params) {
				
				for(int u=0;u<1;u++)
				 {
					
					String temp=pathA;
					if(temp!=null)
					   {
						
				String filePath = temp;
				path=temp;
				
				String uploadUrl = params[1];
				String end = "\r\n";
				String twoHyphens = "--";
				String boundary = "******";
				try {
					URL url = new URL(uploadUrl);
					HttpURLConnection httpURLConnection = (HttpURLConnection) url
							.openConnection();
					httpURLConnection.setDoInput(true);
					httpURLConnection.setDoOutput(true);
					httpURLConnection.setUseCaches(false);
					httpURLConnection.setRequestMethod("POST");
					httpURLConnection.setConnectTimeout(6*1000);
					httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
					httpURLConnection.setRequestProperty("Charset", "UTF-8");
					httpURLConnection.setRequestProperty("Content-Type",
							"multipart/form-data;boundary=" + boundary);

					DataOutputStream dos = new DataOutputStream(httpURLConnection
							.getOutputStream());
					dos.writeBytes(twoHyphens + boundary + end);
					dos
							.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
									+ filePath.substring(filePath.lastIndexOf("/") + 1)
									+ "\"" + end);
					dos.writeBytes(end);

					FileInputStream fis = new FileInputStream(filePath);
					long total = fis.available();
					String totalstr = String.valueOf(total);
					Log.d("文件大小", totalstr);
					byte[] buffer = new byte[8192]; // 8k
					int count = 0;
					int length = 0;
					while ((count = fis.read(buffer)) != -1) {
						dos.write(buffer, 0, count);
						length += count;
						publishProgress((int) ((length / (float) total) * 100));
						//为了演示进度,休眠500毫秒
						//Thread.sleep(100);
					}			
					fis.close();
					dos.writeBytes(end);
					dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
					dos.flush();

					InputStream is = httpURLConnection.getInputStream();
					InputStreamReader isr = new InputStreamReader(is, "utf-8");
					BufferedReader br = new BufferedReader(isr);
					@SuppressWarnings("unused")
					String result = br.readLine();
					dos.close();
					is.close();
					//return "上传成功";
			}catch (Exception e) {
				e.printStackTrace();
				return "上传失败";
			}	
		}
					
						
				 }
			
				return "上传结束";
		
			}
			
		}
	
	
	public static Bitmap scalePicture(String filename, int maxWidth,int maxHeight) {
	     Bitmap bitmap = null;
	    try {
	BitmapFactory.Options opts = new BitmapFactory.Options();
	  opts.inPurgeable = true;
      opts.inInputShareable = true;
      BitmapFactory.decodeFile(filename, opts);
	                        int srcWidth = opts.outWidth;
	                        int srcHeight = opts.outHeight;
	                        int desWidth = 0;
	                        int desHeight = 0;
	                        // 缩放比例
	                        double ratio = 0.0;
	                        if (srcWidth > srcHeight) {
	                                ratio = srcWidth / maxWidth;
	                                desWidth = maxWidth;
	                                desHeight = (int) (srcHeight / ratio);
	                        } else {
	                                ratio = srcHeight / maxHeight;
	                                desHeight = maxHeight;
	                                desWidth = (int) (srcWidth / ratio);
	                        }
	                        // 设置输出宽度、高度
	                        BitmapFactory.Options newOpts = new BitmapFactory.Options();
	                        newOpts.inSampleSize = (int) (ratio) + 1;
	                        newOpts.inJustDecodeBounds = false;
	                        newOpts.outWidth = desWidth;
	                        newOpts.outHeight = desHeight;
	                        bitmap = BitmapFactory.decodeFile(filename, newOpts);

	                } catch (Exception e) {
	                        // TODO: handle exception
	                }
	    return bitmap;
	               
	        }
		
}

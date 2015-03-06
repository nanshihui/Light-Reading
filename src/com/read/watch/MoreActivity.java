package com.read.watch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MoreActivity  extends Activity implements OnClickListener{

	private Button feetback;
	private Button command;
	private Button about;
	private Button more;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab4);
		about=(Button)findViewById(R.id.about);
		command=(Button)findViewById(R.id.command);
		feetback=(Button)findViewById(R.id.feetback);
		about.setOnClickListener(this);
		command.setOnClickListener(this);
		feetback.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.about:
			Toast.makeText(MoreActivity.this, "本软件由sherwel开发，如有问题，请直接反馈nanshihui@gmail.com", Toast.LENGTH_LONG).show();
			break;
		case R.id.command:
			Toast.makeText(MoreActivity.this, "推荐的产品以后会陆续推出哦", Toast.LENGTH_LONG).show();

			break;
		case R.id.feetback:
			Intent intent= new Intent();        
	    intent.setAction("android.intent.action.VIEW");    
	    Uri content_url = Uri.parse("http://weibo.com/sherwel/home?");   
	    intent.setData(content_url);  
	    startActivity(intent);
	    break;
		}
	}

	
	
}

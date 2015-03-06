package com.read.watch;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

public class Welcome extends Activity {
    /** Called when the activity is first created. */
	 private final int SPLASH_DISPLAY_LENGHT = 5000; //延迟三秒  
	 private ImageView image=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**全屏设置，隐藏窗口所有装饰*/ 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效*/ 
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.welcome);
        image=(ImageView)findViewById(R.id.welcome_desk);
        AnimationSet  animationset=new AnimationSet(true);
        AlphaAnimation alphaAnimation=new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(5500);
        animationset.addAnimation(alphaAnimation);
        image.startAnimation(animationset);
        new Handler().postDelayed(new Runnable(){ 
        	  
            @Override 
            public void run() { 
                Intent mainIntent = new Intent(Welcome.this,MainActivity.class); 
                Welcome.this.startActivity(mainIntent); 
                Welcome.this.finish(); 
            } 
               
           }, SPLASH_DISPLAY_LENGHT); 
    }
}

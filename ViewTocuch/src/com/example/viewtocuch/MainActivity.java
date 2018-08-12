package com.example.viewtocuch;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TouchView touchView = (TouchView) findViewById(R.id.touchView1);
		
		ImageView imageView1 = touchView.getImageView();
		imageView1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "组合控件被点击了",Toast.LENGTH_SHORT).show();
			}
		});
		
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView2);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),
						"2被点击",Toast.LENGTH_SHORT).show();
			}
		});
	}
}

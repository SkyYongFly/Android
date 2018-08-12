package com.example.main;

import android.R.anim;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class MainActivity extends Activity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		for(int i =0;i<5;i++){
			adapter.add("item"+i);
		}
		actionBar.setListNavigationCallbacks(adapter, new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				Toast.makeText(MainActivity.this,"第"+itemPosition+"被点击了",Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	/**
	 * 创建顶部ActionBar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		MenuItem shareItem =menu.findItem(R.id.action_share);
		ShareActionProvider provider = (ShareActionProvider) shareItem.getActionProvider();
		provider.setShareIntent(getMyItent());
		
		
		return true;
	}
	
	private Intent getMyItent(){
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		return intent;
	}

	
	/**
	 * 当选项菜单被选中的时候
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_search:
			Toast.makeText(MainActivity.this, "您选中了搜索菜单",Toast.LENGTH_SHORT).show();
			
			break;
		case R.id.action_settings:
			
			break;

		default:
			break;
		}
		
		
		
		
		return super.onOptionsItemSelected(item);
	}
}

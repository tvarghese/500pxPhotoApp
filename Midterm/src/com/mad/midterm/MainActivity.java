/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File: MainActivity.java
 */
package com.mad.midterm;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

public class MainActivity extends Activity{
	static final String SEARCHTERMKEY = "SEARCHTERMKEY";
	
	
	DatabaseHelper dbHelper;
	ProgressDialog progressDialog;
	EditText searchTermEditText;
	String lastSearchTerm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
    	dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

		if (isConnectedToInternet()) {
			Log.d("demo", "isConnectedToTheInternet");
			
		} else {
			Log.d("demo", "isNotConnectedToTheInternet");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		

		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onSubmitButtonClicked(View view) {

		searchTermEditText = (EditText) findViewById(R.id.editText1);
		lastSearchTerm = searchTermEditText.getText().toString();

		if (!isConnectedToInternet()) {
			Toast.makeText(
					this,
					"No internet connection! Please connect to the internet and try again!",
					Toast.LENGTH_SHORT).show();
		} else {
			if (!TextUtils.isEmpty(lastSearchTerm)) {
				Intent intent = new Intent(this, GalleryActivity.class);
				intent.putExtra(MainActivity.SEARCHTERMKEY, lastSearchTerm);

				startActivity(intent);
			}else{
				Toast.makeText(
						this,
						"The search term is blank. Please enter a valid search string.",
						Toast.LENGTH_SHORT).show();
			}
		}

	}
	
	public void onHistoryButtonClicked(View view) throws SQLException{
    	RuntimeExceptionDao<SearchHistory, Integer> searchHistoryDao = dbHelper.getSearchHistoryRuntimeExceptionDao();
    	
    	QueryBuilder<SearchHistory, Integer> qb = searchHistoryDao.queryBuilder();
    	qb.orderBy("addedDate", false);
    	List<SearchHistory> searchHistoryList = searchHistoryDao.query(qb.prepare());
    	
    	//array adapter for the alert dialog
    	final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.select_dialog_item);
    	
    	for(SearchHistory searchHistoryItem : searchHistoryList){
    		arrayAdapter.add(searchHistoryItem.searchTerm);
    	}
    	

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				searchTermEditText = (EditText)findViewById(R.id.editText1);
				searchTermEditText.setText(arrayAdapter.getItem(which));
			}
		});
    	builder.setTitle("Search History");
		builder.show();
    	
		Log.d("demo", "History Clicked");

	}
	
	public boolean isConnectedToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}
	


}

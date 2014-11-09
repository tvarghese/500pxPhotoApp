/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File: GalleryActivity.java
 */
package com.mad.midterm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class GalleryActivity extends Activity implements IPhotoFetch{
	
	static final String PHOTOKEY = "PHOTOKEY";
	ArrayList<Photos> photoList;
	DatabaseHelper dbHelper;
	ProgressDialog progressDialog;
	ImageViewWithTextAdapter imageViewTextAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		
    	dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

		String lastSearchTerm = getIntent().getStringExtra(MainActivity.SEARCHTERMKEY);
		
    	
		RuntimeExceptionDao<SearchHistory, Integer> searchHistoryDao = dbHelper.getSearchHistoryRuntimeExceptionDao();
    	List<SearchHistory> list = searchHistoryDao.queryForEq("searchTerm", lastSearchTerm);
    	
    	if(list.isEmpty()){
    		new Get500pxPhotosFromWebAsyncTask(this, lastSearchTerm, dbHelper).execute("https://api.500px.com/v1/photos/search?consumer_key=ACiBQPvkYeDOpStVSmDZfx5UruGAFMF3nDo5D4Uu&image_size=4&rpp=50&term="+lastSearchTerm);
    	}else{
    		// this is not a new search term
    		new Get500pxPhotosFromDBAsyncTask(this, lastSearchTerm, dbHelper).execute(list.get(0));
    	}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
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

	@Override
	public void showPhotoList(List<Photos> data) {
		
    	//photo list added with photos fetched from the internet and adapter is notified.
    	photoList = (ArrayList<Photos>) data;
    	
    	if(photoList != null && photoList.size() > 0  ){
        	if(imageViewTextAdapter == null){
       		 imageViewTextAdapter = new ImageViewWithTextAdapter(
       					this, R.layout.imageview_with_text, photoList);
       		    	imageViewTextAdapter.setNotifyOnChange(true);
       		    	
       			 GridView gridview = (GridView) findViewById(R.id.GridView1);
       			    gridview.setAdapter(imageViewTextAdapter);

       			    gridview.setOnItemClickListener(new OnItemClickListener() {
       			        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
       			            //Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
       			        	Log.d("demo", "item clicked");
       			        	
       			   		 Intent intent = new Intent(parent.getContext(), DetailsActivity.class);
       			 		intent.putExtra(GalleryActivity.PHOTOKEY, photoList.get(position));


       			 		startActivity(intent);
       			        }
       			    });
           	}
           	
           	imageViewTextAdapter.notifyDataSetChanged();
           	
           	// add the last search term to the searchHistoryDB
           	RuntimeExceptionDao<SearchHistory, Integer> searchHistoryDao = dbHelper.getSearchHistoryRuntimeExceptionDao();
       		
           	//get the searchDetails from the Photos Object and save it in the database
           	searchHistoryDao.createIfNotExists(data.get(0).getSearchHistory());    
    		
    	} else{
			Toast.makeText(this, "No search results! Try searching again. ",
					Toast.LENGTH_SHORT).show();
    	}
    	
	
		
	}

	@Override
	public void showProgressDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading Data");
		progressDialog.setCancelable(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
		
	}

	@Override
	public void hideProgressDialog() {
		progressDialog.dismiss();
	}
	
}

/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File: Get500pxPhotosFromDBAsyncTask.java
 */
package com.mad.midterm;

import java.util.List;
import android.os.AsyncTask;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class Get500pxPhotosFromDBAsyncTask extends AsyncTask<SearchHistory, Void, List<Photos>>{

	IPhotoFetch activity;
	String searchTerm;
	DatabaseHelper dbHelper;
	
	public Get500pxPhotosFromDBAsyncTask(IPhotoFetch activity, String searchTerm, DatabaseHelper dbHelper){
		super();
		this.searchTerm = searchTerm;
		this.activity = activity;
		this.dbHelper = dbHelper;
	}
	
	@Override
	protected List<Photos> doInBackground(SearchHistory... params) {

		RuntimeExceptionDao<Photos, Integer> photosHistoryDao = dbHelper.getPhotosRuntimeExceptionDao();
		return  photosHistoryDao.queryForEq("searchHistory_id", params[0]);
		
	}

	@Override
	protected void onPreExecute() {

		activity.showProgressDialog();
	}

	@Override
	protected void onPostExecute(List<Photos> result) {
		activity.hideProgressDialog();
		activity.showPhotoList(result);
	}

}

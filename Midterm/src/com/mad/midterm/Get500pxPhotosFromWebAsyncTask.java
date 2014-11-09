/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File: Get500pxPhotosFromWebAsyncTask.java
 */

package com.mad.midterm;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;


public class Get500pxPhotosFromWebAsyncTask extends AsyncTask<String, Void, ArrayList<Photos>> {
	
	IPhotoFetch activity;
	String searchTerm;
	DatabaseHelper dbHelper;
	
	
	public Get500pxPhotosFromWebAsyncTask(IPhotoFetch mainActivity, String searchTerm, DatabaseHelper dbHelper) {
		super();
		this.searchTerm = searchTerm;
		this.activity = mainActivity;
		this.dbHelper = dbHelper;
		
	}
	
	@Override
	protected ArrayList<Photos> doInBackground(String... params) {
		try {
			
			//int pos = params[0].lastIndexOf('/') + 1;
			//String encodedString = new String(params[0].substring(0, pos) + URLEncoder.encode(params[0].substring(pos), "utf-8"));
			//encode string
			//String encodedString = URLEncoder.encode(params[0], "UTF-8");
			//avoid spaces in between search terns
			
			URL url = new URL(params[0].replace(" ", "%20"));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			int statuscode = con.getResponseCode();
			if (statuscode == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = reader.readLine();
				while (line != null) {
					sb.append(line);
					line = reader.readLine();
				}
				return PhotosUtilApp.Photo500pxFeedJsonParser
						.parse500pxFeed(sb.toString(), new SearchHistory(this.searchTerm), dbHelper);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	@Override
	protected void onPreExecute() {
		
		activity.showProgressDialog();
	}
	
	@Override
	protected void onPostExecute(ArrayList<Photos> result) {
		
		activity.hideProgressDialog();
		
		activity.showPhotoList(result);
		
	}
	
	
}

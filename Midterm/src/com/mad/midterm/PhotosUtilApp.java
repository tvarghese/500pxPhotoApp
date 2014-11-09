/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File: PhotosUtilApp.java
 */

package com.mad.midterm;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.dao.RuntimeExceptionDao;


public class PhotosUtilApp {
	static public class Photo500pxFeedJsonParser {
		Photos photos;
		static DatabaseHelper dbHelper;
		public static ArrayList<Photos> parse500pxFeed(String in, SearchHistory searchHistory, DatabaseHelper inDbHelper)
				throws JSONException {
			dbHelper = inDbHelper;
			ArrayList<Photos> imageList = new ArrayList<Photos>();

			JSONObject root = new JSONObject(in);
			JSONArray imageArray = root.getJSONArray("photos");

			for (int i = 0; i < imageArray.length(); i++) {
				
				JSONObject image = imageArray.getJSONObject(i);
				Photos photos = new Photos(image, searchHistory);
			
				// add photos fetched to the database
		    	RuntimeExceptionDao<Photos, Integer> photosHistoryDao = dbHelper.getPhotosRuntimeExceptionDao();
		    	photosHistoryDao.create(photos);
		    	
				imageList.add(photos);
			}
			
			return imageList;
		}

	}

}

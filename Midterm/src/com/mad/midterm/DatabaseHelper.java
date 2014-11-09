

/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File:DatabaseHelper.java
 */
package com.mad.midterm;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "searchhistory.db";
	private static final int DATABASE_VERSION = 1;
	private Dao<SearchHistory, Integer> searchHistoryDao = null;
	private Dao<Photos, Integer> photosDao = null;
	private RuntimeExceptionDao<SearchHistory,Integer> searchHistoryRuntimeDao = null;
	private RuntimeExceptionDao<Photos,Integer> photosRuntimeDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null,DATABASE_VERSION, R.raw.ormlite_config);

	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {

		try {
			TableUtils.createTable(connectionSource, SearchHistory.class);
			TableUtils.createTable(connectionSource, Photos.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, SearchHistory.class, true);
			TableUtils.dropTable(connectionSource, Photos.class, true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		onCreate(database, connectionSource);

	}
	
	public Dao<SearchHistory,Integer> getSearchHistoryDao() throws SQLException{
		if(searchHistoryDao == null){
			searchHistoryDao = getDao(SearchHistory.class);
		}
		return searchHistoryDao;
	}
	
	public Dao<Photos,Integer> getPhotosDao() throws SQLException{
		if(photosDao == null){
			photosDao = getDao(Photos.class);
		}
		return photosDao;
	}
	
	public RuntimeExceptionDao<SearchHistory, Integer> getSearchHistoryRuntimeExceptionDao(){
		if (searchHistoryRuntimeDao == null){
			searchHistoryRuntimeDao = getRuntimeExceptionDao(SearchHistory.class);
		}
		return searchHistoryRuntimeDao;
		
	}
	
	
	public RuntimeExceptionDao<Photos, Integer> getPhotosRuntimeExceptionDao(){
		if (photosRuntimeDao == null){
			photosRuntimeDao = getRuntimeExceptionDao(Photos.class);
		}
		return photosRuntimeDao;
		
	}
}

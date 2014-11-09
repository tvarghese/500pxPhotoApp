/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File:DatabaseConfigUtility.java
 */

package com.mad.midterm;
import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

public class DatabaseConfigUtility extends OrmLiteConfigUtil{
	private static final Class<?>[] classes = new Class[]{SearchHistory.class, Photos.class}; 

	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub
		writeConfigFile("ormlite_config.txt", classes);
	}

}

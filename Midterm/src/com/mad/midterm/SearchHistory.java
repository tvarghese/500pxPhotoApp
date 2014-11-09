/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File: SearchHistory.java
 */

package com.mad.midterm;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

@SuppressWarnings("serial")
public class SearchHistory implements Serializable{
	@DatabaseField(id = true)
	String searchTerm;
	@DatabaseField
	Date addedDate;
	
	public SearchHistory(String searchTerm) {
		super();
		this.searchTerm = searchTerm;
		this.addedDate = new Date(System.currentTimeMillis());
	}
	
	public SearchHistory() {
		
	}
	
}


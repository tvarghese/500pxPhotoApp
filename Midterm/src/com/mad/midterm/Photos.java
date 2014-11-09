/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File: Photos.java
 */

package com.mad.midterm;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.field.DatabaseField;

@SuppressWarnings("serial")
public class Photos implements Serializable{
	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField
	String title;
	@DatabaseField
	String author_name;
	@DatabaseField
	String authorImageURL;
	@DatabaseField
	String imageURL; // the image URL wont suffice the purpose of primary ke as there were cases 
					//	where the urls returned were the same for different photos
	
	@DatabaseField
	String notes;

	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@DatabaseField(canBeNull = false, foreign = true)
    private SearchHistory searchHistory;
	
	public Photos(JSONObject appJSONObject, SearchHistory searchTermHistory) throws JSONException {
		this.title = appJSONObject.getString("name");
		this.imageURL = appJSONObject.getString("image_url");
		
		this.author_name = appJSONObject.getJSONObject("user").getString("fullname");
		this.authorImageURL = appJSONObject.getJSONObject("user").getString("userpic_url");
		this.searchHistory = searchTermHistory;
	}
	
	public Photos(){
		
	}

	
	public Photos(String title, String author_name, String authorImageURL,
			String imageURL, SearchHistory searchHistory) {
		super();
		this.title = title;
		this.author_name = author_name;
		this.authorImageURL = authorImageURL;
		this.imageURL = imageURL;
		this.searchHistory = searchHistory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getAuthorImageURL() {
		return authorImageURL;
	}

	public void setAuthorImageURL(String authorImageURL) {
		this.authorImageURL = authorImageURL;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	

	public SearchHistory getSearchHistory() {
		return searchHistory;
	}

	@Override
	public String toString() {
		return "Photos [id=" + id + ", title=" + title + ", author_name="
				+ author_name + ", authorImageURL=" + authorImageURL
				+ ", imageURL=" + imageURL + ", notes=" + notes
				+ ", searchHistory=" + searchHistory + "]";
	}


	
	
	


	
	
	
	

}

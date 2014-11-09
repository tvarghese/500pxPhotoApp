/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File: IPhotoFetch.java
 */
package com.mad.midterm;

import java.util.List;


public interface IPhotoFetch {
	
	public void showPhotoList(List<Photos> result);
	public void showProgressDialog();
	public void hideProgressDialog();
	
}

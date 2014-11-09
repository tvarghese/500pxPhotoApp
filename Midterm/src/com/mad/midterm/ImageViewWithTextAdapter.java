/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File: ImageViewWithTextAdapter.java
 */
package com.mad.midterm;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageViewWithTextAdapter extends ArrayAdapter<Photos> {

	List<Photos> mObject;
	Context mContext;
	int mResource;

	public ImageViewWithTextAdapter(Context context, int resource,
			ArrayList<Photos> photoList) {
		super(context, resource, photoList);
		this.mObject = photoList;
		this.mContext = context;
		this.mResource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mResource, parent, false);

		}
		Photos news = mObject.get(position);
		TextView photoName = (TextView) convertView
				.findViewById(R.id.textView1);
		photoName.setText(news.title);

		ImageView photoImageView = (ImageView) convertView
				.findViewById(R.id.imageView1);

		Picasso.with(mContext).load((mObject.get(position).imageURL))
				.error(R.drawable.photo_not_found).fit().centerCrop()
				.into(photoImageView, new Callback() {
					@Override
					public void onSuccess() {
					}

					@Override
					public void onError() {
					}
				});

		return convertView;
	}

}

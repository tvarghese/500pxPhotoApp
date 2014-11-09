/*
 * Assignment: Homework 07
 * Group Members : Thomson Vadakkenchery Varghese, Alekhya Mosali
 * File:DetailsActivity.java
 */
package com.mad.midterm;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class DetailsActivity extends Activity {
	Photos item;
	DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		
		item = (Photos) getIntent().getSerializableExtra(
				GalleryActivity.PHOTOKEY);
		
		//try fetching the item from database
		RuntimeExceptionDao<Photos, Integer> photosHistoryDao = dbHelper.getPhotosRuntimeExceptionDao();
		 item = photosHistoryDao.queryForEq("id", item.id).get(0);
		 
		if (item.getNotes() == null) {
			showCommentEditView();
		} else {
			TextView commentsTextView = (TextView)findViewById(R.id.textView3);
			commentsTextView.setText(item.getNotes());
			showCommentTextView();
		}

		TextView photoNameTextView = (TextView) findViewById(R.id.textView2);
		photoNameTextView.setText(item.title);

		TextView ownersFullNameTextView = (TextView) findViewById(R.id.textView1);
		ownersFullNameTextView.setText(item.author_name);

		ImageView ownerImageView = (ImageView) findViewById(R.id.imageView2);

		ImageView photoImageView = (ImageView) findViewById(R.id.imageView1);

		// Resizing the image to maintaining the aspect ratio
		// Transformation transformation = getTransformationForWidth(350);

		Picasso.with(this).load(item.imageURL)
				.error(R.drawable.photo_not_found)
				.transform(getTransformationForWidth(350))
				.into(photoImageView, new Callback() {
					@Override
					public void onSuccess() {
					}

					@Override
					public void onError() {
					}
				});

		Picasso.with(this).load(item.authorImageURL)
				.error(R.drawable.user_not_found)
				.transform(getTransformationForWidth(200))
				.into(ownerImageView, new Callback() {
					@Override
					public void onSuccess() {
					}

					@Override
					public void onError() {
					}
				});

	}
	public Transformation getTransformationForWidth(int width){
		final int imageWidth =  width;
		return new Transformation() {
			
			@Override
			public Bitmap transform(Bitmap source) {
				int targetWidth = imageWidth;

				double aspectRatio = (double) source.getHeight()
						/ (double) source.getWidth();
				int targetHeight = (int) (targetWidth * aspectRatio);
				Bitmap result = Bitmap.createScaledBitmap(source, targetWidth,
						targetHeight, false);
				if (result != source) {
					// Same bitmap is returned if sizes are the same
					source.recycle();
				}
				return result;
			}
			
			@Override
			public String key() {
				return "transformation" + " desiredWidth";
			}
		};
		
	}
	
	public void onAddCommentClicked(View view){
		
		 EditText notesEditText = (EditText)findViewById(R.id.editText1);
		 String notesString = notesEditText.getText().toString();
		 
		RuntimeExceptionDao<Photos, Integer> photosHistoryDao = dbHelper.getPhotosRuntimeExceptionDao();
		 item = photosHistoryDao.queryForEq("id", item.id).get(0);
		 item.setNotes(notesString);
		photosHistoryDao.update(item);
		photosHistoryDao.refresh(item);
		
		TextView commentsTextView = (TextView)findViewById(R.id.textView3);
		commentsTextView.setText(notesString);
		showCommentTextView();

	}

	public void showCommentTextView(){
		 EditText notesEditText = (EditText)findViewById(R.id.editText1);
		 notesEditText.setVisibility(View.GONE);
		 
		 ImageButton addCommentButton = (ImageButton)findViewById(R.id.imageButton1);
		 addCommentButton.setVisibility(View.GONE);
		 
		 TextView commentsTextView = (TextView)findViewById(R.id.textView3);
		 commentsTextView.setVisibility(View.VISIBLE);
		 
		 TextView notesHeading = (TextView)findViewById(R.id.textView4);
		 notesHeading.setVisibility(View.VISIBLE); 
	}
	
	public void showCommentEditView(){
		 EditText notesEditText = (EditText)findViewById(R.id.editText1);
		 notesEditText.setVisibility(View.VISIBLE);
		 
		 ImageButton addCommentButton = (ImageButton)findViewById(R.id.imageButton1);
		 addCommentButton.setVisibility(View.VISIBLE);
		 
		 TextView commentsTextView = (TextView)findViewById(R.id.textView3);
		 commentsTextView.setVisibility(View.GONE); 
		 
		 TextView notesHeading = (TextView)findViewById(R.id.textView4);
		 notesHeading.setVisibility(View.GONE); 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.more_details, menu);
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
}

package com.fis.ra;

import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter {
	Context context;
	
	private int[] GalImages = new int[] { R.drawable.a1, R.drawable.a2,
			R.drawable.a3 };
	private InputStream stringImagePath;

	public ImageAdapter(Context context,InputStream path) {
		this.context = context;
		this.stringImagePath = path;
	}

	@Override
	public int getCount() {
		return GalImages.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((ImageView) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(context);
		int padding = context.getResources().getDimensionPixelSize(
				R.dimen.padding_medium);
		imageView.setPadding(padding, padding, padding, padding);
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		displaymetrics = context.getResources().getDisplayMetrics();
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		imageView.setImageBitmap(decodeSampledBitmapFromInputStream(
				context.getResources(), stringImagePath, width, height));

		Log.d("Prueba de mensajes", "carga imagen: " + position);
		((ViewPager) container).addView(imageView, 0);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((ImageView) object);
	}

	public static Bitmap decodeSampledBitmapFromInputStream(Resources res,
			InputStream imageFile, int reqWidth, int reqHeight) {
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(imageFile, null, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(imageFile, null, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
}
package com.fis.ra;


import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fis.ra.ObbExpansionsManager.ObbListener;

public class MainActivity extends Activity {
	
	private DrawerLayout drawerLayout;
	private ListView lvSlidingMenu;
	private ActionBarDrawerToggle drawerToggle;

	// Navigation Drawer titles
	private CharSequence drawerTitle;
	private CharSequence appTitle;

	// Sliding Menu items
	private String[] titles;
	private TypedArray icons;

	private ArrayList<SlidingMenuItem> slidingMenuItems;
	private SlidingMenuAdapter adapter;
	//FOR UNITY
	Toast toast;
	public static Context mContext;
	
	// back button control
	Fragment fragment = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// OBB LOAD
				final ObbListener listener = new ObbListener() {
					
					@Override
					public void onMountSuccess() {
						// TODO Auto-generated method stub
						Log.d("ObbExpansions", "on mount success obblistener.");
					}
					
					@Override
					public void onFilesNotFound() {
						// TODO Auto-generated method stub
						Log.d("ObbExpansions", "on file not found obblistener.");
					}
				};
				ObbExpansionsManager.createNewInstance(this,listener);
				
		
		
		//FOR UNITY
		
		mContext=this;
		setContentView(R.layout.activity_main);

		appTitle = drawerTitle = getTitle();
		
		//full screen
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Load resources
		titles = getResources().getStringArray(R.array.nav_drawer_items);		
		icons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		
		// Get Sliding Menu ListView istance		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		lvSlidingMenu = (ListView) findViewById(R.id.lv_sliding_menu);

		
		slidingMenuItems = new ArrayList<SlidingMenuItem>();

		// Creating and Adding SlidingMenuItems
		slidingMenuItems.add(new SlidingMenuItem(titles[0], icons.getResourceId(0, -1)));
		slidingMenuItems.add(new SlidingMenuItem(titles[1], icons.getResourceId(1, -1)));
		slidingMenuItems.add(new SlidingMenuItem(titles[2], icons.getResourceId(2, -1)));
		slidingMenuItems.add(new SlidingMenuItem(titles[3], icons.getResourceId(3, -1)));
		
		// Recycle the typed array
		icons.recycle();

		lvSlidingMenu.setOnItemClickListener(new SlideMenuClickListener());

		// Assign adapter to listview
		adapter = new SlidingMenuAdapter(getApplicationContext(), slidingMenuItems);
		lvSlidingMenu.setAdapter(adapter);

		// Enable action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, // Navigation Drawer icon
				R.string.app_name, // Navigation Drawer open - description for accessibility
				R.string.app_name // Navigation Drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(appTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(drawerTitle);
				invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			// On first time, show Home Fragment
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// Display appropriate fragment for selected item 
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Toggle Navigation Drawer on selecting action bar app icon/title
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If Navigation Drawer is opened, hide the action items
		boolean drawerOpen = drawerLayout.isDrawerOpen(lvSlidingMenu);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Display fragment view for selected Navigation Drawer list item
	 * */
	private void displayView(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new AboutFragment();
			break;
		case 2:
			fragment = new HelpFragment();
			//quit();
			break;
		case 3:
			quit();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_detail, fragment).commit();

			// Update selected item and title, then close the drawer
			lvSlidingMenu.setItemChecked(position, true);
			lvSlidingMenu.setSelection(position);
			setTitle(titles[position]);
			drawerLayout.closeDrawer(lvSlidingMenu);
		} else {
			// Log error
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		appTitle = title;
		getActionBar().setTitle(appTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		drawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        // your code
	    	Log.d("back", "Al pulsar el boton de back");
	    	fragment = new HomeFragment();
			if (fragment != null) {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.fragment_detail, fragment).commit();

				// Update selected item and title, then close the drawer
				
			} else {
				// Log error
				Log.e("MainActivity", "Error in creating fragment");
			}
			
	        return true;
	    }else if(keyCode == KeyEvent.KEYCODE_SETTINGS){
	    	Log.d("settings", "pulsado botton settings");
	    	lvSlidingMenu.setOnItemClickListener(new SlideMenuClickListener());
	    	return true;
	    	
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	public void quit() {
		HomeFragment.mQCARShared.onDestroy(); 
	    Intent startMain = new Intent(Intent.ACTION_MAIN);
	    startMain.addCategory(Intent.CATEGORY_HOME);
	    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	   // startActivity(startMain);
	    System.exit(0);
	}
	
@Override
    
    public void onResume(){
    	super.onResume();
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	
    }
    
    
    @Override
    
    public void onDestroy(){
    	super.onDestroy();
    	HomeFragment.mQCARShared.onDestroy();
    	System.exit(0); 
    }

	
	
}

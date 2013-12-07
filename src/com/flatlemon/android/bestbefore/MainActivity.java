package com.flatlemon.android.bestbefore;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class MainActivity extends ListActivity{
	
	String[] classes = { "MainActivity", "TextPlay", "Email", "Camera",
			"Data", "Gfx", "GfxSurface", "SoundStuff", "Slider", "Tabs",
			"SimpleBrowser", "Flipper", "SharedPrefs", "SQLLiteExample"};

	List<String> items;
	BBArrayAdapter adapter;
	
	protected Object mActionMode;
	public int selectedItem = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (mActionMode != null) {
					return false;
				}
				selectedItem = position;

				mActionMode = MainActivity.this.startActionMode(mActionModeCallback);
				view.setSelected(true);
				return true;
			}
		});
		
		//setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Database db = new Database(this);
		db.open();	
		items = db.getData();
		db.close();
		adapter = new BBArrayAdapter(this, items);
		
//		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classes));
//		setListAdapter(new ArrayAdapter<String>(this, R.layout.listview, R.id.tvItemName, items));
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// called when the action mode is created; startActionMode() was called
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			// assumes that you have "contexual.xml" menu resources
			inflater.inflate(R.menu.activity_main_context, menu);
			return true;
	    }

	    // the following method is called each time 
	    // the action mode is shown. Always called after
	    // onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	    	return false; // Return false if nothing is done
	    }

	    // called when the user selects a contextual menu item
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	    	switch (item.getItemId()) {
	    	case R.id.menu_delete:
	    		Database db = new Database(MainActivity.this);
	    		String s = items.get(selectedItem);
	    		db.open();
	    		db.deleteEntry(s.substring(0, s.indexOf(",")));
	    		db.close();
	    		adapter.remove(items.get(selectedItem));
	    		mode.finish();
	    		return true;
	    	default:
	    		return false;
	    	}
	    }

	    // called when the user exits the action mode
	    public void onDestroyActionMode(ActionMode mode) {
	      mActionMode = null;
	      selectedItem = -1;
	    }
	  };
	
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.menu_add:
			
			//Intent j = new Intent(this, AddItem.class);
			Intent j = new Intent("com.flatlemon.android.bestbefore.ADDITEM");
			startActivity(j);
			break;
		/*case R.id.menu_settings:
			Intent i = new Intent("com.flatlemon.android.bestbefore.PREFS");
			startActivity(i);
			break;*/
		case R.id.menu_exit:
			finish();
			break;
		}
		return false;
	}
}

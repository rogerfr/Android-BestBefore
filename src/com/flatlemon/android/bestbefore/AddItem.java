package com.flatlemon.android.bestbefore;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class AddItem extends Activity implements OnClickListener {

	Button bnAdd;
	EditText etProduct;
	DatePicker dpDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);

		bnAdd = (Button) findViewById(R.id.bnAdd);
		etProduct = (EditText) findViewById(R.id.etProduct);
		dpDate = (DatePicker) findViewById(R.id.dpDate);

		bnAdd.setOnClickListener(this);
		dpDate.setCalendarViewShown(false);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.bnAdd:

			boolean didItWork = true;
			try {
				String product = etProduct.getText().toString();
				int bbYear = dpDate.getYear();
				int bbMonth = dpDate.getMonth();
				int bbDate = dpDate.getDayOfMonth();

				Database db = new Database(AddItem.this);
				db.open();
				db.createEntry(product, bbYear, bbMonth, bbDate);
				db.close();
				finish();
			} catch (Exception e) {
				e.getStackTrace();
				didItWork = false;
			} finally {
				if (didItWork) {
					Dialog d = new Dialog(this);
					d.setTitle("Success");
					TextView tv = new TextView(this);
					tv.setText("Item added");
					d.setContentView(tv);
					d.show();
					finish();
				} else {
					Dialog d = new Dialog(this);
					d.setTitle("Failure");
					TextView tv = new TextView(this);
					tv.setText("Item was not added");
					d.setContentView(tv);
					d.show();
				}
			}
			break;
		case R.id.dpDate:
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(etProduct.getWindowToken(), 0);
		}
	}
}

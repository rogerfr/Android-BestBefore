package com.flatlemon.android.bestbefore;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

	public static final String KEY_ROWID = "id";
	public static final String KEY_PRODUCT = "product";
	public static final String KEY_BBYEAR = "bbyear";
	public static final String KEY_BBMONTH = "bbmonth";
	public static final String KEY_BBDATE = "bbdate";

	private static final String DATABASE_NAME = "BestBeforeDB";
	private static final String DATABASE_TABLE = "BestBeforeTable";
	private static final int DATABASE_VERSION = 1;

	private DbHelper dbHelper;
	private final Context context;
	private SQLiteDatabase sqlDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PRODUCT
					+ " TEXT NOT NULL, " + KEY_BBYEAR + " TEXT NOT NULL, "
					+ KEY_BBMONTH + " TEXT NOT NULL, " + KEY_BBDATE
					+ " TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public Database(Context c) {
		context = c;
	}

	public Database open() throws SQLException {
		dbHelper = new DbHelper(context);
		sqlDatabase = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long createEntry(String product, int bbYear, int bbMonth, int bbDate) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_PRODUCT, product);
		cv.put(KEY_BBYEAR, bbYear);
		cv.put(KEY_BBMONTH, bbMonth);
		cv.put(KEY_BBDATE, bbDate);
		return sqlDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public void deleteEntry(String id) {
		String[] args = null;
		sqlDatabase.delete(DATABASE_TABLE, KEY_ROWID + " = "
				+ id, args);
	}

	public List<String> getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_PRODUCT, KEY_BBYEAR,
				KEY_BBMONTH, KEY_BBDATE };
		Cursor c = sqlDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		List<String> result = new ArrayList<String>();

		int iRowId = c.getColumnIndex(KEY_ROWID);
		int iProduct = c.getColumnIndex(KEY_PRODUCT);
		int iBbyear = c.getColumnIndex(KEY_BBYEAR);
		int iBbmonth = c.getColumnIndex(KEY_BBMONTH);
		int iBbdate = c.getColumnIndex(KEY_BBDATE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result.add(c.getString(iRowId) + "," 
					+ c.getString(iProduct) + ","
					+ c.getString(iBbyear) + "," 
					+ c.getString(iBbmonth) + ","
					+ c.getString(iBbdate));
		}

		return result;
	}
}

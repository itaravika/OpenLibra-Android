package com.tarsoft.openlibra;

/*OpenLibra - https://github.com/openlibra/OpenLibra
Copyright (C) 2011 David Rodr’guez Alvarez (itaravika) itaravika@gmail.com

OpenLibra is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or
(at your option) any later version.

OpenLibra is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with OpenLibra; if not, see http://www.gnu.org/licenses for more
information.
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBOpenLibra extends SQLiteOpenHelper{

	private final String TAG = "OpenLibra";
	
	//OpenLibra BBDD
	static final String dbName="OpenLibraDB";
	static final Integer version = 1;
	static SQLiteDatabase db=null;
	
	//Date
	private String date;
     
	//**** Table <list of books> ****
	//Table and columns	
	static final String listTable="OLlistTable";
	
	static final String collistID="colID";
	static final String colnameList="nameList"; //"Last" Last books added, "Most" Books most viewed
	static final String colsecList="secList"; 
	static final String colidBookList="idBook"; 
	static final String coldateInsert="dateInsert";
	
	//**** Table <books> ****
	//Table and columns	
	static final String bookTable="OLBooksTable";
	
	static final String colbookID="colID";
	static final String colidBook="colIdBook";
	static final String colTitle="colTitle";
	static final String colAuthor="colAuthor";
	static final String colPublisher="colPublisher";
	static final String colPublisher_date="colPublisher_date";
	static final String colPages="colPages";
	static final String colLanguage="colLanguage";
	static final String colUrl_details="colUrl_details";
	static final String colUrl_download="colUrl_download";
	static final String colUrl_read_online="colUrl_read_online";
	static final String colCover="colCover";
	static final String colThumbnail="colThumbnail";
	static final String colRating="colRating";
	static final String colNum_comments="colNum_comments";
	static final String colDetails="colDetails";
	static final String colCoverBitMap="colCoverBitMap";
	static final String coldateInsertBook="dateInsert";
	
	public DBOpenLibra(Context context) {
		  super(context, dbName, null, version);		  
		  db=this.getWritableDatabase();
		  
		  //get Date
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
		  this.date = sdf.format(new Date()).toString();	
	}

	public void onCreate(SQLiteDatabase db) {		
		try{
		
			//**** Table <list of books> ****
			//Create 
			db.execSQL("CREATE TABLE  "+listTable + " ("+collistID+ " INTEGER PRIMARY KEY AUTOINCREMENT , " +
					colnameList   +" TEXT, " +
					colsecList    +" INTEGER, " +
					colidBookList +" TEXT, " +
					coldateInsert +" TEXT)");	
			
			//**** Table <books> ****
			//Create 
			db.execSQL("CREATE TABLE  "+bookTable + " ("+colbookID+ " INTEGER PRIMARY KEY AUTOINCREMENT , " +
					colidBook  				+" TEXT, " +
					colTitle   				+" TEXT, " +
					colAuthor    			+" TEXT, " +
					colPublisher    		+" TEXT, " +
					colPublisher_date     	+" TEXT, " +
					colPages     			+" TEXT, " +
					colLanguage     		+" TEXT, " +
					colUrl_details  		+" TEXT, " +
					colUrl_download 		+" TEXT, " +
					colUrl_read_online    	+" TEXT, " +
					colCover     			+" TEXT, " +
					colThumbnail    		+" TEXT, " +
					colRating     			+" TEXT, " +
					colNum_comments 		+" TEXT, " +
					colDetails  			+" TEXT, " +
					colCoverBitMap 			+" BLOB, " +
					coldateInsertBook		+" TEXT)"  );
			
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when create bookTable: "+ e.toString()); 
        }			  
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
		//TODO: upgrade tables
	}
	
	//**** Table <list of books> ****
	public void insertListReg(TableList tl) {
		
		Log.v(TAG, "Insert Lista - Tipo: " + tl.getcolnameList() + "ID: " + tl.getcolidBook());
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableList: "+ e.toString()); 
        }
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
		this.date = sdf.format(new Date()).toString();	
		
		ContentValues cv=new ContentValues();
		
		cv.put(colnameList, tl.getcolnameList());
		cv.put(colsecList, tl.getcolsecList());
		cv.put(colidBookList, tl.getcolidBook());
		cv.put(coldateInsert, this.date);
		
		try{		
			db.insert(listTable, collistID, cv);		
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when insert - TableList: : "+ e.toString()); 
        }		
	}	
	
	public int updateListReg(TableList tl) {
		
	    try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableList: "+ e.toString()); 
        }
	    ContentValues cv=new ContentValues();
	   	
		cv.put(colnameList, tl.getcolnameList());
		cv.put(colsecList, tl.getcolsecList());
		cv.put(colidBookList, tl.getcolidBook());
		cv.put(coldateInsert, this.date);
		
	    int regsUpdate = 0;
	   
	    try{	   
			regsUpdate = db.update(listTable, cv, collistID+"=?", 
	    		new String []{String.valueOf(tl.getcolID())}); 	    
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when update - TableList: : "+ e.toString()); 
        }	    
	    return regsUpdate;
	}
	
	public int deleteListReg(long colIDParam){
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableList: "+ e.toString()); 
        }
		
		int regsDelete = 0;
		
		try{
			regsDelete = db.delete(listTable, collistID+"=?", new String[] {Long.toString(colIDParam)});
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when delete - TableList: "+ e.toString()); 
        }		
		return regsDelete;
	}
	
	public int deleteListReg(String colnameListParam){
		
		Log.v(TAG, "Delete Lista - Tipo: " + colnameListParam);
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableList: "+ e.toString()); 
        }
		
		int regsDelete = 0;
		
		try{
			regsDelete = db.delete(listTable, colnameList+"=?", new String[] {colnameListParam});
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when delete - TableList: "+ e.toString()); 
        }		
		return regsDelete;
	}	
	
	public int deleteListAll(){
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableList: "+ e.toString()); 
        }
		
		int regsDelete = 0;
		
		try{
			regsDelete = db.delete(listTable, null, null);
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when delete ALL - TableList: "+ e.toString()); 
        }		
		return regsDelete;
	}
	
	public TableList selectListReg(long colIDParam) {
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableList: "+ e.toString()); 
        }
		TableList tl = null;
		
		try{
			Cursor cursor = db.query(listTable, 
				null, collistID+"=?", new String[] {Long.toString(colIDParam)}, 
				null, null, null);
				
			if (cursor.moveToFirst()) {
				tl = new TableList(cursor.getLong(0), cursor.getString(1),
						cursor.getInt(2), cursor.getString(3),
						cursor.getString(4));
			}
			cursor.close();
			db.close();
		} catch (Exception e) { 
			e.printStackTrace(); 
			Log.d(TAG, "Error when select one reg. - TableList: "+ e.toString()); 
        }
		return tl;
	}
	
	public List<TableList> sLListReg(String colnameListParam) {
		
		Log.v(TAG, "Select Lista - Tipo: " + colnameListParam);
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableList: "+ e.toString()); 
        }
		
		List<TableList> listaList = new ArrayList<TableList>();
		TableList tl = null;		
	
		try{
			Cursor cursor = db.query(listTable, 
				null, colnameList+"=?", new String[] {colnameListParam}, 
				null, null, null);

			if (cursor.moveToFirst()) {
				do {
					tl = new TableList(cursor.getLong(0), cursor.getString(1),
						cursor.getInt(2), cursor.getString(3), 
						cursor.getString(4));
					listaList.add(tl);
				} while(cursor.moveToNext());
			}
			cursor.close();
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when select List. - TableList: "+ e.toString()); 
        }
		
		//Sort List
		Collections.sort(listaList);
		
		return listaList;
	}
	
	
	
	
	//**** Table <books> ****
	public void insertBookReg(TableBook tb) {
		
		Log.v(TAG, "Insert Book - ID: " + tb.getcolidBook());
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableBook: "+ e.toString()); 
        }
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
		this.date = sdf.format(new Date()).toString();	
		
		ContentValues cv=new ContentValues();
		
		cv.put(colidBook, tb.getcolidBook());
		cv.put(colTitle, tb.getcolTitle());
		cv.put(colAuthor, tb.getcolAuthor());
		cv.put(colPublisher, tb.getcolPublisher());
		cv.put(colPublisher_date, tb.getcolPublisher_date());
		cv.put(colPages, tb.getcolPages());
		cv.put(colLanguage, tb.getcolLanguage());
		cv.put(colUrl_details, tb.getcolUrl_details());
		cv.put(colUrl_download, tb.getcolUrl_download());
		cv.put(colUrl_read_online, tb.getcolUrl_download());
		cv.put(colCover, tb.getcolCover());
		cv.put(colThumbnail, tb.getcolThumbnail());
		cv.put(colRating, tb.getcolRating());
		cv.put(colNum_comments, tb.getcolNum_comments());
		cv.put(colDetails, tb.getcolDetails());
		
		if (tb.getcolCoverBitMap() != null){
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        tb.getcolCoverBitMap().compress(Bitmap.CompressFormat.PNG, 100, out);
			cv.put(colCoverBitMap, out.toByteArray());
		} else {
			byte[] out = null;
			cv.put(colCoverBitMap, out);
		}
		
		cv.put(coldateInsertBook, this.date);
		
		try{		
			db.insert(bookTable, colbookID, cv);		
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when insert - TableBook: : "+ e.toString()); 
        }		
	}	
	
	public int updateBookReg(TableBook tb) {
		
		Log.v(TAG, "Update Book - ID: " + tb.getcolidBook());
		
	    try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableBook: "+ e.toString()); 
        }
	    ContentValues cv=new ContentValues();
	   	
		cv.put(colidBook, tb.getcolidBook());
		cv.put(colTitle, tb.getcolTitle());
		cv.put(colAuthor, tb.getcolAuthor());
		cv.put(colPublisher, tb.getcolPublisher());
		cv.put(colPublisher_date, tb.getcolPublisher_date());
		cv.put(colPages, tb.getcolPages());
		cv.put(colLanguage, tb.getcolLanguage());
		cv.put(colUrl_details, tb.getcolUrl_details());
		cv.put(colUrl_download, tb.getcolUrl_download());
		cv.put(colUrl_read_online, tb.getcolUrl_download());
		cv.put(colCover, tb.getcolCover());
		cv.put(colThumbnail, tb.getcolThumbnail());
		cv.put(colRating, tb.getcolRating());
		cv.put(colNum_comments, tb.getcolNum_comments());
		cv.put(colDetails, tb.getcolDetails());
		
		if (tb.getcolCoverBitMap() != null){
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        tb.getcolCoverBitMap().compress(Bitmap.CompressFormat.PNG, 100, out);
			cv.put(colCoverBitMap, out.toByteArray());
		} else {
			byte[] out = null;
			cv.put(colCoverBitMap, out);
		}
		
		cv.put(coldateInsertBook, this.date);
		
	    int regsUpdate = 0;
	   
	    try{	   
			regsUpdate = db.update(bookTable, cv, colbookID+"=?", 
	    		new String []{String.valueOf(tb.getcolID())}); 	    
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when update - TableBook: : "+ e.toString()); 
        }	    
	    return regsUpdate;
	}
	
	public int deleteBookReg(long colIDParam){
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableBook: "+ e.toString()); 
        }
		
		int regsDelete = 0;
		
		try{
			regsDelete = db.delete(bookTable, colbookID+"=?", new String[] {Long.toString(colIDParam)});
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when delete - TableBook: "+ e.toString()); 
        }		
		return regsDelete;
	}
	
	public int deleteBookReg(String colidBookParam){
		
		Log.v(TAG, "Delete Book - ID: " + colidBookParam);
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableBook: "+ e.toString()); 
        }
		
		int regsDelete = 0;
		
		try{
			regsDelete = db.delete(bookTable, colidBook+"=?", new String[] {colidBookParam});
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when delete - TableBook: "+ e.toString()); 
        }		
		return regsDelete;
	}	
	
	public int deleteBookAll(){
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableBook: "+ e.toString()); 
        }
		
		int regsDelete = 0;
		
		try{
			regsDelete = db.delete(bookTable, null, null);
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when delete ALL - TableBook: "+ e.toString()); 
        }		
		return regsDelete;
	}
	
	public TableBook selectBookReg(long colIDParam) {
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableBook: "+ e.toString()); 
        }
		TableBook tb = null;
		
		try{
			Cursor cursor = db.query(bookTable, 
				null, colbookID+"=?", new String[] {Long.toString(colIDParam)}, 
				null, null, null);
				
			if (cursor.moveToFirst()) {
				
	            byte[] blob = cursor.getBlob(16);
	            Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
				
				tb = new TableBook(cursor.getLong(0), cursor.getString(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4), cursor.getString(5),
						cursor.getString(6), cursor.getString(7),
						cursor.getString(8), cursor.getString(9),
						cursor.getString(10), cursor.getString(11),
						cursor.getString(12), cursor.getString(13),
						cursor.getString(14), cursor.getString(15),
						bmp, cursor.getString(17));
			}
			cursor.close();
			db.close();
		} catch (Exception e) { 
			e.printStackTrace(); 
			Log.d(TAG, "Error when select one reg. - TableBook: "+ e.toString()); 
        }
		return tb;
	}
	
	public TableBook selectBookReg(String colidBookParam) {
		
		Log.v(TAG, "Select Book - ID: " + colidBookParam);
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableBook: "+ e.toString()); 
        }
		
		TableBook tb = null;
		
		try{
			Cursor cursor = db.query(bookTable, 
				null, colidBook+"=?", new String[] {colidBookParam}, 
				null, null, null);
				
			if (cursor.moveToFirst()) {
				
				Bitmap bmp;				
				if (cursor.getBlob(16) != null) {
		            byte[] blob = cursor.getBlob(16);
		            bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
				} else {
					bmp = null;
				}
					
				tb = new TableBook(cursor.getLong(0), cursor.getString(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4), cursor.getString(5),
						cursor.getString(6), cursor.getString(7),
						cursor.getString(8), cursor.getString(9),
						cursor.getString(10), cursor.getString(11),
						cursor.getString(12), cursor.getString(13),
						cursor.getString(14), cursor.getString(15),
						bmp, cursor.getString(17));
			}
			cursor.close();
			db.close();
		} catch (Exception e) { 
			e.printStackTrace(); 
			Log.d(TAG, "Error when select one reg. - TableBook: "+ e.toString()); 
        }
		return tb;
	}
	
	public List<TableBook> sLBookReg() {
		
		try{		
			db=this.getWritableDatabase();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when get WritableDatabase - TableBook: "+ e.toString()); 
        }
		
		List<TableBook> listaBook = new ArrayList<TableBook>();
		TableBook tb = null;		
	
		try{
			Cursor cursor = db.query(bookTable, 
				null, null, null, 
				null, null, null);

			if (cursor.moveToFirst()) {
				do {
					Bitmap bmp;				
					if (cursor.getBlob(16) != null) {
			            byte[] blob = cursor.getBlob(16);
			            bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
					} else {
						bmp = null;
					}
					
					tb = new TableBook(cursor.getLong(0), cursor.getString(1),
							cursor.getString(2), cursor.getString(3),
							cursor.getString(4), cursor.getString(5),
							cursor.getString(6), cursor.getString(7),
							cursor.getString(8), cursor.getString(9),
							cursor.getString(10), cursor.getString(11),
							cursor.getString(12), cursor.getString(13),
							cursor.getString(14), cursor.getString(15),
							bmp, cursor.getString(17));
					listaBook.add(tb);
				} while(cursor.moveToNext());
			}
			cursor.close();
			db.close();
		} catch (Exception e) { 
            e.printStackTrace(); 
            Log.d(TAG, "Error when select List. - TableBook: "+ e.toString()); 
        }
		
		//Sort List
		Collections.sort(listaBook);
		
		return listaBook;
	}	
}
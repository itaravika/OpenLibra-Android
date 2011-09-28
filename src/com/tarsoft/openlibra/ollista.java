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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;

import com.thira.examples.actionbar.widget.ActionBar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.Toast;

public class ollista extends Activity {

	
	private ProgressDialog pd;
	
	private static List<Book> listaLast = new ArrayList<Book>();
	private static List<Book> listaMost = new ArrayList<Book>();
	
	private OpenLibraClient OLC = new OpenLibraClient();
	private Criteria criteriaLast = new Criteria(99,"",2,0,5,0);
	private Criteria criteriaMost = new Criteria(10,"most_viewed",0,0,10,0);

	DBOpenLibra DAO = null;
	
	private listaOLIconArrayAdapter adapterLast = null;
	private listaOLArrayAdapter adapterMost = null;
	
	//private ListView listerLast;
	private ListView listerMost;
	
	private boolean checkConexion;
	
	private ActionBar mActionBar;
	
	private String TAG = "OpenLibra";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
       
        // prevent the default title-bar from being displayed
        requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(R.layout.ollista);
        
        //Create BBDD object
        DAO = new DBOpenLibra(this);
        
        //ActionBar        
        mActionBar = (ActionBar) findViewById(R.id.actionBar);
        mActionBar.setTitle(R.string.app_name);
        
        // set home icon that does nothing when the user clicks on it
        mActionBar.setHomeLogo(R.drawable.icon);
        
        mActionBar.addActionIcon(R.drawable.refresh,
        		new OnClickListener() {
	                @Override
	                public void onClick(View v) {
	            		//load data on activity adapters
	            		loadData(true);
	                }
	            });
        
        mActionBar.addActionIcon(R.drawable.change,
        		new OnClickListener() {
	                @Override
	                public void onClick(View v) {
	                    Toast.makeText(ollista.this,
	                        "Clicked on an Icon Change",
	                        Toast.LENGTH_SHORT).show();
	                }
	            });      
        
        // sets an action icon that displays a Toast upon clicking
        mActionBar.addActionIcon(R.drawable.search,
        		new OnClickListener() {
	                @Override
	                public void onClick(View v) {
	                    Toast.makeText(ollista.this,
	                        "Clicked on an Icon Search",
	                        Toast.LENGTH_SHORT).show();
	                }
	            });
        
        //Data
        listerMost = (ListView) findViewById(R.id.list2);
        
        if (adapterLast == null) {
            adapterLast = new listaOLIconArrayAdapter(ollista.this,listaLast);
        }        
        
        //Covers gallery        
        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(adapterLast);

        /*
        gallery.setOnItemClickListener(new OnItemClickListener() {
            
        	public void onItemClick(AdapterView parent, View v, int position, long id) {
                
        		Toast.makeText(ollista.this, "" + position, Toast.LENGTH_SHORT).show();
            }

        });
		*/

        //Most viewed list
        if (adapterMost == null) {
            adapterMost = new listaOLArrayAdapter(ollista.this,listaMost);
        }
        
        listerMost.setTextFilterEnabled(true);        
        listerMost.setItemsCanFocus(true);
        listerMost.setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
        
        listerMost.setAdapter(adapterMost);
        
        
    	//Evaluate Internet available
    	try {
	    	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo checkInternet = cm.getActiveNetworkInfo();
	        if (checkInternet != null && checkInternet.isConnectedOrConnecting()) {
	        	checkConexion = true;
	        } else {
	        	checkConexion = false;
	        } 
	        
		} catch (Exception e) { 
			e.printStackTrace(); 
			Log.d(TAG, "Error when check if internet available: "+ e.toString()); 
		}
		
		//load data on activity adapters
		loadData(false);				
	}
	
	//Application menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		Intent i;
		
		switch (item.getItemId()){
		case R.id.config:
			i = new Intent(ollista.this, config.class);
			startActivity(i);
			return true;
		}
		return false;
	}
	
	//method to load data on activity
	private void loadData(boolean refresh){
		
    	//If internet available
    	if (checkConexion){        
	        //initial load of OpenLibra
    		//if refresh access to OpenLibra else access to BBDD
    		if (refresh){
    			new consultaOL().execute();
    		} else {
    			new consultaBBDDOL().execute();
    		}    			
    	} else {
    		Context context = getApplicationContext();
    		int duration = Toast.LENGTH_SHORT;
    		Toast toast = Toast.makeText(context, 
    				getApplicationContext().getString(R.string.NoInternet),
    				duration);
    		toast.show();
    	}
		
	}

	//asynchronous task to make the initial search - Direct Access to OpenLibra
	private class consultaOL extends AsyncTask<String, Void, List<BooksHome>> {
		
	    protected void onPreExecute() {
	    	//search dial to release the word
	        pd = ProgressDialog.show(ollista.this, getApplicationContext().getString(R.string.dialer),
					 getApplicationContext().getString(R.string.dialerDet), true,
	                false);
	    	mActionBar.showProgressBar();
	    }
	
	    @Override
	    protected List<BooksHome> doInBackground(String... params) {       	
	    	List<BooksHome> result = null;
	    	
	    	try{
	    		if (!postData().isEmpty()) {
	    			result = postData();
	    		}
	        	
	        	//finish progressDialog
	        	pd.dismiss();
	        	
	    	
			} catch (Exception e) { 
				e.printStackTrace(); 
				Log.d(TAG, "Query error in OpenLibra (doinBackground)  : "+ e.toString());
			}		
	    	//get result
	    	return result;
	    }
	    
	
	    @Override
	    protected void onPostExecute(List<BooksHome> result) {
			
	    	if (result.size() > 0 && result.get(0).getcolBooks() != null){
				listaOLIconArrayAdapter adapterLast = new listaOLIconArrayAdapter(ollista.this,result.get(0).getcolBooks());
		        Gallery gallery = (Gallery) findViewById(R.id.gallery);
		        gallery.setAdapter(adapterLast);
	    	}
	    	
	        if (result.size() > 0 && result.get(1).getcolBooks() != null){
				listaOLArrayAdapter adapterMost = new listaOLArrayAdapter(ollista.this,result.get(1).getcolBooks());
				listerMost.setAdapter(adapterMost);
	        }
	        
			//start thread to update bitmaps
			new Thread(new Runnable() {
		        public void run() {
		            
		    		List<TableBook> books = DAO.sLBookReg();
		    		
					for (int i = 0;i<books.size();i++) {
						TableBook book = books.get(i);
						if (book.getcolCoverBitMap() == null){
					    	//get Date
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
							String date = sdf.format(new Date()).toString();	
							
							//Process of Cover bitmap
				        	try {
								book.setcolCoverBitMap(drawableFromUrl(book.getcolThumbnail()));
							} catch (Exception e) {								
								e.printStackTrace();
								Log.d(TAG, "Error when update bitmaps BBDD OpenLibra: "+ e.toString());
							}
				        	book.setcoldateInsert(date);				        	
				        	
				    		DAO.updateBookReg(book);							
						}
					}
		        }
			}).start(); 
			
	    	//finish progressBar
			mActionBar.hideProgressBar();
	    }
	    
	    //Method that returns the list of data from OpenLibra
	    public List<BooksHome> postData() throws JSONException, MalformedURLException, IOException {    	  
	    	
	    	BooksHome BooksLast = new BooksHome(OLC.getBooks(criteriaLast));
	    	if (BooksLast.getcolBooks() != null){
	    		saveBBDD(BooksLast, "last");
	    	}
	    	
	    	BooksHome BooksMost = new BooksHome(OLC.getBooks(criteriaMost));
	    	if (BooksMost.getcolBooks() != null){
	    		saveBBDD(BooksMost, "most");
	    	}
	    	
	    	List<BooksHome> listBooks = new ArrayList<BooksHome>();
	    	listBooks.add(BooksLast);
	    	listBooks.add(BooksMost);
	    	
	    	return listBooks;   
	    }
	}
	
	//asynchronous task to make the initial search - BBDD data of OpenLibra
	private class consultaBBDDOL extends AsyncTask<String, Void, List<BooksHome>> {
		
	    protected void onPreExecute() {
	    	//search dial to release the word
	        pd = ProgressDialog.show(ollista.this, getApplicationContext().getString(R.string.dialer),
					 getApplicationContext().getString(R.string.dialerDet), true,
	                false);
	    	mActionBar.showProgressBar();
	    }
	
	    @Override
	    protected List<BooksHome> doInBackground(String... params) {   
	    	List<BooksHome> result = null;
	    	
	    	try{
	    		result = postData();
	        	
	        	//finish progressDialog
	        	pd.dismiss();	        	
	    	
			} catch (Exception e) { 
				e.printStackTrace(); 
				Log.d(TAG, "Query error in OpenLibra BBDD (doinBackground)  : "+ e.toString());
			}		
	    	//get result
	    	return result;
	    }	    
	
	    @Override
	    protected void onPostExecute(List<BooksHome> result) {
	    	
	    	if (result.size() > 0 && result.get(0).getcolBooks() != null){
				listaOLIconArrayAdapter adapterLast = new listaOLIconArrayAdapter(ollista.this,result.get(0).getcolBooks());
		        Gallery gallery = (Gallery) findViewById(R.id.gallery);
		        gallery.setAdapter(adapterLast);
	    	}
	    	
	    	if (result.size() > 0 && result.get(1).getcolBooks() != null){
				listaOLArrayAdapter adapterMost = new listaOLArrayAdapter(ollista.this,result.get(1).getcolBooks());
				listerMost.setAdapter(adapterMost);
	    	}

			//start thread to update bitmaps
			new Thread(new Runnable() {
		        public void run() {
		            
		    		List<TableBook> books = DAO.sLBookReg();
		    		
					for (int i = 0;i<books.size();i++) {
						TableBook book = books.get(i);
						if (book.getcolCoverBitMap() == null){
					    	//get Date
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
							String date = sdf.format(new Date()).toString();	
							
							//Process of Cover bitmap
				        	try {
								book.setcolCoverBitMap(drawableFromUrl(book.getcolThumbnail()));
							} catch (Exception e) {								
								e.printStackTrace();
								Log.d(TAG, "Error when update bitmaps BBDD OpenLibra: "+ e.toString());
							}
				        	book.setcoldateInsert(date);				        	
				        	
				    		DAO.updateBookReg(book);							
						}
					}
		        }
			}).start(); 
	    	
	    	//finish progressBar
			mActionBar.hideProgressBar();
	    }
	    
	    //Method that returns the list of data from OpenLibra BBDD
	    public List<BooksHome> postData() throws JSONException, MalformedURLException, IOException {    	  
	    	//Create BooksHome objects
	    	BooksHome BooksLast = null;
	    	BooksHome BooksMost = null;
	    	
	    	//Get the date of the last day
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
		    Calendar cal = Calendar.getInstance();
		    cal.add(Calendar.DATE, -1);	
			String dateYesterday = sdf.format(cal.getTime()).toString();		    	
			
	    	//Get the latest Books of OpenLibra
	    	//If the date is from more than one day, access to OpenLibra server
	    	List<TableList> ltl = DAO.sLListReg("last");
	    	
	    	boolean existBooksLast = true;
			Book bookLast = null;
			List<Book> booksLast = new ArrayList<Book>();
			
	    	if (ltl.size() > 0 && ltl.get(0).getcoldateInsert().compareTo(dateYesterday) >= 0){
					for (int i = 0;i<ltl.size();i++) {
						TableBook tb = DAO.selectBookReg(ltl.get(i).getcolidBook());
						if (tb != null){
							bookLast = new Book(tb.getcolidBook(), tb.getcolTitle(), tb.getcolAuthor(),
									tb.getcolPublisher(), tb.getcolPublisher_date(),
									tb.getcolPages(), tb.getcolLanguage(),
									tb.getcolUrl_details(), tb.getcolUrl_download(), tb.getcolUrl_read_online(),
									tb.getcolCover(), tb.getcolThumbnail(), tb.getcolRating(),
									tb.getcolNum_comments(), null, null, tb.getcolCoverBitMap());
							booksLast.add(bookLast);
						} else {
							i = ltl.size();
							existBooksLast = false;
						}						
					}	    			
	    	} else {	  
	    		existBooksLast = false;
	    	}
	    	
	    	if (existBooksLast) {
	    		BooksLast = new BooksHome(booksLast);
	    	} else {
	    		BooksLast = new BooksHome(OLC.getBooks(criteriaLast));
	    		if (BooksLast.getcolBooks() != null){
	    			saveBBDD(BooksLast, "last");
	    		}
	    	}
	    	
	    	//Get the most viewed Books of OpenLibra
	    	//If the date is from more than one day, access to OpenLibra server
	    	List<TableList> mtl = DAO.sLListReg("most");
	    	
	    	boolean existBooksMost = true;
			Book bookMost = null;
			List<Book> booksMost = new ArrayList<Book>();
			
	    	if (mtl.size() > 0 && mtl.get(0).getcoldateInsert().compareTo(dateYesterday) >= 0){
					for (int i = 0;i<mtl.size();i++) {						
						TableBook tb = DAO.selectBookReg(mtl.get(i).getcolidBook());
						if (tb != null){
							bookMost = new Book(tb.getcolidBook(), tb.getcolTitle(), tb.getcolAuthor(),
									tb.getcolPublisher(), tb.getcolPublisher_date(),
									tb.getcolPages(), tb.getcolLanguage(),
									tb.getcolUrl_details(), tb.getcolUrl_download(), tb.getcolUrl_read_online(),
									tb.getcolCover(), tb.getcolThumbnail(), tb.getcolRating(),
									tb.getcolNum_comments(), null, null, tb.getcolCoverBitMap());
							booksMost.add(bookMost);
						} else {
							i = mtl.size();
							existBooksMost = false;
						}						
					}
	    	} else {	    		
	    		existBooksMost = false;
	    	}
	    	
	    	if (existBooksMost) {
	    		BooksMost = new BooksHome(booksMost);
	    	} else {
	    		BooksMost = new BooksHome(OLC.getBooks(criteriaMost));
	    		if (BooksMost.getcolBooks() != null){
	    			saveBBDD(BooksMost, "most");
	    		}
	    	}
	    	
	    	//Return the lists
	    	List<BooksHome> listBooks = new ArrayList<BooksHome>();
	    	listBooks.add(BooksLast);
	    	listBooks.add(BooksMost);
	    	
	    	return listBooks;   
	    }    
	}
	
    public void saveBBDD(BooksHome books, String criteria) throws MalformedURLException, IOException{
    	
    	//get Date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
		String date = sdf.format(new Date()).toString();	  
		
		//get list of books
    	List<Book> lb = books.getcolBooks();
    	
		//Save to List
		DAO.deleteListReg(criteria);
		
    	for (int i = 0;i<lb.size();i++) {
    		TableList tl = new TableList(criteria, i,lb.get(i).getcolID(), date);
    		DAO.insertListReg(tl);
    	}
    	
    	//Save Books
    	for (int i = 0;i<lb.size();i++) {
    		Book book = lb.get(i);
    		
    		//if change then update
    		TableBook tbOld = DAO.selectBookReg(book.getcolID());
    		
    		if (tbOld == null){
    			
	        	//Process of Cover bitmap
	        	//Bitmap OLCoverBitmap = drawableFromUrl(book.getcolThumbnail());
	        	
	        	//Process insert
	    		TableBook tb = new TableBook(book.getcolID(), book.getcolTitle(),
	    				book.getcolAuthor(), book.getcolPublisher(), book.getcolPublisher_date(),
	    				book.getcolPages(), book.getcolLanguage(), 
	    				book.getcolUrl_details(), book.getcolUrl_download(), book.getcolUrl_read_online(),
	    				book.getcolCover(), book.getcolThumbnail(), book.getcolRating(),
	    				book.getcolNum_comments(), null, null, date);
	    		DAO.insertBookReg(tb);
    			
    		} else if
    			(!tbOld.getcolTitle().equals(book.getcolTitle()) ||
    			!tbOld.getcolAuthor().equals(book.getcolAuthor()) ||
    			!tbOld.getcolPublisher().equals(book.getcolPublisher()) ||
    			!tbOld.getcolPublisher_date().equals(book.getcolPublisher_date()) ||
    			!tbOld.getcolPages().equals(book.getcolPages()) ||
    			!tbOld.getcolLanguage().equals(book.getcolLanguage()) ||
    			!tbOld.getcolUrl_details().equals(book.getcolUrl_details()) ||
    			!tbOld.getcolUrl_download().equals(book.getcolUrl_download()) ||
    			!tbOld.getcolUrl_read_online().equals(book.getcolUrl_read_online()) ||
    			!tbOld.getcolCover().equals(book.getcolCover()) ||
    			!tbOld.getcolThumbnail().equals(book.getcolThumbnail()) ||
    			!tbOld.getcolRating().equals(book.getcolRating()) ||
    			!tbOld.getcolNum_comments().equals(book.getcolNum_comments())) {
    				    			
		        	//Process of Cover bitmap
		        	//Bitmap OLCoverBitmap = drawableFromUrl(book.getcolThumbnail());
		        	
		        	//Process update
		    		TableBook tb = new TableBook(book.getcolID(), book.getcolTitle(),
		    				book.getcolAuthor(), book.getcolPublisher(), book.getcolPublisher_date(),
		    				book.getcolPages(), book.getcolLanguage(), 
		    				book.getcolUrl_details(), book.getcolUrl_download(), book.getcolUrl_read_online(),
		    				book.getcolCover(), book.getcolThumbnail(), book.getcolRating(),
		    				book.getcolNum_comments(), null, tbOld.getcolCoverBitMap(), date);
		    		DAO.updateBookReg(tb);
    		}
    	}  	
    }
    
	//Return bitmap from url
	public Bitmap drawableFromUrl(String url) throws java.net.MalformedURLException, java.io.IOException {
	    Bitmap x;

	    HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
	    connection.setRequestProperty("User-agent","Mozilla/4.0");

	    connection.connect();
	    InputStream input = connection.getInputStream();

	    x = BitmapFactory.decodeStream(input);
	    return x;
	}	 
}
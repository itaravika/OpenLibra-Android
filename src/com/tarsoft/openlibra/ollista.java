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
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.thira.examples.actionbar.widget.ActionBar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.Toast;

public class ollista extends Activity {

	
	private ProgressDialog pd;
	
	private static List<Book> listaLast = new ArrayList<Book>();
	private static List<Book> listaMost = new ArrayList<Book>();
	
	private OpenLibraClient OLC = new OpenLibraClient();
	private Criteria criteriaLast = new Criteria(99,"",2,0,5,1);
	private Criteria criteriaMost = new Criteria(10,"most_viewed",0,0,10,0);

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
       
        // prevent the default title-bar from beig displayed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.ollista);
        
        //ActionBar        
        mActionBar = (ActionBar) findViewById(R.id.actionBar);
        mActionBar.setTitle(R.string.app_name);
        
        // set home icon that does nothing when the user clicks on it
        mActionBar.setHomeLogo(R.drawable.ic_title_home_desactive);
        
        // sets an action icon that displays a Toast upon clicking
        mActionBar.addActionIcon(R.drawable.ic_search,
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

        gallery.setOnItemClickListener(new OnItemClickListener() {
            
        	public void onItemClick(AdapterView parent, View v, int position, long id) {
                
        		Toast.makeText(ollista.this, "" + position, Toast.LENGTH_SHORT).show();
            }

        });


        
        if (adapterMost == null) {
            adapterMost = new listaOLArrayAdapter(ollista.this,listaMost);
        }
        
        listerMost.setTextFilterEnabled(true);        
        listerMost.setItemsCanFocus(true);
        listerMost.setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
        
        listerMost.setAdapter(adapterMost);
        
    	//Evaluate internet available
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
		
    	//If internet available
    	if (checkConexion){        
	        //initial load of OpenLibra
	    	new consultaOL().execute();  
    	} else {
    		Context context = getApplicationContext();
    		int duration = Toast.LENGTH_SHORT;
    		Toast toast = Toast.makeText(context, 
    				getApplicationContext().getString(R.string.NoInternet),
    				duration);
    		toast.show();
    	}
	}

//asynchronous task to make the initial search
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
		
		listaOLIconArrayAdapter adapterLast = new listaOLIconArrayAdapter(ollista.this,result.get(0).getcolBooks());
        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(adapterLast);
    	
		listaOLArrayAdapter adapterMost = new listaOLArrayAdapter(ollista.this,result.get(1).getcolBooks());
		listerMost.setAdapter(adapterMost);
		
    	//finish progressDialog
    	//pd.dismiss();
		mActionBar.hideProgressBar();
    }
    
    //Method that returns the list of data from OpenLibra
    public List<BooksHome> postData() throws JSONException, MalformedURLException, IOException {    	  
    	
    	BooksHome BooksLast = new BooksHome(OLC.getBooks(criteriaLast));
    	BooksHome BooksMost = new BooksHome(OLC.getBooks(criteriaMost));
    	
    	List<BooksHome> listBooks = new ArrayList<BooksHome>();
    	listBooks.add(BooksLast);
    	listBooks.add(BooksMost);
    	
    	return listBooks;   
    }
}

}
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

import java.util.List;

import org.json.JSONException;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ollista extends ListActivity {

	
	private ProgressDialog pd;
	private static List<Book> listaL;
	
	private OpenLibraClient OLC = new OpenLibraClient();
	private Criteria criteria = new Criteria(10,"most_voted",0,0);
	
	private String TAG = "OpenLibra";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ollista);     
        
        //**** Manage Events list   ****
        final ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        lv.setItemsCanFocus(false);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
        
        //initial load of OpenLibra
    	new consultaOL().execute();     
	}

//asynchronous task to make the initial search
private class consultaOL extends AsyncTask<String, Void, List<Book>> {
	
    protected void onPreExecute() {
    	//search dial to release the word
        pd = ProgressDialog.show(ollista.this, getApplicationContext().getString(R.string.dialer),
				 getApplicationContext().getString(R.string.dialerDet), true,
                false);
    }

    @Override
    protected List<Book> doInBackground(String... params) {       	
    	List<Book> result = null;
    	
    	try{
        	result = postData();
        	
        	//finish progressDialog
        	pd.dismiss();
    	
		} catch (Exception e) { 
			e.printStackTrace(); 
			Log.d(TAG, "Query error in OpenLibra (doinBackground): "+ e.toString());
		}
		
    	//get result
    	return result;
    }
    

    @Override
    protected void onPostExecute(List<Book> result) {
    	ollista.listaL = result;
			
		listaOLArrayAdapter adapter = new listaOLArrayAdapter(ollista.this,listaL);
		setListAdapter(adapter);
    }
    
    //Method that returns the list of data from OpenLibra
    public List<Book> postData() throws JSONException {    	  
    	return OLC.getBooks(criteria);    	
    }
}

}
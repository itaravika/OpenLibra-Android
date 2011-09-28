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
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class OpenLibraClient{
	
	private Criteria criteria;
	
	private String TAG = "OpenLibra";
	
	public List<Book> getBooks(Criteria criteria) throws JSONException, MalformedURLException, IOException{
		
		this.criteria = criteria;
		
		HttpClient httpClient = new DefaultHttpClient();  
		
		String getURL = getURLOpenLibra();
		
		Log.v(TAG, "URL: " + getURL);
    	
    	URI uri;
    	String data = null;
    	try {
    		uri = new URI(getURL);
    		HttpGet method = new HttpGet(uri);
    		HttpResponse response = httpClient.execute(method);
    		
    		HttpEntity resEntity = response.getEntity();
    		
            //if exists, get it
            if (resEntity != null) {
            	data = EntityUtils.toString(resEntity, HTTP.UTF_8);
            }

    	} catch (Exception e) {
    		e.printStackTrace();
    		Log.v(TAG, "Error: " + e.getMessage() + " - " + e.getLocalizedMessage());
    	} 
    	
    	if (data != null) {
    		//Delete initial "(" and final ");"
    		data = data.substring(1, data.length() - 2);
    	
    		return parseData(data); 
    	} else {
    		return null;
    	}
    		
	}
	
	
    public List<Book> parseData(String jsonData) throws JSONException, MalformedURLException, IOException {

    	//JSON with all data
    	JSONArray OLJson = new JSONArray(jsonData);
    	//JSON for subcategories (Categorie and Tag)
    	JSONArray OLJsonCategorie;
    	JSONArray OLJsonTag;
    	
    	//List of Books
    	List<Book> OLLista = new ArrayList<Book>();
    	//List of Categories
    	List<Categorie> OLCategories = new ArrayList<Categorie>();
    	//List of Tags
    	List<Tag> OLTags = new ArrayList<Tag>();
    	
    	//General Process
        for (int i = 0;i < OLJson.length();i++) {
        	//Process of categories
        	OLJsonCategorie = new JSONArray(OLJson.getJSONObject(i).getString("categories"));
        	for (int j = 0;j < OLJsonCategorie.length();j++) {
        		Categorie categorie = new Categorie(
        				OLJsonCategorie.getJSONObject(j).getString("category_id"),
        				OLJsonCategorie.getJSONObject(j).getString("name"),
        				OLJsonCategorie.getJSONObject(j).getString("nicename")
        				);
        		OLCategories.add(categorie);
        	}
        	
        	//Process of tags
        	OLJsonTag = new JSONArray(OLJson.getJSONObject(i).getString("tags"));
        	for (int j = 0;j < OLJsonTag.length();j++) {
        		Tag tag = new Tag(
        				OLJsonTag.getJSONObject(j).getString("tag_id"),
        				OLJsonTag.getJSONObject(j).getString("name"),
        				OLJsonTag.getJSONObject(j).getString("nicename")
        				);
        		OLTags.add(tag);
        	}
        	
        	//Process of Cover bitmap
        	Bitmap OLCoverBitmap;
        	if (criteria.getcodCoverBitMap() == 1) {
        		OLCoverBitmap = drawableFromUrl(OLJson.getJSONObject(i).getString("thumbnail"));
        	} else {
        		OLCoverBitmap = null;
        	}
        		
        	//Add data for new Book
            Book OLLibro = new Book(
            		OLJson.getJSONObject(i).getString("title"),
            		OLJson.getJSONObject(i).getString("author"),
            		OLJson.getJSONObject(i).getString("publisher"),
            		OLJson.getJSONObject(i).getString("publisher_date"),
					OLJson.getJSONObject(i).getString("pages"),
            		OLJson.getJSONObject(i).getString("language"),
            		OLJson.getJSONObject(i).getString("url_details"),
            		OLJson.getJSONObject(i).getString("url_download"),
					OLJson.getJSONObject(i).getString("url_read_online"),
            		OLJson.getJSONObject(i).getString("cover"),
            		OLJson.getJSONObject(i).getString("thumbnail"),
					OLJson.getJSONObject(i).getString("rating"),
					OLJson.getJSONObject(i).getString("num_comments"),
					OLCategories,
					OLTags,
					OLCoverBitmap
            		);
            OLLista.add(OLLibro);
        }
        return OLLista;
    }

	public String getURLOpenLibra() {

    	String URL="http://openlibra.com/api/v1/get/?";

		//Add criteria search
		if (criteria.getcodField() == 0){
			URL = URL + "id=" + criteria.getvalField();
		} else if (criteria.getcodField() == 1){
			URL = URL + "book_title=" + criteria.getvalField();
		} else if (criteria.getcodField() == 2){
			URL = URL + "book_author=" + criteria.getvalField();
		} else if (criteria.getcodField() == 3){
			URL = URL + "publisher=" + criteria.getvalField();
		} else if (criteria.getcodField() == 4){
			URL = URL + "publisher_date=" + criteria.getvalField();
		} else if (criteria.getcodField() == 5){
			URL = URL + "lang=" + criteria.getvalField();
		} else if (criteria.getcodField() == 6){
			URL = URL + "keyword=" + criteria.getvalField();
		} else if (criteria.getcodField() == 7){
			URL = URL + "category=" + criteria.getvalField();
		} else if (criteria.getcodField() == 8){
			URL = URL + "category_id=" + criteria.getvalField();
		} else if (criteria.getcodField() == 9){
			URL = URL + "subcategory=" + criteria.getvalField();
		} else if (criteria.getcodField() == 10){
			URL = URL + "criteria=" + criteria.getvalField();
		}

		//Add order depending on Field exits or not
		if (criteria.getcodField() == 99){
			//Add order
			if (criteria.getcodOrder() == 0){
				URL = URL + "order=a_z";
			} else if (criteria.getcodOrder() == 1){
				URL = URL + "order=z_a";
			} else if (criteria.getcodOrder() == 2){
				URL = URL + "order=newest";
			} else if (criteria.getcodOrder() == 3){
				URL = URL + "order=oldest";
			}
		} else {
			//Add order
			if (criteria.getcodOrder() == 0){
				URL = URL + "&order=a_z";
			} else if (criteria.getcodOrder() == 1){
				URL = URL + "&order=z_a";
			} else if (criteria.getcodOrder() == 2){
				URL = URL + "&order=newest";
			} else if (criteria.getcodOrder() == 3){
				URL = URL + "&order=oldest";
			}
		}
		
		//Add date
		if (criteria.getcodSince() == 1){
			URL = URL + "&since=today";
		} else if (criteria.getcodSince() == 2){
			URL = URL + "&since=last_week";
		} else if (criteria.getcodSince() == 3){
			URL = URL + "&since=last_month";
		} else if (criteria.getcodSince() == 4){
			URL = URL + "&since=last_year";
		}
		
		//Add max regs number
		URL = URL + "&num_items=" + Integer.toString(criteria.getvalMaxItems());
		
        return URL;
    }
	
	//Return bitmap from url
	private Bitmap drawableFromUrl(String url) throws java.net.MalformedURLException, java.io.IOException {
	    Bitmap x;

	    HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
	    connection.setRequestProperty("User-agent","Mozilla/4.0");

	    connection.connect();
	    InputStream input = connection.getInputStream();

	    x = BitmapFactory.decodeStream(input);
	    return x;
	}
}

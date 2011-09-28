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

import com.thira.examples.actionbar.widget.ActionBar;

import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class config extends PreferenceActivity {
    
	String TAG = "OpenLibra";
	
	private ListPreference carruselTipo;
	private ListPreference carruselNum;
	private ListPreference listaTipo;
	private ListPreference listaNum;
	private ListPreference diasAct;
	private Preference borrarBBDD;
	private Preference cargarBBDD;
	
	private ActionBar mActionBar;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        
        // prevent the default title-bar from being displayed
        addPreferencesFromResource(R.layout.config);
        
        try {
	        //instanciar los objetos de pantalla
        	carruselTipo = (ListPreference) findPreference("carruselTipo");
        	carruselNum = (ListPreference) findPreference("carruselNum");
        	listaTipo = (ListPreference) findPreference("listaTipo");
        	listaNum = (ListPreference) findPreference("listaNum");
        	diasAct = (ListPreference) findPreference("diasAct");
        	borrarBBDD = (ListPreference) findPreference("borrarBBDD");
        	cargarBBDD = (ListPreference) findPreference("cargarBBDD");
    	       
        	carruselTipo.setOnPreferenceChangeListener(new
		    	Preference.OnPreferenceChangeListener() {
	            	public boolean onPreferenceChange(Preference preference, Object newValue) {	            	
		            	String valcarruselTipo = (String) newValue;
		            	SharedPreferences.Editor editor = preference.getEditor();
		            	editor.putString("carruselTipo", valcarruselTipo);
		            	editor.commit();            	
		                return true;
	            	}
        		});
        	    
        	carruselNum.setOnPreferenceChangeListener(new
		    	Preference.OnPreferenceChangeListener() {
	            	public boolean onPreferenceChange(Preference preference, Object newValue) {	            	
		            	String valcarruselNum = (String) newValue;
		            	SharedPreferences.Editor editor = preference.getEditor();
		            	editor.putString("carruselNum", valcarruselNum);
		            	editor.commit();            	
		                return true;
	            	}
        		});
        	   
        	listaTipo.setOnPreferenceChangeListener(new
		    	Preference.OnPreferenceChangeListener() {
	            	public boolean onPreferenceChange(Preference preference, Object newValue) {	            	
		            	String vallistaTipo = (String) newValue;
		            	SharedPreferences.Editor editor = preference.getEditor();
		            	editor.putString("listaTipo", vallistaTipo);
		            	editor.commit();            	
		                return true;
	            	}
        		});
        		    
        	listaNum.setOnPreferenceChangeListener(new
		    	Preference.OnPreferenceChangeListener() {
	            	public boolean onPreferenceChange(Preference preference, Object newValue) {	            	
		            	String vallistaNum = (String) newValue;
		            	SharedPreferences.Editor editor = preference.getEditor();
		            	editor.putString("listaNum", vallistaNum);
		            	editor.commit();            	
		                return true;
	            	}
        		});
        	   
        	diasAct.setOnPreferenceChangeListener(new
		    	Preference.OnPreferenceChangeListener() {
	            	public boolean onPreferenceChange(Preference preference, Object newValue) {	            	
		            	String valdiasAct = (String) newValue;
		            	SharedPreferences.Editor editor = preference.getEditor();
		            	editor.putString("diasAct", valdiasAct);
		            	editor.commit();            	
		                return true;
	            	}
        		});
        	
        	borrarBBDD.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                	Toast.makeText(config.this,
	                        "Clicked on an delete BBDD button",
	                        Toast.LENGTH_SHORT).show();
                    
                    return true;
                }
            });
        	
        	cargarBBDD.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                	Toast.makeText(config.this,
	                        "Clicked on an add BBDD button",
	                        Toast.LENGTH_SHORT).show();
                    
                    return true;
                }
            });		    	

		    
		} catch (Exception e) { 
			e.printStackTrace(); 
			Log.d(TAG, "Error when change config preferences: "+ e.toString()); 
		}	
    }
}
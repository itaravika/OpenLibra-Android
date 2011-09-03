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

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listaOLArrayAdapter extends ArrayAdapter<Book> {

	private final Activity context;
	
	private List<Book> list;
	
	private final String TAG = "OpenLibra";

	public listaOLArrayAdapter(Activity context, List<Book> list) {
		super(context, R.layout.ollistarow, list);		

		this.context = context;
		this.list = list;
	}	
	 
	static class ViewHolder {
		protected ImageView iconLibro;
		protected ImageView iconLang;
		protected TextView tituloLibro;
		protected TextView autorLibro;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		
		try {
		
			if (convertView == null) {
				
				LayoutInflater inflator = context.getLayoutInflater();
				view = inflator.inflate(R.layout.ollistarow, null);
				final ViewHolder viewHolder = new ViewHolder();
				
				viewHolder.iconLibro = (ImageView) view.findViewById(R.id.iconLibro);
				viewHolder.iconLang = (ImageView) view.findViewById(R.id.iconLang);
				viewHolder.tituloLibro = (TextView) view.findViewById(R.id.tituloLibro);
				viewHolder.autorLibro = (TextView) view.findViewById(R.id.autorLibro);

				view.setTag(viewHolder);
			} else {
				view = convertView;
			}
			
			ViewHolder holder = (ViewHolder) view.getTag();;
			
			holder.iconLibro.setBackgroundResource(R.drawable.foldergrey);
			
			//Get icon from language
			if (list.get(position).getcolLanguage().equals("spanish")) {
				holder.iconLang.setBackgroundResource(R.drawable.iconlangspa);
			} else if (list.get(position).getcolLanguage().equals("english")) {
				holder.iconLang.setBackgroundResource(R.drawable.iconlangeng);
			} else {
				holder.iconLang.setBackgroundResource(R.drawable.iconlibrodefecto);
			}
			
			//Author and book title
			holder.tituloLibro.setText(list.get(position).getcolTitle());
			holder.autorLibro.setText(list.get(position).getcolAuthor());
		
		} catch (Exception e) { 
			e.printStackTrace(); 
			Log.d(TAG, "Failed to get view listaOLArrayAdapter "+ e.toString()); 
		}
		return view;
	}	
	
}


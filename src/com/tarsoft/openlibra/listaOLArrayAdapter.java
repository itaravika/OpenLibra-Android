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
	
	public ImageManager imageManager;
	
	private final String TAG = "OpenLibra";

	public listaOLArrayAdapter(Activity context, List<Book> list) {
		super(context, R.layout.ollistarow, list);		

		this.context = context;
		this.list = list;		
		
		imageManager = 
			new ImageManager(context.getApplicationContext());
	}	
	 
	static class ViewHolder {
		protected ImageView iconLibro;
		protected TextView tituloLibro;
		protected TextView autorLibro;
		
		protected ImageView iconLang;
		protected ImageView iconStar1;
		protected ImageView iconStar2;
		protected ImageView iconStar3;
		protected ImageView iconStar4;
		protected ImageView iconStar5;
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
				viewHolder.tituloLibro = (TextView) view.findViewById(R.id.tituloLibro);
				viewHolder.autorLibro = (TextView) view.findViewById(R.id.autorLibro);

				viewHolder.iconLang = (ImageView) view.findViewById(R.id.iconlang);
				viewHolder.iconStar1 = (ImageView) view.findViewById(R.id.iconstar1);
				viewHolder.iconStar2 = (ImageView) view.findViewById(R.id.iconstar2);
				viewHolder.iconStar3 = (ImageView) view.findViewById(R.id.iconstar3);
				viewHolder.iconStar4 = (ImageView) view.findViewById(R.id.iconstar4);
				viewHolder.iconStar5 = (ImageView) view.findViewById(R.id.iconstar5);
				
				view.setTag(viewHolder);
			} else {
				view = convertView;
			}
			
			ViewHolder holder = (ViewHolder) view.getTag();;
			
			//Icon from url
			holder.iconLibro.setTag(list.get(position).getcolThumbnail());
			imageManager.displayImage(list.get(position).getcolThumbnail(), context, holder.iconLibro);				
		
			//Icon from bitmap
			//holder.iconLibro.setImageBitmap(list.get(position).getcolCoverBitMap());		
			
			//Author and book title
			holder.tituloLibro.setText(list.get(position).getcolTitle());
			holder.autorLibro.setText(list.get(position).getcolAuthor());
			
			//Language 
			if (list.get(position).getcolLanguage().equals("spanish")){
				holder.iconLang.setBackgroundResource(R.drawable.iconlangspa);
			} else {
				holder.iconLang.setBackgroundResource(R.drawable.iconlangeng);
			}
			
			//Rating
			
			if (Float.parseFloat(list.get(position).getcolRating()) < 0.6) {
				holder.iconStar1.setBackgroundResource(R.drawable.nostar);
				holder.iconStar2.setBackgroundResource(R.drawable.nostar);
				holder.iconStar3.setBackgroundResource(R.drawable.nostar);
				holder.iconStar4.setBackgroundResource(R.drawable.nostar);
				holder.iconStar5.setBackgroundResource(R.drawable.nostar);
			} else 	if (Float.parseFloat(list.get(position).getcolRating()) >= 0.6 &&
					Float.parseFloat(list.get(position).getcolRating()) < 1.6) {
				holder.iconStar1.setBackgroundResource(R.drawable.star);
				holder.iconStar2.setBackgroundResource(R.drawable.nostar);
				holder.iconStar3.setBackgroundResource(R.drawable.nostar);
				holder.iconStar4.setBackgroundResource(R.drawable.nostar);
				holder.iconStar5.setBackgroundResource(R.drawable.nostar);
			} else 	if (Float.parseFloat(list.get(position).getcolRating()) >= 1.6 &&
					Float.parseFloat(list.get(position).getcolRating()) < 2.6) {
				holder.iconStar1.setBackgroundResource(R.drawable.star);
				holder.iconStar2.setBackgroundResource(R.drawable.star);
				holder.iconStar3.setBackgroundResource(R.drawable.nostar);
				holder.iconStar4.setBackgroundResource(R.drawable.nostar);
			} else 	if (Float.parseFloat(list.get(position).getcolRating()) >= 2.6 &&
					Float.parseFloat(list.get(position).getcolRating()) < 3.6) {
				holder.iconStar1.setBackgroundResource(R.drawable.star);
				holder.iconStar2.setBackgroundResource(R.drawable.star);
				holder.iconStar3.setBackgroundResource(R.drawable.star);
				holder.iconStar4.setBackgroundResource(R.drawable.nostar);
				holder.iconStar5.setBackgroundResource(R.drawable.nostar);
			} else 	if (Float.parseFloat(list.get(position).getcolRating()) >= 3.6 &&
					Float.parseFloat(list.get(position).getcolRating()) < 4.6) {
				holder.iconStar1.setBackgroundResource(R.drawable.star);
				holder.iconStar2.setBackgroundResource(R.drawable.star);
				holder.iconStar3.setBackgroundResource(R.drawable.star);
				holder.iconStar4.setBackgroundResource(R.drawable.star);
				holder.iconStar5.setBackgroundResource(R.drawable.nostar);
			} else {
				holder.iconStar1.setBackgroundResource(R.drawable.star);
				holder.iconStar2.setBackgroundResource(R.drawable.star);
				holder.iconStar3.setBackgroundResource(R.drawable.star);
				holder.iconStar4.setBackgroundResource(R.drawable.star);
				holder.iconStar5.setBackgroundResource(R.drawable.star);
			}
		
		} catch (Exception e) { 
			e.printStackTrace(); 
			Log.d(TAG, "Failed to get view listaOLArrayAdapter "+ e.toString()); 
		}
		return view;
	}	
	
}


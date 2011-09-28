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
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class listaOLIconArrayAdapter extends BaseAdapter {

	private final Activity context;
	
	int FILL_PARENT = -1;
	int WRAP_CONTENT = -2;
	
	private List<Book> list;
	
	//public ImageManager imageManager;
	
	int OLItemBg;
	
	//private final String TAG = "OpenLibra";

	public listaOLIconArrayAdapter(Activity context, List<Book> list) {
	
		this.context = context;
		this.list = list;		
		
		//imageManager = 
		//	new ImageManager(context.getApplicationContext());
		
		TypedArray typArray = context.obtainStyledAttributes(R.styleable.GalleryTheme);
		OLItemBg = typArray.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);
		typArray.recycle();
	}	
	 
    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ImageView imageView = new ImageView(context);

		//imageManager.displayImage(list.get(position).getcolCover(), context, imageView);		
	
		//Icon from bitmap
		imageView.setImageBitmap(list.get(position).getcolCoverBitMap());
				
        imageView.setLayoutParams(new Gallery.LayoutParams(135, 185));
        //imageView.setLayoutParams(new Gallery.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(OLItemBg);
               
        return imageView;	
	}		
}


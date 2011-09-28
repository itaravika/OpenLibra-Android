package com.tarsoft.openlibra;

import java.util.List;

/*OpenLibra - https://github.com/openlibra/OpenLibra
Copyright (C) 2011 David Rodríguez Alvarez (itaravika) itaravika@gmail.com

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

public class BooksHome {
			
	private List<Book> colBooks;

	
	public BooksHome(List<Book> colBooks) {
		
		this.colBooks = colBooks;
	}
	
	public List<Book> getcolBooks() {
		return colBooks;
	}
 
	public void setcolBooks(List<Book> colBooks) {
		this.colBooks = colBooks;
	}
	
}

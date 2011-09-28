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

public class Tag {
		
	private String colTag_id;
	private String colName;
	private String colNicename;
	
	public Tag (String colTag_id, String colName, String colNiceName) {
		
		this.colTag_id = colTag_id;
		this.colName = colName;
		this.colNicename = colNiceName;

	}
 
	public String getcolTag_id() {
		return colTag_id;
	}
 
	public void setcolTag_id(String colTag_id) {
		this.colTag_id = colTag_id;
	}
	
	public String getcolName() {
		return colName;
	}
 
	public void setcolName(String colName) {
		this.colName = colName;
	}
	
	public String getcolNicename() {
		return colNicename;
	}
 
	public void setcolNicename(String colNicename) {
		this.colNicename = colNicename;
	}
}

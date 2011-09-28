package com.tarsoft.openlibra;

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

public class TableList implements Comparable<Object>{
	
	private long colID=0;
	private String colnameList="";
	private Integer colsecList=0;
	private String colidBook="";
	private String coldateInsert="";
	
	public TableList(String colnameList, int colsecList, String colidBook, String coldateInsert) {
		
		this.colnameList = colnameList;
		this.colsecList = colsecList;
		this.colidBook = colidBook;
		this.coldateInsert = coldateInsert;
		
	}
 
	public TableList(Long colID, String colnameList, int colsecList, String colidBook, String coldateInsert) {
		
		this(colnameList, colsecList, colidBook, coldateInsert);
		this.colID = colID;
	}
 
	public Long getcolID() {
		return colID;
	}
 
	public void setcolID(Long colID) {
		this.colID = colID;
	}
	
	public String getcolnameList() {
		return colnameList;
	}
 
	public void setcolnameList(String colnameList) {
		this.colnameList = colnameList;
	}
	
	public int getcolsecList() {
		return colsecList;
	}
 
	public void setcolsecList(int colsecList) {
		this.colsecList = colsecList;
	}
	
	public String getcolidBook() {
		return colidBook;
	}
 
	public void setcolidBook(String colidBook) {
		this.colidBook = colidBook;
	}
	
	public String getcoldateInsert() {
		return coldateInsert;
	}
	public void setcoldateInsert(String coldateInsert) {
		this.coldateInsert = coldateInsert;
	}
	
	//To sort: Collections.sort(lista);     
	public int compareTo(Object o) { 
        TableList tp = (TableList)o;        

        if(this.colnameList.compareTo(tp.colnameList) == 0) {   
            if(this.colsecList.compareTo(tp.colsecList) == 0) { 
                return this.coldateInsert.compareTo(tp.coldateInsert); 
            } else { 
                return this.colsecList.compareTo(tp.colsecList); 
            } 
        } else {
			int result = this.colnameList.compareTo(tp.colnameList);
			return result;
        }     
    } 
	
}

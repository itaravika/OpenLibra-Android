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

import android.graphics.Bitmap;

public class TableBook implements Comparable<Object>{
	
	private long colID=0;
	private String colidBook;
	private String colTitle;
	private String colAuthor;
	private String colPublisher;
	private String colPublisher_date;
	private String colPages;
	private String colLanguage;
	private String colUrl_details;
	private String colUrl_download;
	private String colUrl_read_online;
	private String colCover;
	private String colThumbnail;
	private String colRating;
	private String colNum_comments;	
	private String colDetails;
	private Bitmap colCoverBitMap;
	private String coldateInsert="";
	
	public TableBook(String colidBook, String colTitle, String colAuthor, String colPublisher,
			String colPublisher_date, String colPages, String colLanguage,
			String colUrl_details, String colUrl_download, String colUrl_read_online,
			String colCover, String colThumbnail, String colRating, String colNum_comments,
			String colDetails, Bitmap colCoverBitMap, String coldateInsert) {
		
		this.colidBook = colidBook;
		this.colTitle = colTitle;
		this.colAuthor = colAuthor;
		this.colPublisher = colPublisher;
		this.colPublisher_date = colPublisher_date;
		this.colPages = colPages;
		this.colLanguage = colLanguage;
		this.colUrl_details = colUrl_details;
		this.colUrl_download = colUrl_download;
		this.colUrl_read_online = colUrl_read_online;
		this.colCover = colCover;
		this.colThumbnail = colThumbnail;
		this.colRating = colRating;
		this.colNum_comments = colNum_comments;
		this.colDetails = colDetails;
		this.colCoverBitMap = colCoverBitMap;
		this.coldateInsert = coldateInsert;		
		
	}
 
	public TableBook(Long colID, String colidBook, String colTitle, String colAuthor, String colPublisher,
			String colPublisher_date, String colPages, String colLanguage,
			String colUrl_details, String colUrl_download, String colUrl_read_online,
			String colCover, String colThumbnail, String colRating, String colNum_comments,
			String colDetails, Bitmap colCoverBitMap, String coldateInsert) {
		
		this(colidBook, colTitle, colAuthor, colPublisher, colPublisher_date, colPages, colLanguage,
			 colUrl_details, colUrl_download, colUrl_read_online,
			 colCover, colThumbnail, colRating, colNum_comments,
			 colDetails, colCoverBitMap, coldateInsert);
		this.colID = colID;
	}
 
	public Long getcolID() {
		return colID;
	}
 
	public void setcolID(Long colID) {
		this.colID = colID;
	}
	
	public String getcolidBook() {
		return colidBook;
	}
 
	public void setcolidBook(String colidBook) {
		this.colidBook = colidBook;
	}
	
	public String getcolTitle() {
		return colTitle;
	}
 
	public void setcolTitle(String colTitle) {
		this.colTitle = colTitle;
	}
	
	public String getcolAuthor() {
		return colAuthor;
	}
 
	public void setcolAuthor(String colAuthor) {
		this.colAuthor = colAuthor;
	}
	
	public String getcolPublisher() {
		return colPublisher;
	}
 
	public void setcolPublisher(String colPublisher) {
		this.colPublisher = colPublisher;
	}
	
	public String getcolPublisher_date() {
		return colPublisher_date;
	}
 
	public void setcolPublisher_date(String colPublisher_date) {
		this.colPublisher_date = colPublisher_date;
	}
	
	public String getcolPages() {
		return colPages;
	}
 
	public void setcolPages(String colPages) {
		this.colPages = colPages;
	}
	
	public String getcolLanguage() {
		return colLanguage;
	}
 
	public void setcolLanguage(String colLanguage) {
		this.colLanguage = colLanguage;
	}
	
	public String getcolUrl_details() {
		return colUrl_details;
	}
 
	public void setcolUrl_details(String colUrl_details) {
		this.colUrl_details = colUrl_details;
	}
	
	public String getcolUrl_download() {
		return colUrl_download;
	}
 
	public void setcolUrl_download(String colUrl_download) {
		this.colUrl_download = colUrl_download;
	}
	
	public String getcolUrl_read_online() {
		return colUrl_read_online;
	}
 
	public void setcolUrl_read_online(String colUrl_read_online) {
		this.colUrl_read_online = colUrl_read_online;
	}
	
	public String getcolCover() {
		return colCover;
	}
 
	public void setcolCover(String colCover) {
		this.colCover = colCover;
	}
	
	public String getcolThumbnail() {
		return colThumbnail;
	}
 
	public void setcolThumbnail(String colThumbnail) {
		this.colThumbnail = colThumbnail;
	}
	
	public String getcolRating() {
		return colRating;
	}
 
	public void setcolRating(String colRating) {
		this.colRating = colRating;
	}
	
	public String getcolNum_comments() {
		return colNum_comments;
	}
 
	public void setcolNum_comments(String colNum_comments) {
		this.colNum_comments = colNum_comments;
	}
	
	public String getcolDetails() {
		return colDetails;
	}
 
	public void setcolDetails(String colDetails) {
		this.colDetails = colDetails;
	}
	
	public Bitmap getcolCoverBitMap() {
		return colCoverBitMap;
	}
 
	public void setcolCoverBitMap(Bitmap colCoverBitMap) {
		this.colCoverBitMap = colCoverBitMap;
	}
	
	public String getcoldateInsert() {
		return coldateInsert;
	}
	public void setcoldateInsert(String coldateInsert) {
		this.coldateInsert = coldateInsert;
	}
	
	
	//To sort: Collections.sort(lista);     
	public int compareTo(Object o) { 
        TableBook tp = (TableBook)o;        

        if(this.colidBook.compareTo(tp.colidBook) == 0) {   
            if(this.colTitle.compareTo(tp.colTitle) == 0) { 
                return this.coldateInsert.compareTo(tp.coldateInsert); 
            } else { 
                return this.colTitle.compareTo(tp.colTitle); 
            } 
        } else {
			int result = this.colidBook.compareTo(tp.colidBook);
			return result;
        }     
    } 
	
}

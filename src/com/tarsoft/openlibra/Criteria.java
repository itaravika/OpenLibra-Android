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

public class Criteria {
	
	/*
		Possible values ​​as a criterion for entry
		
			Field:
				    FieldId = 0,
					FieldTitle = 1,
					FieldAuthor = 2,
					FieldPublisher = 3,
					FieldPublisherDate = 4,
					FieldLang = 5,
					FieldKeyworkd = 6,
					FieldCategory = 7,
					FieldCategoryId = 8,
					FieldSubcategory = 9,
					FieldCriteria = 10
			Order:
					OrderAsc = 0,
					OrderDesc = 1,
					OrderNewest = 2,
					OrderOldest = 3
			Since:
					SinceNone = 0,
					SinceToday = 1,
					SinceLastWeek = 2,
					SinceLastMonth = 3,
					SinceLastYear = 4,
	*/
	
	private int codField=0;
	private String valField="";
	private int codOrder=0;
	private int codSince=0;
	private int valMaxItems=0;
	
	public Criteria(int codField, String valField, int codOrder, 
					int codSince, int valMaxItems) {
		
		this.codField = codField;
		this.valField = valField;
		this.codOrder = codOrder;
		this.codSince = codSince;
		this.valMaxItems = valMaxItems;
	}
	
	public Criteria(int codField, String valField, int codOrder, 
					int codSince) {
		
		this.codField = codField;
		this.valField = valField;
		this.codOrder = codOrder;
		this.codSince = codSince;
		this.valMaxItems = 10;
	}
 
	public int getcodField() {
		return codField;
	}
 
	public void setcodField(int codField) {
		this.codField = codField;
	}
	
	public String getvalField() {
		return valField;
	}
 
	public void setvalField(String valField) {
		this.valField = valField;
	}
	
	public int getcodOrder() {
		return codOrder;
	}
 
	public void setcodOrder(int codOrder) {
		this.codOrder = codOrder;
	}

	public int getcodSince() {
		return codSince;
	}
 
	public void setcodSince(int codSince) {
		this.codSince = codSince;
	}

	public int getvalMaxItems() {
		return valMaxItems;
	}
 
	public void setvalMaxItems(int valMaxItems) {
		this.valMaxItems = valMaxItems;
	}	
	
}

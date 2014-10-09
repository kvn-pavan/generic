package com.ail.creyate.generic_spring.db.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menus")
public class MenusBean implements Serializable, Cloneable {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String category;
	
	private String itemName;
	
	private String value;
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}

	public int getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public String getItemName() {
		return itemName;
	}

	public String getValue() {
		return value;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}

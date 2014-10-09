package com.ail.creyate.generic_spring.db.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "system_vars")
public class SystemvarsBean implements Serializable, Cloneable{
	
	@Id
	@Column(name = "keyvalue", columnDefinition="varchar(255)")
	private String key;
	
	@Column(name = "value", columnDefinition="varchar(50000)")
	private String value;
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}

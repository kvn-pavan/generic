package com.ail.creyate.generic_spring.db.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.ail.creyate.generic_spring.db.beans.keys.TestPK;

@Entity
@Table(name = "test")
@IdClass(TestPK.class)
public class TestBean implements Serializable, Cloneable {

	@Column(name = "id")
	private String id;
	
	@Column(name = "name")
	private String name;
	
	@Id
	@Column(name = "garment_type")
	private String garmentType;
	
	@Id
	@Column(name = "lifestyle")
	private String lifestyle;
	
	@Id
	@Column(name = "collection")
	private String collection;

	@Column(name = "erp_id")
	private String erpId;
	
	@Column(name = "consumption_qty")
	private String consumptionQty;
	
	@Column(name = "consumption_unit")
	private String consumptionUnit;
	
	public String getErpId() {
		return erpId;
	}

	public void setErpId(String erpId) {
		this.erpId = erpId;
	}

	public String getId() {
		return id;
	}

	public String getGarmentType() {
		return garmentType;
	}

	public String getLifestyle() {
		return lifestyle;
	}

	public String getCollection() {
		return collection;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setGarmentType(String garmentType) {
		this.garmentType = garmentType;
	}

	public void setLifestyle(String lifestyle) {
		this.lifestyle = lifestyle;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConsumptionQty() {
		return consumptionQty;
	}

	public String getConsumptionUnit() {
		return consumptionUnit;
	}

	public void setConsumptionQty(String consumptionQty) {
		this.consumptionQty = consumptionQty;
	}

	public void setConsumptionUnit(String consumptionUnit) {
		this.consumptionUnit = consumptionUnit;
	}
	
}

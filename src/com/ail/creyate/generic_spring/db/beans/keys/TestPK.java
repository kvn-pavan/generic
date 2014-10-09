package com.ail.creyate.generic_spring.db.beans.keys;

import java.io.Serializable;

public class TestPK implements Serializable {
	protected String garmentType;
    protected String lifestyle;
    protected String collection;

    public TestPK() {}

    public TestPK(String garmentType, String lifestyle, String collection) {
        this.collection = collection;
        this.lifestyle = lifestyle;
        this.garmentType = garmentType;
    }
}

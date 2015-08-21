package org.bahmni.module.bahmnicore.dao;

import org.bahmni.module.bahmnicore.model.ResultList;

import java.util.List;

public interface PersonAttributeDao {
	
	public ResultList getUnique(String personAttribute, String query);
	public List<Integer> getAttributeTypeIds(String personAttribute);

}

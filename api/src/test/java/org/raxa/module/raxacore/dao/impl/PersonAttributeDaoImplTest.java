package org.raxa.module.raxacore.dao.impl;

import org.junit.Test;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.raxa.module.raxacore.model.ResultList;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class PersonAttributeDaoImplTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	PersonAttributeDaoImpl personAttributeDao;
	
	@Test
	public void shouldRetrieveUniqueCasteList() throws Exception {
		executeDataSet("apiTestData.xml");
		
		ResultList result = personAttributeDao.getUnique("caste", "caste");
		assertEquals(2, result.size());
	}
	
	@Test
	public void shouldRetrieveOnly20Results() throws Exception {
		executeDataSet("apiTestData.xml");
		
		ResultList result = personAttributeDao.getUnique("caste", "test");
		assertTrue(result.size() <= 20);
	}
}

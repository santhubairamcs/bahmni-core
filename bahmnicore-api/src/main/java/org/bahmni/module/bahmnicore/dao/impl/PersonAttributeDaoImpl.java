package org.bahmni.module.bahmnicore.dao.impl;

import org.bahmni.module.bahmnicore.dao.PersonAttributeDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.bahmni.module.bahmnicore.model.ResultList;
import org.openmrs.Obs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonAttributeDaoImpl implements PersonAttributeDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public ResultList getUnique(String personAttribute, String query) {
		SQLQuery sqlQuery = sessionFactory
		        .getCurrentSession()
		        .createSQLQuery(
		            "Select distinct value from person_attribute, person_attribute_type "
		                    + "where person_attribute.person_attribute_type_id = person_attribute_type.person_attribute_type_id "
		                    + "and person_attribute_type.name = :name and lower(person_attribute.value) like :value order by value asc");
		sqlQuery.setParameter("name", personAttribute);
		sqlQuery.setParameter("value", query.toLowerCase() + "%");
		sqlQuery.setMaxResults(20);
		return new ResultList(sqlQuery.list());
	}

	@Override
	public List<Integer> getAttributeTypeIds(String personAttribute) {
		SQLQuery sqlQuery = sessionFactory
				.getCurrentSession()
				.createSQLQuery(
						"select distinct person_attribute_type_id "
						+ " from person_attribute_type where name = :personAttribute ");

		sqlQuery.setParameter("personAttribute", personAttribute);
		return sqlQuery.list();
	}
}

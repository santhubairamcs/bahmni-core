package org.bahmni.module.bahmnicore.service.impl;


import org.bahmni.module.bahmnicore.dao.PersonAttributeDao;
import org.bahmni.module.bahmnicore.model.BahmniPersonAttribute;
import org.bahmni.module.bahmnicore.service.PersonAttributeService;
import org.openmrs.api.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonAttributeServiceImp implements PersonAttributeService {

    private PersonService personService;
    private PersonAttributeDao personAttributeDao;

    @Override
    public BahmniPersonAttribute savePersonAttribute(String personAttributeTypeName, String personAttributeValue) {
        List<Integer> personAttributeIds = personAttributeDao.getAttributeTypeIds(personAttributeTypeName);
        if (personAttributeIds != null && personAttributeIds.size() > 0) {
            return new BahmniPersonAttribute(personAttributeIds.get(0).toString(), personAttributeValue);
        }
        return null;
    }
}

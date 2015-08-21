package org.bahmni.module.bahmnicore.service;


import org.bahmni.module.bahmnicore.model.BahmniPersonAttribute;

public interface PersonAttributeService {
    public BahmniPersonAttribute savePersonAttribute(String personAttributeType, String personAttributeValue);
}

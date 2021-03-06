package org.bahmni.module.bahmnicore.web.v1_0.controller;

import org.bahmni.module.bahmnicore.contract.encounter.data.ConceptData;
import org.bahmni.module.bahmnicore.contract.encounter.response.EncounterConfigResponse;
import org.joda.time.DateTime;
import org.openmrs.*;
import org.openmrs.api.*;
import org.openmrs.module.bahmniemrapi.encountertransaction.command.impl.BahmniVisitAttributeSaveCommandImpl;
import org.openmrs.module.bahmniemrapi.encountertransaction.contract.BahmniEncounterTransaction;
import org.openmrs.module.bahmniemrapi.encountertransaction.contract.BahmniObservation;
import org.openmrs.module.bahmniemrapi.encountertransaction.mapper.BahmniEncounterTransactionMapper;
import org.openmrs.module.bahmniemrapi.encountertransaction.matcher.EncounterSessionMatcher;
import org.openmrs.module.bahmniemrapi.encountertransaction.service.BahmniEncounterTransactionService;
import org.openmrs.module.bahmniemrapi.encountertransaction.utils.DateUtil;
import org.openmrs.module.emrapi.encounter.ActiveEncounterParameters;
import org.openmrs.module.emrapi.encounter.EmrEncounterService;
import org.openmrs.module.emrapi.encounter.EncounterSearchParameters;
import org.openmrs.module.emrapi.encounter.EncounterTransactionMapper;
import org.openmrs.module.emrapi.encounter.domain.EncounterTransaction;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/bahmnicore/bahmniencounter")
public class BahmniEncounterController extends BaseRestController {
    private AdministrationService adminService;
    private VisitService visitService;
    private ConceptService conceptService;
    private EncounterService encounterService;
    private OrderService orderService;
    private EmrEncounterService emrEncounterService;
    private EncounterTransactionMapper encounterTransactionMapper;
    private BahmniEncounterTransactionService bahmniEncounterTransactionService;
    private BahmniEncounterTransactionMapper bahmniEncounterTransactionMapper;

    public BahmniEncounterController() {
    }

    @Autowired
    public BahmniEncounterController(VisitService visitService, ConceptService conceptService,
                                     EncounterService encounterService, OrderService orderService,
                                     EmrEncounterService emrEncounterService, EncounterTransactionMapper encounterTransactionMapper,
                                     BahmniEncounterTransactionService bahmniEncounterTransactionService,
                                     BahmniEncounterTransactionMapper bahmniEncounterTransactionMapper, @Qualifier("adminService") AdministrationService administrationService) {
        this.visitService = visitService;
        this.conceptService = conceptService;
        this.encounterService = encounterService;
        this.orderService = orderService;
        this.emrEncounterService = emrEncounterService;
        this.encounterTransactionMapper = encounterTransactionMapper;
        this.bahmniEncounterTransactionService = bahmniEncounterTransactionService;
        this.bahmniEncounterTransactionMapper = bahmniEncounterTransactionMapper;
        this.adminService = administrationService;

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{uuid}")
    @ResponseBody
    public BahmniEncounterTransaction get(@PathVariable("uuid") String uuid, @RequestParam(value = "includeAll", required = false) Boolean includeAll) {
        EncounterTransaction encounterTransaction = emrEncounterService.getEncounterTransaction(uuid, includeAll);
        return bahmniEncounterTransactionMapper.map(encounterTransaction, includeAll);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/find")
    @ResponseBody
    public BahmniEncounterTransaction find(@RequestBody EncounterSearchParameters encounterSearchParameters) {
        EncounterTransaction encounterTransaction = bahmniEncounterTransactionService.find(encounterSearchParameters);

        if (encounterTransaction != null) {
            return bahmniEncounterTransactionMapper.map(encounterTransaction, encounterSearchParameters.getIncludeAll());
        } else {
            return bahmniEncounterTransactionMapper.map(new EncounterTransaction(), false);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{uuid}")
    @ResponseBody
    public void delete(@PathVariable("uuid") String uuid, @RequestParam(value = "reason", defaultValue = "web service call") String reason){
        BahmniEncounterTransaction bahmniEncounterTransaction = get(uuid,false);
        bahmniEncounterTransaction.setReason(reason);
        bahmniEncounterTransactionService.delete(bahmniEncounterTransaction);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public BahmniEncounterTransaction update(@RequestBody BahmniEncounterTransaction bahmniEncounterTransaction) {
        setUuidsForObservations(bahmniEncounterTransaction.getObservations());
        return bahmniEncounterTransactionService.save(bahmniEncounterTransaction);
    }

    public BahmniEncounterTransaction get(String encounterUuid) {
        Encounter encounter = encounterService.getEncounterByUuid(encounterUuid);
        boolean includeAll = false;
        EncounterTransaction encounterTransaction = encounterTransactionMapper.map(encounter, includeAll);
        return bahmniEncounterTransactionMapper.map(encounterTransaction, includeAll);
    }

    private void setUuidsForObservations(Collection<BahmniObservation> bahmniObservations) {
        for (BahmniObservation bahmniObservation : bahmniObservations) {
            if (org.apache.commons.lang3.StringUtils.isBlank(bahmniObservation.getUuid())) {
                bahmniObservation.setUuid(UUID.randomUUID().toString());
            }
        }
    }
}

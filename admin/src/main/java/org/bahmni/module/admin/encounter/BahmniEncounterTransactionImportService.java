package org.bahmni.module.admin.encounter;

import org.bahmni.module.admin.csv.models.EncounterRow;
import org.bahmni.module.admin.observation.DiagnosisImportService;
import org.bahmni.module.admin.observation.ObservationImportService;
import org.bahmni.module.admin.visit.VisitMatcher;
import org.openmrs.EncounterType;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.EncounterService;
import org.openmrs.module.bahmniemrapi.diagnosis.contract.BahmniDiagnosisRequest;
import org.openmrs.module.bahmniemrapi.encountertransaction.contract.BahmniEncounterTransaction;
import org.openmrs.module.emrapi.encounter.domain.EncounterTransaction;

import java.text.ParseException;
import java.util.List;

public class BahmniEncounterTransactionImportService {

    private EncounterService encounterService;
    private final ObservationImportService observationService;
    private final DiagnosisImportService diagnosisService;
    private VisitMatcher visitMatcher;

    public BahmniEncounterTransactionImportService(EncounterService encounterService, VisitMatcher visitMatcher,
                                                   ObservationImportService observationService, DiagnosisImportService diagnosisService) {
        this.encounterService = encounterService;
        this.visitMatcher = visitMatcher;
        this.observationService = observationService;
        this.diagnosisService = diagnosisService;
    }

    public BahmniEncounterTransaction getBahmniEncounterTransaction(EncounterRow encounterRow, Patient patient) throws ParseException {
        EncounterType requestedEncounterType = encounterService.getEncounterType(encounterRow.encounterType);
        Visit matchingVisit = visitMatcher.getMatchingVisit(patient, encounterRow.visitType, encounterRow.getEncounterDate());
        DuplicateObservationsMatcher duplicateObservationsMatcher = new DuplicateObservationsMatcher(matchingVisit, requestedEncounterType);

        List<EncounterTransaction.Observation> bahmniObservations = observationService.getObservations(encounterRow, duplicateObservationsMatcher);
        List<BahmniDiagnosisRequest> bahmniDiagnosis = diagnosisService.getBahmniDiagnosis(encounterRow, duplicateObservationsMatcher);

        BahmniEncounterTransaction bahmniEncounterTransaction = new BahmniEncounterTransaction();
        bahmniEncounterTransaction.setBahmniDiagnoses(bahmniDiagnosis);
        bahmniEncounterTransaction.setObservations(bahmniObservations);
        bahmniEncounterTransaction.setPatientUuid(patient.getUuid());
        bahmniEncounterTransaction.setEncounterDateTime((encounterRow.getEncounterDate()));
        bahmniEncounterTransaction.setEncounterTypeUuid(requestedEncounterType.getUuid());

        // TODO : Mujir - is this alright? Should we further check the actual visit types as this is a fuzzy search?
        bahmniEncounterTransaction.setVisitTypeUuid(matchingVisit.getVisitType().getUuid());
        bahmniEncounterTransaction.setVisitUuid(matchingVisit.getUuid());

        return bahmniEncounterTransaction;
    }

}
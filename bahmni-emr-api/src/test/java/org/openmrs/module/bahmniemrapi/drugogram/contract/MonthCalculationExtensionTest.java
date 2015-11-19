package org.openmrs.module.bahmniemrapi.drugogram.contract;

import org.junit.Test;
import org.openmrs.module.bahmniemrapi.builder.BahmniObservationBuilder;
import org.openmrs.module.bahmniemrapi.encountertransaction.contract.BahmniObservation;
import org.openmrs.module.bahmniemrapi.pivottable.contract.PivotRow;
import org.openmrs.module.bahmniemrapi.pivottable.contract.PivotTable;
import org.openmrs.module.emrapi.encounter.domain.EncounterTransaction;

import java.util.*;

public class MonthCalculationExtensionTest {

    @Test
    public void testUpdate() throws Exception {
        EncounterTransaction.Concept dateConcept = new EncounterTransaction.Concept("dateUuid", "date");
        BahmniObservation bahmniObservation = new BahmniObservationBuilder().withConcept(dateConcept).build();
        EncounterTransaction.Observation encounterTransactionObservation = new EncounterTransaction.Observation();
        encounterTransactionObservation.setValue(new Date());
        bahmniObservation.setEncounterTransactionObservation(encounterTransactionObservation);
        bahmniObservation.setConcept(dateConcept);

        EncounterTransaction.Concept drugConcept = new EncounterTransaction.Concept("drugUuid", "date");
        BahmniObservation bahmniObservation1 = new BahmniObservationBuilder().withConcept(drugConcept).build();
        EncounterTransaction.Observation encounterTransactionObservation1 = new EncounterTransaction.Observation();
        EncounterTransaction.Drug drug = new EncounterTransaction.Drug();
        encounterTransactionObservation1.setValue(drug);
        drug.setName("drug");
        encounterTransactionObservation1.setValue(drug);
        bahmniObservation1.setEncounterTransactionObservation(encounterTransactionObservation1);
        bahmniObservation1.setValue(addDays(new Date(), 10));
        bahmniObservation1.setConcept(drugConcept);

        EncounterTransaction.Concept concept = new EncounterTransaction.Concept("conceptUuid", "date");
        BahmniObservation bahmniObservation2 = new BahmniObservationBuilder().withConcept(concept).build();
        EncounterTransaction.Observation encounterTransactionObservation2 = new EncounterTransaction.Observation();
        encounterTransactionObservation2.setValue(concept);
        bahmniObservation2.setEncounterTransactionObservation(encounterTransactionObservation2);
        bahmniObservation2.setConcept(concept);
        bahmniObservation2.setValue(addDays(new Date(), 20));

        PivotRow pivotRow1 = new PivotRow();
        pivotRow1.addColumn(bahmniObservation.getConcept().getName(), bahmniObservation);
        pivotRow1.addColumn(bahmniObservation1.getConcept().getName(), bahmniObservation1);
        pivotRow1.addColumn(bahmniObservation2.getConcept().getName(), bahmniObservation1);

        PivotRow pivotRow2 = new PivotRow();
        pivotRow2.addColumn(bahmniObservation1.getConcept().getName(), bahmniObservation1);
        pivotRow2.addColumn(bahmniObservation.getConcept().getName(), bahmniObservation);
        pivotRow2.addColumn(bahmniObservation2.getConcept().getName(), bahmniObservation);

        PivotRow pivotRow3 = new PivotRow();
        pivotRow3.addColumn(bahmniObservation2.getConcept().getName(), bahmniObservation);
        pivotRow3.addColumn(bahmniObservation1.getConcept().getName(), bahmniObservation1);
        pivotRow3.addColumn(bahmniObservation.getConcept().getName(), bahmniObservation);

        PivotTable pivotTable = new PivotTable();
        Set<EncounterTransaction.Concept> headers = new LinkedHashSet<>();

        headers.add(bahmniObservation.getConcept());
        headers.add(bahmniObservation1.getConcept());
        headers.add(bahmniObservation2.getConcept());

        pivotTable.setHeaders(headers);
        pivotTable.setRows(Arrays.asList(pivotRow1, pivotRow2, pivotRow3));

        MonthCalculationExtension extension = new MonthCalculationExtension();
        extension.update(pivotTable);
        System.out.println("done");
    }

    private Date addDays(Date now, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
}

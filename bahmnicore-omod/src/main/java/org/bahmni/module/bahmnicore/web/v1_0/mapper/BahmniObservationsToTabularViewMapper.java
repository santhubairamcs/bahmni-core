package org.bahmni.module.bahmnicore.web.v1_0.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.openmrs.module.bahmniemrapi.encountertransaction.contract.BahmniObservation;
import org.openmrs.module.bahmniemrapi.pivottable.contract.PivotRow;
import org.openmrs.module.bahmniemrapi.pivottable.contract.PivotTable;
import org.openmrs.module.emrapi.encounter.domain.EncounterTransaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class BahmniObservationsToTabularViewMapper {
    public PivotTable constructTable(Set<EncounterTransaction.Concept> conceptNames, Collection<BahmniObservation> bahmniObservations) {
        PivotTable pivotTable = new PivotTable();
        if (bahmniObservations == null) {
            return pivotTable;
        }

        List<PivotRow> rows = new ArrayList<>();

        for (BahmniObservation bahmniObservation : bahmniObservations) {
            rows.add(constructRow(bahmniObservation, conceptNames));
        }

        pivotTable.setRows(rows);
        pivotTable.setHeaders(conceptNames);
        return pivotTable;
    }

    private PivotRow constructRow(BahmniObservation bahmniObservation, Set<EncounterTransaction.Concept> conceptNames) {
        PivotRow row = new PivotRow();
        constructColumns(conceptNames, row, bahmniObservation);
        return row;
    }

    private void constructColumns(Set<EncounterTransaction.Concept> conceptNames, PivotRow row, BahmniObservation observation) {
        if (observation.getConcept().isSet()) {
            if (observation.getConcept().getConceptClass().equals("Concept Details")) {
                addColumn(conceptNames, row, observation);
            }
            for (BahmniObservation bahmniObservation : observation.getGroupMembers()) {
                constructColumns(conceptNames, row, bahmniObservation);
            }
        } else {
            addColumn(conceptNames, row, observation);
        }
    }

    private void addColumn(Set<EncounterTransaction.Concept> conceptNames, PivotRow row, final BahmniObservation observation) {
        Object foundElement = CollectionUtils.find(conceptNames, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                EncounterTransaction.Concept concept = (EncounterTransaction.Concept) o;
                return concept.getUuid().equals(observation.getConcept().getUuid());
            }
        });
        if (foundElement != null) {
            row.addColumn(observation.getConcept().getName(), observation);
        }
    }

}

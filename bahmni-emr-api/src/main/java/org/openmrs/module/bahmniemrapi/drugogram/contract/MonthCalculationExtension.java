package org.openmrs.module.bahmniemrapi.drugogram.contract;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.openmrs.module.bahmniemrapi.encountertransaction.contract.BahmniObservation;
import org.openmrs.module.bahmniemrapi.pivottable.contract.PivotRow;
import org.openmrs.module.bahmniemrapi.pivottable.contract.PivotTable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class MonthCalculationExtension {

    public void update(PivotTable pivotTable) throws ParseException {
        Date startDate = (Date) pivotTable.getRows().get(0).getValue("date").get(0).getValue();

        for (PivotRow pivotRow : pivotTable.getRows()) {
            ArrayList<BahmniObservation> obs = pivotRow.getValue("date");
            Date rowDate = (Date) obs.get(0).getValue();
            Days days = Days.daysBetween(new DateTime(startDate), new DateTime(rowDate));

            String month = String.format("%.1f", days.getDays() / 30.0F);
            pivotRow.setMonth(month);
        }
    }
}

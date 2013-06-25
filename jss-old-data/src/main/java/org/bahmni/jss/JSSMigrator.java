package org.bahmni.jss;

import org.apache.log4j.Logger;
import org.bahmni.datamigration.*;
import org.bahmni.datamigration.session.AllPatientAttributeTypes;
import org.bahmni.jss.registration.AllRegistrations;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;

public class JSSMigrator {
    private final Migrator migrator;
    private final HashMap<String, AllLookupValues> lookupValuesMap;
    private String csvLocation;
    private static Logger logger = Logger.getLogger(JSSMigrator.class);

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException, SQLException {
        if (args.length < 2) {
            logger.error(String.format("Usage %s CSV-File-Location RegistrationCSVFileName", JSSMigrator.class.getName()));
            logPropertyUsage("localhost", "root", "password", "admin", "test");
            System.exit(1);
        }

        String csvLocation = args[0];
        String registrationCSVFileName = args[1];
        int noOfThreads = 20;
        if(args[2] != null)
            noOfThreads = Integer.valueOf(args[2]);
        logger.info(String.format("Using CSVFileLocation=%s; RegistrationFileName=%s", new File(csvLocation).getAbsolutePath(), registrationCSVFileName));

        String openMRSHostName = System.getProperty("openmrs.host.name", "localhost");
        String databaseUserId = System.getProperty("database.user.id", "root");
        String databasePassword = System.getProperty("database.user.password", "password");
        String openmrsUserId = System.getProperty("openmrs.user.id", "admin");
        String openmrsUserPassword = System.getProperty("openmrs.user.password", "test");
        logPropertyUsage(openMRSHostName, databaseUserId, databasePassword, openmrsUserId, openmrsUserPassword);

        OpenMRSRESTConnection openMRSRESTConnection = new OpenMRSRESTConnection(openMRSHostName, openmrsUserId, openmrsUserPassword);
        MasterTehsils masterTehsils = new MasterTehsils(csvLocation, "MasterTehsils.csv");
        AmbiguousTehsils ambiguousTehsils = new AmbiguousTehsils(csvLocation, "AmbiguousTehsils.txt");
        CorrectedTehsils correctedTehsils = new CorrectedTehsils(csvLocation, "CorrectedTehsils.csv");
        AddressService addressService = new AddressService(masterTehsils, ambiguousTehsils, correctedTehsils);

//        Class<Driver> variableToLoadDriver = Driver.class;
//        String url = String.format("jdbc:mysql://%s:3306/openmrs", openMRSHostName);
//        Connection connection = DriverManager.getConnection(url, databaseUserId, databasePassword);

//        try {
        JSSMigrator jssMigrator = new JSSMigrator(csvLocation, "LU_Caste.csv", "LU_District.csv", "LU_State.csv",
                "LU_Class.csv", "LU_Tahsil.csv", openMRSRESTConnection, noOfThreads);
        jssMigrator.migratePatient(registrationCSVFileName, addressService);
//        } finally {
//            connection.close();
//        }
    }

    private static void logPropertyUsage(String openMRSHostName, String databaseUserId, String databaseUserPassword, String openmrsUserId, String openmrsPassword) {
        logger.info(String.format("By default uses following properties: openmrs.host.name=%s; database.user.id=%s; database.user.password=%s; openmrs.user.id=%s; " +
                "openmrs.user.password=%s", openMRSHostName, databaseUserId, databaseUserPassword, openmrsUserId, openmrsPassword));
    }

    public JSSMigrator(String csvLocation, String casteFileName, String districtFileName, String stateFileName, String classFileName, String tahsilFileName,
                       OpenMRSRESTConnection openMRSRESTConnection, int noOfThreads) throws IOException,
            URISyntaxException {
        this.csvLocation = csvLocation;
        AllLookupValues allCastes = new AllLookupValues(csvLocation, casteFileName);
        AllLookupValues allDistricts = new AllLookupValues(csvLocation, districtFileName);
        AllLookupValues allStates = new AllLookupValues(csvLocation, stateFileName);
        AllLookupValues allClasses = new AllLookupValues(csvLocation, classFileName);
        AllLookupValues allTahsils = new AllLookupValues(csvLocation, tahsilFileName);
        lookupValuesMap = new HashMap<String, AllLookupValues>();
        lookupValuesMap.put("Castes", allCastes);
        lookupValuesMap.put("Districts", allDistricts);
        lookupValuesMap.put("States", allStates);
        lookupValuesMap.put("Classes", allClasses);
        lookupValuesMap.put("Tahsils", allTahsils);

        migrator = new Migrator(openMRSRESTConnection,noOfThreads);
    }

    public void migratePatient(String csvFileName, AddressService addressService) throws IOException {
        AllPatientAttributeTypes allPatientAttributeTypes = migrator.getAllPatientAttributeTypes();
        AllRegistrations allRegistrations = new AllRegistrations(csvLocation, csvFileName, allPatientAttributeTypes, lookupValuesMap, addressService);
        try {
            migrator.migratePatient(allRegistrations);
        } finally {
            allRegistrations.done();
        }
    }
}
package solution.testing.example.records.service;

import solution.testing.example.records.dao.DatabaseProperties;
import solution.testing.example.records.pojo.Entry;
import solution.testing.example.records.pojo.ResultEntries;
import solution.testing.example.records.pojo.ResultEntry;

import java.util.List;

public final class MainService {
    private final ServiceDaoDatabaseActionsImpl serviceDaoDatabaseActionsImpl;
    private final XmlService xmlService;

    public MainService() {
        this.serviceDaoDatabaseActionsImpl = new ServiceDaoDatabaseActionsImpl();
        this.xmlService = new XmlService();
    }

    public void run() {
        System.out.println("Create work directory if not exist..." + XmlService.WORK_DIRECTORY);
        xmlService.createWorkDirectoryIfNotExist();
        System.out.println("Work directory " + XmlService.WORK_DIRECTORY + " is created.");
        System.out.println("Update fields in database . " + DatabaseProperties.getInstance().getEntryCount() + " records.");
        serviceDaoDatabaseActionsImpl.updateFields();
        System.out.println("Success update fields in database.");
        System.out.println("Select fields from database.");
        List<Entry> entries = serviceDaoDatabaseActionsImpl.selectFields();
        System.out.println("Create 1.xml file ...");
        xmlService.createFirstXmlFile(entries);
        System.out.println("Transform 1.xml in to 2.xml");
        xmlService.transformFirstXmlToSecondXml();
        System.out.println("Unmarshal 2.xml");
        ResultEntries resultEntries = xmlService.getEntriesFromSecondXml();
        System.out.println("Success.");

        long sum = 0;
        if (resultEntries != null) {
            for (ResultEntry resultEntry : resultEntries.getEntries()) {
                sum += resultEntry.getField();
            }
        }

        System.out.println("\n Summa = " + sum + " \n");

    }

}

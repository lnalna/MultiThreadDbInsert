package solution.testing.example.main;

import solution.testing.example.records.dao.DatabaseProperties;
import solution.testing.example.records.service.MainService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start program!");
        long startTime = System.currentTimeMillis();

        /*if(args.length != 5) {
            System.out.println("\n Error: Program parameters a not valid ... \n");
            System.out.println(
            "\n Required parameters by order: databaseDriver databaseUrl userName password entryCount\n");
            System.out.println(
            "Example:  com.mysql.jdbc.Driver \"jdbc:mysql://localhost:3306/testDB?autoReconnect=true\" test test 1000000");
            return;
        }*/
        /*
        DatabaseProperties databaseProperties = DatabaseProperties.getInstance();
        databaseProperties.setDataBaseDriver(args[0]);
        databaseProperties.setUrl(args[1]);
        databaseProperties.setUserName(args[2]);
        databaseProperties.setPassword(args[3]);
        */
        DatabaseProperties databaseProperties = DatabaseProperties.getInstance();
        databaseProperties.setDataBaseDriver("oracle.jdbc.OracleDriver");
        databaseProperties.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:ora");
        databaseProperties.setUserName("input username");
        databaseProperties.setPassword("input password");
        databaseProperties.setEntryCount(1_000_000);

//        int entryCount = 0;
//        try {
//            entryCount = Integer.parseInt("1000000");
//
//            if (entryCount <= 0) {
//                throw new IllegalArgumentException();
//            }
//        } catch (NumberFormatException e) {
//            System.err.println("\n Entry count must be integer and > 0\n");
//            return;
//        }

        MainService mainService = new MainService();
        mainService.run();

        long totalTime = (System.currentTimeMillis() - startTime) / 1000;

        System.out.println("Program running time : " + totalTime + " seconds");
    }
}

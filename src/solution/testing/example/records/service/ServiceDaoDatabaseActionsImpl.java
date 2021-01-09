package solution.testing.example.records.service;

import solution.testing.example.records.dao.DaoDatabaseActions;
import solution.testing.example.records.dao.DatabaseProperties;
import solution.testing.example.records.exception.DaoException;
import solution.testing.example.records.exception.ServiceException;
import solution.testing.example.records.pojo.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ServiceDaoDatabaseActionsImpl {
    private static final Integer ONE_FINISHED_TASK = 1;
    private final DaoDatabaseActions daoDatabaseActions;

    public ServiceDaoDatabaseActionsImpl() {
        this.daoDatabaseActions = new DaoDatabaseActions();
    }

    public List<Entry> selectFields() {
        List<Entry> entries = null;
        try {
            entries = daoDatabaseActions.selectAllFields();
        } catch (DaoException e) {
            System.err.println("\n Error select fields from database \n");
            throw new ServiceException(e);
        }
        return entries;
    }

    public void updateFields() {
        int entryCount = DatabaseProperties.getInstance().getEntryCount();
        int batchCount = entryCount / DatabaseProperties.BATCH_SIZE;
        int restEntry = entryCount % DatabaseProperties.BATCH_SIZE;

        try {
            daoDatabaseActions.deleteAllFields();
        } catch (DaoException e) {
            System.err.println("\n Can not insert fields in to database, " + e.getMessage() + "\n");
            throw new ServiceException(e);
        }

        Integer finishedTasks = 0;
        ExecutorService tasksPool = Executors.newFixedThreadPool(DatabaseProperties.POOL_SIZE);
        List<Future<Integer>> insertedBatch = new ArrayList<>();

        for (int batch = 0; batch < batchCount; batch++) {
            FieldsInserter inserter =
                    new FieldsInserter(batch * DatabaseProperties.BATCH_SIZE + 1, DatabaseProperties.BATCH_SIZE * (batch + 1));
            Future<Integer> result = tasksPool.submit(inserter);
            insertedBatch.add(result);
        }

        FieldsInserter inserter =
                new FieldsInserter(batchCount * DatabaseProperties.BATCH_SIZE + 1, batchCount * DatabaseProperties.BATCH_SIZE + restEntry);
        Future<Integer> result = tasksPool.submit(inserter);
        insertedBatch.add(result);

        while (finishedTasks < batchCount + 1) {
            finishedTasks = 0;
            for (Future<Integer> future : insertedBatch) {
                try {
                    finishedTasks += future.get();
                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("\n Can not insert fields to database, " + e.getMessage() + "\n");
                    tasksPool.shutdown();
                    throw new ServiceException(e);
                }
            }
        }
        tasksPool.shutdown();
    }

    /**
     * Insert fields in database using multithreads
     */
    private final class FieldsInserter implements Callable<Integer> {
        private final Integer from;
        private final Integer to;

        public FieldsInserter(Integer from, Integer to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public Integer call() {
            try {
                daoDatabaseActions.insertFields(from, to);
            } catch (DaoException e) {
                System.err.println("Error in thread [" + from.toString() + "," + to.toString() + "]");
                throw new ServiceException(e);
            }
            return ONE_FINISHED_TASK;
        }
    }

}

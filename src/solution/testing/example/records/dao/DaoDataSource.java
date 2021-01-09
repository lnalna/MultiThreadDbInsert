package solution.testing.example.records.dao;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public final class DaoDataSource {

    public static DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        DatabaseProperties databaseProperties = DatabaseProperties.getInstance();

        dataSource.setDriverClassName(databaseProperties.getDataBaseDriver());
        dataSource.setJdbcUrl(databaseProperties.getUrl());
        dataSource.setUsername(databaseProperties.getUserName());
        dataSource.setPassword(databaseProperties.getPassword());
        dataSource.setMaximumPoolSize(DatabaseProperties.POOL_SIZE);

        return dataSource;
    }
}

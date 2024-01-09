package pl.Exchange_Rate.Exchange_Rate.database;

import pl.Exchange_Rate.Exchange_Rate.domain.currency.Currency;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyHomePageDto;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyStatsDto;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DataProcessor {
    public static void saveDataToDatabase(List<Currency> currencyStats){
        try(Connection connection = DatabaseConnector.getConnection()){

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String tableName = "currencies_"+dateFormat.format(new Date());


            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);

            if (!resultSet.next()){
                String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (id BIGINT PRIMARY KEY, name VARCHAR(100), currency_code VARCHAR(3), mid DOUBLE)";
                try(PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery)){
                    createTableStatement.execute();
                }

                String insertDataQuery = "INSERT INTO " + tableName + " (id, name, currency_code, mid) VALUES (?,?,?,?)";
                try(PreparedStatement insertDataStatement = connection.prepareStatement(insertDataQuery)){
                    for (Currency currency : currencyStats) {
                        insertDataStatement.setLong(1, currency.getId());
                        insertDataStatement.setString(2, currency.getName());
                        insertDataStatement.setString(3, currency.getCurrencyCode());
                        insertDataStatement.setDouble(4, currency.getMid());
                        insertDataStatement.addBatch();
                    }
                    insertDataStatement.executeBatch();
                }
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

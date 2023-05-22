import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;

import java.util.*;

public class SqlScriptParser {
    public static void main(String[] args) {
        String sqlScript = "CREATE TABLE myTable (\n" +
                "  id INT NOT NULL AUTO_INCREMENT,\n" +
                "  name VARCHAR(255) NOT NULL DEFAULT 'John Doe',\n" +
                "  age INT NOT NULL DEFAULT 18,\n" +
                "  PRIMARY KEY (id)\n" +
                ");";

        try {
            // Parse the SQL script
            Statement statement = CCJSqlParserUtil.parse(sqlScript);

            if (statement instanceof CreateTable) {
                CreateTable createTable = (CreateTable) statement;
                String tableName = createTable.getTable().getName();
                System.out.println("Table: " + tableName);

                List<ColumnDefinition> columnDefinitions = createTable.getColumnDefinitions();

                Map<String, String> columnMap = new LinkedHashMap<>(); // LinkedHashMap to maintain insertion order

                for (ColumnDefinition columnDefinition : columnDefinitions) {
                    String columnName = columnDefinition.getColumnName();
                    String dataType = columnDefinition.getColDataType().getDataType();
                    String defaultValue = columnDefinition.getDefaultExpression() != null
                            ? columnDefinition.getDefaultExpression().toString()
                            : null;

                    columnMap.put(columnName, "Data Type: " + dataType + ", Default Value: " + defaultValue);
                }

                // Store table name and column map in a result map
                Map<String, Map<String, String>> resultMap = new LinkedHashMap<>();
                resultMap.put(tableName, columnMap);

                // Print the result map
                for (Map.Entry<String, Map<String, String>> entry : resultMap.entrySet()) {
                    String table = entry.getKey();
                    Map<String, String> columns = entry.getValue();

                    System.out.println("Table: " + table);
                    for (Map.Entry<String, String> columnEntry : columns.entrySet()) {
                        String column = columnEntry.getKey();
                        String columnInfo = columnEntry.getValue();
                        System.out.println("Column: " + column + ", " + columnInfo);
                    }
                }
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }
}

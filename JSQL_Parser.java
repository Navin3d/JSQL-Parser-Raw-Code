import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlScriptParser {
    public static void main(String[] args) {
        String filePath = "path/to/your/sql/script.sql";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder scriptBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                scriptBuilder.append(line);
                scriptBuilder.append("\n");
            }

            String script = scriptBuilder.toString();

            // Regular expressions to extract table and column information
            Pattern tablePattern = Pattern.compile("CREATE TABLE ([^\\s]+) \\(");
            Pattern columnPattern = Pattern.compile("\\s+([^\\s]+)\\s+([^\\s]+)\\s*(DEFAULT ([^\\s]+))?,");

            Matcher tableMatcher = tablePattern.matcher(script);
            while (tableMatcher.find()) {
                String tableName = tableMatcher.group(1);
                System.out.println("Table: " + tableName);

                int tableEndIndex = script.indexOf(");", tableMatcher.end());
                String tableScript = script.substring(tableMatcher.start(), tableEndIndex);

                Matcher columnMatcher = columnPattern.matcher(tableScript);
                while (columnMatcher.find()) {
                    String columnName = columnMatcher.group(1);
                    String dataType = columnMatcher.group(2);
                    String defaultValue = columnMatcher.group(4);

                    System.out.println("Column: " + columnName + ", Data Type: " + dataType + ", Default Value: " + defaultValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SqlScriptParser {
    public static void main(String[] args) {
        String filePath = "path/to/your/sql/script.sql";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder scriptBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                scriptBuilder.append(line);
                scriptBuilder.append("\n");
            }

            String script = scriptBuilder.toString();

            // Parse the SQL script
            Statement statement = CCJSqlParserUtil.parse(script);

            if (statement instanceof CreateTable) {
                CreateTable createTable = (CreateTable) statement;
                String tableName = createTable.getTable().getName();
                System.out.println("Table: " + tableName);

                List<ColumnDefinition> columnDefinitions = createTable.getColumnDefinitions();
                for (ColumnDefinition columnDefinition : columnDefinitions) {
                    String columnName = columnDefinition.getColumnName();
                    String dataType = columnDefinition.getColDataType().getDataType();
                    String defaultValue = columnDefinition.getDefaultExpression() != null
                            ? columnDefinition.getDefaultExpression().toString()
                            : null;

                    System.out.println("Column: " + columnName + ", Data Type: " + dataType + ", Default Value: " + defaultValue);
                }
            }
        } catch (IOException | JSQLParserException e) {
            e.printStackTrace();
        }
    }
}


String insertRegex = "^\\s*INSERT\\s+INTO\\s+\\w+\\s*\\(.*\\)\\s*VALUES\\s*\\(.*\\);\\s*$";

String tableNameRegex = "(?i)\\bFROM\\b\\s+([\\w.]+)";

String alterRegex = "^\\s*ALTER\\s+TABLE\\s+\\w+\\s+(ADD|DROP|ALTER|RENAME|MODIFY)\\s+.*;\\s*$";

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlParser {
    public static void main(String[] args) {
        String sql = "CREATE TABLE my_table (id INT PRIMARY KEY, name VARCHAR(255), created_at TIMESTAMP);";
        Map<String, String> columns = parseTableColumns(sql);
        System.out.println(columns);
    }

    public static Map<String, String> parseTableColumns(String sql) {
        Map<String, String> columns = new HashMap<>();
        Pattern pattern = Pattern.compile("^\\s*CREATE\\s+TABLE\\s+(\\w+)\\s*\\((.*)\\)\\s*;\\s*$");
        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            String tableName = matcher.group(1);
            String[] columnDefs = matcher.group(2).split(",");
            for (String columnDef : columnDefs) {
                String[] parts = columnDef.trim().split("\\s+");
                String columnName = parts[0];
                String dataType = parts[1];
                columns.put(columnName, dataType);
            }
        }
        return columns;
    }
}


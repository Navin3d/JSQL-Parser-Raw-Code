import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.deparser.SelectDeParser;

import java.util.HashMap;
import java.util.Map;

public class SQLToMapConverter {
    public static void main(String[] args) {
        String sql = "SELECT id, name FROM users WHERE age > 18";

        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            Select select = (Select) statement;
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

            // Extract column names and aliases
            Map<String, String> columnMap = new HashMap<>();
            for (SelectItem selectItem : plainSelect.getSelectItems()) {
                if (selectItem instanceof SelectExpressionItem) {
                    SelectExpressionItem selectExpr = (SelectExpressionItem) selectItem;
                    String columnName = selectExpr.getExpression().toString();
                    String columnAlias = selectExpr.getAlias() != null ? selectExpr.getAlias().getName() : columnName;
                    columnMap.put(columnName, columnAlias);
                }
            }

            // Extract table name
            String tableName = plainSelect.getFromItem().toString();

            // Extract WHERE clause
            String whereClause = plainSelect.getWhere() != null ? plainSelect.getWhere().toString() : "";

            // Store information in a map
            Map<String, Object> sqlMap = new HashMap<>();
            sqlMap.put("tableName", tableName);
            sqlMap.put("columns", columnMap);
            sqlMap.put("whereClause", whereClause);

            System.out.println(sqlMap);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }
}



import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

public class SQLToTreeConverter {
    public static void main(String[] args) {
        String sql = "SELECT * FROM users WHERE age > 18";

        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            StatementDeParser deparser = new StatementDeParser(new StringBuilder());
            statement.accept(deparser);

            String tree = deparser.getBuffer().toString();
            System.out.println(tree);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }
}

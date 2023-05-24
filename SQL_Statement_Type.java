import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLStatementTypeIdentifier {
    public static void main(String[] args) {
        String sqlStatement = "SELECT * FROM customers";

        String statementType = getSQLStatementType(sqlStatement);
        System.out.println("SQL Statement Type: " + statementType);
    }

    public static String getSQLStatementType(String sqlStatement) {
        String statementType = "UNKNOWN";

        // Regex patterns to match different statement types
        Pattern createPattern = Pattern.compile("^\\s*CREATE", Pattern.CASE_INSENSITIVE);
        Pattern alterPattern = Pattern.compile("^\\s*ALTER", Pattern.CASE_INSENSITIVE);
        Pattern insertPattern = Pattern.compile("^\\s*INSERT", Pattern.CASE_INSENSITIVE);
        Pattern selectPattern = Pattern.compile("^\\s*SELECT", Pattern.CASE_INSENSITIVE);
        Pattern dropPattern = Pattern.compile("^\\s*DROP", Pattern.CASE_INSENSITIVE);

        // Match the SQL statement against the patterns
        Matcher createMatcher = createPattern.matcher(sqlStatement);
        Matcher alterMatcher = alterPattern.matcher(sqlStatement);
        Matcher insertMatcher = insertPattern.matcher(sqlStatement);
        Matcher selectMatcher = selectPattern.matcher(sqlStatement);
        Matcher dropMatcher = dropPattern.matcher(sqlStatement);

        if (createMatcher.find()) {
            statementType = "CREATE";
        } else if (alterMatcher.find()) {
            statementType = "ALTER";
        } else if (insertMatcher.find()) {
            statementType = "INSERT";
        } else if (selectMatcher.find()) {
            statementType = "SELECT";
        } else if (dropMatcher.find()) {
            statementType = "DROP";
        }

        return statementType;
    }
}

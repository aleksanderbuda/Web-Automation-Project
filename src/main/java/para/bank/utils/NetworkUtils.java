package para.bank.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NetworkUtils {

    public static List<LogEntry> findLogsByUrlAndMethod(WebDriver driver, String urlSlice, Predicate<String> methodPredicate) {
        Predicate<LogEntry> urlPredicate = logEntry -> getRequestUrl(logEntry) != null && getRequestUrl(logEntry).contains(urlSlice);
        Predicate<LogEntry> httpMethodPredicate = logEntry -> getRequestMethod(logEntry) != null && methodPredicate.test(getRequestMethod(logEntry));
        return findLogsByPredicates(driver, urlPredicate, httpMethodPredicate);
    }

    @SafeVarargs
    private static List<LogEntry> findLogsByPredicates(WebDriver driver, Predicate<LogEntry>... filters) {
        return getPerformanceLogs(driver).stream()
                .filter(logEntry -> Arrays.stream(filters).allMatch(filter -> filter.test(logEntry)))
                .collect(Collectors.toList());
    }

    public static List<LogEntry> getPerformanceLogs(WebDriver driver) {
        return driver.manage().logs().get(LogType.PERFORMANCE).getAll();
    }

    public static String getBodyAsString(LogEntry logEntry) {
        String result = null;
        try {
            result = getPostDataAsString(logEntry);
        } catch (JsonPathException e) {
            // do nothing
        }
        return result;
    }

    private static String getPostDataAsString(LogEntry logEntry) {
        return JsonPath.from(logEntry.getMessage()).getString("message.params.request.postData");
    }

    private static String getRequestUrl(LogEntry logEntry) {
        return JsonPath.from(logEntry.getMessage()).getString("message.params.request.url");
    }

    private static String getRequestMethod(LogEntry logEntry) {
        return JsonPath.from(logEntry.getMessage()).getString("message.params.request.method");
    }
}

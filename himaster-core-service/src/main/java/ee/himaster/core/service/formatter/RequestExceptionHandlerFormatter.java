package ee.himaster.core.service.formatter;


import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestExceptionHandlerFormatter {
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String EXCEPTION = "exception";
    private static final String MESSAGE = "message";
    private static final String PATH = "path";

    private RequestExceptionHandlerFormatter() {
        throw new UnsupportedOperationException();
    }

    public static Map<String, Object> format(final Exception e, final HttpServletRequest httpRequest, final HttpStatus httpStatus) {
        final Map<String, Object> exceptionAttributes = new LinkedHashMap<>();

        exceptionAttributes.put(TIMESTAMP, new Date());
        addHttpStatus(exceptionAttributes, httpStatus);
        addExceptionDetails(exceptionAttributes, e);
        addPath(exceptionAttributes, httpRequest);

        return exceptionAttributes;
    }

    private static void addPath(final Map<String, Object> exceptionAttributes, final HttpServletRequest servletRequest) {
        exceptionAttributes.put(PATH, servletRequest.getServletPath());
    }

    private static void addExceptionDetails(final Map<String, Object> exceptionAttributes, final Exception e) {
        exceptionAttributes.put(EXCEPTION, e.getClass().getName());
        exceptionAttributes.put(MESSAGE, e.getMessage());
    }

    private static void addHttpStatus(final Map<String, Object> exceptionAttributes, final HttpStatus httpStatus) {
        exceptionAttributes.put(STATUS, httpStatus.value());
        exceptionAttributes.put(ERROR, httpStatus.getReasonPhrase());
    }
}

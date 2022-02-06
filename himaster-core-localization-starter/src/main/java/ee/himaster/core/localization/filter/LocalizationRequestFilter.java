package ee.himaster.core.localization.filter;

import ee.himaster.core.localization.service.LocaleService;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocalizationRequestFilter implements Filter {
    private static final String REQUEST_HEADER_LOCALE_CODE_PARAM_NAME = "locale-code";

    private final LocaleService localeService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String localeCode = request.getHeader(REQUEST_HEADER_LOCALE_CODE_PARAM_NAME);
        if (localeCode != null) {
            localeService.initialize(localeCode);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

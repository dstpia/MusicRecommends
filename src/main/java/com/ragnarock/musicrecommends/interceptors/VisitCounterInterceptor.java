package com.ragnarock.musicrecommends.interceptors;

import com.ragnarock.musicrecommends.services.VisitCounterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class VisitCounterInterceptor implements HandlerInterceptor {

    private final VisitCounterService visitCounterService;

    private static final String ATTRIBUTE_ALREADY_COUNTED = "alreadyCountedVisit";

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        if (request.getAttribute(ATTRIBUTE_ALREADY_COUNTED) == null) {
            String requestUri = request.getRequestURI();
            visitCounterService.incrementVisit(requestUri);
            request.setAttribute(ATTRIBUTE_ALREADY_COUNTED, true);
        }
        return true;
    }
}

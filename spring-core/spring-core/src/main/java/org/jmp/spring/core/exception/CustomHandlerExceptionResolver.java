package org.jmp.spring.core.exception;

import static java.lang.String.format;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

@Component
@Slf4j
public class CustomHandlerExceptionResolver extends AbstractHandlerExceptionResolver
{
    private static final String ERROR_VIEW_NAME = "error";
    private static final String ERROR_MODEL_NAME = "error";

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    {
        ModelAndView modelAndView = new ModelAndView(ERROR_VIEW_NAME);
        if (ex instanceof IllegalStateException) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            modelAndView.addObject(ERROR_MODEL_NAME, format("The server got the following error: %s", ex.getMessage()));
        } else {
            modelAndView.addObject(ERROR_MODEL_NAME, ex.getCause());
        }
        return modelAndView;
    }
}

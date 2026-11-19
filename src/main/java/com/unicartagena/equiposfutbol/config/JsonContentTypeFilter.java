package com.unicartagena.equiposfutbol.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JsonContentTypeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String contentType = request.getContentType();
        boolean esFormulario = contentType != null
                && contentType.toLowerCase().startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        if (esFormulario) {
            chain.doFilter(new ForzarJson(request), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private static final class ForzarJson extends HttpServletRequestWrapper {

        private ForzarJson(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getContentType() {
            return MediaType.APPLICATION_JSON_VALUE;
        }

        @Override
        public String getHeader(String name) {
            return "content-type".equalsIgnoreCase(name)
                    ? MediaType.APPLICATION_JSON_VALUE
                    : super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            return "content-type".equalsIgnoreCase(name)
                    ? Collections.enumeration(List.of(MediaType.APPLICATION_JSON_VALUE))
                    : super.getHeaders(name);
        }
    }
}
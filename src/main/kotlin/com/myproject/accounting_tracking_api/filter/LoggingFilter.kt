package com.myproject.accounting_tracking_api.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.nio.charset.StandardCharsets

@Component
@Order(1)
class LoggingFilter : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(LoggingFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestWrapper = ContentCachingRequestWrapper(request)
        val responseWrapper = ContentCachingResponseWrapper(response)

        val startTime = System.currentTimeMillis()

        // Log request before the filter chain
        logRequest(requestWrapper)

        filterChain.doFilter(requestWrapper, responseWrapper)

        val duration = System.currentTimeMillis() - startTime

        // Log response after the filter chain
        logResponse(responseWrapper, duration)

        responseWrapper.copyBodyToResponse()
    }

    private fun logRequest(request: ContentCachingRequestWrapper) {
        val requestBody = String(request.contentAsByteArray, StandardCharsets.UTF_8)
        val headers = request.headerNames.toList().joinToString(", ") { "$it: ${request.getHeader(it)}" }

        log.info(
            "\n[REQUEST] {} {}\n[HEADERS]: {}\n[BODY]: {}",
            request.method,
            request.requestURI,
            headers,
            requestBody
        )
    }

    private fun logResponse(response: ContentCachingResponseWrapper, duration: Long) {
        val responseBody = String(response.contentAsByteArray, StandardCharsets.UTF_8)
        val headers = response.headerNames.toList().joinToString(", ") { "$it: ${response.getHeader(it)}" }

        log.info(
            "\n[RESPONSE] {} in {}ms\n[HEADERS]: {}\n[BODY]: {}",
            response.status,
            duration,
            headers,
            responseBody
        )
    }
}

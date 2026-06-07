package com.beautyhub.beautyhubbackend.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;

// Only handles Web UI controllers
@ControllerAdvice(basePackages =
        "com.beautyhub.beautyhubbackend.controller.web")
public class WebExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(
                    WebExceptionHandler.class);

    // FK constraint — cannot delete
    @ExceptionHandler(
            DataIntegrityViolationException.class)
    public String handleDataIntegrity(
            DataIntegrityViolationException ex,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        log.error("Web data integrity violation: {}",
                ex.getMessage());
        redirectAttributes.addFlashAttribute(
                "errorMessage",
                "❌ Cannot delete — this record " +
                        "is linked to other data. " +
                        "Remove related records first.");
        // Redirect back to the list page
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ?
                referer : "/");
    }

    // Entity not found
    @ExceptionHandler(
            EntityNotFoundException.class)
    public String handleEntityNotFound(
            EntityNotFoundException ex,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        log.error("Web entity not found: {}",
                ex.getMessage());
        redirectAttributes.addFlashAttribute(
                "errorMessage",
                "❌ Record not found: " +
                        ex.getMessage());
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ?
                referer : "/");
    }

    // Our custom service RuntimeExceptions
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(
            RuntimeException ex,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        log.error("Web runtime exception: {}",
                ex.getMessage());
        redirectAttributes.addFlashAttribute(
                "errorMessage",
                "❌ " + ex.getMessage());
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ?
                referer : "/");
    }

    // Fallback for all other web errors
    @ExceptionHandler(Exception.class)
    public String handleException(
            Exception ex,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        log.error("Web unexpected exception: {}",
                ex.getMessage(), ex);
        redirectAttributes.addFlashAttribute(
                "errorMessage",
                "❌ An unexpected error occurred. " +
                        "Please try again.");
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ?
                referer : "/");
    }
}
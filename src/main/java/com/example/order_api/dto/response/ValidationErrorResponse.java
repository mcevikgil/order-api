package com.example.order_api.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse extends ErrorResponse {
    private List<FieldError> fields;

    public ValidationErrorResponse() {
        super();
        this.fields = new ArrayList<>();
    }

    public ValidationErrorResponse(int status, String error, String errorCode, String message, String path) {
        super(status, error, errorCode, message, path);
        this.fields = new ArrayList<>();
    }

    public void addFieldError(String field, String message) {
        this.fields.add(new FieldError(field, message));
    }

    public List<FieldError> getFields() {
        return fields;
    }

    public void setFields(List<FieldError> fields) {
        this.fields = fields;
    }

    // Inner class for field errors
    public static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

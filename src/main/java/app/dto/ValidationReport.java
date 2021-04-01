package app.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationReport {
    private final Map<String, Error> errors = new LinkedHashMap<>();

    public void addError(String fieldName, String fieldValue, String error) {
        errors.put(fieldName, new Error(fieldName, fieldValue, error));
    }

    public boolean hasError(String fieldName) {return errors.containsKey(fieldName);}

    public String getValue(String fieldName) {
        Error error = errors.get(fieldName);
        if (error != null) {
            return error.getFieldValue();
        } else {
            return null;
        }
    }

    public Map<String, Error> getErrors() {
        return errors;
    }

    public boolean isValid() {
        return getErrors().isEmpty();
    }

    public class Error {
        private final String fieldName;
        private final String fieldValue;
        private final String error;

        public Error(String fieldName, String fieldValue, String error) {
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
            this.error = error;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public String getError() {
            return error;
        }
    }
}

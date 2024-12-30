/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;
import java.util.List;
/**
 *
 * @author vothimaihoa
 */


public class ValidationException extends Exception {
    private List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors) {
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder("Validation failed: ");
        for (ValidationError error : errors) {
            message.append("\n").append(error.getField()).append(": ").append(error.getMessage());
        }
        return message.toString();
    }
}


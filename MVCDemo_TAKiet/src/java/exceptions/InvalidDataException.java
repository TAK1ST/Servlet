/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author vothimaihoa
 */
public class InvalidDataException extends RuntimeException{

    public InvalidDataException(String message) {
        super(message);
    }
    
}

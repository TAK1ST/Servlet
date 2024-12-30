package dto;


import exceptions.ValidationException;

/**
 *
 * @author vothimaihoa
 */
public interface DtoBase {
    void validate()  throws ValidationException;
}

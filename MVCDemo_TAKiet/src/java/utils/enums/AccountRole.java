/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.enums;

/**
 *
 * @author vothimaihoa
 */
public enum AccountRole {
    CUSTOMER(0),
    STAFF(1);

    private final int value;

    AccountRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

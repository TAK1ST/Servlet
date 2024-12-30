/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import utils.enums.AccountRole;

/**
 *
 * @author vothimaihoa
 */

public class Account {
    private int id;
    private String username;
    private String password;
    private AccountRole role;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }
    
    
}

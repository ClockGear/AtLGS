package com.dvo.lgs.domain;

import com.dvo.lgs.enums.EmailDisplayOption;
import com.dvo.lgs.enums.NameDisplayOption;
import com.dvo.lgs.enums.Role;

/**
 * Created by Dennis van Opstal on 22-12-2017.
 */

public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private NameDisplayOption nameDisplayOption;
    private EmailDisplayOption emailDisplayOption;

    public User(long id, String firstName, String lastName, String email, Role role, NameDisplayOption nameDisplayOption, EmailDisplayOption emailDisplayOption) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.nameDisplayOption = nameDisplayOption;
        this.emailDisplayOption = emailDisplayOption;
    }

    public User(String firstName, String lastName, String email, Role role, NameDisplayOption nameDisplayOption, EmailDisplayOption emailDisplayOption) {
        this(0,firstName,lastName,email,role,nameDisplayOption,emailDisplayOption);
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public NameDisplayOption getNameDisplayOption() {
        return nameDisplayOption;
    }

    public void setNameDisplayOption(NameDisplayOption nameDisplayOption) {
        this.nameDisplayOption = nameDisplayOption;
    }

    public EmailDisplayOption getEmailDisplayOption() {
        return emailDisplayOption;
    }

    public void setEmailDisplayOption(EmailDisplayOption emailDisplayOption) {
        this.emailDisplayOption = emailDisplayOption;
    }

    public String getDisplayName() {
        switch (nameDisplayOption) {
            case FULL_NAME:
                return firstName + " " + lastName;
            case FIRST_NAME:
                return firstName;
            case LAST_NAME:
                return lastName;
            case FIRST_INITIAL_LAST_NAME:
                return firstName.substring(0,1) + ". " + lastName;
            case FIRST_NAME_LAST_INITIAL:
                return firstName + " " + lastName.substring(0,1) + ".";
            case ANONYMOUS:
                return "????????";
            default:
                return "????????";
        }
    }
}

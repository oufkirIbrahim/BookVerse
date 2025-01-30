package com.BookVerse.BookVerse.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate-account");

    private final String templateName;

    EmailTemplateName(String templateName){
        this.templateName = templateName;
    }
}

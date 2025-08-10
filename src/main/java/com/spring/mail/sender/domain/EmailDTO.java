package com.spring.mail.sender.domain;

public record EmailDTO(String[] toUser, String subject, String message) {


}

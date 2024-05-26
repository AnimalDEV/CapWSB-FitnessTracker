package com.capgemini.wsb.fitnesstracker.mail.api;

public record EmailDto(String from, String toAddress, String subject, String content) {

}

package com.spring.mail.sender.domain;

import org.springframework.web.multipart.MultipartFile;

public record EmailFileDTO(String[] toUser, String subject, String message, MultipartFile file) {
}

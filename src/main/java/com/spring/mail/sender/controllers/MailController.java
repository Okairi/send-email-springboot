package com.spring.mail.sender.controllers;

import com.spring.mail.sender.domain.EmailDTO;
import com.spring.mail.sender.domain.EmailFileDTO;
import com.spring.mail.sender.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/v1")
public class MailController {

    @Autowired
    private IEmailService iEmailService;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO) {

        System.out.println("Mnesaje redibido" + emailDTO);

        iEmailService.sendEmail(emailDTO.toUser(), emailDTO.subject(), emailDTO.message());


        Map<String, String> response = new HashMap<>();

        response.put("status", "Enviado");


        return ResponseEntity.ok(response);


    }


    @PostMapping("/sendMessageFile")
    public ResponseEntity<?>
    receiveRequestEmailWithFile(@ModelAttribute EmailFileDTO emailFileDTO) {


        try {

            String filName = emailFileDTO.file().getName();

            Path path = Paths.get("src/mail/resource/file" + filName);

            Files.createDirectories(path.getParent());
            Files.copy(emailFileDTO.file().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);


            File file = path.toFile();

            iEmailService.sendEmailWithFile(emailFileDTO.toUser(), emailFileDTO.subject(), emailFileDTO.message(), file);


            Map<String, String> response = new HashMap<>();

            response.put("status", "Enviado");
            response.put("archivo", "filename");

            return ResponseEntity.ok(response);


        } catch (Exception e) {


            throw new RuntimeException("Error al enviar el email con el archivo" + e.getMessage());
        }


    }
}

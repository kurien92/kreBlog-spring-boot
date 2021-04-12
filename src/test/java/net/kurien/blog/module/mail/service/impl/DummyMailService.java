package net.kurien.blog.module.mail.service.impl;

import net.kurien.blog.module.mail.service.MailService;

import javax.mail.MessagingException;
import java.util.List;

public class DummyMailService implements MailService {
    @Override
    public void send(String from, String to, String title, String content) throws MessagingException {
        System.out.println("DummyMailService.send(String from, String to, String title, String content)");
    }

    @Override
    public void send(String from, List<String> toList, String title, String content) throws MessagingException {
        System.out.println("DummyMailService.send(String from, List<String> toList, String title, String content)");
    }
}

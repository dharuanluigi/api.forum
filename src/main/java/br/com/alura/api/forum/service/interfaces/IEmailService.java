package br.com.alura.api.forum.service.interfaces;

public interface IEmailService {

    void sendActivationCode(String email, String code);
}

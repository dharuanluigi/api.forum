package br.com.alura.api.forum.dto;

public record ListProfileDTO(String name) {

    public ListProfileDTO(String name) {
        this.name = name.replace("ROLE_", "");
    }
}

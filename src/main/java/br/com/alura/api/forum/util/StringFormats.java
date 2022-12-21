package br.com.alura.api.forum.util;

public class StringFormats {
    public static String removePrefixRoleName(String name) {
        return name.replace("ROLE_", "");
    }
}

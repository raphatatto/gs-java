package br.com.fiap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senhaHash;
    private Timestamp dataCriacao;
}
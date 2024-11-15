package br.com.fiap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EnergiaRenovavel {
    private int id;
    private String tipo;
    private float custoInstalacao;
    private float taxaEconomia;
    private float custoManutencao;
    private float tempoRetorno;
    private String tipoCliente;
}

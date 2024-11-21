package br.com.fiap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Simulacao {
    private int id;
    private int usuarioId;
    private String tipoCliente;
    private String localizacao;
    private float custoMensal;
    private float orcamento;
    private String tipoEnergiaEscolhida;
    private String tipoEnergiaRecomendada;
    private float economiaAnual;
    private float custoInstalacaoRecomendada;
    private float tempoRetornoRecomendado;
    private Timestamp dataRegistro;
}

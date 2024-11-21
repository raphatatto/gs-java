package br.com.fiap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Consumo {
    private int id;
    private int usuarioId;
    private float gastoMes1;
    private float gastoMes2;
    private float gastoMes3;
    private float consumoKwhMes1;
    private float consumoKwhMes2;
    private float consumoKwhMes3;
    private float mediaGasto;
    private float mediaConsumo;
    private Date dataRegistro;
}
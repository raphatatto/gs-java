package br.com.fiap.bo;

import br.com.fiap.dao.EnergiaRenovavelDAO;
import br.com.fiap.exception.CustomException;
import br.com.fiap.model.EnergiaRenovavel;
import br.com.fiap.model.Simulacao;

import java.sql.SQLException;
import java.util.List;

public class SimulacaoBO {

    private final EnergiaRenovavelDAO energiaRenovavelDAO = new EnergiaRenovavelDAO();

    public Simulacao processarSimulacao(Simulacao simulacao) {
        try {
            if (simulacao.getTipoCliente() == null || simulacao.getTipoCliente().trim().isEmpty()) {
                throw new CustomException("O tipo de cliente não pode ser nulo ou vazio.", 400);
            }

            // Lista todas as energias do banco
            List<EnergiaRenovavel> energias = energiaRenovavelDAO.listarTodas();

            System.out.println("Simulação recebida: " + simulacao);
            System.out.println("Energias disponíveis: " + energias);

            if (energias == null || energias.isEmpty()) {
                throw new CustomException("Nenhuma energia renovável cadastrada.", 404);
            }

            EnergiaRenovavel melhorOpcao = null;

            // Verifica o tipo de cliente e seleciona a energia apropriada
            if ("Residencial".equalsIgnoreCase(simulacao.getTipoCliente().trim())) {
                melhorOpcao = energias.stream()
                        .filter(energia -> "Solar".equalsIgnoreCase(energia.getTipo()))
                        .findFirst()
                        .orElse(null);
                System.out.println("Energia escolhida para Residencial: " + melhorOpcao);
            } else if ("Comercial".equalsIgnoreCase(simulacao.getTipoCliente().trim())) {
                melhorOpcao = energias.stream()
                        .filter(energia -> "Eolica".equalsIgnoreCase(energia.getTipo()))
                        .findFirst()
                        .orElse(null);
                System.out.println("Energia escolhida para Comercial: " + melhorOpcao);
            }

            if (melhorOpcao == null) {
                System.out.println("Nenhuma energia renovável encontrada para o tipo de cliente: " + simulacao.getTipoCliente());
                throw new CustomException("Não foi encontrada uma energia renovável adequada para o tipo de cliente: " + simulacao.getTipoCliente(), 404);
            }

            float economiaMensal = simulacao.getCustoMensal() * melhorOpcao.getTaxaEconomia();
            float economiaAnual = economiaMensal * 12;

            simulacao.setTipoEnergiaRecomendada(melhorOpcao.getTipo());
            simulacao.setEconomiaAnual(economiaAnual);
            simulacao.setCustoInstalacaoRecomendada(melhorOpcao.getCustoInstalacao());
            simulacao.setTempoRetornoRecomendado(calcularTempoRetorno(melhorOpcao.getCustoInstalacao(), economiaAnual));

            System.out.println("Simulação processada com sucesso: " + simulacao);

            return simulacao;

        } catch (ClassNotFoundException | SQLException e) {
            throw new CustomException("Erro ao processar a simulação: " + e.getMessage(), 500);
        }
    }

    private float calcularTempoRetorno(float custoInstalacao, float economiaAnual) {
        if (economiaAnual <= 0) {
            return Float.MAX_VALUE; // Valor muito alto para indicar que o retorno não é viável
        }
        return custoInstalacao / economiaAnual;
    }
}
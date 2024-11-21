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

            if (simulacao.getTipoEnergiaEscolhida() == null || simulacao.getTipoEnergiaEscolhida().trim().isEmpty()) {
                throw new CustomException("O tipo de energia escolhida não pode ser nulo ou vazio.", 400);
            }

            // Lista todas as energias do banco
            List<EnergiaRenovavel> energias = energiaRenovavelDAO.listarTodas();

            // Busca a melhor opção com base apenas no tipo de energia escolhida
            EnergiaRenovavel melhorOpcao = energias.stream()
                    .filter(energia -> energia.getTipo().equalsIgnoreCase(simulacao.getTipoEnergiaEscolhida()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException("Tipo de energia selecionada não encontrada.", 404));

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

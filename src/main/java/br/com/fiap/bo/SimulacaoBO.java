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
            List<EnergiaRenovavel> energias = energiaRenovavelDAO.listarTodas();

            if (energias == null || energias.isEmpty()) {
                throw new CustomException("Nenhuma energia renovável cadastrada.", 404);
            }

            EnergiaRenovavel melhorOpcao = null;
            float maiorEconomiaAnual = 0;

            for (EnergiaRenovavel energia : energias) {
                if (energia.getTipoCliente().equalsIgnoreCase(simulacao.getTipoCliente())) {
                    float economiaMensal = simulacao.getCustoMensal() * energia.getTaxaEconomia();
                    float economiaAnual = economiaMensal * 12;

                    if (economiaAnual > maiorEconomiaAnual) {
                        maiorEconomiaAnual = economiaAnual;
                        melhorOpcao = energia;
                    }
                }
            }

            if (melhorOpcao == null) {
                throw new CustomException("Não foi encontrada uma energia renovável adequada para o tipo de cliente.", 404);
            }

            simulacao.setTipoEnergiaRecomendada(melhorOpcao.getTipo());
            simulacao.setEconomiaAnual(maiorEconomiaAnual);
            simulacao.setCustoInstalacaoRecomendada(melhorOpcao.getCustoInstalacao());
            simulacao.setTempoRetornoRecomendado(calcularTempoRetorno(melhorOpcao.getCustoInstalacao(), maiorEconomiaAnual));

            return simulacao;

        } catch (SQLException | ClassNotFoundException e) {
            throw new CustomException("Erro ao processar a simulação: " + e.getMessage(), 500);
        }
    }

    private float calcularTempoRetorno(float custoInstalacao, float economiaAnual) {
        if (economiaAnual == 0) {
            return Float.MAX_VALUE;
        }
        return custoInstalacao / economiaAnual;
    }
}

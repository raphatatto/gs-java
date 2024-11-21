package br.com.fiap.bo;

import br.com.fiap.dao.ConsumoDAO;
import br.com.fiap.model.Consumo;

import java.sql.SQLException;
import java.util.List;

public class ConsumoBO {

    private final ConsumoDAO consumoDAO = new ConsumoDAO();

    // Processa e insere o consumo no banco
    public void processarConsumo(Consumo consumo) throws SQLException, ClassNotFoundException {
        // Validações
        if (consumo.getGastoMes1() <= 0 || consumo.getGastoMes2() <= 0 || consumo.getGastoMes3() <= 0) {
            throw new IllegalArgumentException("Os gastos mensais devem ser maiores que zero.");
        }

        if (consumo.getConsumoKwhMes1() <= 0 || consumo.getConsumoKwhMes2() <= 0 || consumo.getConsumoKwhMes3() <= 0) {
            throw new IllegalArgumentException("Os consumos em kWh devem ser maiores que zero.");
        }

        // Insere no banco
        consumoDAO.inserirConsumo(consumo);
    }

    // Lista consumos por usuário
    public List<Consumo> listarConsumosPorUsuario(int usuarioId) throws SQLException, ClassNotFoundException {
        return consumoDAO.listarConsumosPorUsuario(usuarioId);
    }

    // Calcula a média de consumo e gastos
    public Consumo calcularMediaMensal(Consumo consumo) {
        // Validações
        if (consumo.getGastoMes1() <= 0 || consumo.getGastoMes2() <= 0 || consumo.getGastoMes3() <= 0) {
            throw new IllegalArgumentException("Os gastos mensais devem ser maiores que zero.");
        }

        if (consumo.getConsumoKwhMes1() <= 0 || consumo.getConsumoKwhMes2() <= 0 || consumo.getConsumoKwhMes3() <= 0) {
            throw new IllegalArgumentException("Os consumos em kWh devem ser maiores que zero.");
        }

        // Calcular médias
        float mediaGastos = (consumo.getGastoMes1() + consumo.getGastoMes2() + consumo.getGastoMes3()) / 3;
        float mediaConsumo = (consumo.getConsumoKwhMes1() + consumo.getConsumoKwhMes2() + consumo.getConsumoKwhMes3()) / 3;

        // Arredondar para 2 casas decimais
        mediaGastos = Math.round(mediaGastos * 100.0f) / 100.0f;
        mediaConsumo = Math.round(mediaConsumo * 100.0f) / 100.0f;

        // Atualizar os campos do consumo com as médias calculadas
        consumo.setMediaGasto(mediaGastos);
        consumo.setMediaConsumo(mediaConsumo);

        return consumo;
    }
}

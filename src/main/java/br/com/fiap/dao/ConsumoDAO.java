package br.com.fiap.dao;

import br.com.fiap.model.Consumo;
import br.com.fiap.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsumoDAO {

    public void inserirConsumo(Consumo consumo) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO t_gs_consumo (usuario_id, gasto_mes1, gasto_mes2, gasto_mes3, consumo_kwh_mes1, consumo_kwh_mes2, consumo_kwh_mes3) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consumo.getUsuarioId());
            stmt.setFloat(2, consumo.getGastoMes1());
            stmt.setFloat(3, consumo.getGastoMes2());
            stmt.setFloat(4, consumo.getGastoMes3());
            stmt.setFloat(5, consumo.getConsumoKwhMes1());
            stmt.setFloat(6, consumo.getConsumoKwhMes2());
            stmt.setFloat(7, consumo.getConsumoKwhMes3());
            stmt.executeUpdate();
        }
    }

    public List<Consumo> listarConsumosPorUsuario(int usuarioId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM t_gs_consumo WHERE usuario_id = ?";
        List<Consumo> consumos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Consumo consumo = new Consumo(
                            rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getFloat("gasto_mes1"),
                            rs.getFloat("gasto_mes2"),
                            rs.getFloat("gasto_mes3"),
                            rs.getFloat("consumo_kwh_mes1"),
                            rs.getFloat("consumo_kwh_mes2"),
                            rs.getFloat("consumo_kwh_mes3"),
                            rs.getFloat("media_gasto"),
                            rs.getFloat("media_consumo"),
                            rs.getTimestamp("data_registro")
                    );
                    consumos.add(consumo);
                }
            }
        }

        System.out.println("Consumos encontrados para usuarioId " + usuarioId + ": " + consumos.size());
        return consumos;
    }

}

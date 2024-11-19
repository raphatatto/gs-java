package br.com.fiap.dao;

import br.com.fiap.model.EnergiaRenovavel;
import br.com.fiap.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnergiaRenovavelDAO {

    // GET: Listar todas as energias renováveis
    public List<EnergiaRenovavel> listarTodas() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM t_gs_energia_renovavel";
        List<EnergiaRenovavel> energias = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EnergiaRenovavel energia = new EnergiaRenovavel(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getFloat("custo_instalacao"),
                        rs.getFloat("taxa_economia"),
                        rs.getFloat("custo_manutencao"),
                        rs.getFloat("tempo_retorno"),
                        rs.getString("tipo_cliente")
                );
                System.out.println("Energia encontrada: " + energia);
                energias.add(energia);
            }
        }

        return energias;
    }


    // GET: Buscar energia renovável por ID
    public EnergiaRenovavel buscarPorId(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM t_gs_energia_renovavel WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new EnergiaRenovavel(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getFloat("custo_instalacao"),
                        rs.getFloat("taxa_economia"),
                        rs.getFloat("custo_manutencao"),
                        rs.getFloat("tempo_retorno"),
                        rs.getString("tipo_cliente")
                );
            }
        }
        return null; // Caso não encontre a energia renovável
    }

    // POST: Criar nova energia renovável
    public void cadastrarEnergia(EnergiaRenovavel energia) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO t_gs_energia_renovavel (tipo, custo_instalacao, taxa_economia, custo_manutencao, tempo_retorno, tipo_cliente) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, energia.getTipo());
            stmt.setFloat(2, energia.getCustoInstalacao());
            stmt.setFloat(3, energia.getTaxaEconomia());
            stmt.setFloat(4, energia.getCustoManutencao());
            stmt.setFloat(5, energia.getTempoRetorno());
            stmt.setString(6, energia.getTipoCliente());
            stmt.executeUpdate();
        }
    }

    // PUT: Atualizar energia renovável
    public boolean atualizarEnergia(EnergiaRenovavel energia) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE t_gs_energia_renovavel SET tipo = ?, custo_instalacao = ?, taxa_economia = ?, custo_manutencao = ?, tempo_retorno = ?, tipo_cliente = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, energia.getTipo());
            stmt.setFloat(2, energia.getCustoInstalacao());
            stmt.setFloat(3, energia.getTaxaEconomia());
            stmt.setFloat(4, energia.getCustoManutencao());
            stmt.setFloat(5, energia.getTempoRetorno());
            stmt.setString(6, energia.getTipoCliente());
            stmt.setInt(7, energia.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE: Remover energia renovável
    public boolean deletarEnergia(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM t_gs_energia_renovavel WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}

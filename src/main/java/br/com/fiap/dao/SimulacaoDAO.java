package br.com.fiap.dao;

import br.com.fiap.model.Simulacao;
import br.com.fiap.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimulacaoDAO {

    // GET: Listar todas as simulações
    public List<Simulacao> listarTodas() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM t_gs_simulacao";
        List<Simulacao> simulacoes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                simulacoes.add(new Simulacao(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("tipo_cliente"),
                        rs.getString("localizacao"),
                        rs.getFloat("custo_mensal"),
                        rs.getFloat("orcamento"),
                        rs.getString("tipo_energia_escolhida"),
                        rs.getString("tipo_energia_recomendada"),
                        rs.getFloat("economia_anual"),
                        rs.getFloat("custo_instalacao_recomendada"),
                        rs.getFloat("tempo_retorno_recomendado"),
                        rs.getTimestamp("data_simulacao")
                ));
            }
        }
        return simulacoes;
    }

    // GET: Buscar simulação por ID
    public Simulacao buscarPorId(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM t_gs_simulacao WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Simulacao(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("tipo_cliente"),
                        rs.getString("localizacao"),
                        rs.getFloat("custo_mensal"),
                        rs.getFloat("orcamento"),
                        rs.getString("tipo_energia_escolhida"),
                        rs.getString("tipo_energia_recomendada"),
                        rs.getFloat("economia_anual"),
                        rs.getFloat("custo_instalacao_recomendada"),
                        rs.getFloat("tempo_retorno_recomendado"),
                        rs.getTimestamp("data_simulacao")
                );
            }
        }
        return null; // Caso não encontre a simulação
    }
    // GET: Buscar simulações por usuário
    public List<Simulacao> listarPorUsuarioId(int usuarioId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM t_gs_simulacao WHERE usuario_id = ?";
        List<Simulacao> simulacoes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    simulacoes.add(new Simulacao(
                            rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getString("tipo_cliente"),
                            rs.getString("localizacao"),
                            rs.getFloat("custo_mensal"),
                            rs.getFloat("orcamento"),
                            rs.getString("tipo_energia_escolhida"),
                            rs.getString("tipo_energia_recomendada"),
                            rs.getFloat("economia_anual"),
                            rs.getFloat("custo_instalacao_recomendada"),
                            rs.getFloat("tempo_retorno_recomendado"),
                            rs.getTimestamp("data_simulacao")
                    ));
                }
            }
        }
        System.out.println("Usuário ID recebido: " + usuarioId);
        System.out.println("Simulações encontradas: " + simulacoes.size());
        return simulacoes;
    }

    // POST: Criar nova simulação
    public void cadastrarSimulacao(Simulacao simulacao) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO t_gs_simulacao (usuario_id, tipo_cliente, localizacao, custo_mensal, orcamento, tipo_energia_escolhida, tipo_energia_recomendada, economia_anual, custo_instalacao_recomendada, tempo_retorno_recomendado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, simulacao.getUsuarioId());
            stmt.setString(2, simulacao.getTipoCliente());
            stmt.setString(3, simulacao.getLocalizacao());
            stmt.setFloat(4, simulacao.getCustoMensal());
            stmt.setFloat(5, simulacao.getOrcamento());
            stmt.setString(6, simulacao.getTipoEnergiaEscolhida());
            stmt.setString(7, simulacao.getTipoEnergiaRecomendada());
            stmt.setFloat(8, simulacao.getEconomiaAnual());
            stmt.setFloat(9, simulacao.getCustoInstalacaoRecomendada());
            stmt.setFloat(10, simulacao.getTempoRetornoRecomendado());
            stmt.executeUpdate();
        }
    }


    // PUT: Atualizar uma simulação
    public boolean atualizarSimulacao(Simulacao simulacao) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE t_gs_simulacao SET usuario_id = ?, tipo_cliente = ?, localizacao = ?, custo_mensal = ?, orcamento = ?, " + "tipo_energia_escolhida = ?, tipo_energia_recomendada = ?, economia_anual = ?, custo_instalacao_recomendada = ?, tempo_retorno_recomendado = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, simulacao.getUsuarioId());
            stmt.setString(2, simulacao.getTipoCliente());
            stmt.setString(3, simulacao.getLocalizacao());
            stmt.setFloat(4, simulacao.getCustoMensal());
            stmt.setFloat(5, simulacao.getOrcamento());
            stmt.setString(6, simulacao.getTipoEnergiaEscolhida());
            stmt.setString(7, simulacao.getTipoEnergiaRecomendada());
            stmt.setFloat(8, simulacao.getEconomiaAnual());
            stmt.setFloat(9, simulacao.getCustoInstalacaoRecomendada());
            stmt.setFloat(10, simulacao.getTempoRetornoRecomendado());
            stmt.setInt(11, simulacao.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE: Remover uma simulação
    public boolean deletarSimulacao(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM t_gs_simulacao WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

}

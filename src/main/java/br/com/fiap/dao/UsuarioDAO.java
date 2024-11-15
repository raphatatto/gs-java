package br.com.fiap.dao;



import br.com.fiap.model.Usuario;
import br.com.fiap.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // GET: Listar todos os usuários
    public List<Usuario> listarTodos() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM t_gs_usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha_hash"),
                        rs.getTimestamp("data_criacao")
                ));
            }
        }
        return usuarios;
    }

    // GET: Buscar usuário por ID
    public Usuario buscarPorId(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM t_gs_usuario WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha_hash"),
                        rs.getTimestamp("data_criacao")
                );
            }
        }
        return null; // Caso não encontre o usuário
    }

    // POST: Criar novo usuário
    public void cadastrarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO T_GS_USUARIO (nome, email, senha_hash) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenhaHash());
            stmt.executeUpdate();
        }
    }

    // PUT: Atualizar um usuário
    public boolean atualizarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE t_gs_usuario SET nome = ?, email = ?, senha_hash = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenhaHash());
            stmt.setInt(4, usuario.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE: Remover um usuário
    public boolean deletarUsuario(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM t_gs_usuario WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public Usuario buscarPorEmailESenha(String email, String senhaHash) {
        String sql = "SELECT * FROM t_gs_usuario WHERE email = ? AND senha_hash = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senhaHash);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha_hash"),
                            rs.getTimestamp("data_criacao")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Retorna null se o usuário não for encontrado
    }

    public boolean usuarioExiste(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM t_gs_usuario WHERE email = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true se o e-mail já existir
                }
            }
        }
        return false; // Retorna false caso o e-mail não seja encontrado
    }
}

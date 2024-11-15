package br.com.fiap.resource;

import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.model.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @POST
    public Response login(Usuario usuario) {
        try {
            // Validar os dados recebidos
            if (usuario.getEmail() == null || usuario.getSenhaHash() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("E-mail e senha são obrigatórios.")
                        .build();
            }

            // Buscar usuário no banco de dados
            Usuario usuarioAutenticado = usuarioDAO.buscarPorEmailESenha(
                    usuario.getEmail(),
                    usuario.getSenhaHash()
            );

            if (usuarioAutenticado == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("E-mail ou senha inválidos.")
                        .build();
            }

            // Login bem-sucedido
            return Response.ok(usuarioAutenticado).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro no servidor: " + e.getMessage())
                    .build();
        }
    }
}

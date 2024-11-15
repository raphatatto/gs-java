package br.com.fiap.resource;


import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.model.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // GET: Listar todos os usuários
    @GET
    public Response listarUsuarios() {
        try {
            return Response.ok(usuarioDAO.listarTodos()).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // GET: Obter usuário por ID
    @GET
    @Path("/{id}")
    public Response buscarUsuario(@PathParam("id") int id) {
        try {
            Usuario usuario = usuarioDAO.buscarPorId(id);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
            }
            return Response.ok(usuario).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // POST: Criar novo usuário
    @POST
    public Response criarUsuario(Usuario usuario) {
        try {
            if (usuarioDAO.usuarioExiste(usuario.getEmail())) {
                return Response.status(Response.Status.CONFLICT).entity("E-mail já cadastrado.").build();
            }
            usuarioDAO.cadastrarUsuario(usuario);
            return Response.status(Response.Status.CREATED).entity("Usuário criado com sucesso!").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // PUT: Atualizar usuário existente
    @PUT
    @Path("/{id}")
    public Response atualizarUsuario(@PathParam("id") int id, Usuario usuario) {
        try {
            usuario.setId(id);
            boolean atualizado = usuarioDAO.atualizarUsuario(usuario);
            if (!atualizado) {
                return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
            }
            return Response.ok("Usuário atualizado com sucesso!").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // DELETE: Remover usuário por ID
    @DELETE
    @Path("/{id}")
    public Response deletarUsuario(@PathParam("id") int id) {
        try {
            boolean removido = usuarioDAO.deletarUsuario(id);
            if (!removido) {
                return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
            }
            return Response.ok("Usuário removido com sucesso!").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

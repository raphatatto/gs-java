package br.com.fiap.resource;

import br.com.fiap.dao.EnergiaRenovavelDAO;
import br.com.fiap.model.EnergiaRenovavel;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/energias-renovaveis")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnergiaRenovavelResource {

    private final EnergiaRenovavelDAO energiaRenovavelDAO = new EnergiaRenovavelDAO();

    // GET: Listar todas as energias renováveis
    @GET
    public Response listarEnergias() {
        try {
            return Response.ok(energiaRenovavelDAO.listarTodas()).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // GET: Obter energia por ID
    @GET
    @Path("/{id}")
    public Response buscarEnergia(@PathParam("id") int id) {
        try {
            EnergiaRenovavel energia = energiaRenovavelDAO.buscarPorId(id);
            if (energia == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Energia renovável não encontrada").build();
            }
            return Response.ok(energia).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // POST: Criar nova energia renovável
    @POST
    public Response criarEnergia(EnergiaRenovavel energia) {
        try {
            energiaRenovavelDAO.cadastrarEnergia(energia);
            return Response.status(Response.Status.CREATED).entity("Energia renovável cadastrada com sucesso!").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // PUT: Atualizar energia renovável existente
    @PUT
    @Path("/{id}")
    public Response atualizarEnergia(@PathParam("id") int id, EnergiaRenovavel energia) {
        try {
            energia.setId(id);
            boolean atualizado = energiaRenovavelDAO.atualizarEnergia(energia);
            if (!atualizado) {
                return Response.status(Response.Status.NOT_FOUND).entity("Energia renovável não encontrada").build();
            }
            return Response.ok("Energia renovável atualizada com sucesso!").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // DELETE: Remover energia renovável por ID
    @DELETE
    @Path("/{id}")
    public Response deletarEnergia(@PathParam("id") int id) {
        try {
            boolean removido = energiaRenovavelDAO.deletarEnergia(id);
            if (!removido) {
                return Response.status(Response.Status.NOT_FOUND).entity("Energia renovável não encontrada").build();
            }
            return Response.ok("Energia renovável removida com sucesso!").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

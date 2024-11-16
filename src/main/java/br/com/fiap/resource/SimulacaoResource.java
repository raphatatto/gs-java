package br.com.fiap.resource;

import br.com.fiap.dao.SimulacaoDAO;
import br.com.fiap.model.Simulacao;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/simulacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    private final SimulacaoDAO simulacaoDAO = new SimulacaoDAO();

    @GET
    public Response listarSimulacoes() {
        try {
            return Response.ok(simulacaoDAO.listarTodas()).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarSimulacao(@PathParam("id") int id) {
        try {
            Simulacao simulacao = simulacaoDAO.buscarPorId(id);
            if (simulacao == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Simulação não encontrada").build();
            }
            return Response.ok(simulacao).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response cadastrarSimulacao(Simulacao simulacao) {
        try {
            simulacaoDAO.cadastrarSimulacao(simulacao);
            // Retorna uma mensagem de sucesso como JSON
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Simulação criada com sucesso!\"}")
                    .build();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao salvar a simulação: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarSimulacao(@PathParam("id") int id, Simulacao simulacao) {
        try {
            simulacao.setId(id);
            boolean atualizado = simulacaoDAO.atualizarSimulacao(simulacao);
            if (!atualizado) {
                return Response.status(Response.Status.NOT_FOUND).entity("Simulação não encontrada").build();
            }
            return Response.ok("Simulação atualizada com sucesso!").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarSimulacao(@PathParam("id") int id) {
        try {
            boolean deletado = simulacaoDAO.deletarSimulacao(id);
            if (!deletado) {
                return Response.status(Response.Status.NOT_FOUND).entity("Simulação não encontrada").build();
            }
            return Response.ok("Simulação removida com sucesso!").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

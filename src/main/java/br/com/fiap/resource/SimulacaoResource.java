package br.com.fiap.resource;

import br.com.fiap.bo.SimulacaoBO;
import br.com.fiap.dao.SimulacaoDAO;
import br.com.fiap.exception.CustomException;
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
            SimulacaoBO simulacaoBO = new SimulacaoBO();
            Simulacao resultado = simulacaoBO.processarSimulacao(simulacao);

            simulacaoDAO.cadastrarSimulacao(resultado);
            return Response.status(Response.Status.CREATED).entity(resultado).build();
        } catch (CustomException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Erro no servidor: " + e.getMessage() + "\"}").build();
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
    @OPTIONS
    public Response preflight() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:3000")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .build();
    }

}

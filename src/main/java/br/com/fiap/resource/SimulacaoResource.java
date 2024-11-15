package br.com.fiap.resource;




import br.com.fiap.bo.SimulacaoBO;
import br.com.fiap.dao.SimulacaoDAO;
import br.com.fiap.exception.CustomException;
import br.com.fiap.model.Simulacao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    private final SimulacaoDAO simulacaoDAO = new SimulacaoDAO();
    private final SimulacaoBO simulacaoBO = new SimulacaoBO();
    // GET: Listar todas as simulações
    @GET
    public Response listarSimulacoes() {
        try {
            return Response.ok(simulacaoDAO.listarTodas()).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // GET: Obter simulação por ID
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

    // POST: Criar nova simulação


    // POST: Criar nova simulação
    @POST
    public Response criarSimulacao(Simulacao simulacao) {
        try {
            Simulacao resultado = simulacaoBO.processarSimulacao(simulacao);
            return Response.status(Response.Status.CREATED).entity(resultado).build();
        } catch (CustomException e) {
            return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro inesperado: " + e.getMessage()).build();
        }
    }
    // PUT: Atualizar simulação existente
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

    // DELETE: Remover simulação por ID
    @DELETE
    @Path("/{id}")
    public Response deletarSimulacao(@PathParam("id") int id) {
        try {
            boolean removido = simulacaoDAO.deletarSimulacao(id);
            if (!removido) {
                return Response.status(Response.Status.NOT_FOUND).entity("Simulação não encontrada").build();
            }
            return Response.ok("Simulação removida com sucesso!").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

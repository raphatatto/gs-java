package br.com.fiap.resource;

import br.com.fiap.bo.SimulacaoBO;
import br.com.fiap.dao.SimulacaoDAO;
import br.com.fiap.exception.CustomException;
import br.com.fiap.model.Simulacao;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/simulacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    private final SimulacaoDAO simulacaoDAO = new SimulacaoDAO();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarSimulacoes(@QueryParam("usuarioId") int usuarioId) {
        try {
            List<Simulacao> simulacoes = simulacaoDAO.listarPorUsuarioId(usuarioId);
            return Response.ok(simulacoes).build();
        } catch (Exception e) {
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
        System.out.println("Simulação recebida: " + simulacao);
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
            Simulacao simulacaoExistente = simulacaoDAO.buscarPorId(id);
            if (simulacaoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Simulação não encontrada.\"}")
                        .build();
            }

            if (simulacaoExistente.getUsuarioId() != simulacao.getUsuarioId()) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\": \"Usuário não autorizado para esta simulação.\"}")
                        .build();
            }

            simulacao.setId(id);
            boolean atualizado = simulacaoDAO.atualizarSimulacao(simulacao);
            if (!atualizado) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\": \"Erro ao atualizar a simulação.\"}")
                        .build();
            }

            return Response.ok("{\"message\": \"Simulação atualizada com sucesso!\"}").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro no servidor: " + e.getMessage() + "\"}")
                    .build();
        }
    }
    @DELETE
    @Path("/{id}")
    public Response deletarSimulacao(@PathParam("id") int id, @HeaderParam("usuarioId") int usuarioId) {
        System.out.println("Recebido para exclusão - Simulação ID: " + id + ", Usuário ID: " + usuarioId);

        try {
            Simulacao simulacao = simulacaoDAO.buscarPorId(id);
            if (simulacao == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Simulação não encontrada.\"}")
                        .build();
            }
            System.out.println("Simulação encontrada - Usuário associado: " + simulacao.getUsuarioId());

            boolean deletado = simulacaoDAO.deletarSimulacao(id);
            if (deletado) {
                return Response.ok("{\"message\":\"Simulação removida com sucesso.\"}").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\":\"Erro ao excluir a simulação.\"}")
                        .build();
            }
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Erro ao processar a exclusão.\"}")
                    .build();
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

    @GET
    @Path("/usuario")
    public Response listarSimulacoesPorUsuario(@QueryParam("usuarioId") int usuarioId) {
        try {
            List<Simulacao> simulacoes = simulacaoDAO.listarPorUsuarioId(usuarioId);
            return Response.ok(simulacoes).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


}


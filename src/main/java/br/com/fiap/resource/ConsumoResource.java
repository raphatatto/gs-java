package br.com.fiap.resource;

import br.com.fiap.bo.ConsumoBO;
import br.com.fiap.model.Consumo;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/consumo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsumoResource {

    private final ConsumoBO consumoBO = new ConsumoBO();

    @POST
    public Response criarConsumo(Consumo consumo) {
        try {
            consumoBO.processarConsumo(consumo);
            return Response.status(Response.Status.CREATED).entity("Consumo registrado com sucesso!").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao registrar consumo: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{usuarioId}")
    public Response listarConsumosPorUsuario(@PathParam("usuarioId") int usuarioId) {
        try {
            List<Consumo> consumos = consumoBO.listarConsumosPorUsuario(usuarioId);
            return Response.ok(consumos).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar consumos: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/calcular-media")
    public Response calcularMedia(Consumo consumo) {
        try {
            // Usar o BO para calcular as médias
            Consumo resultado = consumoBO.calcularMediaMensal(consumo);

            // Salvar no banco
            consumoBO.processarConsumo(resultado);

            return Response.ok(resultado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Erro ao calcular médias\"}").build();
        }
    }


}

package it.prova.personajaxrs.web.rest.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.prova.personajaxrs.model.Persona;
import it.prova.personajaxrs.service.MyServiceFactory;
import it.prova.personajaxrs.service.PersonaService;

@Path("/persona")
public class PersonaResource {

	private static final Logger LOGGER = Logger.getLogger(PersonaResource.class.getName());

	@Context
	HttpServletRequest request;

	private PersonaService personaService;

	public PersonaResource() {
		this.personaService = MyServiceFactory.getPersonaServiceInstance();
	}

	@GET
	@Path("{id : \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPersona(@PathParam("id") Long id) {
		LOGGER.info("Verbo Http.........................." + request.getMethod());

		try {
			Persona personaDaCaricare = personaService.caricaSingoloElemento(id);
			ObjectMapper objectMapper = new ObjectMapper();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
			objectMapper.setDateFormat(df);
			String risultato = objectMapper.writeValueAsString(personaDaCaricare);

			return Response.status(200).entity(risultato).build();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertiNuovaPersona(Persona personaInput) {
		LOGGER.info("Verbo Http.........................." + request.getMethod());

		try {

			personaService.inserisciNuovo(personaInput);

			return Response.status(200).entity(personaInput).build();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll() {
		LOGGER.info("Verbo Http.........................." + request.getMethod());

		try {
			List<Persona> result = personaService.listAllElements();

			ObjectMapper objectMapper = new ObjectMapper();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
			objectMapper.setDateFormat(df);
			String risultato = objectMapper.writeValueAsString(result);

			return Response.status(200).entity(risultato).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchPersona(@QueryParam("nome") String nome, @QueryParam("cognome") String cognome) {
		LOGGER.info("Verbo Http.........................." + request.getMethod());
		try {
			List<Persona> result = personaService.findByNomeCognome(nome, cognome);

			ObjectMapper objectMapper = new ObjectMapper();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
			objectMapper.setDateFormat(df);
			String risultato = objectMapper.writeValueAsString(result);

			return Response.status(200).entity(risultato).build();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@DELETE
	@Path("{id : \\d+}")
	public Response deletePersona(@PathParam("id") Long id) {
		LOGGER.info("Verbo Http.........................." + request.getMethod());
		try {
			if (personaService.rimuovi(id))
				return Response.status(200).entity("Rimossa Persona con id: " + id).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("not found").build();

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response aggiornaPersona(Persona personaInput) {
		LOGGER.info("Verbo Http.........................." + request.getMethod());
		String result = "";
		try {
			personaService.aggiorna(personaInput);
			result = "aggiornamento riuscito";
		} catch (Exception e) {
			e.printStackTrace();
			result = "si è verificato un errore";
		}
		return Response.status(200).entity(result).build();
	}
}

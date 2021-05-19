package it.prova.personajaxrs.service;

import java.util.List;

import it.prova.personajaxrs.dao.PersonaDao;
import it.prova.personajaxrs.model.Persona;

public interface PersonaService {

	public void setPersonaDao(PersonaDao personaDao);

	public List<Persona> listAllElements() throws Exception;

	public Persona caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Persona personaInstance) throws Exception;

	public void inserisciNuovo(Persona personaInstance) throws Exception;

	public boolean rimuovi(Long id) throws Exception;

	public List<Persona> findByNomeCognome(String nome, String cognome) throws Exception;

}

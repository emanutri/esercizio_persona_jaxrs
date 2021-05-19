package it.prova.personajaxrs.dao;

import java.util.List;

import it.prova.personajaxrs.model.Persona;

public interface PersonaDao extends IBaseDAO<Persona> {

	public List<Persona> findByFields(String nome, String cognome);


}

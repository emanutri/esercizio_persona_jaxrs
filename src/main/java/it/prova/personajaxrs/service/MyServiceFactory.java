package it.prova.personajaxrs.service;

import it.prova.personajaxrs.dao.PersonaDao;
import it.prova.personajaxrs.dao.PersonaDaoImpl;

public class MyServiceFactory {

	private static PersonaService PERSONA_SERVICE_INSTANCE;
	private static PersonaDao PERSONA_DAO_INSTANCE;

	public static PersonaService getPersonaServiceInstance() {
		if (PERSONA_SERVICE_INSTANCE == null)
			PERSONA_SERVICE_INSTANCE = new PersonaServiceImpl();

		if (PERSONA_DAO_INSTANCE == null)
			PERSONA_DAO_INSTANCE = new PersonaDaoImpl();

		PERSONA_SERVICE_INSTANCE.setPersonaDao(PERSONA_DAO_INSTANCE);
		return PERSONA_SERVICE_INSTANCE;
	}

}

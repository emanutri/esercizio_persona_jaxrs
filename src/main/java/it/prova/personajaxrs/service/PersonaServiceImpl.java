package it.prova.personajaxrs.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.personajaxrs.dao.PersonaDao;
import it.prova.personajaxrs.model.Persona;
import it.prova.personajaxrs.web.listener.LocalEntityManagerFactoryListener;

public class PersonaServiceImpl implements PersonaService {

	private PersonaDao personaDao;

	@Override
	public void setPersonaDao(PersonaDao personaDao) {
		this.personaDao = personaDao;
	}

	@Override
	public List<Persona> listAllElements() throws Exception {
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			personaDao.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return personaDao.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public Persona caricaSingoloElemento(Long id) throws Exception {
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			personaDao.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return personaDao.findOne(id).get();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Persona personaInstance) throws Exception {
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			personaDao.setEntityManager(entityManager);
			personaDao.update(personaInstance);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public void inserisciNuovo(Persona personaInstance) throws Exception {
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			personaDao.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			personaDao.insert(personaInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public boolean rimuovi(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			personaDao.setEntityManager(entityManager);
			
			Persona personaInstance = personaDao.findOne(id).get();
			
			personaDao.delete(entityManager.merge(personaInstance));
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			return false;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
		
	}

	@Override
	public List<Persona> findByNomeCognome(String nome, String cognome) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			personaDao.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return personaDao.findByFields(nome, cognome);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}
	
	
}

package angularmail.modelo.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import angularmail.modelo.Mensaje;



public class MensajeControlador extends Controlador {

	private static MensajeControlador controller = null; // Instancia para Singleton
	
	/**
	 * Constructor, que inicializa al Super Controlador con la clase Usuario.class
	 */
	public MensajeControlador() {
		super(Mensaje.class);
	}

	/**
	 * Método para obtener la instancia Singleton
	 * @return
	 */
	public static MensajeControlador getControlador () {
		if (controller == null) {
			controller = new MensajeControlador();
		}
		return controller;
	}

	/**
	 * Para hacer un "find sencillo" llamo al método find del Super Controlador. Ese
	 * método devuelve una entidad y en este método hacemos un casting para convertir
	 * en la entidad que controla este controlador
	 */
	public Mensaje find (int id) {
		return (Mensaje) super.find(id);
	}



	
	/**
	 * Método para hacer un "findAll", devolver un listado con todas las entidades del 
	 * tipo de la clase controlada por este controlador.
	 * @return
	 * @throws NoResultException
	 */
	@SuppressWarnings("unchecked")
	public List<Mensaje> findAll () {
		List<Mensaje> entities = new ArrayList<Mensaje>();
		EntityManager em = getEntityManagerFactory().createEntityManager();
		try {			
			Query q = em.createQuery("SELECT o FROM Mensaje o", Mensaje.class);
			entities = (List<Mensaje>) q.getResultList();
		}
		catch (NoResultException nrEx) {
		}
		em.close();
		return entities;
	}

}

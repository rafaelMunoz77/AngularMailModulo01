package angularmail.modelo.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import angularmail.modelo.DestinatarioMensaje;



public class DestinatarioMensajeControlador extends Controlador {

	private static DestinatarioMensajeControlador controller = null; // Instancia para Singleton
	
	/**
	 * Constructor, que inicializa al Super Controlador con la clase Usuario.class
	 */
	public DestinatarioMensajeControlador() {
		super(DestinatarioMensaje.class);
	}

	/**
	 * Método para obtener la instancia Singleton
	 * @return
	 */
	public static DestinatarioMensajeControlador getControlador () {
		if (controller == null) {
			controller = new DestinatarioMensajeControlador();
		}
		return controller;
	}

	/**
	 * Para hacer un "find sencillo" llamo al método find del Super Controlador. Ese
	 * método devuelve una entidad y en este método hacemos un casting para convertir
	 * en la entidad que controla este controlador
	 */
	public DestinatarioMensaje find (int id) {
		return (DestinatarioMensaje) super.find(id);
	}



	
	/**
	 * Método para hacer un "findAll", devolver un listado con todas las entidades del 
	 * tipo de la clase controlada por este controlador.
	 * @return
	 * @throws NoResultException
	 */
	@SuppressWarnings("unchecked")
	public List<DestinatarioMensaje> findAll () {
		List<DestinatarioMensaje> entities = new ArrayList<DestinatarioMensaje>();
		EntityManager em = getEntityManagerFactory().createEntityManager();
		try {			
			Query q = em.createQuery("SELECT o FROM DestinatarioMensaje o", DestinatarioMensaje.class);
			entities = (List<DestinatarioMensaje>) q.getResultList();
		}
		catch (NoResultException nrEx) {
		}
		em.close();
		return entities;
	}

}

package angularmail.modelo.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import angularmail.modelo.TipoSexo;




public class TipoSexoControlador extends Controlador {

	private static TipoSexoControlador controller = null; // Instancia para Singleton
	
	/**
	 * Constructor, que inicializa al Super Controlador con la clase Usuario.class
	 */
	public TipoSexoControlador() {
		super(TipoSexo.class);
	}

	/**
	 * Método para obtener la instancia Singleton
	 * @return
	 */
	public static TipoSexoControlador getControlador () {
		if (controller == null) {
			controller = new TipoSexoControlador();
		}
		return controller;
	}

	/**
	 * Para hacer un "find sencillo" llamo al método find del Super Controlador. Ese
	 * método devuelve una entidad y en este método hacemos un casting para convertir
	 * en la entidad que controla este controlador
	 */
	public TipoSexo find (int id) {
		return (TipoSexo) super.find(id);
	}



	
	/**
	 * Método para hacer un "findAll", devolver un listado con todas las entidades del 
	 * tipo de la clase controlada por este controlador.
	 * @return
	 * @throws NoResultException
	 */
	@SuppressWarnings("unchecked")
	public List<TipoSexo> findAll () {
		List<TipoSexo> entities = new ArrayList<TipoSexo>();
		EntityManager em = getEntityManagerFactory().createEntityManager();
		try {			
			Query q = em.createQuery("SELECT o FROM TipoSexo o", TipoSexo.class);
			entities = (List<TipoSexo>) q.getResultList();
		}
		catch (NoResultException nrEx) {
		}
		em.close();
		return entities;
	}

}

package angularmail.modelo.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import angularmail.modelo.Usuario;



public class UsuarioControlador extends Controlador {

	private static UsuarioControlador controller = null; // Instancia para Singleton
	
	/**
	 * Constructor, que inicializa al Super Controlador con la clase Usuario.class
	 */
	public UsuarioControlador() {
		super(Usuario.class);
	}

	/**
	 * Método para obtener la instancia Singleton
	 * @return
	 */
	public static UsuarioControlador getControlador () {
		if (controller == null) {
			controller = new UsuarioControlador();
		}
		return controller;
	}

	/**
	 * Para hacer un "find sencillo" llamo al método find del Super Controlador. Ese
	 * método devuelve una entidad y en este método hacemos un casting para convertir
	 * en la entidad que controla este controlador
	 */
	public Usuario find (int id) {
		return (Usuario) super.find(id);
	}



	
	/**
	 * Método para hacer un "findAll", devolver un listado con todas las entidades del 
	 * tipo de la clase controlada por este controlador.
	 * @return
	 * @throws NoResultException
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> findAll () {
		List<Usuario> entities = new ArrayList<Usuario>();
		EntityManager em = getEntityManagerFactory().createEntityManager();
		try {			
			Query q = em.createQuery("SELECT o FROM Usuario o", Usuario.class);
			entities = (List<Usuario>) q.getResultList();
		}
		catch (NoResultException nrEx) {
		}
		em.close();
		return entities;
	}

	
	/**
	 * Método para hacer un "findAll", devolver un listado con todas las entidades del 
	 * tipo de la clase controlada por este controlador.
	 * @return
	 * @throws NoResultException
	 */
	@SuppressWarnings("unchecked")
	public Usuario findByUsuario (String nombreUsuario) {
		Usuario entity = null;
		EntityManager em = getEntityManagerFactory().createEntityManager();
		try {			
			Query q = em.createNativeQuery("SELECT * FROM usuario u where u.usuario = ?", Usuario.class);
			q.setParameter(1, nombreUsuario);
			entity = (Usuario) q.getSingleResult();
		}
		catch (NoResultException nrEx) {
		}
		em.close();
		return entity;
	}

}

package angularmail.modelo.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import angularmail.modelo.Usuario;


public class UsuarioControlador {

	// Instancia estática típica de un patrón singleton
	private static UsuarioControlador controller = null;
	// EntityManagerFactory que necesitamos para obtener objetos EntityManager, para poder operar con entidades
	private static EntityManagerFactory entityManagerFactory;
	
	/**
	 * 
	 */
	public UsuarioControlador() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("AngularMailModulo01"); // Conexión con el persistence.xml
	}

	/**
	 * Método para obtener un Singleton, la instancia estática "controller", de la línea 20
	 * @return
	 */
	public static UsuarioControlador getControlador () {
		if (controller == null) {
			controller = new UsuarioControlador();
		}
		return controller;
	}

	/**
	 * Un método simple para obtener un usuario
	 */
	public Usuario find (int id) {
		return this.entityManagerFactory.createEntityManager().find(Usuario.class, id);
	}



	
	/**
	 * Método para obtener un listado de todos los usuarios
	 * @return
	 * @throws NoResultException
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> findAll () {
		List<Usuario> entities = new ArrayList<Usuario>(); // Creo una lista vacía de usuarios
		EntityManager em = this.entityManagerFactory.createEntityManager(); // Obtengo el EntityManager
		try {			
			Query q = em.createQuery("SELECT o FROM Usuario o", Usuario.class); // JPQL para obtener todos los usuarios
			entities = (List<Usuario>) q.getResultList(); // Ejecución de la Query
		}
		catch (NoResultException nrEx) { // En caso de que no se encuentren resultados, no hago nada
		}
		em.close(); // Cierro el entityManager y devuelvo el listado de entidades
		return entities;
	}


	
}

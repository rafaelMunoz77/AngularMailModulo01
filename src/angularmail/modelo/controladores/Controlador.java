package angularmail.modelo.controladores;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import angularmail.modelo.Entidad;


/**
 * Superclase de todos los controladores JPA
 * @author rafae
 *
 */
public class Controlador {

	private static EntityManagerFactory entityManagerFactory;  // Factoria para obtener objetos EntityManager 
	private Class entidadControlada = null;  // Clase controlada por el controlador. Puede ser la clase Usuario.class, Mensaje.class, Nacionalidad.class, etc.

	/**
	 * El constructor de un Controlador inicializa el entityManagerFactory y el tipo de clase que controla
	 * este controlador.
	 * @param nombreEntidad
	 */
	public Controlador(Class entidadControlada) {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("AngularMailModulo01");
		}
		this.entidadControlada = entidadControlada;
	}

	/**
	 * Patrón Singleton
	 * @return
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	/**
	 * Método genérico que obtiene una entidad, de cualquier tipo
	 * @param id
	 * @return
	 */
	public Entidad find(int id) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Entidad entidad = (Entidad) em.find(this.entidadControlada, id);
			em.close();
			return entidad;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null; // Si hubiese cualquier problema, por ejemplo que la entidad
			// buscada no existiera, se devuelve un valor nulo
		}
	}
	
	
	/**
	 * Método general que graba una nueva entidad mediante una transacción. Equivale en SQL a INSERT INTO
	 */
	public void persist(Entidad e) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		em.persist(e);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Método general que modifica una entidad mediante una transacción. Equivale en SQL a UPDATE
	 */
	public void merge(Entidad e) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		em.merge(e);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Método muy útil que determina si se debe llamar al método persist o merge.
	 * Se examina si el id de la entidad que se desea "save" es distinto de 0.
	 * Si la entidad es distinta de cero significará que el registro en la BBDD ya
	 * existe, por tanto se llama al método merge (UPDATE). En caso de que el id de 
	 * la entidad sea igual a cero, se debe insertar un nuevo registro en la BBDD, se
	 * llama al método "persist" (INSERT INTO)
	 * @param e
	 */
	public void save(Entidad e) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		if (e.getId() != 0) {
			merge(e);
		}
		else {
			persist(e);
		}
	}
	
	/**
	 * Método general para eliminar cualquier entidad
	 */
	public void remove(Entidad e) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Entidad actual = null;
		em.getTransaction().begin();
		if (!em.contains(e)) {
			actual = em.merge(e);
		}
		em.remove(actual);
		em.getTransaction().commit();
		em.close();
	}
		
}

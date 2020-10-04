package angularmail.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import angularmail.modelo.Nacionalidad;
import angularmail.modelo.TipoSexo;
import angularmail.modelo.Usuario;

public class Ejemplo02_ConexionBasicaConJPA {
	
	/**
	 * Llama a diferentes métodos para que conozcamos las posibilidades de JPA. Este código, todavía, no es demasiado
	 * eficiente, pero nos permite hacernos una idea de la utilidad de JPA.
	 * @param args
	 */
	public static void main(String[] args) {
		//obtencionUnaSolaEntidad();
		//obtencionUnaSolaEntidadSegundoMetodo();
		//obtencionUnaSolaEntidadTercerMetodo();
		//listadoEntidades();
		//listadoEntidadesSegundoMetodo();
		//listadoEntidadesTercerMetodo();
		//creacionEntidad();
		//modificacionEntidad();
		eliminacionEntidad();
	}

	
	
	/**
	 * Obtenemos una unica entidad, a partir de su identificador numérico
	 */
	private static void obtencionUnaSolaEntidad () {
		// Necesitamos un EntityManager para trabajar con las entidades, lo obtenemos a través del 
		// EntityManagerFactory y este se obtiene por el nombre de la unidad de persistencia, que
		// coincide con la del fichero XML
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AngularMailModulo01");

		EntityManager em = entityManagerFactory.createEntityManager(); // Obtengo el EntityManager

		Usuario u = (Usuario) em.find(Usuario.class, 1); // Con el EntityManager puedo buscar una entidad de un tipo y un identificador
		
		// Para demostrar que he accedido a la BBDD, muestro en pantalla.
		System.out.println("Usuario localizado -> " + u.getId() + " " + u.getNombre() + " " + u.getEmail());
		
		// Obtengo una referencia cruzada, a parti del usuario hasta la tabla Nacionalidad, para demostrar que esto es posible
		System.out.println("Nacionalidad asociada al usuario: " + u.getNacionalidad().getDescripcion());
		
		em.close(); // Cierro el objeto EntityManager.
	}
	
	
	/**
	 * Para no repetir mucho el código, creo este método, a utilizar en todos los demás.
	 * @return
	 */
	private static EntityManager getNewEntityManager () {
		// Obtengo un objeto de tipo EntityManager
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AngularMailModulo01");
		EntityManager em = entityManagerFactory.createEntityManager();
		return em;
	}
	
	
	/**
	 * 
	 * @param u
	 */
	private static void muestraEnLaConsola (Usuario u) {
		if (u != null) {
			System.out.println("Usuario -> " + u.getId() + " " + u.getNombre() + " " + u.getEmail());
		}
		else {
			// Si el usuario fuese null significaría que no se ha encontrado.
			System.out.println("Usuario nulo");
		}
	}
	
	/**
	 * Ejemplo de como obtener una entidad a partir de una consulta SQL nativa.
	 */
	private static void obtencionUnaSolaEntidadSegundoMetodo () {
		EntityManager em = getNewEntityManager(); 
		
		// Realizo una consulta SQL nativa. La fila resultante de la consulta se mapeará automáticamente en un usuario,
		// una instancia de la clase Usuario.
		Query q = em.createNativeQuery("SELECT * FROM usuario where id = ?", Usuario.class);
		q.setParameter(1, 1); // Ejemplo de como introducir parámetros en la consulta
		Usuario u = (Usuario) q.getSingleResult(); // Utilizo getSingleResult porque sé que sólo hay un registro resultante de la consulta
		
		muestraEnLaConsola(u); 
		
		em.close(); // Cierro el objeto entityManager
	}
	
	
	
	/**
	 * Ejemplo de como obtener una entidad de tipo usuario, utilizando JPQL, en lugar de SQL
	 */
	private static void obtencionUnaSolaEntidadTercerMetodo () {
		EntityManager em = getNewEntityManager(); 

		Query q = em.createQuery("SELECT u FROM Usuario u where u.id = :id", Usuario.class); // Consulta en JPQL
		q.setParameter("id", 1); // Puedo introducir el parámetro a través del identificador
		Usuario u = (Usuario) q.getSingleResult(); // Obtengo un registro
		
		muestraEnLaConsola(u); 
		
		em.close();
	}
	
	
	
	/**
	 * Al igual que puedo obtener una entidad, en este puedo obtener un listado.
	 */
	private static void listadoEntidades () {
		EntityManager em = getNewEntityManager(); 
		
		Query q = em.createNativeQuery("SELECT * FROM usuario;", Usuario.class); // Consulta con SQL nativa
		
		List<Usuario> usuarios = (List<Usuario>) q.getResultList(); // No utilizo getSingleResult
		
		// Muestro los usuarios hallados
		for (Usuario u : usuarios) {
			muestraEnLaConsola(u); 
		}
		
		em.close();
	}
	
	
	/**
	 * Ejemplo de como obtener un listado a través de JPQL
	 */
	private static void listadoEntidadesSegundoMetodo () {
		EntityManager em = getNewEntityManager(); 
		
		Query q = em.createQuery("SELECT u FROM Usuario u", Usuario.class); // Consulta con JPQL
		
		List<Usuario> usuarios = (List<Usuario>) q.getResultList();
		
		for (Usuario u : usuarios) {
			muestraEnLaConsola(u); 
		}
		
		em.close(); // Cierro el entityManager
	}
	
	
	/**
	 * Ejemplo de uso de una namedQuery, las "namedQuery" se encuentra en la clase de la entidad. En este caso
	 * se encuentra en la clae Usuario.
	 */
	private static void listadoEntidadesTercerMetodo () {
		EntityManager em = getNewEntityManager(); 
		
		Query q = em.createNamedQuery("Usuario.findAll"); // Ejecuto la NamedQuery de la clase Usuario
		
		List<Usuario> usuarios = (List<Usuario>) q.getResultList();
		
		for (Usuario u : usuarios) {
			muestraEnLaConsola(u); 
		}
		
		em.close(); // Cierro el entityManager
	}
	
	

	/**
	 * Ejemplo de como se puede crear una entidad y almacenarla en el esquema de BBDD
	 */
	private static void creacionEntidad () {
		EntityManager em = getNewEntityManager(); 

		// Creo un nuevo objeto, un nuevo usuario, y le asigno valores
		Usuario u = new Usuario();
		u.setNombre("Luisa");
		u.setUsuario("luisa1981");
		u.setEmail("luisa@luisa.com");
		u.setNacionalidad(em.find(Nacionalidad.class, 1)); // Busco la nacionalidad con id = 1 y se la asigno al usuario
		u.setTipoSexo(em.find(TipoSexo.class, 1)); // Busco la tipología de sexo con id = 1 y se la asigno al usuario
		
		// Guardo el objeto mediante una transacción
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
		
		em.close(); // Cierro el entityManager
	}
	
	
	
	/**
	 * Ejemplo de modificación de una entidad
	 */
	private static void modificacionEntidad () {
		EntityManager em = getNewEntityManager(); 

		// Obtengo un usuario que voy a modificar, utilizo JPQL pero puedo usar otros métodos, recomiendo SQL nativo
		Query q = em.createQuery("SELECT u FROM Usuario as u where u.nombre = 'Luisa'", Usuario.class);
		List<Usuario> usuarios = q.getResultList();
		
		// Mediante una transacción modifico todos los registros que necesite
		em.getTransaction().begin();
		for (Usuario u : usuarios) {
			u.setNombre("María Luisa");
			em.persist(u);
		}
		em.getTransaction().commit();
		
		em.close(); // Cierro el entityManager
	}
	
	
	
	/**
	 * Ejemplo de como eliminar una entidad
	 */
	private static void eliminacionEntidad () {
		EntityManager em = getNewEntityManager(); 

		Query q = em.createQuery("SELECT u FROM Usuario as u where u.nombre = 'María Luisa'", Usuario.class);
		
		List<Usuario> usuarios = q.getResultList(); // Obtengo la lista de posibles usuarios
		
		// Mediante la transacción, elimino la entidad y, por tanto, también elimino el registro en la BBDD
		em.getTransaction().begin();
		for (Usuario u : usuarios) {
			em.remove(u);
		}
		em.getTransaction().commit();
		
		em.close(); // Cierro el entityManager
	}
	
	
	
	

	
}

package angularmail.test;

import java.util.List;

import angularmail.modelo.Usuario;
import angularmail.modelo.controladores.UsuarioControlador;

public class Ejemplo03_ConexionJPAMedianteControladores {

	/**
	 * Ejemplo para ver como trabajaremos con los controladores de entidades JPA
	 * @param args
	 */
	public static void main(String[] args) {
		// Obtengo un usuario desde su controlador
		Usuario u = UsuarioControlador.getControlador().find(1);
		System.out.println("Encontrado el usuario: " + u.getNombre());
		
		// Obtengo un listado de usuarios desde el controlador
		List<Usuario> usuarios = UsuarioControlador.getControlador().findAll();
		for (Usuario usuario : usuarios) {
			System.out.println("Listado. Usuario: " + usuario.getNombre());
		}
	}

}

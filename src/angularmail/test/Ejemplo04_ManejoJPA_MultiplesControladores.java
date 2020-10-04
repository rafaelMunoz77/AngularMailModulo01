package angularmail.test;

import java.util.List;

import angularmail.modelo.DestinatarioMensaje;
import angularmail.modelo.Mensaje;
import angularmail.modelo.Usuario;
import angularmail.modelo.controladores.UsuarioControlador;

public class Ejemplo04_ManejoJPA_MultiplesControladores {

	public static void main (String args[]) {
		// En una única línea localizo a un usuaro por su nombre de usuario
		Usuario carmen = UsuarioControlador.getControlador().findByUsuario("carmen");
		
		// Recorro los mensajes enviados por Carmen
		for (Mensaje m : carmen.getMensajes()) {
			System.out.println("Mensaje -> id: " + m.getId() + " - Asunto: " + m.getAsunto());
			// Recorro los destinatarios de cada mensaje
			for (DestinatarioMensaje dm : m.getDestinatarioMensajes()) {
				System.out.println("\t Destinatario: " + dm.getUsuario().getNombre());
			}
		}
	}
}

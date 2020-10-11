package angularmail.webAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import angularmail.modelo.Mensaje;
import angularmail.modelo.Usuario;
import angularmail.modelo.controladores.MensajeControlador;

/**
 * Servlet implementation class GetListadoMensajes
 */
@WebServlet("/GetListadoMensajes")
public class GetListadoMensajes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetListadoMensajes() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // Necesito un mapper para trabajar con JSON
		ObjectMapper mapper = new ObjectMapper();

		// Obtengo el usuario autenticado y guardado en la sesión.
		// Si no hay usuario no obtengo ni devuelvo listado de mensajes
		Usuario u = (Usuario) request.getSession().getAttribute("usuarioAutenticado");
		
		if (u != null) {
			// Obtengo la lista de mensajes del controlador JPA
			List<Mensaje> mensajes = MensajeControlador.getControlador().findMensajesBandejaEntradaDeUsuario(u.getId());
			
			// Transformo la lista de mensajes en una lista de objetos DTO, que se transformará en JSON
			List<DTO> mensajesDTO = new ArrayList<DTO>();
			for (Mensaje m : mensajes) {
				DTO dto = new DTO();
				dto.put("id", m.getId());
				dto.put("asunto", m.getAsunto());
				dto.put("fecha", m.getFecha());
				
				mensajesDTO.add(dto); // Agrego el nuevo dto creado a la lista de dtos que van a viajar al cliente
			}
			
			// Devuelvo, en formato JSON, la lista de mensajes
			response.getWriter().append(mapper.writeValueAsString(mensajesDTO));
		}
		else {
			// En caso de que no haya usuario autenticado, devuelvo nulo, advirtiendo del error.
			response.getWriter().append(null);
		}
			
	}
}

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

		// si existe una cabecera de autenticación en el request, con nombre "Authentication", leo el JWT, que vendrá tras
		// los caracteres "Bearer ". Tomo el JWT y obtengo el usuario autenticado gracias al id encriptado en su interior
		if (request.getHeader("Authorization") != null) {
			// Obtengo la cabecera del request, etiquetada como "Autorization", ahí debe llegar el JWT del usuario autenticado
			String stringAuthorization = request.getHeader("Authorization");
			String jwt = stringAuthorization.substring(7); // Eliminamos la cabecera "Bearer ", antes del jwt			

			Usuario usuAutenticado = AutenticadorJWT.cargaUsuarioDesdeJWT(jwt);

			if (usuAutenticado != null) {
				// Obtengo la lista de mensajes del controlador JPA
				List<Mensaje> mensajes = MensajeControlador.getControlador().findMensajesBandejaEntradaDeUsuario(usuAutenticado.getId());
				
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
				return;
			}

		}
		// En caso de que no haya usuario autenticado, devuelvo nulo, advirtiendo del error.
		response.getWriter().append(null);
	}
}

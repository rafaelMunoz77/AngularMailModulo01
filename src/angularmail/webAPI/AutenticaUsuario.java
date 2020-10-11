package angularmail.webAPI;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import angularmail.modelo.Usuario;
import angularmail.modelo.controladores.UsuarioControlador;

/**
 * Servlet implementation class AutenticarUsuario
 */
@WebServlet("/AutenticaUsuario")
public class AutenticaUsuario extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see SuperServlet#SuperServlet()
     */
    public AutenticaUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

    
 	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(request.getInputStream());
		
		try {
			// Obtengo los datos recibidos en el JSON
			String usuario = rootNode.path("usuario").asText();
			String password = rootNode.path("password").asText();
			Usuario usuarioAutenticado = UsuarioControlador.getControlador().findByUsuarioAndPassword(usuario, password);

			// Si el usuario existe, creo un DTO para devolverlo en formato JSON
			if (usuarioAutenticado != null) { // Se ha autenticado al usuario
				DTO dto = new DTO (); // Construyo el dto
				dto.put("id", usuarioAutenticado.getId());
				dto.put("nombre", usuarioAutenticado.getNombre());
				dto.put("usuario", usuarioAutenticado.getUsuario());
				dto.put("email", usuarioAutenticado.getEmail());
				dto.put("fechaNac", usuarioAutenticado.getFechaNacimiento());

				// Guardo el usuario autenticado en la sesión de trabajo del cliente en el servidor
				request.getSession().setAttribute("usuarioAutenticado", usuarioAutenticado);
				
				response.getWriter().println(mapper.writeValueAsString(dto)); // Devuelvo el DTO, en formato JSON
				return;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		// Si llega hasta aquí no se ha podido autenticar al usuario, devuelvo un nulo como indicador
		response.getWriter().println("null");
	}

}

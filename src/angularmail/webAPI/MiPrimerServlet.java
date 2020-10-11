package angularmail.webAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import angularmail.modelo.Usuario;
import angularmail.modelo.controladores.UsuarioControlador;

/**
 * Servlet implementation class MiPrimerServlet
 */
//@WebServlet("/MiPrimerServlet")
public class MiPrimerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MiPrimerServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Usuario> usuarios = UsuarioControlador.getControlador().findAll();
		
		List<DTO> listaAEnviar = new ArrayList<DTO>();
		
		for (Usuario u : usuarios) {
			DTO dto = new DTO(); 
			dto.put("id", u.getId());
			dto.put("nombre", u.getNombre());
			dto.put("email", u.getEmail());
			
			listaAEnviar.add(dto);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		response.getWriter().append(mapper.writeValueAsString(listaAEnviar));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);  
	}

}

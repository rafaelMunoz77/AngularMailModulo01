package angularmail.webAPI;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		
		response.getWriter().append("<html><body><table border='1'>");
		for (Usuario u : usuarios) {
			response.getWriter().append("<tr><td>Usuario: " + u.getEmail() + "</td></tr>");
		}
		response.getWriter().append("</table></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);  
	}

}

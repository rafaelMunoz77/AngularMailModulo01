package angularmail.webAPI;

import java.security.Key;

import angularmail.modelo.Usuario;
import angularmail.modelo.controladores.UsuarioControlador;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class AutenticadorJWT {
	
	private static Key key = null;
	
	/**
	 * 
	 * @param u
	 * @return
	 */
	public static String codificaJWT (Usuario u) {
		String jws = Jwts.builder().setSubject("" + u.getId()).
				signWith(SignatureAlgorithm.HS512, getGeneratedKey()).compact();
		return jws;
	}
	
	/**
	 * 
	 * @param jwt
	 * @return
	 */
	public static Usuario cargaUsuarioDesdeJWT (String jwt) {
		try {
			String stringIdUsuario = Jwts.parser().setSigningKey(getGeneratedKey()).parseClaimsJws(jwt).getBody().getSubject();
			int idUsuario = Integer.parseInt(stringIdUsuario);
			return UsuarioControlador.getControlador().find(idUsuario);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	private static Key getGeneratedKey () {
		if (key == null) {
			key = MacProvider.generateKey();
		}
		return key;
	}

}

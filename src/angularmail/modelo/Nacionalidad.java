package angularmail.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the nacionalidad database table.
 * 
 */
@Entity
@Table(name = "nacionalidad")
@NamedQuery(name="Nacionalidad.findAll", query="SELECT n FROM Nacionalidad n")
public class Nacionalidad extends Entidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="descripcion")
	private String descripcion;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="nacionalidad")
	private List<Usuario> usuarios;

	public Nacionalidad() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setNacionalidad(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setNacionalidad(null);

		return usuario;
	}

}
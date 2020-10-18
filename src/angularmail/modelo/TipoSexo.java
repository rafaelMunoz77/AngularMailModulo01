package angularmail.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tiposexo database table.
 * 
 */
@Entity
@Table(name = "tiposexo")
@NamedQuery(name="TipoSexo.findAll", query="SELECT t FROM TipoSexo t")
public class TipoSexo extends Entidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="descripcion")
	private String descripcion;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="tipoSexo")
	private List<Usuario> usuarios;

	public TipoSexo() {
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
		usuario.setTipoSexo(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setTipoSexo(null);

		return usuario;
	}

}
package angularmail.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the destinatariomensaje database table.
 * 
 */
@Entity
@Table(name = "destinatariomensaje")
@NamedQuery(name="DestinatarioMensaje.findAll", query="SELECT d FROM DestinatarioMensaje d")
public class DestinatarioMensaje extends Entidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="archivado")
	private boolean archivado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechaEliminacion")
	private Date fechaEliminacion;

	@Column(name="leido")
	private boolean leido;

	@Column(name="spam")
	private boolean spam;

	//bi-directional many-to-one association to Mensaje
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idMensaje")
	private Mensaje mensaje;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idDestinatario")
	private Usuario usuario;

	public DestinatarioMensaje() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getArchivado() {
		return this.archivado;
	}

	public void setArchivado(boolean archivado) {
		this.archivado = archivado;
	}

	public Date getFechaEliminacion() {
		return this.fechaEliminacion;
	}

	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	public boolean getLeido() {
		return this.leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	public boolean getSpam() {
		return this.spam;
	}

	public void setSpam(boolean spam) {
		this.spam = spam;
	}

	public Mensaje getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
package angularmail.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the mensaje database table.
 * 
 */
@Entity
@NamedQuery(name="Mensaje.findAll", query="SELECT m FROM Mensaje m")
public class Mensaje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String asunto;

	@Lob
	private String cuerpo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	//bi-directional many-to-one association to DestinatarioMensaje
	@OneToMany(mappedBy="mensaje")
	private List<DestinatarioMensaje> destinatarioMensajes;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEmisor")
	private Usuario usuarioEmisor;

	public Mensaje() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAsunto() {
		return this.asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getCuerpo() {
		return this.cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<DestinatarioMensaje> getDestinatarioMensajes() {
		return this.destinatarioMensajes;
	}

	public void setDestinatarioMensajes(List<DestinatarioMensaje> destinatarioMensajes) {
		this.destinatarioMensajes = destinatarioMensajes;
	}

	public DestinatarioMensaje addDestinatarioMensaje(DestinatarioMensaje destinatarioMensaje) {
		getDestinatarioMensajes().add(destinatarioMensaje);
		destinatarioMensaje.setMensaje(this);

		return destinatarioMensaje;
	}

	public DestinatarioMensaje removeDestinatarioMensaje(DestinatarioMensaje destinatarioMensaje) {
		getDestinatarioMensajes().remove(destinatarioMensaje);
		destinatarioMensaje.setMensaje(null);

		return destinatarioMensaje;
	}

	public Usuario getUsuarioEmisor() {
		return this.usuarioEmisor;
	}

	public void setUsuarioEmisor(Usuario usuarioEmisor) {
		this.usuarioEmisor = usuarioEmisor;
	}

}
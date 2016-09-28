package by.pvt.dudko.company.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;




@Entity
// XXX prevent using reductions in names, use full name.  Role. By the way, 'rol' is similar to  'ro1'
@Table(name="ROL")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 5L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idRol")
	private int idRol;
	@Column(name="rol")
	private String rol;
	@OneToMany(mappedBy="rol" ,cascade=CascadeType.ALL)
    private Set<Client> clients;
	
	public Role() {
	}
	
	public Role(String rol, int idRol) {
		this.rol = rol;
		this.idRol = idRol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}
	
	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

	public String getRol() {
		return rol;
	}
	
	public Set<Client> getClients() {
		return clients;
	}

	public int getIdRol() {
		return idRol;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Role))
			return false;
		Role rol = (Role) o;
		if (idRol != 0 ? idRol != rol.idRol : rol.idRol != 0)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = idRol != 0 ? new Integer(idRol).hashCode() : 0;
	return result;
	}

	@Override
	public String toString() {
		return "Rol : id: " + idRol + " : " + rol;
	}
}

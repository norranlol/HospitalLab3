package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the hospital database table.
 * 
 */
@Entity
@NamedQuery(name="Hospital.findAll", query="SELECT h FROM Hospital h")
public class Hospital implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String title;

	//bi-directional many-to-many association to Doctor
	@ManyToMany(mappedBy="hospitals")
	private List<Doctor> doctors;

	public Hospital() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Doctor> getDoctors() {
		return this.doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

}
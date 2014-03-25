package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the speciality database table.
 * 
 */
@Entity
@NamedQuery(name="Speciality.findAll", query="SELECT s FROM Speciality s")
public class Speciality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String title;

	//bi-directional many-to-one association to Doctor
	@OneToMany(mappedBy="speciality")
	private List<Doctor> doctors;

	public Speciality() {
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

	public Doctor addDoctor(Doctor doctor) {
		getDoctors().add(doctor);
		doctor.setSpeciality(this);

		return doctor;
	}

	public Doctor removeDoctor(Doctor doctor) {
		getDoctors().remove(doctor);
		doctor.setSpeciality(null);

		return doctor;
	}

}
package model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

import listeners.DoctorEntityListener;


/**
 * The persistent class for the doctor database table.
 * 
 */
@Entity
@EntityListeners(DoctorEntityListener.class)
@NamedQuery(name="Doctor.findAll", query="SELECT d FROM Doctor d")
public class Doctor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	private String patronymic;

	private String schedule;

	private String surname;
	
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	//bi-directional many-to-many association to Hospital
	@ManyToMany
	@JoinTable(
		name="doctorhospital"
		, joinColumns={
			@JoinColumn(name="doctorId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="hospitalId")
			}
		)
	private List<Hospital> hospitals;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="CategoryId")
	private Category category;

	//bi-directional many-to-one association to Speciality
	@ManyToOne
	@JoinColumn(name="SpecialityId")
	private Speciality speciality;

	public Doctor() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatronymic() {
		return this.patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getSchedule() {
		return this.schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Hospital> getHospitals() {
		return this.hospitals;
	}

	public void setHospitals(List<Hospital> hospitals) {
		this.hospitals = hospitals;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Speciality getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@Override
	public String toString() {
		return "Doctor [ID = " + id + ", Surname = " + surname + ", Name = " + name +
				", Patronymic = " + patronymic + ", Speciality = " + speciality.getTitle() + 
				", Category = " + category.getTitle() + ", Schedule = " + schedule + 
				", DateOfBirth = " + dateOfBirth;
	}
}
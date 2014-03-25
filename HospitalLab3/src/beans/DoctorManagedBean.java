package beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Category;
import model.Doctor;
import model.Hospital;
import model.Speciality;

@Named("doctorMB")
@SessionScoped
public class DoctorManagedBean implements Serializable {
	
	private List<Category> listOfCategories;
	
	private String surname;
	
	private String name;
	
	private String patronymic;
	
	private Speciality speciality;
	
	private int categoryId;
	
	private int hospitalId;
	
	private int specialityId;
	
	private String schedule;
	
	private Date dateOfBirth;
	
	@Inject
	private SpecialityManagedBean specialityMB;
	
	@Inject
	private HospitalManagedBean hospitalMB;
	
	private List<Doctor> listOfDoctors;
	
	private Speciality selectedSpeciality;
	
	private Hospital selectedHospital;
	
	private Doctor selectedDoctor;
	
	private Category categoryOfDoctor;
	
	private String typeOfPage = "";
	
	public String showDoctorsBySpeciality(Speciality speciality){
		listOfDoctors = specialityMB.getDao().getListOfDoctorsBySpeciality(speciality);
		selectedSpeciality = speciality;
		typeOfPage = "Specialities";
		return "doctorsOfSpecialities";
	}
	
	public String showDoctorsOfHospital(Hospital hospital){
		listOfDoctors = specialityMB.getDao().getListOfDoctorByHospital(hospital);
		selectedHospital = hospital;
		typeOfPage = "Hospitals";
		return "doctorsOfHospitals";
	}
	
	public String showInfo(Doctor doctor){
		selectedDoctor = doctor;
		categoryOfDoctor = specialityMB.getDao().findCategoryByDoctor(selectedDoctor);
		return "infoAboutDoctor";
	}
	
	public String deleteDoctor(Doctor doctor){
		selectedDoctor = doctor;
		specialityMB.getDao().deleteDoctor(doctor);
		if (typeOfPage.equals("Specialities")) return showDoctorsBySpeciality(selectedSpeciality);
			else return showDoctorsOfHospital(selectedHospital);	
	}
	
	public String goToEditDoctor(Doctor doctor){
		selectedDoctor = doctor;
		listOfCategories = specialityMB.getDao().getListOfCategories();
		return "editDoctorPage";
	}
	
	public String editDoctor(){
		Doctor doctor = new Doctor();
		doctor.setId(selectedDoctor.getId());
		doctor.setSurname(selectedDoctor.getSurname());
		doctor.setName(selectedDoctor.getName());
		doctor.setPatronymic(selectedDoctor.getPatronymic());
		doctor.setCategory(selectedDoctor.getCategory());
		//doctor.setSpeciality(selectedDoctor.getSpeciality());
		doctor.setSchedule(selectedDoctor.getSchedule());
		doctor.setDateOfBirth(selectedDoctor.getDateOfBirth());
		specialityMB.getDao().editDoctor(doctor, categoryId, specialityId, hospitalId);
		if (typeOfPage.equals("Specialities")) return showDoctorsBySpeciality(selectedSpeciality);		
			else return showDoctorsOfHospital(selectedHospital);
	}
	
	public String goToAddDoctor(){
		specialityMB.setListOfSpecialities(specialityMB.getDao().getListOfSpecialities());
		hospitalMB.setListOfHospitals(hospitalMB.getDao().getListOfHospitals());
		listOfCategories = specialityMB.getDao().getListOfCategories();
		return "addDoctor";
	}
	
	public String addDoctor(){
		Doctor doctor = new Doctor();
		doctor.setSurname(surname);
		doctor.setName(name);
		doctor.setPatronymic(patronymic);
		//doctor.setSpeciality(selectedSpeciality);
		doctor.setSchedule(schedule);
		doctor.setDateOfBirth(dateOfBirth);
		specialityMB.getDao().addDoctor(doctor, categoryId, specialityId, hospitalId);
		surname = "";
		name = "";
		patronymic = "";
		speciality = null;
		categoryId = 0;
		schedule = "";
		dateOfBirth = null;
		if (typeOfPage.equals("Specialities")) return showDoctorsBySpeciality(selectedSpeciality);
			else return showDoctorsOfHospital(selectedHospital); 
	}
	
	public String finishEditDoctor(Doctor doctor){
		return showDoctorsBySpeciality(selectedSpeciality);
	}

	public List<Doctor> getListOfDoctors() {
		return listOfDoctors;
	}

	public void setListOfDoctors(List<Doctor> listOfDoctors) {
		this.listOfDoctors = listOfDoctors;
	}

	public Speciality getSelectedSpeciality() {
		return selectedSpeciality;
	}

	public Category getCategoryOfDoctor() {
		return categoryOfDoctor;
	}

	public Doctor getSelectedDoctor() {
		return selectedDoctor;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Category> getListOfCategories() {
		return listOfCategories;
	}

	public void setListOfCategories(List<Category> listOfCategories) {
		this.listOfCategories = listOfCategories;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Hospital getSelectedHospital() {
		return selectedHospital;
	}

	public void setSelectedHospital(Hospital selectedHospital) {
		this.selectedHospital = selectedHospital;
	}

	public String getTypeOfPage() {
		return typeOfPage;
	}

	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}

	public int getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(int specialityId) {
		this.specialityId = specialityId;
	}

	public HospitalManagedBean getHospitalMB() {
		return hospitalMB;
	}

	public void setHospitalMB(HospitalManagedBean hospitalMB) {
		this.hospitalMB = hospitalMB;
	}
}

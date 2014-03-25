package ejb;

import java.util.List;

import javax.ejb.Remote;

import model.Category;
import model.Doctor;
import model.Hospital;
import model.Speciality;

@Remote
public interface DAOHospitalSessionBeanRemote {
	public List<Speciality> getListOfSpecialities();
	public List<Category> getListOfCategories();
	public void addSpeciality(Speciality speciality);
	public void deleteSpeciality(Speciality speciality);
	public List<Doctor> getListOfDoctorsBySpeciality(Speciality speciality);
	public Category findCategoryByDoctor(Doctor doctor);
	public void deleteDoctor(Doctor doctor);
	public void addDoctor(Doctor doctor, int categoryId, int specialityId, int hospitalId);
	public void editDoctor(Doctor doctor, int categoryId, int specialityId, int hospitalId);
	public List<Hospital> getListOfHospitals();
	public List<Doctor> getListOfDoctorByHospital(Hospital hospital);
	//public List<Doctor> getListOfDoctors();
}

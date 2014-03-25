package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import model.Category;
import model.Doctor;
import model.Hospital;
import model.Speciality;

@Stateless
public class DAOHospitalSessionBean implements DAOHospitalSessionBeanRemote {

	@PersistenceUnit(unitName="HospitalLab3")
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
    public DAOHospitalSessionBean() {
    }
    
    @PostConstruct
    private void createEntityManager(){
    	em = emf.createEntityManager();
    }
    
    @Override
    public void addSpeciality(Speciality speciality){
     	String query = "insert into Speciality values(0,?)";
    	em.joinTransaction();
    	em.createNativeQuery(query).setParameter(1, speciality.getTitle()).executeUpdate();
    }

    @Override
    public void deleteSpeciality(Speciality speciality){	
    	Speciality specialityFind = em.find(Speciality.class, speciality.getId());
    	List<Doctor> listOfDoctors = getListOfDoctorsBySpeciality(specialityFind);
    	em.joinTransaction();
    	for (Doctor doctor : listOfDoctors)
    		deleteDoctor(doctor);
    	em.remove(specialityFind);
    	em.flush();
    }
    
    @Override
    public void deleteDoctor(Doctor doctor){
    	Doctor doctorFind = em.find(Doctor.class, doctor.getId());
    	em.joinTransaction();
    	em.remove(doctorFind);
    	em.flush();
    }
    
    @Override
    public List<Speciality> getListOfSpecialities(){
    	CriteriaQuery<Speciality> criteriaQuery = em.getCriteriaBuilder().createQuery(Speciality.class);
    	criteriaQuery.select(criteriaQuery.from(Speciality.class));
    	List<Speciality> listOfSpecialities = em.createQuery(criteriaQuery).getResultList();
    	return listOfSpecialities;
    }
    
    @Override
    public List<Category> getListOfCategories(){
    	CriteriaQuery<Category> criteriaQuery = em.getCriteriaBuilder().createQuery(Category.class);
    	criteriaQuery.select(criteriaQuery.from(Category.class));
    	List<Category> listOfCategories = em.createQuery(criteriaQuery).getResultList();
    	return listOfCategories;
    }
    
    @Override
    public List<Hospital> getListOfHospitals(){
    	CriteriaQuery<Hospital> criteriaQuery = em.getCriteriaBuilder().createQuery(Hospital.class);
    	criteriaQuery.select(criteriaQuery.from(Hospital.class));
    	List<Hospital> listOfHospitals = em.createQuery(criteriaQuery).getResultList();
    	return listOfHospitals;
    }
    
    @Override
    public List<Doctor> getListOfDoctorsBySpeciality(Speciality speciality){
    	Speciality spec = em.find(Speciality.class, speciality.getId());
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Doctor> criteriaQuery = cb.createQuery(Doctor.class);
    	Root<Doctor> doctor = criteriaQuery.from(Doctor.class); 
    	Predicate predicate = doctor.get("speciality").in(speciality);
    	criteriaQuery.select(doctor).where(predicate);
    	List<Doctor> doctors = em.createQuery(criteriaQuery).getResultList();
    	return doctors;
    } 
    
    @Override
    public List<Doctor> getListOfDoctorByHospital(Hospital hospital){
    	Hospital hosp = em.find(Hospital.class, hospital.getId());
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Doctor> criteriaQuery = cb.createQuery(Doctor.class);
    	Root<Doctor> doctor = criteriaQuery.from(Doctor.class);
    	Predicate predicate = doctor.get("hospitals").in(hospital);
    	criteriaQuery.select(doctor).where(predicate);
    	List<Doctor> doctors = em.createQuery(criteriaQuery).getResultList();
    	return doctors;
    }
    
    @Override
    public Category findCategoryByDoctor(Doctor doctor){
    	Category category = em.find(Category.class, doctor.getCategory().getId());
    	return category;
    }
    
    private Category findCategoryById(int categoryId){
    	Category category = em.find(Category.class, categoryId);
    	return category;
    }
    
    private Doctor findDoctorById(int doctorId){
    	Doctor doctor = em.find(Doctor.class, doctorId);
    	return doctor;
    }
    
    @Override
    public void addDoctor(Doctor doctor, int categoryId, int specialityId, int hospitalId){
    	Category category = findCategoryById(categoryId);
    	Speciality speciality = em.find(Speciality.class, specialityId);
    	Hospital hospital = em.find(Hospital.class, hospitalId);
    	doctor.setCategory(category);
    	doctor.setSpeciality(speciality);
    	//em.joinTransaction();
    	//em.persist(doctor);
    	String queryInsert = "INSERT INTO doctor(surname,name,patronymic,SpecialityId,CategoryId,schedule,dateOfBirth) " +
        		"VALUES (?1,?2,?3,?4,?5,?6,?7)";
        em.joinTransaction();
       	em.createNativeQuery(queryInsert).setParameter(1, doctor.getSurname())
       	.setParameter(2, doctor.getName())
       	.setParameter(3, doctor.getPatronymic())
       	.setParameter(4, doctor.getSpeciality().getId())
       	.setParameter(5, doctor.getCategory().getId())
       	.setParameter(6, doctor.getSchedule())
       	.setParameter(7, doctor.getDateOfBirth()).executeUpdate();
       	
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery criteriaQuery = cb.createQuery();
    	Root root = criteriaQuery.from(Doctor.class);
    	criteriaQuery.select(cb.max(root.get("id")));
    	Query query = em.createQuery(criteriaQuery);
    	Integer result = (Integer)query.getSingleResult();
    	Doctor doctorFind = em.find(Doctor.class, result);
    	List<Hospital> hospitals = new ArrayList<Hospital>();
    	hospitals.add(hospital);
    	doctorFind.setHospitals(hospitals);
    	em.joinTransaction();
    	em.merge(doctorFind);
    }
    
    @Override
    public void editDoctor(Doctor doctor, int categoryId, int specialityId, int hospitalId){
    	Category category = em.find(Category.class, categoryId);
    	Speciality speciality = em.find(Speciality.class, specialityId);
    	Hospital hospital = em.find(Hospital.class, hospitalId);
    	Doctor findDoctor = findDoctorById(doctor.getId());
    	findDoctor.setSurname(doctor.getSurname());
    	findDoctor.setName(doctor.getName());
    	findDoctor.setPatronymic(doctor.getPatronymic());
    	findDoctor.setCategory(category);
    	findDoctor.setSpeciality(speciality);
    	findDoctor.setSchedule(doctor.getSchedule());
    	findDoctor.setDateOfBirth(doctor.getDateOfBirth());
    	List<Hospital> hospitals = new ArrayList<Hospital>();
    	hospitals.add(hospital);
    	findDoctor.setHospitals(hospitals);
    	em.joinTransaction();
    	em.merge(findDoctor);
    }
    
}

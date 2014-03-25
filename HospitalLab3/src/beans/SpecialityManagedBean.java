package beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Speciality;
import ejb.DAOHospitalSessionBeanRemote;

@Named("specialityMB")
@RequestScoped
public class SpecialityManagedBean implements Serializable {
	
	@EJB
	private DAOHospitalSessionBeanRemote dao;
	
	private static final long serialVersionUID = 1L;
	
	private List<Speciality> listOfSpecialities;
	
	private String title;
	
	public List<Speciality> getListOfSpecialities() {
		listOfSpecialities = dao.getListOfSpecialities();
		return listOfSpecialities;
	}	
	
	public String addSpeciality(){
		Speciality speciality = new Speciality();
		speciality.setTitle(this.title);
		dao.addSpeciality(speciality);
		return "specialities";
	}
	
	public String deleteSpeciality(Speciality speciality){
		dao.deleteSpeciality(speciality);
		return null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DAOHospitalSessionBeanRemote getDao() {
		return dao;
	}

	public void setListOfSpecialities(List<Speciality> listOfSpecialities) {
		this.listOfSpecialities = listOfSpecialities;
	}
}

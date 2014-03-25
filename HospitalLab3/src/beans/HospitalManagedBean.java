package beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import model.Hospital;
import ejb.DAOHospitalSessionBeanRemote;

@Named("hospitalMB")
@RequestScoped
public class HospitalManagedBean implements Serializable {
	
	@EJB
	private DAOHospitalSessionBeanRemote dao;
	
	private List<Hospital> listOfHospitals;

	public List<Hospital> getListOfHospitals() {
		listOfHospitals = dao.getListOfHospitals();
		return listOfHospitals;
	}

	public void setListOfHospitals(List<Hospital> listOfHospitals) {
		this.listOfHospitals = listOfHospitals;
	}

	public DAOHospitalSessionBeanRemote getDao() {
		return dao;
	}
}

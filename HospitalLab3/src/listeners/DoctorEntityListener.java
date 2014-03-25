package listeners;

import java.io.IOException;
import java.util.Date;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import model.Doctor;

public class DoctorEntityListener {
	
	private Logger logger = new Logger(); 

	@PrePersist
	public void prePersist(Doctor doctor) throws IOException {
		logger.writeToFile("CurrentDate = " + new Date() + ". " + doctor.toString());
	}
 
	@PostPersist
	public void postPersist(Doctor doctor) throws IOException {
		logger.writeToFile("CurrentDate = " + new Date() + ". " + doctor.toString());
	}
 
	@PreUpdate
	public void preUpdate(Doctor doctor) throws IOException {
		logger.writeToFile("CurrentDate = " + new Date() + ". " + doctor.toString());
	}
 
	@PostUpdate
	public void postUpdate(Doctor doctor) throws IOException {
		logger.writeToFile("CurrentDate = " + new Date() + ". " + doctor.toString());
	}
 
	@PostLoad
	public void postLoad(Doctor doctor) throws IOException {
		logger.writeToFile("CurrentDate = " + new Date() + ". " + doctor.toString());
	}
	
	@PreRemove
	public void preRemove(Doctor doctor) throws IOException {
		logger.writeToFile("CurrentDate = " + new Date() + ". " + doctor.toString());
	}
 
	@PostRemove
	public void postRemove(Doctor doctor) throws IOException {
		logger.writeToFile("CurrentDate = " + new Date() + ". " + doctor.toString());
	}	
}

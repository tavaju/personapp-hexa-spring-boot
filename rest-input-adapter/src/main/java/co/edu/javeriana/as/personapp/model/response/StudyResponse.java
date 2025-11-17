package co.edu.javeriana.as.personapp.model.response;

import java.time.LocalDate;

import co.edu.javeriana.as.personapp.model.request.StudyRequest;

public class StudyResponse extends StudyRequest {
	
	private String status;
	
	public StudyResponse(Integer personaCc, Integer profesionId, LocalDate fechaGraduacion, String universidad, String database, String status) {
		super(personaCc, profesionId, fechaGraduacion, universidad, database);
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}


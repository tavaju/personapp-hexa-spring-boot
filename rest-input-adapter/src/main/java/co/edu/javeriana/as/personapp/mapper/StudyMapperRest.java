package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.model.request.StudyRequest;
import co.edu.javeriana.as.personapp.model.response.StudyResponse;

@Mapper
public class StudyMapperRest {
	
	public StudyResponse fromDomainToAdapterRestMaria(Study study) {
		return fromDomainToAdapterRest(study, "MariaDB");
	}
	
	public StudyResponse fromDomainToAdapterRestMongo(Study study) {
		return fromDomainToAdapterRest(study, "MongoDB");
	}
	
	public StudyResponse fromDomainToAdapterRest(Study study, String database) {
		Integer personaCc = study.getPerson() != null ? study.getPerson().getIdentification() : null;
		Integer profesionId = study.getProfession() != null ? study.getProfession().getIdentification() : null;
		return new StudyResponse(
				personaCc,
				profesionId,
				study.getGraduationDate(),
				study.getUniversityName(),
				database,
				"OK");
	}

	public Study fromAdapterToDomain(StudyRequest request) {
		Study study = new Study();
		if (request.getPersonaCc() != null) {
			Person person = new Person();
			person.setIdentification(request.getPersonaCc());
			study.setPerson(person);
		}
		if (request.getProfesionId() != null) {
			Profession profession = new Profession();
			profession.setIdentification(request.getProfesionId());
			study.setProfession(profession);
		}
		study.setGraduationDate(request.getFechaGraduacion());
		study.setUniversityName(request.getUniversidad());
		return study;
	}
}


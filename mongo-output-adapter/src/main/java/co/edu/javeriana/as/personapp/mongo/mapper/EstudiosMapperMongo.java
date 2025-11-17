package co.edu.javeriana.as.personapp.mongo.mapper;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument;
import co.edu.javeriana.as.personapp.mongo.document.PersonaDocument;
import co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument;

@Mapper
public class EstudiosMapperMongo {

	@Autowired
	@Lazy
	private PersonaMapperMongo personaMapperMongo;

	@Autowired
	private ProfesionMapperMongo profesionMapperMongo;

	public EstudiosDocument fromDomainToAdapter(Study study) {
		EstudiosDocument estudio = new EstudiosDocument();
		estudio.setId(validateId(study.getPerson().getIdentification(), study.getProfession().getIdentification()));
		estudio.setPrimaryPersona(validatePrimaryPersona(study.getPerson()));
		estudio.setPrimaryProfesion(validatePrimaryProfesion(study.getProfession()));
		estudio.setFecha(validateFecha(study.getGraduationDate()));
		estudio.setUniver(validateUniver(study.getUniversityName()));
		return estudio;
	}

	private String validateId(Integer identificationPerson, Integer identificationProfession) {
		return identificationPerson + "-" + identificationProfession;
	}

	private PersonaDocument validatePrimaryPersona(Person person) {
		PersonaDocument personaDoc = personaMapperMongo.fromDomainToAdapter(person);
		personaDoc.setEstudios(null);
		return personaDoc;
	}

	private ProfesionDocument validatePrimaryProfesion(Profession profession) {
		return profession != null ? profesionMapperMongo.fromDomainToAdapter(profession) : new ProfesionDocument();
	}

	private LocalDate validateFecha(LocalDate graduationDate) {
		return graduationDate != null ? graduationDate : null;
	}

	private String validateUniver(String universityName) {
		return universityName != null ? universityName : "";
	}

	public Study fromAdapterToDomain(EstudiosDocument estudiosDocument) {
		Study study = new Study();
		study.setPerson(personaMapperMongo.fromAdapterToDomain(estudiosDocument.getPrimaryPersona()));
		study.setProfession(profesionMapperMongo.fromAdapterToDomain(estudiosDocument.getPrimaryProfesion()));
		study.setGraduationDate(validateGraduationDate(estudiosDocument.getFecha()));
		study.setUniversityName(validateUniversityName(estudiosDocument.getUniver()));
		return study;
	}

	public Study fromAdapterToDomainBasic(EstudiosDocument estudiosDocument) {
		Study study = new Study();
		
		Person person;
		if (estudiosDocument.getPrimaryPersona() != null) {
			person = personaMapperMongo.fromAdapterToDomainBasic(estudiosDocument.getPrimaryPersona());
		} else {
			person = new Person();
			person.setIdentification(0);
			person.setFirstName("Desconocido");
			person.setLastName("Desconocido");
			person.setGender(co.edu.javeriana.as.personapp.domain.Gender.OTHER);
		}
		study.setPerson(person);

		Profession profession;
		if (estudiosDocument.getPrimaryProfesion() != null) {
			profession = profesionMapperMongo.fromAdapterToDomainBasic(estudiosDocument.getPrimaryProfesion());
		} else {
			profession = new Profession();
			profession.setIdentification(0);
			profession.setName("Desconocido");
		}
		study.setProfession(profession);

		study.setGraduationDate(validateGraduationDate(estudiosDocument.getFecha()));
		study.setUniversityName(validateUniversityName(estudiosDocument.getUniver()));

		return study;
	}

	private LocalDate validateGraduationDate(LocalDate fecha) {
		return fecha != null ? fecha : null;
	}

	private String validateUniversityName(String univer) {
		return univer != null ? univer : "";
	}
}
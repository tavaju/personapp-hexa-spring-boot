package co.edu.javeriana.as.personapp.mongo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument;
import co.edu.javeriana.as.personapp.mongo.document.PersonaDocument;
import co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument;

@Mapper
public class PersonaMapperMongo {

	@Autowired
	@Lazy
	private EstudiosMapperMongo estudiosMapperMongo;

	public PersonaDocument fromDomainToAdapter(Person person) {
		PersonaDocument personaDocument = new PersonaDocument();
		personaDocument.setId(person.getIdentification());
		personaDocument.setNombre(person.getFirstName());
		personaDocument.setApellido(person.getLastName());
		personaDocument.setGenero(validateGenero(person.getGender()));
		personaDocument.setEdad(person.getAge() != null ? person.getAge() : 0);
		personaDocument.setEstudios(validateEstudios(person.getStudies()));
		personaDocument.setTelefonos(new ArrayList<>());
		return personaDocument;
	}

	public Person fromAdapterToDomain(PersonaDocument personaDocument) {
		Person person = new Person();
		person.setIdentification(personaDocument.getId() != null ? personaDocument.getId() : 0);
		person.setFirstName(personaDocument.getNombre() != null ? personaDocument.getNombre() : "Desconocido");
		person.setLastName(personaDocument.getApellido() != null ? personaDocument.getApellido() : "Desconocido");
		person.setGender(validateGender(personaDocument.getGenero()));
		person.setAge(personaDocument.getEdad() != null ? personaDocument.getEdad() : 0);
		person.setStudies(validateStudies(personaDocument.getEstudios()));
		person.setPhoneNumbers(new ArrayList<>());
		return person;
	}

	private String validateGenero(Gender gender) {
		return gender == Gender.FEMALE ? "F" : gender == Gender.MALE ? "M" : " ";
	}

	private Gender validateGender(String genero) {
		return "F".equals(genero) ? Gender.FEMALE : "M".equals(genero) ? Gender.MALE : Gender.OTHER;
	}

	private List<EstudiosDocument> validateEstudios(List<Study> studies) {
		return studies != null ? studies.stream()
				.map(study -> {
					EstudiosDocument doc = estudiosMapperMongo.fromDomainToAdapter(study);
					doc.setPrimaryPersona(null);
					return doc;
				})
				.collect(Collectors.toList()) : new ArrayList<>();
	}

	private List<Study> validateStudies(List<EstudiosDocument> estudiosDocuments) {
		return estudiosDocuments != null ? estudiosDocuments.stream()
				.map(estudiosMapperMongo::fromAdapterToDomainBasic)
				.collect(Collectors.toList()) : new ArrayList<>();
	}

	public Person fromAdapterToDomainBasic(PersonaDocument personaDocument) {
		Person person = new Person();
		person.setIdentification(personaDocument.getId() != null ? personaDocument.getId() : 0);
		person.setFirstName(personaDocument.getNombre() != null ? personaDocument.getNombre() : "Desconocido");
		person.setLastName(personaDocument.getApellido() != null ? personaDocument.getApellido() : "Desconocido");
		person.setGender(validateGender(personaDocument.getGenero()));
		person.setAge(personaDocument.getEdad() != null ? personaDocument.getEdad() : 0);
		// No cargar 'studies' ni 'phoneNumbers' para evitar referencias cíclicas
		return person;
	}
}
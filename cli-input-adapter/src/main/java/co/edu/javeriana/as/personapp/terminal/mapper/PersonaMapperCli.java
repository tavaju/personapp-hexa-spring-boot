package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;

@Mapper
public class PersonaMapperCli {

	public PersonaModelCli fromDomainToAdapterCli(Person person) {
		PersonaModelCli personaModelCli = new PersonaModelCli();
		personaModelCli.setCc(person.getIdentification());
		personaModelCli.setNombre(person.getFirstName());
		personaModelCli.setApellido(person.getLastName());
		personaModelCli.setGenero(person.getGender().toString());
		personaModelCli.setEdad(person.getAge());
		return personaModelCli;
	}

	public Person fromAdapterCliToDomain(PersonaModelCli personaModelCli) {
		Gender gender;
		try {
			gender = Gender.valueOf(personaModelCli.getGenero().toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			gender = Gender.OTHER;
		}
		Person person = new Person(
			personaModelCli.getCc(),
			personaModelCli.getNombre(),
			personaModelCli.getApellido(),
			gender
		);
		person.setAge(personaModelCli.getEdad());
		return person;
	}
}

package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.terminal.mapper.PersonaMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class PersonaInputAdapterCli {

	@Autowired
	@Qualifier("personOutputAdapterMaria")
	private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("personOutputAdapterMongo")
	private PersonOutputPort personOutputPortMongo;

	@Autowired
	private PersonaMapperCli personaMapperCli;

	PersonInputPort personInputPort;

	public void setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMaria);
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public void historial1() {
		log.info("Into historial PersonaEntity in Input Adapter");
		List<PersonaModelCli> persona = personInputPort.findAll().stream().map(personaMapperCli::fromDomainToAdapterCli)
					.collect(Collectors.toList());
		persona.forEach(p -> System.out.println(p.toString()));
	}
	
	public void historial() {
	    log.info("Into historial PersonaEntity in Input Adapter");
	    personInputPort.findAll().stream()
	        .map(personaMapperCli::fromDomainToAdapterCli)
	        .forEach(System.out::println);
	}

	public void crear(Scanner keyboard) {
		try {
			System.out.print("Ingrese la cédula: ");
			Integer cc = keyboard.nextInt();
			keyboard.nextLine();
			
			System.out.print("Ingrese el nombre: ");
			String nombre = keyboard.nextLine();
			
			System.out.print("Ingrese el apellido: ");
			String apellido = keyboard.nextLine();
			
			System.out.print("Ingrese el género (MALE/FEMALE/OTHER): ");
			String generoStr = keyboard.nextLine().toUpperCase();
			if (generoStr.isEmpty()) {
				generoStr = "OTHER";
			}
			
			System.out.print("Ingrese la edad o presione Enter para omitir: ");
			String edadStr = keyboard.nextLine();
			Integer edad = null;
			if (!edadStr.isEmpty()) {
				try {
					edad = Integer.parseInt(edadStr);
				} catch (NumberFormatException e) {
					log.warn("Edad inválida, se omitirá");
				}
			}
			
			PersonaModelCli personaModelCli = new PersonaModelCli(cc, nombre, apellido, generoStr, edad);
			Person person = personaMapperCli.fromAdapterCliToDomain(personaModelCli);
			Person created = personInputPort.create(person);
			System.out.println("Persona creada exitosamente: " + personaMapperCli.fromDomainToAdapterCli(created));
		} catch (Exception e) {
			log.error("Error al crear persona: " + e.getMessage());
		}
	}

	public void editar(Scanner keyboard) {
		try {
			System.out.print("Ingrese la cédula de la persona a editar: ");
			Integer cc = keyboard.nextInt();
			keyboard.nextLine();
			
			Person oldPerson = personInputPort.findOne(cc);
			
			System.out.print("Ingrese el nuevo nombre (actual: " + oldPerson.getFirstName() + "): ");
			String nombre = keyboard.nextLine();
			if (nombre.isEmpty()) {
				nombre = oldPerson.getFirstName();
			}
			
			System.out.print("Ingrese el nuevo apellido (actual: " + oldPerson.getLastName() + "): ");
			String apellido = keyboard.nextLine();
			if (apellido.isEmpty()) {
				apellido = oldPerson.getLastName();
			}
			
			System.out.print("Ingrese el nuevo género (actual: " + oldPerson.getGender() + ", MALE/FEMALE/OTHER): ");
			String generoStr = keyboard.nextLine().toUpperCase();
			if (generoStr.isEmpty()) {
				generoStr = oldPerson.getGender().toString();
			}
			
			System.out.print("Ingrese la nueva edad (actual: " + oldPerson.getAge() + ") o presione Enter para mantener: ");
			String edadStr = keyboard.nextLine();
			Integer edad = oldPerson.getAge();
			if (!edadStr.isEmpty()) {
				try {
					edad = Integer.parseInt(edadStr);
				} catch (NumberFormatException e) {
					log.warn("Edad inválida, se mantendrá la actual");
				}
			}
			
			PersonaModelCli personaModelCli = new PersonaModelCli(cc, nombre, apellido, generoStr, edad);
			Person person = personaMapperCli.fromAdapterCliToDomain(personaModelCli);
			Person updated = personInputPort.edit(cc, person);
			System.out.println("Persona actualizada exitosamente: " + personaMapperCli.fromDomainToAdapterCli(updated));
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al editar persona: " + e.getMessage());
		}
	}

	public void eliminar(Scanner keyboard) {
		try {
			System.out.print("Ingrese la cédula de la persona a eliminar: ");
			Integer cc = keyboard.nextInt();
			keyboard.nextLine();
			
			Boolean deleted = personInputPort.drop(cc);
			if (deleted) {
				System.out.println("Persona eliminada exitosamente");
			} else {
				System.out.println("No se pudo eliminar la persona");
			}
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al eliminar persona: " + e.getMessage());
		}
	}

	public void buscarPorId(Scanner keyboard) {
		try {
			System.out.print("Ingrese la cédula de la persona a buscar: ");
			Integer cc = keyboard.nextInt();
			keyboard.nextLine();
			
			Person person = personInputPort.findOne(cc);
			System.out.println(personaMapperCli.fromDomainToAdapterCli(person));
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al buscar persona: " + e.getMessage());
		}
	}

}

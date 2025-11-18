package co.edu.javeriana.as.personapp.terminal.adapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.application.port.in.StudyInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.StudyUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.terminal.mapper.StudyMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.StudyModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class StudyInputAdapterCli {

	@Autowired
	@Qualifier("studyOutputAdapterMaria")
	private StudyOutputPort studyOutputPortMaria;

	@Autowired
	@Qualifier("studyOutputAdapterMongo")
	private StudyOutputPort studyOutputPortMongo;

	@Autowired
	@Qualifier("personOutputAdapterMaria")
	private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("personOutputAdapterMongo")
	private PersonOutputPort personOutputPortMongo;

	@Autowired
	@Qualifier("professionOutputAdapterMaria")
	private ProfessionOutputPort professionOutputPortMaria;

	@Autowired
	@Qualifier("professionOutputAdapterMongo")
	private ProfessionOutputPort professionOutputPortMongo;

	@Autowired
	private StudyMapperCli studyMapperCli;

	StudyInputPort studyInputPort;
	PersonInputPort personInputPort;
	ProfessionInputPort professionInputPort;

	public void setStudyOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			studyInputPort = new StudyUseCase(studyOutputPortMaria);
			personInputPort = new co.edu.javeriana.as.personapp.application.usecase.PersonUseCase(personOutputPortMaria);
			professionInputPort = new co.edu.javeriana.as.personapp.application.usecase.ProfessionUseCase(professionOutputPortMaria);
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			studyInputPort = new StudyUseCase(studyOutputPortMongo);
			personInputPort = new co.edu.javeriana.as.personapp.application.usecase.PersonUseCase(personOutputPortMongo);
			professionInputPort = new co.edu.javeriana.as.personapp.application.usecase.ProfessionUseCase(professionOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public void historial() {
		log.info("Into historial Study in Input Adapter");
		studyInputPort.findAll().stream()
			.map(studyMapperCli::fromDomainToAdapterCli)
			.forEach(System.out::println);
	}

	public void crear(Scanner keyboard) {
		try {
			System.out.print("Ingrese la cédula de la persona: ");
			Integer personaCc = keyboard.nextInt();
			keyboard.nextLine();
			
			System.out.print("Ingrese el ID de la profesión: ");
			Integer profesionId = keyboard.nextInt();
			keyboard.nextLine();
			
			System.out.print("Ingrese la fecha de graduación (YYYY-MM-DD) o presione Enter para omitir: ");
			String fechaStr = keyboard.nextLine();
			LocalDate fechaGraduacion = null;
			if (!fechaStr.isEmpty()) {
				try {
					fechaGraduacion = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);
				} catch (DateTimeParseException e) {
					log.warn("Formato de fecha inválido, se omitirá la fecha");
				}
			}
			
			System.out.print("Ingrese el nombre de la universidad o presione Enter para omitir: ");
			String universidad = keyboard.nextLine();
			
			Person person = personInputPort.findOne(personaCc);
			Profession profession = professionInputPort.findOne(profesionId);
			StudyModelCli studyModelCli = new StudyModelCli(personaCc, null, profesionId, null, fechaGraduacion, universidad.isEmpty() ? null : universidad);
			Study study = studyMapperCli.fromAdapterCliToDomain(studyModelCli, person, profession);
			Study created = studyInputPort.create(study);
			System.out.println("Estudio creado exitosamente: " + studyMapperCli.fromDomainToAdapterCli(created));
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al crear estudio: " + e.getMessage());
		}
	}

	public void editar(Scanner keyboard) {
		try {
			System.out.print("Ingrese la cédula de la persona: ");
			Integer personaCc = keyboard.nextInt();
			keyboard.nextLine();
			
			System.out.print("Ingrese el ID de la profesión: ");
			Integer profesionId = keyboard.nextInt();
			keyboard.nextLine();
			
			Study oldStudy = studyInputPort.findOne(personaCc, profesionId);
			
			System.out.print("Ingrese la nueva fecha de graduación (YYYY-MM-DD) o presione Enter para mantener actual (" + oldStudy.getGraduationDate() + "): ");
			String fechaStr = keyboard.nextLine();
			LocalDate fechaGraduacion = oldStudy.getGraduationDate();
			if (!fechaStr.isEmpty()) {
				try {
					fechaGraduacion = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);
				} catch (DateTimeParseException e) {
					log.warn("Formato de fecha inválido, se mantendrá la fecha actual");
				}
			}
			
			System.out.print("Ingrese el nuevo nombre de la universidad o presione Enter para mantener actual (" + oldStudy.getUniversityName() + "): ");
			String universidad = keyboard.nextLine();
			if (universidad.isEmpty()) {
				universidad = oldStudy.getUniversityName();
			}
			
			Person person = personInputPort.findOne(personaCc);
			Profession profession = professionInputPort.findOne(profesionId);
			StudyModelCli studyModelCli = new StudyModelCli(personaCc, null, profesionId, null, fechaGraduacion, universidad);
			Study study = studyMapperCli.fromAdapterCliToDomain(studyModelCli, person, profession);
			Study updated = studyInputPort.edit(personaCc, profesionId, study);
			System.out.println("Estudio actualizado exitosamente: " + studyMapperCli.fromDomainToAdapterCli(updated));
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al editar estudio: " + e.getMessage());
		}
	}

	public void eliminar(Scanner keyboard) {
		try {
			System.out.print("Ingrese la cédula de la persona: ");
			Integer personaCc = keyboard.nextInt();
			keyboard.nextLine();
			
			System.out.print("Ingrese el ID de la profesión: ");
			Integer profesionId = keyboard.nextInt();
			keyboard.nextLine();
			
			Boolean deleted = studyInputPort.drop(personaCc, profesionId);
			if (deleted) {
				System.out.println("Estudio eliminado exitosamente");
			} else {
				System.out.println("No se pudo eliminar el estudio");
			}
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al eliminar estudio: " + e.getMessage());
		}
	}

	public void buscarPorId(Scanner keyboard) {
		try {
			System.out.print("Ingrese la cédula de la persona: ");
			Integer personaCc = keyboard.nextInt();
			keyboard.nextLine();
			
			System.out.print("Ingrese el ID de la profesión: ");
			Integer profesionId = keyboard.nextInt();
			keyboard.nextLine();
			
			Study study = studyInputPort.findOne(personaCc, profesionId);
			System.out.println(studyMapperCli.fromDomainToAdapterCli(study));
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al buscar estudio: " + e.getMessage());
		}
	}

	public void buscarPorPersona(Scanner keyboard) {
		try {
			System.out.print("Ingrese la cédula de la persona: ");
			Integer personaCc = keyboard.nextInt();
			keyboard.nextLine();
			
			List<Study> studies = studyInputPort.findByPerson(personaCc);
			studies.stream()
				.map(studyMapperCli::fromDomainToAdapterCli)
				.forEach(System.out::println);
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al buscar estudios: " + e.getMessage());
		}
	}

	public void buscarPorProfesion(Scanner keyboard) {
		try {
			System.out.print("Ingrese el ID de la profesión: ");
			Integer profesionId = keyboard.nextInt();
			keyboard.nextLine();
			
			List<Study> studies = studyInputPort.findByProfession(profesionId);
			studies.stream()
				.map(studyMapperCli::fromDomainToAdapterCli)
				.forEach(System.out::println);
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al buscar estudios: " + e.getMessage());
		}
	}

}


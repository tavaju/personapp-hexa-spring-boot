package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.ProfessionUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.terminal.mapper.ProfessionMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.ProfessionModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class ProfessionInputAdapterCli {

	@Autowired
	@Qualifier("professionOutputAdapterMaria")
	private ProfessionOutputPort professionOutputPortMaria;

	@Autowired
	@Qualifier("professionOutputAdapterMongo")
	private ProfessionOutputPort professionOutputPortMongo;

	@Autowired
	private ProfessionMapperCli professionMapperCli;

	ProfessionInputPort professionInputPort;

	public void setProfessionOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			professionInputPort = new ProfessionUseCase(professionOutputPortMaria);
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			professionInputPort = new ProfessionUseCase(professionOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public void historial() {
		log.info("Into historial Profession in Input Adapter");
		professionInputPort.findAll().stream()
			.map(professionMapperCli::fromDomainToAdapterCli)
			.forEach(System.out::println);
	}

	public void crear(Scanner keyboard) {
		try {
			System.out.print("Ingrese el ID de la profesión: ");
			Integer id = keyboard.nextInt();
			keyboard.nextLine();
			
			System.out.print("Ingrese el nombre de la profesión: ");
			String nombre = keyboard.nextLine();
			
			System.out.print("Ingrese la descripción o presione Enter para omitir: ");
			String descripcion = keyboard.nextLine();
			
			ProfessionModelCli professionModelCli = new ProfessionModelCli(id, nombre, descripcion.isEmpty() ? null : descripcion);
			Profession profession = professionMapperCli.fromAdapterCliToDomain(professionModelCli);
			Profession created = professionInputPort.create(profession);
			System.out.println("Profesión creada exitosamente: " + professionMapperCli.fromDomainToAdapterCli(created));
		} catch (Exception e) {
			log.error("Error al crear profesión: " + e.getMessage());
		}
	}

	public void editar(Scanner keyboard) {
		try {
			System.out.print("Ingrese el ID de la profesión a editar: ");
			Integer id = keyboard.nextInt();
			keyboard.nextLine();
			
			Profession oldProfession = professionInputPort.findOne(id);
			
			System.out.print("Ingrese el nuevo nombre (actual: " + oldProfession.getName() + "): ");
			String nombre = keyboard.nextLine();
			if (nombre.isEmpty()) {
				nombre = oldProfession.getName();
			}
			
			System.out.print("Ingrese la nueva descripción (actual: " + oldProfession.getDescription() + ") o presione Enter para mantener: ");
			String descripcion = keyboard.nextLine();
			if (descripcion.isEmpty()) {
				descripcion = oldProfession.getDescription();
			}
			
			ProfessionModelCli professionModelCli = new ProfessionModelCli(id, nombre, descripcion);
			Profession profession = professionMapperCli.fromAdapterCliToDomain(professionModelCli);
			Profession updated = professionInputPort.edit(id, profession);
			System.out.println("Profesión actualizada exitosamente: " + professionMapperCli.fromDomainToAdapterCli(updated));
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al editar profesión: " + e.getMessage());
		}
	}

	public void eliminar(Scanner keyboard) {
		try {
			System.out.print("Ingrese el ID de la profesión a eliminar: ");
			Integer id = keyboard.nextInt();
			keyboard.nextLine();
			
			Boolean deleted = professionInputPort.drop(id);
			if (deleted) {
				System.out.println("Profesión eliminada exitosamente");
			} else {
				System.out.println("No se pudo eliminar la profesión");
			}
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al eliminar profesión: " + e.getMessage());
		}
	}

	public void buscarPorId(Scanner keyboard) {
		try {
			System.out.print("Ingrese el ID de la profesión a buscar: ");
			Integer id = keyboard.nextInt();
			keyboard.nextLine();
			
			Profession profession = professionInputPort.findOne(id);
			System.out.println(professionMapperCli.fromDomainToAdapterCli(profession));
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al buscar profesión: " + e.getMessage());
		}
	}

}


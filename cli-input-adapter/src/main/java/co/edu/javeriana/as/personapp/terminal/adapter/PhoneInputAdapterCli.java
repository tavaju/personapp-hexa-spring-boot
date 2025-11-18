package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.in.PhoneInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PhoneUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.mapper.PhoneMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.PhoneModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class PhoneInputAdapterCli {

	@Autowired
	@Qualifier("phoneOutputAdapterMaria")
	private PhoneOutputPort phoneOutputPortMaria;

	@Autowired
	@Qualifier("phoneOutputAdapterMongo")
	private PhoneOutputPort phoneOutputPortMongo;

	@Autowired
	@Qualifier("personOutputAdapterMaria")
	private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("personOutputAdapterMongo")
	private PersonOutputPort personOutputPortMongo;

	@Autowired
	private PhoneMapperCli phoneMapperCli;

	PhoneInputPort phoneInputPort;
	PersonInputPort personInputPort;

	public void setPhoneOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			phoneInputPort = new PhoneUseCase(phoneOutputPortMaria);
			personInputPort = new co.edu.javeriana.as.personapp.application.usecase.PersonUseCase(personOutputPortMaria);
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			phoneInputPort = new PhoneUseCase(phoneOutputPortMongo);
			personInputPort = new co.edu.javeriana.as.personapp.application.usecase.PersonUseCase(personOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public void historial() {
		log.info("Into historial Phone in Input Adapter");
		phoneInputPort.findAll().stream()
			.map(phoneMapperCli::fromDomainToAdapterCli)
			.forEach(System.out::println);
	}

	public void crear(Scanner keyboard) {
		try {
			System.out.print("Ingrese el número de teléfono: ");
			String numero = keyboard.next();
			keyboard.nextLine();
			
			System.out.print("Ingrese el operador: ");
			String operador = keyboard.nextLine();
			
			System.out.print("Ingrese la cédula del dueño: ");
			Integer duenioCc = keyboard.nextInt();
			keyboard.nextLine();
			
			Person owner = personInputPort.findOne(duenioCc);
			PhoneModelCli phoneModelCli = new PhoneModelCli(numero, operador, duenioCc, null);
			Phone phone = phoneMapperCli.fromAdapterCliToDomain(phoneModelCli, owner);
			Phone created = phoneInputPort.create(phone);
			System.out.println("Teléfono creado exitosamente: " + phoneMapperCli.fromDomainToAdapterCli(created));
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al crear teléfono: " + e.getMessage());
		}
	}

	public void editar(Scanner keyboard) {
		try {
			System.out.print("Ingrese el número de teléfono a editar: ");
			String numero = keyboard.next();
			keyboard.nextLine();
			
			Phone oldPhone = phoneInputPort.findOne(numero);
			
			System.out.print("Ingrese el nuevo operador (actual: " + oldPhone.getCompany() + "): ");
			String operador = keyboard.nextLine();
			if (operador.isEmpty()) {
				operador = oldPhone.getCompany();
			}
			
			System.out.print("Ingrese la nueva cédula del dueño (actual: " + oldPhone.getOwner().getIdentification() + "): ");
			String duenioCcStr = keyboard.nextLine();
			Integer duenioCc = duenioCcStr.isEmpty() ? oldPhone.getOwner().getIdentification() : Integer.parseInt(duenioCcStr);
			
			Person owner = personInputPort.findOne(duenioCc);
			PhoneModelCli phoneModelCli = new PhoneModelCli(numero, operador, duenioCc, null);
			Phone phone = phoneMapperCli.fromAdapterCliToDomain(phoneModelCli, owner);
			Phone updated = phoneInputPort.edit(numero, phone);
			System.out.println("Teléfono actualizado exitosamente: " + phoneMapperCli.fromDomainToAdapterCli(updated));
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al editar teléfono: " + e.getMessage());
		}
	}

	public void eliminar(Scanner keyboard) {
		try {
			System.out.print("Ingrese el número de teléfono a eliminar: ");
			String numero = keyboard.next();
			keyboard.nextLine();
			
			Boolean deleted = phoneInputPort.drop(numero);
			if (deleted) {
				System.out.println("Teléfono eliminado exitosamente");
			} else {
				System.out.println("No se pudo eliminar el teléfono");
			}
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al eliminar teléfono: " + e.getMessage());
		}
	}

	public void buscarPorNumero(Scanner keyboard) {
		try {
			System.out.print("Ingrese el número de teléfono a buscar: ");
			String numero = keyboard.next();
			keyboard.nextLine();
			
			Phone phone = phoneInputPort.findOne(numero);
			System.out.println(phoneMapperCli.fromDomainToAdapterCli(phone));
		} catch (NoExistException e) {
			log.error("Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error al buscar teléfono: " + e.getMessage());
		}
	}

}


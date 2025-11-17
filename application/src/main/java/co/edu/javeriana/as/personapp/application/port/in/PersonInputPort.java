package co.edu.javeriana.as.personapp.application.port.in;

import java.util.List;

import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.domain.Study;

/**
 * Puerto de entrada para operaciones de casos de uso de Personas.
 * Define el contrato que deben implementar los casos de uso.
 */
@Port
public interface PersonInputPort {
	
	/**
	 * Establece el adaptador de persistencia a utilizar.
	 * Permite cambiar dinámicamente entre diferentes adaptadores (MariaDB, MongoDB, etc.).
	 * 
	 * @param personPersistence El adaptador de persistencia a utilizar
	 */
	public void setPersistence(PersonOutputPort personPersistence);
	
	/**
	 * Crea una nueva persona en el sistema.
	 * 
	 * @param person La persona a crear
	 * @return La persona creada con los datos actualizados
	 */
	public Person create(Person person);

	/**
	 * Edita una persona existente en el sistema.
	 * 
	 * @param identification Identificación de la persona a editar
	 * @param person Los nuevos datos de la persona
	 * @return La persona actualizada
	 * @throws NoExistException Si la persona no existe
	 */
	public Person edit(Integer identification, Person person) throws NoExistException;

	/**
	 * Elimina una persona del sistema.
	 * 
	 * @param identification Identificación de la persona a eliminar
	 * @return true si la eliminación fue exitosa, false en caso contrario
	 * @throws NoExistException Si la persona no existe
	 */
	public Boolean drop(Integer identification) throws NoExistException;

	/**
	 * Obtiene todas las personas del sistema.
	 * 
	 * @return Lista de todas las personas
	 */
	public List<Person> findAll();

	/**
	 * Busca una persona por su identificación.
	 * 
	 * @param identification Identificación de la persona a buscar
	 * @return La persona encontrada
	 * @throws NoExistException Si la persona no existe
	 */
	public Person findOne(Integer identification) throws NoExistException;

	/**
	 * Cuenta el número total de personas en el sistema.
	 * 
	 * @return El número total de personas
	 */
	public Integer count();

	/**
	 * Obtiene los teléfonos de una persona.
	 * 
	 * @param identification Identificación de la persona
	 * @return Lista de teléfonos de la persona
	 * @throws NoExistException Si la persona no existe
	 */
	public List<Phone> getPhones(Integer identification) throws NoExistException;

	/**
	 * Obtiene los estudios de una persona.
	 * 
	 * @param identification Identificación de la persona
	 * @return Lista de estudios de la persona
	 * @throws NoExistException Si la persona no existe
	 */
	public List<Study> getStudies(Integer identification) throws NoExistException;
}

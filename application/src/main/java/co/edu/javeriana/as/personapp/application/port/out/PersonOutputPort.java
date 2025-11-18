package co.edu.javeriana.as.personapp.application.port.out;

import java.util.List;

import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.domain.Person;

/**
 * Puerto de salida para operaciones de persistencia de Personas.
 * Define el contrato que deben implementar los adaptadores de salida.
 */
@Port
public interface PersonOutputPort {
	
	/**
	 * Guarda o actualiza una persona en el sistema de persistencia.
	 * 
	 * @param person La persona a guardar o actualizar
	 * @return La persona guardada con los datos actualizados
	 */
	public Person save(Person person);
	
	/**
	 * Elimina una persona del sistema de persistencia.
	 * 
	 * @param identification Identificación de la persona a eliminar
	 * @return true si la eliminación fue exitosa, false en caso contrario
	 */
	public Boolean delete(Integer identification);
	
	/**
	 * Obtiene todas las personas del sistema de persistencia.
	 * 
	 * @return Lista de todas las personas
	 */
	public List<Person> find();
	
	/**
	 * Busca una persona por su identificación.
	 * 
	 * @param identification Identificación de la persona a buscar
	 * @return La persona encontrada, o null si no existe
	 */
	public Person findById(Integer identification);
}

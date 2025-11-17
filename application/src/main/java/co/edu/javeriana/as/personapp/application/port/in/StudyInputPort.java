package co.edu.javeriana.as.personapp.application.port.in;

import java.util.List;

import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Study;

/**
 * Puerto de entrada para operaciones de casos de uso de Estudios.
 * Define el contrato que deben implementar los casos de uso.
 */
@Port
public interface StudyInputPort {
	
	/**
	 * Establece el adaptador de persistencia a utilizar.
	 * Permite cambiar dinámicamente entre diferentes adaptadores (MariaDB, MongoDB, etc.).
	 * 
	 * @param studyPersistence El adaptador de persistencia a utilizar
	 */
	public void setPersistence(StudyOutputPort studyPersistence);
	
	/**
	 * Crea un nuevo estudio en el sistema.
	 * 
	 * @param study El estudio a crear
	 * @return El estudio creado con los datos actualizados
	 */
	public Study create(Study study);

	/**
	 * Edita un estudio existente en el sistema.
	 * 
	 * @param personId Identificación de la persona
	 * @param professionId Identificación de la profesión
	 * @param study Los nuevos datos del estudio
	 * @return El estudio actualizado
	 * @throws NoExistException Si el estudio no existe
	 */
	public Study edit(Integer personId, Integer professionId, Study study) throws NoExistException;

	/**
	 * Elimina un estudio del sistema.
	 * 
	 * @param personId Identificación de la persona
	 * @param professionId Identificación de la profesión
	 * @return true si la eliminación fue exitosa, false en caso contrario
	 * @throws NoExistException Si el estudio no existe
	 */
	public Boolean drop(Integer personId, Integer professionId) throws NoExistException;

	/**
	 * Obtiene todos los estudios del sistema.
	 * 
	 * @return Lista de todos los estudios
	 */
	public List<Study> findAll();

	/**
	 * Busca un estudio por persona y profesión.
	 * 
	 * @param personId Identificación de la persona
	 * @param professionId Identificación de la profesión
	 * @return El estudio encontrado
	 * @throws NoExistException Si el estudio no existe
	 */
	public Study findOne(Integer personId, Integer professionId) throws NoExistException;

	/**
	 * Obtiene todos los estudios de una persona.
	 * 
	 * @param personId Identificación de la persona
	 * @return Lista de estudios de la persona
	 * @throws NoExistException Si la persona no existe
	 */
	public List<Study> findByPerson(Integer personId) throws NoExistException;

	/**
	 * Obtiene todos los estudios de una profesión.
	 * 
	 * @param professionId Identificación de la profesión
	 * @return Lista de estudios de la profesión
	 * @throws NoExistException Si la profesión no existe
	 */
	public List<Study> findByProfession(Integer professionId) throws NoExistException;

	/**
	 * Cuenta el número total de estudios en el sistema.
	 * 
	 * @return El número total de estudios
	 */
	public Integer count();
}


package co.edu.javeriana.as.personapp.application.port.out;

import java.util.List;

import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.domain.Study;

/**
 * Puerto de salida para operaciones de persistencia de Estudios.
 * Define el contrato que deben implementar los adaptadores de salida.
 */
@Port
public interface StudyOutputPort {
	
	/**
	 * Guarda o actualiza un estudio en el sistema de persistencia.
	 * 
	 * @param study El estudio a guardar o actualizar
	 * @return El estudio guardado con los datos actualizados
	 */
	public Study save(Study study);
	
	/**
	 * Elimina un estudio del sistema de persistencia.
	 * 
	 * @param personId Identificación de la persona
	 * @param professionId Identificación de la profesión
	 * @return true si la eliminación fue exitosa, false en caso contrario
	 */
	public Boolean delete(Integer personId, Integer professionId);
	
	/**
	 * Obtiene todos los estudios del sistema de persistencia.
	 * 
	 * @return Lista de todos los estudios
	 */
	public List<Study> find();
	
	/**
	 * Busca un estudio por persona y profesión.
	 * 
	 * @param personId Identificación de la persona
	 * @param professionId Identificación de la profesión
	 * @return El estudio encontrado, o null si no existe
	 */
	public Study findById(Integer personId, Integer professionId);
	
	/**
	 * Obtiene todos los estudios de una persona.
	 * 
	 * @param personId Identificación de la persona
	 * @return Lista de estudios de la persona
	 */
	public List<Study> findByPersonId(Integer personId);
	
	/**
	 * Obtiene todos los estudios de una profesión.
	 * 
	 * @param professionId Identificación de la profesión
	 * @return Lista de estudios de la profesión
	 */
	public List<Study> findByProfessionId(Integer professionId);
}


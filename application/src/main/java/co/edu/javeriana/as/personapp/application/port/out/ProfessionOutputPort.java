package co.edu.javeriana.as.personapp.application.port.out;

import java.util.List;

import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.domain.Profession;

/**
 * Puerto de salida para operaciones de persistencia de Profesiones.
 * Define el contrato que deben implementar los adaptadores de salida.
 */
@Port
public interface ProfessionOutputPort {
	
	/**
	 * Guarda o actualiza una profesión en el sistema de persistencia.
	 * 
	 * @param profession La profesión a guardar o actualizar
	 * @return La profesión guardada con los datos actualizados
	 */
	public Profession save(Profession profession);
	
	/**
	 * Elimina una profesión del sistema de persistencia.
	 * 
	 * @param identification Identificación de la profesión a eliminar
	 * @return true si la eliminación fue exitosa, false en caso contrario
	 */
	public Boolean delete(Integer identification);
	
	/**
	 * Obtiene todas las profesiones del sistema de persistencia.
	 * 
	 * @return Lista de todas las profesiones
	 */
	public List<Profession> find();
	
	/**
	 * Busca una profesión por su identificación.
	 * 
	 * @param identification Identificación de la profesión a buscar
	 * @return La profesión encontrada, o null si no existe
	 */
	public Profession findById(Integer identification);
}


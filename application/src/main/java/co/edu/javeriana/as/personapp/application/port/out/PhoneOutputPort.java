package co.edu.javeriana.as.personapp.application.port.out;

import java.util.List;

import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.domain.Phone;

/**
 * Puerto de salida para operaciones de persistencia de Teléfonos.
 * Define el contrato que deben implementar los adaptadores de salida.
 */
@Port
public interface PhoneOutputPort {
	
	/**
	 * Guarda o actualiza un teléfono en el sistema de persistencia.
	 * 
	 * @param phone El teléfono a guardar o actualizar
	 * @return El teléfono guardado con los datos actualizados
	 */
	public Phone save(Phone phone);
	
	/**
	 * Elimina un teléfono del sistema de persistencia.
	 * 
	 * @param number Número del teléfono a eliminar
	 * @return true si la eliminación fue exitosa, false en caso contrario
	 */
	public Boolean delete(String number);
	
	/**
	 * Obtiene todos los teléfonos del sistema de persistencia.
	 * 
	 * @return Lista de todos los teléfonos
	 */
	public List<Phone> find();
	
	/**
	 * Busca un teléfono por su número.
	 * 
	 * @param number Número del teléfono a buscar
	 * @return El teléfono encontrado, o null si no existe
	 */
	public Phone findByNumber(String number);
}


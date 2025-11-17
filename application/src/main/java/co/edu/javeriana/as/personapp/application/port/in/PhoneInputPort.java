package co.edu.javeriana.as.personapp.application.port.in;

import java.util.List;

import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Phone;

/**
 * Puerto de entrada para operaciones de casos de uso de Teléfonos.
 * Define el contrato que deben implementar los casos de uso.
 */
@Port
public interface PhoneInputPort {
	
	/**
	 * Establece el adaptador de persistencia a utilizar.
	 * Permite cambiar dinámicamente entre diferentes adaptadores (MariaDB, MongoDB, etc.).
	 * 
	 * @param phonePersistence El adaptador de persistencia a utilizar
	 */
	public void setPersistence(PhoneOutputPort phonePersistence);
	
	/**
	 * Crea un nuevo teléfono en el sistema.
	 * 
	 * @param phone El teléfono a crear
	 * @return El teléfono creado con los datos actualizados
	 */
	public Phone create(Phone phone);

	/**
	 * Edita un teléfono existente en el sistema.
	 * 
	 * @param number Número del teléfono a editar
	 * @param phone Los nuevos datos del teléfono
	 * @return El teléfono actualizado
	 * @throws NoExistException Si el teléfono no existe
	 */
	public Phone edit(String number, Phone phone) throws NoExistException;

	/**
	 * Elimina un teléfono del sistema.
	 * 
	 * @param number Número del teléfono a eliminar
	 * @return true si la eliminación fue exitosa, false en caso contrario
	 * @throws NoExistException Si el teléfono no existe
	 */
	public Boolean drop(String number) throws NoExistException;

	/**
	 * Obtiene todos los teléfonos del sistema.
	 * 
	 * @return Lista de todos los teléfonos
	 */
	public List<Phone> findAll();

	/**
	 * Busca un teléfono por su número.
	 * 
	 * @param number Número del teléfono a buscar
	 * @return El teléfono encontrado
	 * @throws NoExistException Si el teléfono no existe
	 */
	public Phone findOne(String number) throws NoExistException;

	/**
	 * Cuenta el número total de teléfonos en el sistema.
	 * 
	 * @return El número total de teléfonos
	 */
	public Integer count();
}


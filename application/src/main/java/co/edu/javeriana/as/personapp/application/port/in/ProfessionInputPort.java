package co.edu.javeriana.as.personapp.application.port.in;

import java.util.List;

import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Profession;

/**
 * Puerto de entrada para operaciones de casos de uso de Profesiones.
 * Define el contrato que deben implementar los casos de uso.
 */
@Port
public interface ProfessionInputPort {
	
	/**
	 * Establece el adaptador de persistencia a utilizar.
	 * Permite cambiar dinámicamente entre diferentes adaptadores (MariaDB, MongoDB, etc.).
	 * 
	 * @param professionPersistence El adaptador de persistencia a utilizar
	 */
	public void setPersistence(ProfessionOutputPort professionPersistence);
	
	/**
	 * Crea una nueva profesión en el sistema.
	 * 
	 * @param profession La profesión a crear
	 * @return La profesión creada con los datos actualizados
	 */
	public Profession create(Profession profession);

	/**
	 * Edita una profesión existente en el sistema.
	 * 
	 * @param identification Identificación de la profesión a editar
	 * @param profession Los nuevos datos de la profesión
	 * @return La profesión actualizada
	 * @throws NoExistException Si la profesión no existe
	 */
	public Profession edit(Integer identification, Profession profession) throws NoExistException;

	/**
	 * Elimina una profesión del sistema.
	 * 
	 * @param identification Identificación de la profesión a eliminar
	 * @return true si la eliminación fue exitosa, false en caso contrario
	 * @throws NoExistException Si la profesión no existe
	 */
	public Boolean drop(Integer identification) throws NoExistException;

	/**
	 * Obtiene todas las profesiones del sistema.
	 * 
	 * @return Lista de todas las profesiones
	 */
	public List<Profession> findAll();

	/**
	 * Busca una profesión por su identificación.
	 * 
	 * @param identification Identificación de la profesión a buscar
	 * @return La profesión encontrada
	 * @throws NoExistException Si la profesión no existe
	 */
	public Profession findOne(Integer identification) throws NoExistException;

	/**
	 * Cuenta el número total de profesiones en el sistema.
	 * 
	 * @return El número total de profesiones
	 */
	public Integer count();
}


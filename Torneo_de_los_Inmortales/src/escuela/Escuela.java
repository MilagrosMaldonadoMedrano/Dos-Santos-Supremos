package escuela;
import combatiente.Combatiente;
import excepciones.EquipamientoInvalidoException;
import equipamiento_y_tecnicas.Equipamiento;
import equipamiento_y_tecnicas.Tecnica;
import java.util.List;

/*
 * Una escuela es solo configuración: un nombre, las técnicas iniciales y el equipamiento
 * inicial que le da a sus combatientes. Agregar una escuela es crear otro objeto, sin
 * escribir código nuevo. No determina el tipo del combatiente.
 * La crean: el main (App) y las pruebas.
 */
public class Escuela {
	private String nombre;
	private List<Tecnica> tecnicasIniciales;   // en este orden se desempata el criterio
	private List<Equipamiento> equipamiento;

	/*
	 * Guarda el nombre y copias de las listas (List.copyOf), así nadie puede modificar
	 * la configuración de la escuela desde afuera una vez creada.
	 * Lo usan: el main (App) y las pruebas.
	 */
	public Escuela(String nombre, List<Tecnica> tecnicas, List<Equipamiento> equipamiento) {
		this.nombre = nombre;
		this.tecnicasIniciales = List.copyOf(tecnicas);
		this.equipamiento = List.copyOf(equipamiento);
	}

	/*
	 * Prepara a un combatiente: le enseña las técnicas (cada una con 5 usos) y le equipa
	 * las piezas. equipar valida el máximo de 3 y la categoría; si falla, lanza
	 * EquipamientoInvalidoException.
	 * Lo usa: CreadorCombatiente.crear, después de crear al combatiente.
	 */
	public void preparar(Combatiente c) throws EquipamientoInvalidoException{
		c.aprenderTecnicas(tecnicasIniciales);
		for (Equipamiento e : equipamiento) {
			c.equipar(e);
		}
	}

	/*
	 * Devuelve el nombre de la escuela.
	 * Hoy no lo usa ninguna clase; queda para consultar la configuración.
	 */
	public String getNombre() {
		return nombre;
	}

	/*
	 * Devuelve las técnicas iniciales (lista de solo lectura).
	 * Hoy no lo usa ninguna clase; queda para consultar la configuración.
	 */
	public List<Tecnica> getTecnicasIniciales() {
		return tecnicasIniciales;
	}

	/*
	 * Devuelve el equipamiento inicial (lista de solo lectura).
	 * Hoy no lo usa ninguna clase; queda para consultar la configuración.
	 */
	public List<Equipamiento> getEquipamiento() {
		return equipamiento;
	}
}

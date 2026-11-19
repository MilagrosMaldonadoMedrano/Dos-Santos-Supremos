package escuela;
import combatiente.Combatiente;
import excepciones.EquipamientoInvalidoException;
import equipamiento_y_tecnicas.Equipamiento;
import equipamiento_y_tecnicas.Tecnica;
import java.util.List;

public class Escuela {
	private String nombre;
    private List<Tecnica> tecnicasIniciales;
    private List<Equipamiento> equipamiento;

    public Escuela(String nombre, List<Tecnica> tecnicas, List<Equipamiento> equipamiento) {
        this.nombre = nombre;
        this.tecnicasIniciales = List.copyOf(tecnicas);
        this.equipamiento = List.copyOf(equipamiento);
    }
    
    
    //la escuela prepara al combatiente
    public void preparar(Combatiente c) throws EquipamientoInvalidoException{
		c.aprenderTecnicas(tecnicasIniciales); // cada tecnica con 5 usos
		for (Equipamiento e : equipamiento) {
			c.equipar(e);       // valida maximo 3 y categ
		}
	}

    public String getNombre() {
        return nombre;
    }

    public List<Tecnica> getTecnicasIniciales() {
        return tecnicasIniciales;
    }

    public List<Equipamiento> getEquipamiento() {
        return equipamiento;
    }
}

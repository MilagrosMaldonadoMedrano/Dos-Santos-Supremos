package escuela;

import equipamiento_y_tecnicas.Equipamiento;
import equipamiento_y_tecnicas.Tecnica;
import java.util.List;

public class Escuela {
	private String nombre;
    private List<Tecnica> tecnicasIniciales;
    private List<Equipamiento> equipamiento;

    public Escuela(String nombre, List<Tecnica> tecnicas, List<Equipamiento> equipamiento) {
        this.nombre = nombre;
        this.tecnicasIniciales = tecnicas;
        this.equipamiento = equipamiento;
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

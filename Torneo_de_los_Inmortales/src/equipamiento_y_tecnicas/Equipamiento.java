package equipamiento_y_tecnicas;

import combatiente.Combatiente;

public abstract class Equipamiento {
	private final String nombre;

	//le agregue el constructor porque sino no se asignaba el nombre (no se usaba el atributo)
	protected Equipamiento(String nombre) {
		if (nombre == null || nombre.isBlank())
			throw new IllegalArgumentException("El equipamiento necesita nombre");
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	
	public abstract Categoria getCategoria();

	public abstract void aplicarEfecto(Combatiente c);
	public abstract void quitarEfecto(Combatiente c);
}


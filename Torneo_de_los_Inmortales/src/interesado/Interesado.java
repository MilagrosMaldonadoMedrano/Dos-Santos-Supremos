package interesado;

// Observer: cualquier clase que quiera enterarse de los acontecimientos.
// Los métodos son "default" (vacíos): cada interesado redefine solo los que le importan.
public interface Interesado {

	default void inicioCombate(InicioCombate acontecimiento) {}

	default void accionRealizada(AccionRealizada acontecimiento) {}

	default void eliminacion(Eliminacion acontecimiento) {}

	default void resultadoCombate(ResultadoCombate acontecimiento) {}

	default void proclamacion(Proclamacion acontecimiento) {}
}
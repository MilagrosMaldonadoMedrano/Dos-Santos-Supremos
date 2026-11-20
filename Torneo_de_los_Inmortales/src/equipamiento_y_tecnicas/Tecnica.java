package equipamiento_y_tecnicas;

import java.util.Objects;

import combatiente.Combatiente;
import efectos.Efecto;
import excepciones.TecnicaNoDisponibleException;

/*
 * Clase abstracta de todas las técnicas. Una técnica es solo su definición: nombre,
 * costo de energía, tipo y un efecto. Los usos NO se guardan acá, sino en cada
 * combatiente, porque la misma técnica la pueden tener varios.
 * COMPOSITE: el efecto puede ser simple (Daño) o compuesto (EfectoCompuesto).
 * Subclases: las cinco técnicas concretas.
 */
public abstract class Tecnica {
	private String nombre;
	private int costoEnergia;     // costo base, sin el descuento del Hechicero
	private TipoTecnica tipo;
	protected Efecto efecto;

	/*
	 * Guarda nombre, costo, tipo y efecto.
	 * Lo usan: las cinco técnicas concretas, con super(...) en su constructor.
	 */
	public Tecnica(String nombre, int costoEnergia, TipoTecnica tipo, Efecto efecto) {
		this.nombre = nombre;
		this.costoEnergia = costoEnergia;
		this.tipo = tipo;
		this.efecto = efecto;
	}

	/*
	 * Devuelve el nombre de la técnica.
	 * Lo usan: Combate.actuar (para el acontecimiento AccionRealizada) y los mensajes
	 * de excepción de Combatiente.usarTecnica.
	 */
	public String getNombre() {
		return nombre;
	}

	/*
	 * Devuelve el costo base de energía, sin descuentos (el Hechicero lo paga con descuento).
	 * Lo usan: Combatiente.puedeUsar, CriterioAgresivo (elige la más costosa) y realizar.
	 */
	public int getCostoEnergia() {
		return costoEnergia;
	}

	/*
	 * Devuelve si es OFENSIVA, DEFENSIVA o de RECUPERACION.
	 * Lo usan: CriterioPorDefecto y CriterioAgresivo (para elegir según el tipo)
	 * y Combate.actuar (para saber a quién afectó la acción).
	 */
	public TipoTecnica getTipoTecnica() {
		return tipo;
	}

	/*
	 * Ejecuta la técnica. Decide a quién afecta: las ofensivas al objetivo; las defensivas
	 * y de recuperación al ejecutor. Valida TODO antes de cobrar: que el afectado esté
	 * en pie y que al ejecutor le alcance la energía (con su descuento); si no, lanza
	 * TecnicaNoDisponibleException. Después cobra la energía y aplica el efecto, pasando
	 * al ejecutor para que el daño use su multiplicador.
	 * Lo usa: Combatiente.usarTecnica.
	 */
	public void realizar(Combatiente ejecutor, Combatiente objetivo) throws TecnicaNoDisponibleException {
		Combatiente afectado = (tipo == TipoTecnica.OFENSIVA) ? objetivo : ejecutor;
		if (!afectado.puedeSerObjetivo())
			throw new TecnicaNoDisponibleException(afectado.getNombre() + " no puede ser objetivo");
		if (!ejecutor.tieneEnergiaPara(getCostoEnergia()))
			throw new TecnicaNoDisponibleException(ejecutor.getNombre() + " no tiene suficiente energia para usar la tecnica: " + nombre);
		ejecutor.gastarEnergia(getCostoEnergia());
		efecto.aplicarEfecto(ejecutor, afectado);
	}

	/*
	 * Hash por nombre, igual que equals. Es necesario porque las técnicas son claves de
	 * mapas (los usos de cada combatiente y la regla del compañero en Pareja).
	 */
	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	/*
	 * Dos técnicas son la misma si son de la misma clase y tienen el mismo nombre.
	 * Así, dos objetos new CorteDeLosMilAmaneceres() cuentan como la misma técnica.
	 * Lo usan: el LinkedHashMap de usos de Combatiente, el HashMap tecnicasEnUso de Pareja
	 * y el Set de técnicas bloqueadas que recibe el criterio.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tecnica other = (Tecnica) obj;
		return Objects.equals(nombre, other.nombre);
	}
}

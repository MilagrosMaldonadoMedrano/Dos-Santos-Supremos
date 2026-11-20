package interesado;

/*
 * Una fila de la tabla de posiciones: id e integrantes de la pareja, la última ronda
 * que jugó y la vida total con la que terminó su último combate.
 * Implementa Comparable: define el orden de la tabla.
 * La crea: CronistaDeLaSecta.resultadoCombate.
 */
public class Posicion implements Comparable<Posicion> {
	private final int idPareja;
	private final String integrantes;
	private final int ultimaRonda;
	private final int vidaFinal;

	/*
	 * Guarda los datos de la fila.
	 * Lo usa: CronistaDeLaSecta.resultadoCombate.
	 */
	public Posicion(int idPareja, String integrantes, int ultimaRonda, int vidaFinal) {
		this.idPareja = idPareja;
		this.integrantes = integrantes;
		this.ultimaRonda = ultimaRonda;
		this.vidaFinal = vidaFinal;
	}

	/*
	 * Orden de la tabla: primero por última ronda jugada (de mayor a menor), en empate
	 * por vida final (de mayor a menor) y después por id (de menor a mayor).
	 * Lo usa: Collections.sort en CronistaDeLaSecta.tablaDePosiciones.
	 */
	@Override
	public int compareTo(Posicion otra) {
		if (this.ultimaRonda != otra.ultimaRonda) {
			return Integer.compare(otra.ultimaRonda, this.ultimaRonda);
		}
		if (this.vidaFinal != otra.vidaFinal) {
			return Integer.compare(otra.vidaFinal, this.vidaFinal);
		}
		return Integer.compare(this.idPareja, otra.idPareja);
	}

	/*
	 * Consultas de los datos de la fila.
	 * Las usa: CronistaDeLaSectaTest.
	 */
	public int getIdPareja() { return idPareja; }
	public String getIntegrantes() { return integrantes; }
	public int getUltimaRonda() { return ultimaRonda; }
	public int getVidaFinal() { return vidaFinal; }

	/*
	 * Texto de la fila, como la muestra el Heraldo en la tabla de posiciones.
	 * Lo usa: HeraldoImperial.presentarTabla.
	 */
	@Override
	public String toString() {
		return "Pareja " + idPareja + " (" + integrantes + ") | última ronda: " + ultimaRonda + " | vida final: " + vidaFinal;
	}
}

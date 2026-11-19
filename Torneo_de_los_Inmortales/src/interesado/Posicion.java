package interesado;

// Una fila de la tabla de posiciones. Comparable: define el orden de la tabla (§7).
public class Posicion implements Comparable<Posicion> {
	private final int idPareja;
	private final String integrantes;
	private final int ultimaRonda;
	private final int vidaFinal;

	public Posicion(int idPareja, String integrantes, int ultimaRonda, int vidaFinal) {
		this.idPareja = idPareja;
		this.integrantes = integrantes;
		this.ultimaRonda = ultimaRonda;
		this.vidaFinal = vidaFinal;
	}

	// 1) última ronda jugada, de mayor a menor; 2) vida final, de mayor a menor; 3) id, de menor a mayor.
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

	public int getIdPareja() { return idPareja; }
	public String getIntegrantes() { return integrantes; }
	public int getUltimaRonda() { return ultimaRonda; }
	public int getVidaFinal() { return vidaFinal; }

	@Override
	public String toString() {
		return "Pareja " + idPareja + " (" + integrantes + ") | última ronda: " + ultimaRonda + " | vida final: " + vidaFinal;
	}
}
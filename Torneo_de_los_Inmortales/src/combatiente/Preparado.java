package combatiente;

public class Preparado implements EstadoCombatiente{
	
	public boolean puedeActuar() {
		return false;
	}
	public boolean puedeSerObjetivo() {
		return false;
	}
	public boolean puedeModificarEquipamiento() {
		return true;
	}
	public void recibirDanio(Combatiente c, int danio) {
		throw new IllegalStateException( "No está en combate.");
	}
}

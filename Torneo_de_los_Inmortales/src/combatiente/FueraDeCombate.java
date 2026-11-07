package combatiente;

public class FueraDeCombate implements EstadoCombatiente{

	public boolean puedeActuar() {
		return false;
	}
	
	public boolean puedeSerObjetivo() {
		return false;
	}
	
	public boolean puedeModificarEquipamiento(){
		return false;
	}
	
	public void recibirDanio(Combatiente c, int danio) {
		throw new IllegalStateException("No está en combate.");
	}

}

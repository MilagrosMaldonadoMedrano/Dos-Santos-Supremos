package combatiente;

public class EnPie implements EstadoCombatiente{

	public boolean puedeActuar() {
		return true;
	}
	
	public boolean puedeModificarEquipamiento() {
		return false;
	}
	public boolean puedeSerObjetivo() {
		return true;
	}
	
	
	public void recibirDanio(Combatiente c, int danio) {
		c.perderVida(danio);
		if(c.getVidaActual()==0) {
			c.cambiarEstado(new FueraDeCombate());
		}
	}
	
	
}

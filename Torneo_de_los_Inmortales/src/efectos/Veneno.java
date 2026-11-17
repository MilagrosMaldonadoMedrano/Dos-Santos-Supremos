package efectos;

import combatiente.Combatiente;

public class Veneno extends EfectoTemporal{
    private int dañoPorTurno;

    public Veneno(int turnos, int dañoPorTurno){
        super(turnos);
        this.dañoPorTurno = dañoPorTurno;
    }

    public Veneno(Veneno original){
        super(original);
        this.dañoPorTurno = original.dañoPorTurno;
    }

    @Override
    protected void ejecutarEfecto(Combatiente afectado) {
        afectado.recibirDanio(dañoPorTurno);
    }

    @Override
    public EfectoTemporal copiar() {
        return new Veneno(this);
    }
}

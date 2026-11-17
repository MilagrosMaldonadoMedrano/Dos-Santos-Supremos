package efectos;

import combatiente.Combatiente;

public class Regeneracion extends EfectoTemporal{
    private int curacionPorTurno;

    public Regeneracion(int turnos, int curacionPorTurno){
        super(turnos);
        this.curacionPorTurno = curacionPorTurno;
    }

    public Regeneracion (Regeneracion original){
        super(original);
        curacionPorTurno = original.curacionPorTurno;
    }

    @Override
    protected void ejecutarEfecto(Combatiente afectado) {
        afectado.curar(curacionPorTurno);
    }

    @Override
    public EfectoTemporal copiar() {
        return new Regeneracion(this);
    }
}

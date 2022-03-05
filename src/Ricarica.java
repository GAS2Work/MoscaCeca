public class Ricarica extends Thread{
    int x;
    int y;
    Agente agente;

    public Ricarica(int x, int y, Agente agente){
        this.x = x;
        this.y = y;
        this.agente = agente;
    }

    public void setAgente(Agente agente){
        this.agente = agente;
    }

    public void run(){
        while(agente.getPosizione().getX()==x && agente.getPosizione().getY()==y){
            agente.addEnergia();
        }
    }

}

public class Casella {
    int x;
    int y;
    String stato;
    int stoffa;
    Ricarica ricarica;
    boolean occupato;
    Agente agente_occupante;
    String proprietà;

    public Casella(int x, int y, String stato){
        this.x = x;
        this.y = y;
        this.stato = stato;
        if(stato.equals("stoffa"))
            stoffa = (int)(Math.random()*11);
        else
            stoffa = 0;
        if(stato.equals("ricarica"))
            ricarica = new Ricarica(x, y, agente_occupante);
        else
            ricarica = null;
        occupato = false;
        agente_occupante = null;
        proprietà = "";
    }
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String getStato(){
        return stato;
    }

    public String getProprietà(){
        return proprietà;
    }

    public void setProprietà(String nome){
        proprietà = nome;
    }

    public boolean occupato(){
        return occupato;
    }

    public String getOccupazione(){
        if(occupato())
            return "occupato";
        else
            return "libero";
    }

    public void occupa(Agente agente){
        occupato = true;
        agente_occupante = agente;
        switch(stato){
            case "stoffa":
                if((agente_occupante.getStoffa()+stoffa)>100){
                    agente_occupante.setStoffa(stoffa - (100-agente_occupante.getStoffa()));
                    stoffa -= (100-agente_occupante.getStoffa());
                }else{
                    agente_occupante.setStoffa(stoffa);
                    stoffa = 0;
                }
            break;
            case "ricarica":
                ricarica.setAgente(agente_occupante);
                ricarica.start();
            break;
            default:
            break;
        }
    }

    public void libera(){
        occupato = false;
        agente_occupante = null;
    }
}

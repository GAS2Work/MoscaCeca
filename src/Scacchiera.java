import java.util.ArrayList;

public class Scacchiera {
    Casella[][] scacchiera;
    Agente[] agenti;
    ArrayList<Casella> ricariche;

    public Scacchiera(){
        scacchiera = new Casella[100][100];
        agenti = new Agente[20];
        ricariche = new ArrayList<>();
        crea();
    }

    private void crea(){
        for(int i=0;i<100;i++) {
            for (int j = 0; j < 100; j++) {
                String stato = stato();
                scacchiera[i][j] = new Casella(i, j, stato);
                if(scacchiera[i][j].getStato().equals("ricarica")) {
                    ricariche.add(scacchiera[i][j]);
                }
            }
        }
        for(int i=0;i<agenti.length;i++) {
            int x = xy();
            int y = xy();
            while(scacchiera[x][y].occupato())
                if(!scacchiera[x][y].occupato()) {
                    agenti[i] = new Agente("A" + i, this, scacchiera[x][y], trovaRicarica(x, y), adiacenti(x, y));
                    scacchiera[x][y].occupa(agenti[i]);
                    break;
                }else {
                    x = xy();
                    y = xy();
                }
        }
    }

    private String stato(){
        switch((int) Math.random()*2){
            case 1:
                return "risorsa";
            case 2:
                return "ricarica";
            default:
                return "vuota";
        }
    }

    private int xy(){
        return (int) Math.random()*99;
    }

    private Casella trovaRicarica(int x, int y){
        Casella ricarica = ricariche.get(0);
        for(Casella c : ricariche){
            if((Math.abs(c.getX()-x)+Math.abs(c.getY()-y))<(Math.abs(ricarica.getX()-x)+Math.abs(ricarica.getY()-y)))
                ricarica = c;
        }
        return ricarica;
    }

    public Casella[] adiacenti(int x, int y){
        Casella[] adiacenti = new Casella[]{
                scacchiera[x][y-1],
                scacchiera[x-1][y-1],
                scacchiera[x-1][y],
                scacchiera[x-1][y+1],
                scacchiera[x][y+1],
                scacchiera[x+1][y+1],
                scacchiera[x+1][y],
                scacchiera[x+1][y-1]
        };
        return adiacenti;
    }

    synchronized public boolean accesso(int x, int y, Casella posizione, Agente agente){
        if(agente.getEnergia()>0) {
            if (!scacchiera[x][y].occupato()) {
                scacchiera[x][y].occupa(agente);
                posizione.libera();
                agente.setRicarica(trovaRicarica(x, y));
                agente.setAdiacenti(adiacenti(x, y));
                agente.setPosizione(scacchiera[x][y]);
                return true;
            } else {
                System.out.println(agente.getNome() + ": spostamento negato");
                return false;
            }
        }else{
            System.out.println("Energia insufficiente");
            return false;
        }
    }

    synchronized public boolean bandiera(Casella posizione, String nome, int stoffa, Casella[] adiacenti, Agente agente){
        boolean b = false;
        for(Casella c : adiacenti){
            if(c.getProprietà().equals(nome)) {
                b = true;
                break;
            }
        }
        if(b){
            if(posizione.getProprietà().equals("") && stoffa>=4) {
                posizione.setProprietà(nome);
                agente.setStoffa(-4);
                return true;
            }else {
                System.out.println("Casella già posseduta in territorio altrui o stoffa insufficiente");
                return false;
            }
        }else{
            if(posizione.getProprietà().equals("") && stoffa>=8) {
                posizione.setProprietà(nome);
                agente.setStoffa(-8);
                return true;
            }else {
                System.out.println("Casella già posseduta in territorio altrui o stoffa insufficiente");
                return false;
            }
        }
    }

    public void esegui() throws InterruptedException {
        for(int i=0;i<agenti.length;i++)
            agenti[i].start();
        for(int i=0;i<agenti.length;i++)
            agenti[i].join();
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class Agente extends Thread{
    Scanner sc = new Scanner(System.in);
    String nome;
    private int energia;
    private int stoffa;
    Scacchiera scacchiera;
    Casella posizione;
    Casella ricarica;
    Casella[] adiacenti;
    ArrayList<Casella> territori;

    public Agente(String nome, Scacchiera scacchiera, Casella posizione, Casella ricarica, Casella[] adiacenti){
        this.nome = nome;
        energia = 100;
        stoffa = 40;
        this.scacchiera = scacchiera;
        this.posizione = posizione;
        this.ricarica = ricarica;
        this.adiacenti = adiacenti;
        territori = new ArrayList<>();
    }

    public void run(){
        System.out.println(getNome());
        System.out.println(adiacenti[1].getStato() +"(" +adiacenti[1].getOccupazione() +")" +"   " +adiacenti[2].getStato() +"(" +adiacenti[2].getOccupazione() +")" +"   " +adiacenti[3].getStato() +"(" +adiacenti[3].getOccupazione() +")\n"
                          +adiacenti[0].getStato() +"(" +adiacenti[0].getOccupazione() +")" +"   " +posizione.getStato() +"(" +posizione.getOccupazione() +")" +"   " +adiacenti[4].getStato() +"(" +adiacenti[4].getOccupazione() +")\n"
                          +adiacenti[7].getStato() +"(" +adiacenti[7].getOccupazione() +")" +"   " +adiacenti[6].getStato() +"(" +adiacenti[6].getOccupazione() +")" +"   " +adiacenti[5].getStato() +"(" +adiacenti[5].getOccupazione() +")");
        System.out.println("Ricarica più vicina: [" +ricarica.getX() +", " +ricarica.getY() +"]");
        System.out.println("Territori:");
        for(Casella c : territori)
            System.out.println("[" +c.getX() +", " +c.getY() +"]");
        int scelta = 1;
        if(nome.equals("A0")) {
            System.out.println("Che cosa vuoi effettuare?\n1.Pianta bandiera\n2.Spostati");
            while(scelta!=1 && scelta!=2){
                scelta = sc.nextInt();
                switch(scelta){
                    case 1:
                        pianta();
                    break;
                    case 2:
                        int mossa = 5;
                        System.out.println("1.basso a sinistra\n2.basso\n3.basso a destra\n4.sinistra\n5.fermo\n6.destra\n7.alto a sinistra\n8.alto\n9.alto a destra");
                        while(mossa<1 || mossa>9) {
                            mossa = sc.nextInt();
                            switch (mossa) {
                                case 1:
                                    basso_sinistra();
                                    break;
                                case 2:
                                    basso();
                                    break;
                                case 3:
                                    basso_destra();
                                    break;
                                case 4:
                                    sinistra();
                                    break;
                                case 6:
                                    destra();
                                    break;
                                case 7:
                                    alto_sinistra();
                                    break;
                                case 8:
                                    alto();
                                    break;
                                case 9:
                                    alto_destra();
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }else{
            scelta = (int)((Math.random()*1)+1);
            switch(scelta) {
                case 1:
                    pianta();
                    break;
                case 2:
                    int mossa = 5;
                    System.out.println("1.basso a sinistra\n2.basso\n3.basso a destra\n4.sinistra\n5.fermo\n6.destra\n7.alto a sinistra\n8.alto\n9.alto a destra");
                    mossa = (int)((Math.random()*8)+1);
                    switch (mossa) {
                        case 1:
                            basso_sinistra();
                            break;
                        case 2:
                            basso();
                            break;
                        case 3:
                            basso_destra();
                            break;
                        case 4:
                            sinistra();
                            break;
                        case 6:
                            destra();
                            break;
                        case 7:
                            alto_sinistra();
                            break;
                        case 8:
                            alto();
                            break;
                        case 9:
                            alto_destra();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public String getNome(){
        return nome;
    }

    public int getStoffa(){
        return stoffa;
    }

    public int getEnergia(){
        return energia;
    }

    public Casella getPosizione(){
        return posizione;
    }

    public void setPosizione(Casella posizione){
        this.posizione = posizione;
    }

    public void setRicarica(Casella ricarica){
        this.ricarica = ricarica;
    }

    public void setAdiacenti(Casella[] adiacenti){
        this.adiacenti = adiacenti;
    }

    public void setStoffa(int stoffapersa){
        this.stoffa += stoffapersa;
    }

    public void addEnergia(){
        energia += 10;
    }

    public void sinistra(){
        if(scacchiera.accesso(posizione.getX(), posizione.getY() -1, posizione, this))
            energia--;
    }

    public void alto_sinistra(){
        if(scacchiera.accesso(posizione.getX()-1, posizione.getY()-1, posizione, this))
            energia--;
    }

    public void alto(){
        if(scacchiera.accesso(posizione.getX()-1, posizione.getY(), posizione, this))
            energia--;
    }

    public void alto_destra(){
        if(scacchiera.accesso(posizione.getX()-1, posizione.getY()+1, posizione, this))
            energia--;
    }

    public void destra(){
        if(scacchiera.accesso(posizione.getX(), posizione.getY()+1, posizione, this))
            energia--;
    }

    public void basso_destra(){
        if(scacchiera.accesso(posizione.getX()+1, posizione.getY()+1, posizione, this))
            energia--;
    }

    public void basso(){
        if(scacchiera.accesso(posizione.getX()+1, posizione.getY(), posizione, this))
            energia--;
    }

    public void basso_sinistra(){
        if(scacchiera.accesso(posizione.getX()+1, posizione.getY()-1, posizione, this))
            energia--;
    }

    public void pianta(){
        if(scacchiera.bandiera(posizione, nome, stoffa, adiacenti, this)){
            energia--;
            territori.add(posizione);
        }
    }
}

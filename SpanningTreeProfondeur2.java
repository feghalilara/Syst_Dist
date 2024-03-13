import visidia.simulation.process.algorithm.LC0_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class SpanningTreeProfondeur2 extends LC0_Algorithm{
        
        int portPere = -1;
        String etatVoisins[];

        @Override
        public String getDescription() {

                return "Spanning tree algorithm using parcours en profondeur.\n" +
                                "Rule 01: N---A ---> A--x--M \n" +
                                "Rule 2: A--x--M ---> F--x--A \n" + 
                                "A--x--N";
        }

        @Override
        protected void beforeStart(){
                setLocalProperty("label", vertex.getLabel());

                etatVoisins=new String[getArity()];
                for (int i = 0; i < getArity(); i++) {
                        etatVoisins[i] = "N";
                }

        }

        @Override
        protected void onStarCenter() {
                etatVoisins[neighborDoor]=getNeighborProperty("label").toString();

                // rule 1
                if(getLocalProperty("label").equals("N") &&
                        getNeighborProperty("label").equals("A")){

                        setLocalProperty("label", "A");
                        setNeighborProperty("label","M");
                        setDoorState(new MarkedState(true), neighborDoor);
                        portPere = neighborDoor;
                        etatVoisins[neighborDoor]="M";
                }
                // rule 2
                else if(getLocalProperty("label").equals("A") &&
                                getNeighborProperty("label").equals("M") &&
                                portPere == neighborDoor &&
                                nbVoisinsN() == 0){
                        
                        setLocalProperty("label", "F");
                        setNeighborProperty("label","A");
                        setDoorState(new MarkedState(true), neighborDoor);
                        portPere = neighborDoor;
                        etatVoisins[neighborDoor]="A";
                }

        }

        private int nbVoisinsN(){
                int nb = 0;
                for(int i=0; i < etatVoisins.length; i++){
                        if(etatVoisins[i]=="N"){
                                nb++;
                        }
                }
                return nb;
        }

        @Override
        public Object clone() {
                return new SpanningTreeProfondeur2();
        }
}
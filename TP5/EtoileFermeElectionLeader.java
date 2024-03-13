import visidia.simulation.process.algorithm.LC2_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;
import visidia.simulation.SimulationConstants;
import java.util.ArrayList;

public class EtoileFermeElectionLeader extends LC2_Algorithm{
        
        @Override
        public String getDescription() {

                return "Spanning tree algorithm using LC2 étoile ferme.\n" +
                                "Rule 01: N---N(1) ---> N---F \n" + 
                                "Rule 2: N---!N ---> E(0)---F(0) \n";
        }

        @Override
        protected void beforeStart(){
                setLocalProperty("label", vertex.getLabel());
                setLocalProperty("nbrVoisins", vertex.degree());

                putProperty("Affichage", vertex.degree(), SimulationConstants.PropertyStatus.DISPLAYED);
        }

        @Override
        protected void onStarCenter() {
                for(int i=0; i < getActiveDoors().size(); i++){
                        int numPort = getActiveDoors().get(i);
                        
                        //rule 1
                        if(getLocalProperty("label").equals("N") && 
                                nbVoisinsN() > 0 &&
                                (Integer)getNeighborProperty(numPort, "nbrVoisins")==1){
                                setNeighborProperty(numPort, "label", "F");
                                setNeighborProperty(numPort, "nbrVoisins", 0);
                                setLocalProperty("nbrVoisins", (Integer)getLocalProperty("nbrVoisins")-1);
                        }

                        //rule 2
                        else if(getLocalProperty("label").equals("N") &&
                                nbVoisinsN() == 0 ){

                                setLocalProperty("label", "E");
                        }

                        else if(getLocalProperty("label").equals("F") || getLocalProperty("label").equals("E")){
                                localTermination();
                        }
                        putProperty("Affichage", getLocalProperty("nbrVoisins").toString(), SimulationConstants.PropertyStatus.DISPLAYED);
                }
                putProperty("Affichage", getLocalProperty("nbrVoisins").toString(), SimulationConstants.PropertyStatus.DISPLAYED);

                
        }

        private int nbVoisinsN(){
                int nb = 0; 
                for(int i=0; i < getActiveDoors().size(); i++){
                        int numPort = getActiveDoors().get(i); 
                        if(getNeighborProperty(numPort, "label").equals("N")){
                                nb++;
                        }
                }
                return nb;
        }

        @Override
        public Object clone() {
                return new EtoileFermeElectionLeader();
        }
}
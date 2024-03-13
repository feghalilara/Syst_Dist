import visidia.simulation.process.algorithm.LC1_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;
import visidia.simulation.SimulationConstants;
import java.util.ArrayList;

public class EtoileOuverteElectionLeader extends LC1_Algorithm{
        
        @Override
        public String getDescription() {

                return "Spanning tree algorithm using LC1 étoile ouverte.\n" +
                                "Rule 1: N(1)---N(x) ---> F(0)---N(x-1) with x>1 \n" + 
                                "Rule 2: N(x)---!N(x) ---> E(0)---F(0) \n";
        }

        @Override
        protected void beforeStart(){
                setLocalProperty("label", vertex.getLabel());

        }

        @Override
        protected void onStarCenter() {
                //rule 1
                if(getLocalProperty("label").equals("N") && nbVoisinsN() == 1){

                        setLocalProperty("label", "F");
                }
                // rule 2
                else if(getLocalProperty("label").equals("N") && nbVoisinsN() ==0 ){
                        
                        setLocalProperty("label", "E");
                }

                
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
                return new EtoileOuverteElectionLeader();
        }
}
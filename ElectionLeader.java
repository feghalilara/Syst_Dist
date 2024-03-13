import visidia.simulation.process.algorithm.LC0_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;
import visidia.simulation.SimulationConstants;

public class ElectionLeader extends LC0_Algorithm{
        
        int portPere = -1;
        String etatVoisins[];

        @Override
        public String getDescription() {

                return "Algorithm Leader Election using handshake.\n" +
                                "Rule 1: N(1)---N(x) ---> F(0)---N(x-1)  with x>1 \n" +
                                "Rule 2: N(1)---N(1) ---> E(0)---F(0) \n";
        }

        @Override
        protected void beforeStart(){
                setLocalProperty("label", vertex.getLabel());
                setLocalProperty("nbrVoisins", vertex.degree());

                putProperty("Affichage", vertex.degree(), SimulationConstants.PropertyStatus.DISPLAYED);
        }

        @Override
        protected void onStarCenter() {
                // rule 1
                if(getLocalProperty("label").equals("N") &&
                        Integer.parseInt(getLocalProperty("nbrVoisins").toString()) == 1 &&
                        getNeighborProperty("label").equals("N") &&
                        Integer.parseInt(getNeighborProperty("nbrVoisins").toString()) > 1){

                        setLocalProperty("label", "F");
                        setLocalProperty("nbrVoisins", 0);
                        setNeighborProperty("nbrVoisins", Integer.parseInt(getNeighborProperty("nbrVoisins").toString())-1);
                        
                }
                // rule 2
                else if(getLocalProperty("label").equals("N") &&
                        Integer.parseInt(getLocalProperty("nbrVoisins").toString()) == 1 &&
                        getNeighborProperty("label").equals("N")  &&
                        Integer.parseInt(getNeighborProperty("nbrVoisins").toString()) == 1){
                        
                        setLocalProperty("label", "E");
                        setLocalProperty("nbrVoisins", 0);
                        setNeighborProperty("label", "F");
                        setNeighborProperty("nbrVoisins", 0);

                }
                putProperty("Affichage", getLocalProperty("nbrVoisins").toString(), SimulationConstants.PropertyStatus.DISPLAYED);

        }


        @Override
        public Object clone() {
                return new ElectionLeader();
        }
}
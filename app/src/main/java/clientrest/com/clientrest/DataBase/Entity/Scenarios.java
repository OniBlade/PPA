package clientrest.com.clientrest.DataBase.Entity;

/**
 * Created by Fagner Roger on 16/10/2017.
 */

public class Scenarios {

    private int trainingScenariosId;
    private String scenario;
    private int state;
    private int decision;
    private int trainingSetId;

    public int getDecision() {
        return decision;
    }

    public int getTrainingSetId() {
        return trainingSetId;
    }

    public void setTrainingSetId(int trainingSetId) {
        this.trainingSetId = trainingSetId;
    }

    public void setDecision(int decision) {
        this.decision = decision;
    }

    public int getTrainingScenariosId() {
        return trainingScenariosId;
    }

    public void setTrainingScenariosId(int trainingScenariosId) {
        this.trainingScenariosId = trainingScenariosId;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

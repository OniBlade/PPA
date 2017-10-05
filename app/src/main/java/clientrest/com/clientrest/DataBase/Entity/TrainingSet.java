package clientrest.com.clientrest.DataBase.Entity;

/**
 * Created by Fagner Roger on 03/10/2017.
 */

public class TrainingSet {
    private Integer trainingSetId;
    private Integer deviceType;
    private String dataType;
    private String userBenefit;
    private String retention;
    private String location;
    private String shared;
    private String inferred;
    private String result;

    public TrainingSet(Integer trainingSetId) {
        this.trainingSetId = trainingSetId;
    }

    public TrainingSet() {

    }

    public Integer getTrainingSetId() {
        return trainingSetId;
    }

    public void setTrainingSetId(Integer trainingSetId) {
        this.trainingSetId = trainingSetId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getUserBenefit() {
        return userBenefit;
    }

    public void setUserBenefit(String userBenefit) {
        this.userBenefit = userBenefit;
    }

    public String getRetention() {
        return retention;
    }

    public void setRetention(String retention) {
        this.retention = retention;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public String getInferred() {
        return inferred;
    }

    public void setInferred(String inferred) {
        this.inferred = inferred;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

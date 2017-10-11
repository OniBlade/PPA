/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientrest.com.clientrest.DataBase.Entity;


/**
 * @author Fagner Roger
 */
public class InferredDecisionAttributes {

    private static final long serialVersionUID = 1L;
    private int inferredDecisionAttributesId;
    private int state;
    private Double trustLevel;
    private DataAttributes dataAttributes;
    private InferredDecision inferredDecisionId;
    private String anonymised_information;


    public InferredDecisionAttributes() {
    }

    public int getInferredDecisionAttributesId() {
        return inferredDecisionAttributesId;
    }

    public void setInferredDecisionAttributesId(int inferredDecisionAttributesId) {
        this.inferredDecisionAttributesId = inferredDecisionAttributesId;
    }

    public String getAnonymised_information() {
        return anonymised_information;
    }

    public void setAnonymised_information(String anonymised_information) {
        this.anonymised_information = anonymised_information;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Double getTrustLevel() {
        return trustLevel;
    }

    public void setTrustLevel(Double trustLevel) {
        this.trustLevel = trustLevel;
    }

    public DataAttributes getDataAttributes() {
        return dataAttributes;
    }

    public void setDataAttributes(DataAttributes dataAttributes) {
        this.dataAttributes = dataAttributes;
    }

    public InferredDecision getInferredDecisionId() {
        return inferredDecisionId;
    }

    public void setInferredDecisionId(InferredDecision inferredDecisionId) {
        this.inferredDecisionId = inferredDecisionId;
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientrest.com.clientrest.DataBase.Entity;


/**
 * @author Fagner Roger
 */
public class Request {

    private static final long serialVersionUID = 1L;
    private Integer requestId;
    private String uuid;
    private Integer checkUser;
    private Integer state;
    private String reason;
    private String userBenefit;
    private String location;
    private Consumer consumerId;
    private Data dataId;
    private InferredDecision inferredDecisionId;
    private UserDecision userDecisionId;

    public Request() {
        this.state = 0; //false
    }

    public Request(Integer requestId) {
        this.requestId = requestId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(Integer checkUser) {
        this.checkUser = checkUser;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserBenefit() {
        return userBenefit;
    }

    public void setUserBenefit(String userBenefit) {
        this.userBenefit = userBenefit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Consumer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Consumer consumerId) {
        this.consumerId = consumerId;
    }

    public Data getDataId() {
        return dataId;
    }

    public void setDataId(Data dataId) {
        this.dataId = dataId;
    }

    public InferredDecision getInferredDecisionId() {
        return inferredDecisionId;
    }

    public void setInferredDecisionId(InferredDecision inferredDecisionId) {
        this.inferredDecisionId = inferredDecisionId;
    }

    public UserDecision getUserDecisionId() {
        return userDecisionId;
    }

    public void setUserDecisionId(UserDecision userDecisionId) {
        this.userDecisionId = userDecisionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestId != null ? requestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Request)) {
            return false;
        }
        Request other = (Request) object;
        if ((this.requestId == null && other.requestId != null) || (this.requestId != null && !this.requestId.equals(other.requestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clientrest.entity.Request[ requestId=" + requestId + " ]";
    }

}

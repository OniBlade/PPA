/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientrest.com.clientrest.DataBase.Entity;


import java.util.List;

/**
 *
 * @author Fagner Roger
 */public class UserDecision {

    private static final long serialVersionUID = 1L;
    private Integer userDecisionId;
    private List<UserDecisionAttributes> userDecisionAttributesList;
    
    public UserDecision() {
    }

    public List<UserDecisionAttributes> getUserDecisionAttributesList() {
        return userDecisionAttributesList;
    }

    public void setUserDecisionAttributesList(List<UserDecisionAttributes> userDecisionAttributesList) {
        this.userDecisionAttributesList = userDecisionAttributesList;
    }

    public UserDecision(Integer userDecisionId) {
        this.userDecisionId = userDecisionId;
    }

    public Integer getUserDecisionId() {
        return userDecisionId;
    }

    public void setUserDecisionId(Integer userDecisionId) {
        this.userDecisionId = userDecisionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userDecisionId != null ? userDecisionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserDecision)) {
            return false;
        }
        UserDecision other = (UserDecision) object;
        if ((this.userDecisionId == null && other.userDecisionId != null) || (this.userDecisionId != null && !this.userDecisionId.equals(other.userDecisionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clientrest.entity.UserDecision[ userDecisionId=" + userDecisionId + " ]";
    }
    
}

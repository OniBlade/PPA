/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientrest.com.clientrest.DataBase.Entity;

import android.util.Log;

/**
 * @author Fagner Roger
 */
public class UserDecisionAttributes {

    private static final long serialVersionUID = 1L;
    private Integer userDecisionAttributesId;
    private Integer state;
    private String information;
    private DataAttributes dataAtttributeId;
    private UserDecision userDecisionId;

    public UserDecisionAttributes() {
    }

    public UserDecisionAttributes(Integer userDecisionAttributesId) {
        this.userDecisionAttributesId = userDecisionAttributesId;
    }

    public Integer getUserDecisionAttributesId() {
        return userDecisionAttributesId;
    }

    public void setUserDecisionAttributesId(Integer userDecisionAttributesId) {
        this.userDecisionAttributesId = userDecisionAttributesId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        if (information != null) {
            this.information = information;
        } else {
            this.information = "";
        }
    }

    public DataAttributes getDataAtttributeId() {
        return dataAtttributeId;
    }

    public void setDataAtttributeId(DataAttributes dataAtttributeId) {
        this.dataAtttributeId = dataAtttributeId;
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
        hash += (userDecisionAttributesId != null ? userDecisionAttributesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserDecisionAttributes)) {
            return false;
        }
        UserDecisionAttributes other = (UserDecisionAttributes) object;
        if ((this.userDecisionAttributesId == null && other.userDecisionAttributesId != null) || (this.userDecisionAttributesId != null && !this.userDecisionAttributesId.equals(other.userDecisionAttributesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clientrest.entity.UserDecisionAttributes[ userDecisionAttributesId=" + userDecisionAttributesId + " ]";
    }

}

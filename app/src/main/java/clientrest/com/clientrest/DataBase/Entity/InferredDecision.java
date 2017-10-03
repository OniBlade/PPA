/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientrest.com.clientrest.DataBase.Entity;


/**
 *
 * @author Fagner Roger
 */
public class InferredDecision  {

    private static final long serialVersionUID = 1L;
     private Integer inferredDecisionId;
    
    public InferredDecision() {
    }

    public InferredDecision(Integer inferredDecisionId) {
        this.inferredDecisionId = inferredDecisionId;
    }

    public Integer getInferredDecisionId() {
        return inferredDecisionId;
    }

    public void setInferredDecisionId(Integer inferredDecisionId) {
        this.inferredDecisionId = inferredDecisionId;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inferredDecisionId != null ? inferredDecisionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InferredDecision)) {
            return false;
        }
        InferredDecision other = (InferredDecision) object;
        if ((this.inferredDecisionId == null && other.inferredDecisionId != null) || (this.inferredDecisionId != null && !this.inferredDecisionId.equals(other.inferredDecisionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clientrest.entity.InferredDecision[ inferredDecisionId=" + inferredDecisionId + " ]";
    }
    
}

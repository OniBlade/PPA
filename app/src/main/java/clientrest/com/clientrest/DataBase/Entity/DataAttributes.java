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
public class DataAttributes  {

    private static final long serialVersionUID = 1L;
    private Integer dataAttributesId;
    private String attribute;
    private Integer shared;
    private String retention;
    private Integer inferred;
    private Integer dataId;
    //private String user_benefit;
    private Integer trainingSetId;

    public DataAttributes() {
    }

    public Integer getTrainingSetId() {
        return trainingSetId;
    }

    public void setTrainingSetId(Integer trainingSetId) {
        this.trainingSetId = trainingSetId;
    }


    public DataAttributes(Integer dataAttributesId) {
        this.dataAttributesId = dataAttributesId;
    }

    public Integer getDataAttributesId() {
        return dataAttributesId;
    }

    public void setDataAttributesId(Integer dataAttributesId) {
        this.dataAttributesId = dataAttributesId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Integer getShared() {
        return shared;
    }

    public void setShared(Integer shared) {
        this.shared = shared;
    }

    public String getRetention() {
        return retention;
    }

    public void setRetention(String retention) {
        this.retention = retention;
    }

    public Integer getInferred() {
        return inferred;
    }

    public void setInferred(Integer inferred) {
        this.inferred = inferred;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataAttributesId != null ? dataAttributesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataAttributes)) {
            return false;
        }
        DataAttributes other = (DataAttributes) object;
        if ((this.dataAttributesId == null && other.dataAttributesId != null) || (this.dataAttributesId != null && !this.dataAttributesId.equals(other.dataAttributesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clientrest.entity.DataAttributes[ dataAttributesId=" + dataAttributesId + " ]";
    }

}

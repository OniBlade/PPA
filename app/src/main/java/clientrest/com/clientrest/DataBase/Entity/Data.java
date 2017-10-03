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
 */
public class Data {

    private static final long serialVersionUID = 1L;
    private Integer dataId;
    private List<DataAttributes> dataAttributesList;


    public Data() {
    }

    public Data(Integer dataId) {
        this.dataId = dataId;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public List<DataAttributes> getDataAttributesList() {
        return dataAttributesList;
    }

    public void setDataAttributesList(List<DataAttributes> dataAttributesList) {
        this.dataAttributesList = dataAttributesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataId != null ? dataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Data)) {
            return false;
        }
        Data other = (Data) object;
        if ((this.dataId == null && other.dataId != null) || (this.dataId != null && !this.dataId.equals(other.dataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clientrest.entity.Data[ dataId=" + dataId + " ]";
    }
    
}

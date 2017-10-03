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
public class Consumer {

    String teste="";
    private static final long serialVersionUID = 1L;
    private Integer consumerId;
    private List<ConsumerAttributes> consumerAttributesList;
    
    public Consumer() {
    }

    public Consumer(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public List<ConsumerAttributes> getConsumerAttributesList() {
        return consumerAttributesList;
    }

    public void setConsumerAttributesList(List<ConsumerAttributes> consumerAttributesList) {
        this.consumerAttributesList = consumerAttributesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consumerId != null ? consumerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumer)) {
            return false;
        }
        Consumer other = (Consumer) object;
        if ((this.consumerId == null && other.consumerId != null) || (this.consumerId != null && !this.consumerId.equals(other.consumerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clientrest.entity.Consumer[ consumerId=" + consumerId + " ]";
    }
    
}

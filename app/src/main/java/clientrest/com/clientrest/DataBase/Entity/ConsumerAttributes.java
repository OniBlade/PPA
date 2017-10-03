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
public class ConsumerAttributes  {

    private static final long serialVersionUID = 1L;
    private Integer consumerAttributesId;
    private String attribute;
    private String value;
    private Consumer consumerId;

    public ConsumerAttributes() {
    }

    public ConsumerAttributes(Integer consumerAttributesId) {
        this.consumerAttributesId = consumerAttributesId;
    }

    public Integer getConsumerAttributesId() {
        return consumerAttributesId;
    }

    public void setConsumerAttributesId(Integer consumerAttributesId) {
        this.consumerAttributesId = consumerAttributesId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Consumer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Consumer consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consumerAttributesId != null ? consumerAttributesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsumerAttributes)) {
            return false;
        }
        ConsumerAttributes other = (ConsumerAttributes) object;
        if ((this.consumerAttributesId == null && other.consumerAttributesId != null) || (this.consumerAttributesId != null && !this.consumerAttributesId.equals(other.consumerAttributesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clientrest.entity.ConsumerAttributes[ consumerAttributesId=" + consumerAttributesId + " ]";
    }
    
}

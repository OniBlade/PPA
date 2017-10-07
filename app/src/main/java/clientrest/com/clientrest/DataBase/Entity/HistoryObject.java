package clientrest.com.clientrest.DataBase.Entity;

/**
 * Created by Fagner Roger on 06/10/2017.
 */

public class HistoryObject {
    private int data_attributes_id;
    private String consumer_attribute;
    private String consumer_value;
    private String request_reason;
    private String data_attributes_attribute;
    private int inferred_decision_attributes_state;
    private Double inferred_decision_attributes_trust_level;

    public HistoryObject() {
    }

    public int getData_attributes_id() {
        return data_attributes_id;
    }

    public void setData_attributes_id(int data_attributes_id) {
        this.data_attributes_id = data_attributes_id;
    }

    public String getConsumer_attribute() {
        return consumer_attribute;
    }

    public void setConsumer_attribute(String consumer_attribute) {
        this.consumer_attribute = consumer_attribute;
    }

    public String getConsumer_value() {
        return consumer_value;
    }

    public void setConsumer_value(String consumer_value) {
        this.consumer_value = consumer_value;
    }

    public String getRequest_reason() {
        return request_reason;
    }

    public void setRequest_reason(String request_reason) {
        this.request_reason = request_reason;
    }

    public String getData_attributes_attribute() {
        return data_attributes_attribute;
    }

    public void setData_attributes_attribute(String data_attributes_attribute) {
        this.data_attributes_attribute = data_attributes_attribute;
    }

    public int getInferred_decision_attributes_state() {
        return inferred_decision_attributes_state;
    }

    public void setInferred_decision_attributes_state(int inferred_decision_attributes_state) {
        this.inferred_decision_attributes_state = inferred_decision_attributes_state;
    }

    public Double getInferred_decision_attributes_trust_level() {
        return inferred_decision_attributes_trust_level;
    }

    public void setInferred_decision_attributes_trust_level(Double inferred_decision_attributes_trust_level) {
        this.inferred_decision_attributes_trust_level = inferred_decision_attributes_trust_level;
    }
}

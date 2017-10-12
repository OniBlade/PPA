package clientrest.com.clientrest.DataBase.Entity;

/**
 * Created by Fagner Roger on 11/10/2017.
 */

public class Settings {

    private int configurationId;
    private int confidenceLevel;
    private int alwaysNotify;
    private int notifyNewConsumer;
    private int soundNotification;

    public int getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(int configurationId) {
        this.configurationId = configurationId;
    }

    public int getConfidenceLevel() {
        return confidenceLevel;
    }

    public void setConfidenceLevel(int confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    public int getAlwaysNotify() {
        return alwaysNotify;
    }

    public void setAlwaysNotify(int alwaysNotify) {
        this.alwaysNotify = alwaysNotify;
    }

    public int getNotifyNewConsumer() {
        return notifyNewConsumer;
    }

    public void setNotifyNewConsumer(int notifyNewConsumer) {
        this.notifyNewConsumer = notifyNewConsumer;
    }

    public int getSoundNotification() {
        return soundNotification;
    }

    public void setSoundNotification(int soundNotification) {
        this.soundNotification = soundNotification;
    }
}

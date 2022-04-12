package Entities;

abstract class SmartDevice {

    private String id;
    private Mode mode;


    public SmartDevice() {
        this.id = "";
        this.mode = Mode.OFF;
    }

    public SmartDevice(String s) {
        this.id = s;
        this.mode = Mode.OFF;
    }

    public SmartDevice(String s, Mode m){
        this.id = s;
        this.mode = m;
    }

    public SmartDevice(SmartDevice sm) {
        this.id = sm.getId();
        this.mode = sm.getMode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void turnOn() {
        this.mode = Mode.ON;
    }

    public void turnOff() {
        this.mode = Mode.OFF;
    }
}

package Entities;

import Enums.Mode;

abstract class SmartDevice {

    private String id;
    private Mode mode;
    private double installationCost;


    public SmartDevice() {
        this.id = "";
        this.mode = Mode.OFF;
        this.installationCost = 0;
    }

    public SmartDevice(String s) {
        this.id = s;
        this.mode = Mode.OFF;
        this.installationCost = 0;
    }

    public SmartDevice(String s, Mode m, double installationCost){
        this.id = s;
        this.mode = m;
        this.installationCost = installationCost;
    }

    public SmartDevice(SmartDevice sm) {
        this.id = sm.getId();
        this.mode = sm.getMode();
        this.installationCost = sm.getInstallationCost();
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

    public double getInstallationCost() {
        return this.installationCost;
    }

    public void setInstallationCost(double installationCost) {
        this.installationCost = installationCost;
    }

    public void turnOn() {
        this.mode = Mode.ON;
    }

    public void turnOff() {
        this.mode = Mode.OFF;
    }

   public abstract SmartDevice clone();

    public abstract double consumoEnergetico();

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        SmartDevice sd = (SmartDevice) o;
        return ( this.id.equals(sd.getId()) && this.mode == sd.getMode());
    }
}

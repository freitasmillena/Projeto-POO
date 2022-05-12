package Entities;

import Enums.Mode;

import java.util.Set;
import java.util.TreeSet;

abstract class SmartDevice {

    private String id;
    private Mode mode;
    private double consumptionBase;
    private Set<Log> logs;

    public SmartDevice() {
        this.id = "";
        this.mode = Mode.OFF;
        this.consumptionBase = 0;
        this.logs = new TreeSet<>();
    }

    public SmartDevice(String s) {
        this.id = s;
        this.mode = Mode.OFF;
        this.consumptionBase = 0;
        this.logs = new TreeSet<>();
    }

    public SmartDevice(String s, Mode m, double consumptionBase, Set<Log> logs){
        this.id = s;
        this.mode = m;
        this.consumptionBase = consumptionBase;
        this.logs = new TreeSet<>();
        for(Log l : logs){
            this.logs.add(l.clone());
        }
    }

    public SmartDevice(String s, double consumptionBase){
        this.id = s;
        this.mode = Mode.OFF;
        this.consumptionBase = consumptionBase;
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

    public double getConsumptionBase(){ return this.consumptionBase;}

    public void setConsumptionBase(double consumptionBase){ this.consumptionBase = consumptionBase;}

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

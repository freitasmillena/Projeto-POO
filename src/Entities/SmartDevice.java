package Entities;

import Enums.Mode;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

abstract class SmartDevice {

    private String id;
    private Mode mode;
    private double consumptionBase;
    private NavigableMap<LocalDate, Mode> logs;

    public SmartDevice() {
        this.id = "";
        this.mode = Mode.OFF;
        this.consumptionBase = 0;
        this.logs = new TreeMap<>();
    }

    public SmartDevice(String s) {
        this.id = s;
        this.mode = Mode.OFF;
        this.consumptionBase = 0;
        this.logs = new TreeMap<>();
    }

    public SmartDevice(String s, Mode m, double consumptionBase, LocalDate fromDate){
        this.id = s;
        this.mode = m;
        this.consumptionBase = consumptionBase;
        this.logs = new TreeMap<>();
        this.logs.put(fromDate, m);

    }

    public SmartDevice(String s, double consumptionBase){
        this.id = s;
        this.mode = Mode.OFF;
        this.consumptionBase = consumptionBase;
        this.logs = new TreeMap<>();
    }

    public SmartDevice(SmartDevice sm) {
        this.id = sm.getId();
        this.mode = sm.getMode();
        this.consumptionBase = sm.getConsumptionBase();
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

    public void addLog(LocalDate fromDate, Mode mode){

        this.logs.put(fromDate,mode);
    }

   public abstract SmartDevice clone();

    public abstract double consumoEnergetico();

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        SmartDevice sd = (SmartDevice) o;
        return ( this.id.equals(sd.getId()) && this.mode == sd.getMode());
    }

    public double totalConsumo(LocalDate fromDate, LocalDate toDate){

        LocalDate inicio = fromDate;
        LocalDate intermedio =fromDate;
        Mode mode;
        long dias = 0;


        while(intermedio.compareTo(toDate) < 0) {
            if (this.logs.containsKey(inicio)) {
                mode = this.logs.get(inicio);

            }
            else {
                Map.Entry<LocalDate, Mode> value = this.logs.lowerEntry(inicio);
                mode = value.getValue();
            }

            Map.Entry<LocalDate, Mode> value = this.logs.higherEntry(inicio);

            if(value == null){
                intermedio = toDate;
            }
            else {
                intermedio = value.getKey();
            }

            if (mode.equals(Mode.ON)) {
                    dias += Duration.between(inicio, intermedio).toDays();
            }

            inicio = intermedio;
        }

        return consumoEnergetico()*dias;

    }
}

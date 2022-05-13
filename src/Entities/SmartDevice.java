package Entities;

import Enums.Mode;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

abstract class SmartDevice {

    private String id;
    private double consumptionBase;
    private NavigableMap<LocalDate, Mode> logs;

    public SmartDevice() {
        this.id = "";
        this.consumptionBase = 0;
        this.logs = new TreeMap<>();
    }

    public SmartDevice(String s) {
        this.id = s;
        this.consumptionBase = 0;
        this.logs = new TreeMap<>();
    }

    public SmartDevice(String s, Mode m, double consumptionBase, LocalDate fromDate){
        this.id = s;
        this.consumptionBase = consumptionBase;
        this.logs = new TreeMap<>();
        this.logs.put(fromDate,m);

    }

    public SmartDevice(String s, double consumptionBase){
        this.id = s;
        this.consumptionBase = consumptionBase;
        this.logs = new TreeMap<>();
    }

    public SmartDevice(SmartDevice sm) {
        this.id = sm.getId();
        this.consumptionBase = sm.getConsumptionBase();
        this.logs = sm.getLogs();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

      public double getConsumptionBase(){ return this.consumptionBase;}

    public void setConsumptionBase(double consumptionBase){ this.consumptionBase = consumptionBase;}


    public void addLog(LocalDate fromDate, Mode mode){

        this.logs.put(fromDate,mode);
    }

   public NavigableMap<LocalDate, Mode> getLogs(){
        NavigableMap<LocalDate, Mode> result = new TreeMap<>();

        for(LocalDate date : this.logs.keySet()){
            result.put(date, this.logs.get(date));
        }
        return result;
    }

   public Mode lastRecentMode(){
        Map.Entry<LocalDate, Mode> last = this.logs.lastEntry();
        return last.getValue();
    }

   public abstract SmartDevice clone();

    public abstract double consumoEnergetico();

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        SmartDevice sd = (SmartDevice) o;
        return ( this.id.equals(sd.getId()) &&
                this.consumptionBase == sd.getConsumptionBase() &&
                this.logs.equals(sd.getLogs()));
    }

    public double totalConsumo(LocalDate fromDate, LocalDate toDate){

        LocalDate inicio = fromDate;
        LocalDate intermedio =fromDate;
        Mode mode;
        int dias = 0;
        double total = consumoEnergetico();


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
                    dias += ChronoUnit.DAYS.between(inicio,intermedio);
            }

            inicio = intermedio;


        }


        return total;

    }
}

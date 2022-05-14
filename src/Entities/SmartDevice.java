package Entities;

import Entities.Exceptions.DateAlreadyExistsException;
import Enums.Mode;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

abstract class SmartDevice {

    private String id;
    private double consumptionBase;
    private NavigableMap<LocalDate, Mode> logs; // histórico das alterações, por data

    //Construtor vazio
    public SmartDevice() {
        this.id = "";
        this.consumptionBase = 0;
        this.logs = new TreeMap<>();
    }


   //Construtor completo
    public SmartDevice(String s, Mode m, double consumptionBase, LocalDate fromDate){
        this.id = s;
        this.consumptionBase = consumptionBase;
        this.logs = new TreeMap<>();
        this.logs.put(fromDate,m);

    }

    //Construtor sem Mode
    public SmartDevice(String s, double consumptionBase, LocalDate fromDate){
        this.id = s;
        this.consumptionBase = consumptionBase;
        this.logs = new TreeMap<>();
        this.logs.put(fromDate, Mode.ON); //começam ligados
    }

    //Construtor de cópia
    public SmartDevice(SmartDevice sm) {
        this.id = sm.getId();
        this.consumptionBase = sm.getConsumptionBase();
        this.logs = sm.getLogs();
    }

    //Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getConsumptionBase(){ return this.consumptionBase;}

    public void setConsumptionBase(double consumptionBase){ this.consumptionBase = consumptionBase;}

    //Clone
    public abstract SmartDevice clone();

    //Consumo energético para o dispositivo
    public abstract double consumoEnergetico();

    //Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        SmartDevice sd = (SmartDevice) o;
        return ( this.id.equals(sd.getId()) &&
                this.consumptionBase == sd.getConsumptionBase() &&
                this.logs.equals(sd.getLogs()));
    }

    /****/

    //Adicionar registo de mudança de estado do dispositivo
    public void addLog(LocalDate fromDate, Mode mode) throws DateAlreadyExistsException {
        if(this.logs.containsKey(fromDate)){
            throw new DateAlreadyExistsException("Invalid Date: " + fromDate + " already exists");
        }

        this.logs.put(fromDate,mode);

    }

   //Get logs
   public NavigableMap<LocalDate, Mode> getLogs(){
        NavigableMap<LocalDate, Mode> result = new TreeMap<>();

        for(LocalDate date : this.logs.keySet()){
            result.put(date, this.logs.get(date));
        }
        return result;
    }

   //Modo atual do dispositivo
   public Mode lastRecentMode(){
        Map.Entry<LocalDate, Mode> last = this.logs.lastEntry();
        return last.getValue();
   }


    //Método para percorrer os logs e calcular o consumo total para o dispositivo no intervalo de datas da fatura. toDate exclusive
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

        total *= dias;
        return total;

    }
}

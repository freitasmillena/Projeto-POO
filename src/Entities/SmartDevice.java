package Entities;

import Entities.Exceptions.DateAlreadyExistsException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

abstract class SmartDevice implements Serializable {

    private String id;
    private double consumptionBase;
    private NavigableMap<LocalDate, Integer> logs; // histórico das alterações, por data
    //Mode ON = 1, MODE OFF = 0

    //Construtor vazio
    public SmartDevice() {
        this.id = "";
        this.consumptionBase = 0;
        this.logs = new TreeMap<>();
    }


   //Construtor completo
    public SmartDevice(String s, int mode, double consumptionBase, LocalDate fromDate){
        this.id = s;
        this.consumptionBase = consumptionBase;
        this.logs = new TreeMap<>();
        this.logs.put(fromDate,mode);

    }

    //Construtor sem Mode
    public SmartDevice(String s, double consumptionBase, LocalDate fromDate){
        this.id = s;
        this.consumptionBase = consumptionBase;
        this.logs = new TreeMap<>();
        this.logs.put(fromDate, 1); //começam ligados
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

    public List<String> logToString(NavigableMap<LocalDate, Integer> logs){
        List<String> list = new ArrayList<>();
        String modo;
        for(LocalDate entry : logs.keySet()){
            if(logs.get(entry) == 1) modo = "ON";
            else modo = "OFF";
            list.add(entry.toString()+ " Modo: " + modo);
        }

        return list;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(this.id)
                .append(" ")
                .append("Consumo base: ").append(this.consumptionBase)
                .append(" ")
                .append("\n")
                .append("Histórico de mudanças de estado (ON/OFF): ").append(logToString(this.logs))
                .append("\n");

        return sb.toString();
    }

    /****/

    //Adicionar registo de mudança de estado do dispositivo
    public void addLog(LocalDate fromDate, int mode) throws DateAlreadyExistsException {
        if(this.logs.containsKey(fromDate)){
            throw new DateAlreadyExistsException("Invalid Date: " + fromDate + " already exists");
        }

        this.logs.put(fromDate,mode);

    }

   //Get logs
   public NavigableMap<LocalDate, Integer> getLogs(){
        NavigableMap<LocalDate, Integer> result = new TreeMap<>();

        for(LocalDate date : this.logs.keySet()){
            result.put(date, this.logs.get(date));
        }
        return result;
    }

   //Modo atual do dispositivo
   public int lastRecentMode(){
        Map.Entry<LocalDate, Integer> last = this.logs.lastEntry();
        int mode = last.getValue();
        return mode;
   }


    //Método para percorrer os logs e calcular o consumo total para o dispositivo no intervalo de datas da fatura. toDate exclusive
    public double totalConsumo(LocalDate fromDate, LocalDate toDate){

        LocalDate inicio = fromDate;
        LocalDate intermedio =fromDate;
        int mode;
        int dias = 0;
        double total = consumoEnergetico();


        while(intermedio.compareTo(toDate) < 0) {
            if (this.logs.containsKey(inicio)) {
                mode = this.logs.get(inicio);
            }
            else {
                Map.Entry<LocalDate, Integer> value = this.logs.lowerEntry(inicio);
                mode = value.getValue();

            }

            Map.Entry<LocalDate, Integer> value = this.logs.higherEntry(inicio);

            if(value == null){
                intermedio = toDate;

            }
            else {
                intermedio = value.getKey();

            }

            if (mode==1) { // ON
                dias += ChronoUnit.DAYS.between(inicio,intermedio);
            }

            inicio = intermedio;


        }

        total *= dias;
        return total;

    }


    public void printLogs(){
        for(LocalDate date : this.logs.keySet()){
            System.out.println("Date: " + date + " Mode: " + this.logs.get(date));
        }
    }
}

package Entities;

import Enums.Mode;

import java.time.LocalDate;

public class SmartSpeaker extends SmartDevice{
    public static final int MAX = 100; //volume máximo
    private int volume;
    private String channel;
    private String brand;

    //Construtor vazio
    public SmartSpeaker() {
        // initialise instance variables
        super();
        this.volume = 0;
        this.channel = "";
    }

   //Construtor completo
    public SmartSpeaker(String cod, double consumptionBase, String channel, int volume, String brand, Mode mode, LocalDate fromDate) {
        // initialise instance variables
        super(cod, mode,consumptionBase,fromDate);
        this.channel = channel;
        if(volume>=0 && volume<=MAX) {
            this.volume = volume;
        }
        else this.volume = 0;
        this.brand = brand;
    }

    //Construtor sem Mode
    public SmartSpeaker(String cod, double consumptionBase, String channel, int volume, String brand, LocalDate fromDate) {
        // initialise instance variables
        super(cod,consumptionBase,fromDate);
        this.channel = channel;
        if(volume>=0 && volume<=MAX) {
            this.volume = volume;
        }
        else this.volume = 0;
        this.brand = brand;
    }

    //Construtor de cópia
    public SmartSpeaker(SmartSpeaker s){
        super(s);
        this.channel = s.getChannel();
        this.volume = s.getVolume();
        this.brand = s.getBrand();
    }

    //Getters e Setters
    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {this.volume = volume;}

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    //Clone
    public SmartSpeaker clone(){
        return new SmartSpeaker(this);
    }

    //Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        SmartSpeaker s = (SmartSpeaker) o;
        return (super.equals(s) &&
                this.volume == s.getVolume() &&
                this.channel.equals(s.getChannel()) &&
                this.brand.equals(s.getBrand()));
    }

    //Mudar volume por porcentagem
    public void setPercentageVolume(int percentage){ //hey siri 30% volume :))
        this.volume = percentage * MAX;
    }

    //Calcular consumo energético de um smartspeaker
    public double consumoEnergetico(){
        double consumo;
        if(this.volume <= 0.1*MAX){
            consumo = super.getConsumptionBase();
        }
        else if(this.volume > 0.1*MAX && this.volume <= 0.5*MAX){
                consumo = super.getConsumptionBase()*1.25;
        }
        else{
                consumo = super.getConsumptionBase()*1.5;
        }
        return consumo;
    }


}


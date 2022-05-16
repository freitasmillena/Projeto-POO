package Entities;

import java.time.LocalDate;

public class SmartBulb extends SmartDevice{
    private double dimension;
    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;
    private int tone;

    //Construtor vazio
    public SmartBulb(){
        super();
        this.tone = NEUTRAL;
        this.dimension = 0.0;
    }

    //Construtor completo
    public SmartBulb(String id, int tone, double consumptionBase, double dimension, int mode, LocalDate fromDate) {
        super(id, mode,consumptionBase, fromDate);
        this.tone = tone;
        this.dimension = dimension;
    }

    //Construtor sem Mode e Tone
    public SmartBulb(String id, double consumptionBase, double dimension, LocalDate fromDate) {
        super(id, consumptionBase, fromDate);
        this.tone = NEUTRAL;
        this.dimension = dimension;
    }

    //Construtor cópia
    public SmartBulb(SmartBulb sb) {
        // initialise instance variables
        super(sb);
        this.tone = sb.getTone();
        this.dimension = sb.getDimension();
    }

    //Getters e Setters
    public int getTone() {
        return tone;
    }

    public void setTone(int t) {
        this.tone = t;
    }

    public double getDimension() {
        return dimension;
    }

    public void setDimension(double dimension) {
        this.dimension = dimension;
    }

    //Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        SmartBulb sb = (SmartBulb) o;
        return (super.equals(sb) &&
                this.tone == sb.getTone() &&
                this.dimension == sb.getDimension());
    }

    //Clone
    public SmartBulb clone() {
        return new SmartBulb(this);
    }

    public String toneToString(int tone){
        String result;
        if(tone == WARM) result = "WARM";
        else if(tone == NEUTRAL) result = "NEUTRAL";
        else result = "COLD";
        return result;
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append("Dimensão: ").append(this.dimension)
                .append("\n")
                .append("Tonalidade: ").append(toneToString(this.tone))
                .append("\n");

        return sb.toString();
    }

    //Calcula consumo energético da lâmpada
    public double consumoEnergetico(){
        double valor;
        switch(this.tone){
            case COLD:
                valor = super.getConsumptionBase()*0.15;
                break;
            case WARM:
                valor = super.getConsumptionBase()*0.35;
                break;
            default:
                valor = super.getConsumptionBase()*0.22;
                break;
        }
        return super.getConsumptionBase() + valor;
    }


}

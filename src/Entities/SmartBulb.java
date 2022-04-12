package Entities;

import Enums.Tone;

public class SmartBulb extends SmartDevice{
    private Tone tone;
    private double dimension;
    private double consumoDiario;

    public SmartBulb(){
        super();
        this.tone = Tone.NEUTRAL;
        this.consumoDiario = 0.0;
        this.dimension = 0.0;
    }

    public SmartBulb(String id, Tone tone, double consumoDiario, double dimension) {
        super(id);
        this.tone = tone;
        this.consumoDiario = consumoDiario;
        this.dimension = dimension;
    }

    public SmartBulb(String id) {
        // initialise instance variables
        super(id);
        this.tone = Tone.NEUTRAL;
        this.consumoDiario = 0.0;
        this.dimension = 0.0;
    }

    public SmartBulb(SmartBulb sb) {
        // initialise instance variables
        super(sb);
        this.tone = sb.getTone();
        this.consumoDiario = sb.getConsumoDiario();
        this.dimension = sb.getDimension();
    }

    public Tone getTone() {
        return tone;
    }

    public void setTone(Tone t) {
        this.tone = t;
    }

    public double getDimension() {
        return dimension;
    }

    public void setDimension(double dimension) {
        this.dimension = dimension;
    }

    public double getConsumoDiario() {
        return consumoDiario;
    }

    public void setConsumoDiario(double consumoDiario) {
        this.consumoDiario = consumoDiario;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        SmartBulb sb = (SmartBulb) o;
        return (super.equals(sb) &&
                this.tone == sb.getTone() &&
                this.dimension == sb.getDimension() &&
                this.consumoDiario == sb.getConsumoDiario());
    }

    public SmartBulb clone() {
        return new SmartBulb(this);
    }

    public double consumoEnergetico(){
        //tem que implementar
        return 0;
    }

    /*
    TO THINK
    --------
    O consumo de energia de uma destas lâmpadas é dado por uma fórmula (a conceber por cada grupo)
    e que é função de um valor fixo + factor em função do tipo de luz que está a ser emitida

    Onde entra o consumo diário nesta fórmula?
     */
}

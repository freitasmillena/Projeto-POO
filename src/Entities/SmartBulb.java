package Entities;

import Enums.Tone;

public class SmartBulb extends SmartDevice{
    private Tone tone;
    private double dimension;

    public SmartBulb(){
        super();
        this.tone = Tone.NEUTRAL;
        this.dimension = 0.0;
    }

    public SmartBulb(String id, Tone tone, double consumptionBase, double dimension) {
        super(id,consumptionBase);
        this.tone = tone;
        this.dimension = dimension;
    }

    public SmartBulb(String id) {
        // initialise instance variables
        super(id);
        this.tone = Tone.NEUTRAL;
        this.dimension = 0.0;
    }

    public SmartBulb(SmartBulb sb) {
        // initialise instance variables
        super(sb);
        this.tone = sb.getTone();
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

        public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        SmartBulb sb = (SmartBulb) o;
        return (super.equals(sb) &&
                this.tone == sb.getTone() &&
                this.dimension == sb.getDimension());
    }

    public SmartBulb clone() {
        return new SmartBulb(this);
    }

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

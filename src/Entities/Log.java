package Entities;

import Enums.Mode;

import java.time.LocalDate;

public class Log implements Comparable<Log>{
    private LocalDate fromDate;
    private Mode mode;

    public Log(){
        this.fromDate = LocalDate.now();
        this.mode = Mode.OFF;
    }

    public Log(LocalDate fromDate, Mode mode){
        this.fromDate = fromDate;
        this.mode = mode;
    }

    public Log(Log log){
        this.fromDate = log.getFromDate();
        this.mode = log.getMode();
    }

    public LocalDate getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Log clone(){
        return new Log(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Log log = (Log) o;
        return ( this.fromDate.equals(log.getFromDate()) &&
                this.mode.equals(log.mode));
    }


    public int compareTo(Log log) {
        if(this.fromDate == log.getFromDate()) return 0;
        else if(this.fromDate.compareTo(log.getFromDate()) > 0) return 1;
        else return -1;

    }


}

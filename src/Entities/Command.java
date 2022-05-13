package Entities;

import java.time.LocalDate;

public class Command {
    private LocalDate date;
    private String name; //setMode, changeSupplier, changeFormula
    private String command1;
    private String command2;
    private String command3;

    //Construtor vazio
    public Command(){
        this.name = "";
        this.command1 = "";
        this.command2 = "";
        this.command3 = "";
    }

    //Construtor completo
    public Command(LocalDate date, String name, String command1, String command2, String command3){
        this.date = date;
        this.name = name;
        this.command1 = command1;
        this.command2 = command2;
        this.command2 = command3;
    }

    //Construtor pra nos casos de mudar de fornecedor e fornecedor mudar de formula que só tem 2 comandos
    public Command(LocalDate date, String name, String command1, String command2){
        this.date = date;
        this.name = name;
        this.command1 = command1;
        this.command2 = command2;
        this.command2 = "";
    }

    //Construtor de cópia
    public Command(Command c){
        this.date = c.getDate();
        this.name = c.getName();
        this.command1 = c.getCommand1();
        this.command2 = c.getCommand2();
        this.command3 = c.getCommand3();
    }

    //Getters e Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand1() {
        return command1;
    }

    public void setCommand1(String command1) {
        this.command1 = command1;
    }

    public String getCommand2() {
        return command2;
    }

    public void setCommand2(String command2) {
        this.command2 = command2;
    }

    public String getCommand3() {
        return command3;
    }

    public void setCommand3(String command3) {
        this.command3 = command3;
    }

    //Clone
    public Command clone(){
        return new Command(this);
    }

    //Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Command c = (Command) o;
        return (this.date.equals(c.getDate()) &&
                this.name.equals(c.getName()) &&
                this.command1.equals(c.getCommand1()) &&
                this.command2.equals(c.getCommand2()) &&
                this.command3.equals(c.getCommand3()));
    }


}

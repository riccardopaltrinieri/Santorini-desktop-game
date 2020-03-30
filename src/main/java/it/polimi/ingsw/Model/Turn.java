package it.polimi.ingsw.Model;

public class Turn {
    //Attributi
    private int number;
    private Divinity godName;
    private GodPower godPower;

    //getter e setter

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Divinity getGodName() {
        return godName;
    }

    public void setGodName(Divinity godName) {
        this.godName = godName;
    }

    public GodPower getGodPower() {
        return godPower;
    }

    public void setGodPower(GodPower godPower) {
        this.godPower = godPower;
    }
}

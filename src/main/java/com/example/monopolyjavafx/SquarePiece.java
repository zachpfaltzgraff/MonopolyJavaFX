package com.example.monopolyjavafx;

public class SquarePiece {
    private String name;
    private int type;
    private int occupied;
    private double price;

    SquarePiece() {
        this.name = "";
        this.type = -1;
        this.occupied = -1;
        this.price = -1;
    }

    SquarePiece(String name, int type, double price) {
        this.name = name;
        this.type = type;
        this.occupied = 0;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

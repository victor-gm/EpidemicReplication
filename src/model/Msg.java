package model;

import com.google.gson.Gson;

import java.io.Serializable;

public class Msg implements Serializable {

    private String type;
    private int element;
    private int value;

    public Msg(String type, int element, int value) {
        this.type = type;
        this.element = element;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getElement() {
        return element;
    }

    public String getType() {
        return type;
    }

    public String toString(){
        return "MSG: " + type + " Element: " + element + " Value: " + value;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}

package model;

public class TransactionType {

    private String type;
    private int element;
    private int value;

    public TransactionType(int element, int value) {
        this.element = element;
        this.value = value;
    }

    public TransactionType(String type, int element, int value) {
        this.type = type;
        this.element = element;
        this.value = value;
    }

    public String getType(){
        return this.type;
    }

    public int getElement(){
        return this.element;
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("type: ").append(type).append(" ");
        sb.append("element: ").append(String.valueOf(element)).append(" ");
        sb.append("value: ").append(String.valueOf(value)).append("\n");
        return sb.toString();
    }
}

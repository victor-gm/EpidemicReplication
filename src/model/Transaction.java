/**
 * Class:           Transaction
 * Description:     Contains the data of a transaction
 * @attributes
 * @authors: Matias Villarroel, Víctor Garrido
 */
package model;

public class Transaction {

    private int layer;
    private TransactionType [] transactions;

    public Transaction(int layer, TransactionType[] transactions) {
        this.layer = layer;
        this.transactions = transactions;
    }

    public int getLayer(){
        return layer;
    }
    public TransactionType [] getTransactions(){
        return transactions;
    }


    @Override
    public String toString(){
        String layerString = "Layer: " + String.valueOf(layer) + "\n";
        StringBuilder sb = new StringBuilder(layerString);
        for (int i = 0; i < transactions.length; i++){
            sb.append(transactions[i].toString());
        }
        return sb.toString();
    }
}

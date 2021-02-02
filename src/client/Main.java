package client;

public class Main {

    public static void main(String[] args){

        System.out.println("Starting Client");
        Client c = new Client();
        c.readFile("clientFiles/t_file.txt");
        c.doTransactions();
    }
}

package observer_ex1;

import java.util.ArrayList;
import java.util.List;

public class Stock {
    private String name;
    private double price;
    private List<Investor> investors = new ArrayList<>();
    public Stock(String name, double price) {
        this.name = name;
    }

    public void addInvestor(Investor investor) {
        investors.add(investor);
    }

    public void removeInvestor(Investor investor) {
        investors.remove(investor);
    }

    private void notifyInvestors(){
        for(Investor investor : investors){
            investor.update(name, price);
        }
    }

    public void setPrice(double price) {
        this.price = price;
        notifyInvestors(); // thong bao
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }

    public List<Investor> getInvestors() {
        return investors;
    }
}

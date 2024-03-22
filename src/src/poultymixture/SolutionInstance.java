/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poultymixture;

/**
 *
 * @author Yasser
 */
public class SolutionInstance {
    public double []weights;
    public double []elements;
    double weight=0;
    double pricePerKg=0;
    double totalPrice=0;
    

    public SolutionInstance(int wCount,int eCount) {
        weights=new double[wCount];
        elements=new double[eCount];
    }   
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(double pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

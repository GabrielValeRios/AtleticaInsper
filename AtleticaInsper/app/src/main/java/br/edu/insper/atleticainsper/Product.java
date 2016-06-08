package br.edu.insper.atleticainsper;


public class Product {

    private int id;
    private float costPrice;
    private int criticalQty;
    private int currentQty;
    private boolean hasDiscount;
    private boolean isAvailable;
    private String name;
    private float price;
    private float priceAtl;
    private float priceSoc;
    private int soldQty;

    public Product(int id, float costPrice, int criticalQty, int currentQty, boolean hasDiscount, boolean isAvailable, String name, float price, float priceAtl, float priceSoc, int soldQty) {
        this.id = id;
        this.costPrice = costPrice;
        this.criticalQty = criticalQty;
        this.currentQty = currentQty;
        this.hasDiscount = hasDiscount;
        this.isAvailable = isAvailable;
        this.name = name;
        this.price = price;
        this.priceAtl = priceAtl;
        this.priceSoc = priceSoc;
        this.soldQty = soldQty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public int getCriticalQty() {
        return criticalQty;
    }

    public void setCriticalQty(int criticalQty) {
        this.criticalQty = criticalQty;
    }

    public int getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(int currentQty) {
        this.currentQty = currentQty;
    }

    public boolean hasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPriceAtl() {
        return priceAtl;
    }

    public void setPriceAtl(float priceAtl) {
        this.priceAtl = priceAtl;
    }

    public float getPriceSoc() {
        return priceSoc;
    }

    public void setPriceSoc(float priceSoc) {
        this.priceSoc = priceSoc;
    }

    public int getSoldQty() {
        return soldQty;
    }

    public void setSoldQty(int soldQty) {
        this.soldQty = soldQty;
    }
}

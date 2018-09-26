package me.yorick.poc.protostuff;

public class Order {

    private double price;
    private double qty;
    private String exchange;
    private String symbol;
    private Side side;
    
    
    public Order(double price, double qty, String symbol, String exchange, Side side) {
        this.price = price;
        this.qty=qty;
        this.symbol=symbol;
        this.exchange=exchange;
        this.side=side;
    }
    
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getQty() {
        return qty;
    }
    public void setQty(double qty) {
        this.qty = qty;
    }
    public String getExchange() {
        return exchange;
    }
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public Side getSide() {
        return side;
    }
    public void setSide(Side side) {
        this.side = side;
    }
    
    
}

package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products;

    public Invoice() {
        this.products = new HashMap<Product, Integer>();
    }

    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + 1);
        }
        this.products.put(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {


        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero!");
        }
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + quantity);
        }
        this.products.put(product, quantity);
    }



    public BigDecimal getSubtotal() {
        BigDecimal result = new BigDecimal("0");
        if (products.isEmpty()) {
            return result;
        }
        return products.entrySet().stream().map(entry -> entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue()))).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public BigDecimal getTax() {
        if (products.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return products.entrySet().stream().map(entry -> entry.getKey().getPrice().multiply(entry.getKey().getTaxPercent()).multiply(new BigDecimal(entry.getValue()))).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public BigDecimal getTotal() {
        if (products.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return products.entrySet().stream().map(entry -> entry.getKey().getPriceWithTax().multiply(new BigDecimal(entry.getValue()))).reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
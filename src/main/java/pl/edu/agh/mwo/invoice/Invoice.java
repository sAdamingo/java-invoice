package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private  Collection<Product> products;

    public Invoice() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero!");
        }
        for (int i = 0; i < quantity; i++) {
                this.products.add(product);
        }
    }



    public BigDecimal getSubtotal() {
        BigDecimal result = new BigDecimal("0");
        if (products.isEmpty()) {
            return result;
        }
        BigDecimal sum = products.stream().map(prod -> prod.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum;
    }

    public BigDecimal getTax() {
        if (products.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = products.stream().map(prod -> prod.getPrice().multiply(prod.getTaxPercent())).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum;
    }

    public BigDecimal getTotal() {
        if (products.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = new BigDecimal("0");
        BigDecimal sum = products.stream().map(prod -> prod.getPriceWithTax()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum;
    }
}
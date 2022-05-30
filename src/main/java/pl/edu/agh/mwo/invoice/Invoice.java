package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private int number;

    public void addProduct(Product product) {
        if (products.containsKey(product)){
            products.put(product, products.get(product) + 1);
        } else {
            addProduct(product, 1);
        }
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if (products.containsKey(product)){
            products.put(product, products.get(product) + quantity);
        } else {
            products.put(product, quantity);
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String printInvoice() {
        StringBuilder invoice = new StringBuilder();
        invoice.append("Faktura numer: ").append(getNumber()).append("\n");

        products.forEach((p,k) -> invoice.append(p.getName())
                .append(" sztuk: ").append(k)
                .append(" cena: ").append(p.getPrice().multiply(new BigDecimal(k)).setScale(2, RoundingMode.FLOOR))
                .append("\n"));

        invoice.append("Liczba pozycji: ").append(this.products.size());
        return invoice.toString();
    }

    public Map<Product, Integer> getProducts() {
        return this.products;
    }
}

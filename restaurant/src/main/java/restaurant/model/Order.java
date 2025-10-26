package restaurant.model;

import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.text.NumberFormat;
import java.util.Locale;

public class Order implements Calculable {
    private final List<OrderItem> items = new ArrayList<>();
    private double taxRate = 0.0;
    private double discountRate = 0.0;
    private String customerName = "";

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void addItem(OrderItem item) {
        for (OrderItem oi : items) {
            if (oi.getName().equalsIgnoreCase(item.getName())) {
                oi.setQuantity(oi.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double calculateSubtotal() {
        return items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public double calculateTax() {
        return calculateSubtotal() * taxRate / 100.0;
    }

    public double calculateDiscount() {
        return calculateSubtotal() * discountRate / 100.0;
    }

    @Override
    public double calculateTotal() {
        return calculateSubtotal() + calculateTax() - calculateDiscount();
    }

    private String formatCurrency(double value) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("id","ID"));
        nf.setMaximumFractionDigits(0);
        return nf.format(Math.round(value));
    }

    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        String title = "AMBA CAFE";
        String address = "Jl. Mawar No.21 — Karangploso";
        String line = "=====================================================";
        String line2 = "-----------------------------------------------------";
        sb.append(String.format("%38s%n", title));
        sb.append(String.format("%27s%n", address));
        sb.append(line).append("\n");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        sb.append(String.format("Pelanggan : %-20s Tanggal: %s%n", customerName.isEmpty() ? "-" : customerName, time));
        sb.append(line2).append("\n");
        sb.append(String.format("%-17s %5s %12s %12s%n", "ITEM", "QTY", "HARGA", "SUBTOTAL"));
        sb.append(line2).append("\n");
        for (OrderItem oi : items) {
            String item = oi.getName();
            int qty = oi.getQuantity();
            String harga = formatCurrency(oi.getPrice());
            String subtotal = formatCurrency(oi.getSubtotal());
            sb.append(String.format("%-17s %5d %12s %12s%n", item, qty, harga, subtotal));
        }
        sb.append(line2).append("\n");
        String subtotalS = formatCurrency(calculateSubtotal());
        String taxS = formatCurrency(calculateTax());
        String discS = formatCurrency(calculateDiscount());
        String totalS = formatCurrency(calculateTotal());
        sb.append(String.format("%-36s %12s%n", "Subtotal", subtotalS));
        sb.append(String.format("Pajak (%.2f%%)%-27s %12s%n", taxRate, "", taxS));
        sb.append(String.format("Diskon (%.2f%%)%-25s (%s)%n", discountRate, "", discS));
        sb.append(line).append("\n");
        sb.append(String.format("%-36s %12s%n", "TOTAL", totalS));
        sb.append(line).append("\n");
        sb.append(String.format("%18s%n", "Terima kasih telah berkunjung"));
        return sb.toString();
    }
}

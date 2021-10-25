import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;

class StockItem {
    private String name;
    private double pricePerUnit;
    private int quantity;

    public StockItem(String name, double pricePerUnit, int quantity) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + ": " + pricePerUnit + ", " + quantity + ";";
    }

    public String getName() {
        return name;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public int getQuantity() {
        return quantity;
    }
}

class Utils {
    public static List<StockItem> sort(List<StockItem> stockItems) {
        stockItems.sort(Comparator.comparingDouble((ToDoubleFunction<? super StockItem>) i -> i.getPricePerUnit() * i.getQuantity())
                .reversed());
        return stockItems;
    }
}
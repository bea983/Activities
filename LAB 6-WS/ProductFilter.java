import java.util.ArrayList;
import java.util.Scanner;

public class ProductFilter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<part6Product> products = new ArrayList<>();
        products.add(new part6Product("Laptop", 45000));
        products.add(new part6Product("Mouse", 250));
        products.add(new part6Product("Keyboard", 800));
        products.add(new part6Product("Headset", 1200));
        products.add(new part6Product("Monitor", 6500));

        System.out.print("Enter minimum price: ");
        double minPrice = sc.nextDouble();

        long count = products.stream()
                .filter(p -> p.price > minPrice)
                .count();

        System.out.println("Number of products above " + minPrice + " = " + count);
    }
}

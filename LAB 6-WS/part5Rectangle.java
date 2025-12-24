public class part5Rectangle {
    double length;
    double width;

    public part5Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public double calculateArea() {
        return length * width;
    }
}

class RectangleDemo {
    public static void main(String[] args) {
        part5Rectangle r1 = new part5Rectangle(5, 3);
        part5Rectangle r2 = new part5Rectangle(10, 4.5);

        System.out.println("Area of Rectangle 1: " + r1.calculateArea());
        System.out.println("Area of Rectangle 2: " + r2.calculateArea());
    }
}


package JavaMidtermControlTask;

public class Toy {
    private int id;
    private String name;
    private int quantity;
    private double frequency;

    public Toy(int id, String name, int quantity, double frequency) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.frequency = frequency;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        sb.append("Игрушка: ");
        sb.append(this.name);
        sb.append(" ");
        sb.append("id: ");
        sb.append(this.id);
        sb.append(" ");
        sb.append("Количество: ");
        sb.append(this.quantity);
        sb.append(" ");
        sb.append("Частота выпадения: ");
        sb.append(this.frequency);
        sb.append("% ");
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
}

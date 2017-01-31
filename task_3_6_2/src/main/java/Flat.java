public class Flat {
    private String region;
    private String address;
    private double square;
    private int rooms;
    private double price;

    public Flat(String region, String address, double square, int rooms, double price) {
        this.region = region;
        this.address = address;
        this.square = square;
        this.rooms = rooms;
        this.price = price;
    }

    public String getRegion() {
        return region;
    }

    public String getAddress() {
        return address;
    }

    public double getSquare() {
        return square;
    }

    public int getRooms() {
        return rooms;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "[" + region + "; " + address + "; " + square + "; " + rooms + "; " + price + "]";
    }
}

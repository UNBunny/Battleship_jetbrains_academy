package battleship;

public enum Ship {

    AIRCRAFT("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String printName;
    private final int length;

    public String getPrintName() {
        return printName;
    }
    public int getLength() {
        return length;
    }
    Ship(String printName, int length) {
        this.printName = printName;
        this.length = length;
    }
}

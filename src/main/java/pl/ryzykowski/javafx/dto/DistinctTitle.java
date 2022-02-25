package pl.ryzykowski.javafx.dto;

public class DistinctTitle {

    private String title;
    private long number;

    public DistinctTitle(String title, long number) {
        this.title = title;
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return title + ": " + number;
    }
}

package ru.geekbrains;

import java.util.Objects;

public class Vertex {
    private final String name;
    private boolean checkIn;

    public Vertex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public String toString() {
        return "Vertex{ " + name + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return name.equals(vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

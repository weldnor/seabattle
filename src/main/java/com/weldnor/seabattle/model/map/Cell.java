package com.weldnor.seabattle.model.map;

public class Cell implements Cloneable {
    private boolean isDestroyed;
    private CellType type;
    private Point point;

    public Cell(CellType type, Point point) {
        this.type = type;
        this.point = point;
        this.isDestroyed = false;
    }

    public Cell(boolean isDestroyed, CellType type, Point point) {
        this.isDestroyed = isDestroyed;
        this.type = type;
        this.point = point;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (isDestroyed != cell.isDestroyed) return false;
        if (type != cell.type) return false;
        return point.equals(cell.point);
    }

    @Override
    public int hashCode() {
        int result = (isDestroyed ? 1 : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + point.hashCode();
        return result;
    }

    @Override
    protected Object clone() {
        return new Cell(isDestroyed, type, (Point) point.clone());
    }
}

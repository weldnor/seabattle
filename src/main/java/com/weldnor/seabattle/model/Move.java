package com.weldnor.seabattle.model;

import com.weldnor.seabattle.model.map.Point;

public class Move {
    private MoveType type;
    private Point point;

    public Move(MoveType type, Point point) {
        this.type = type;
        this.point = point;
    }

    public MoveType getType() {
        return type;
    }

    public void setType(MoveType type) {
        this.type = type;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Move{" +
                "type=" + type +
                ", point=" + point +
                '}';
    }
}

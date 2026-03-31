package org.firstinspires.ftc.teamcode;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * VisualPathVirtualField
 *
 * A simple 2D matrix wrapper intended to be used to simulate a field for
 * VisualPath.
 *
 * Usage:
 * Create a new field with the width, length, and initial position, to then
 * write/shift/read the position of the robot.
 *
 * Has support for field elements, which can be given their own dimensions and positions.
 */

public class VisualPathVirtualField {

    // Field Dimensions
    private double[] fieldSize;

    // Robot Position
    private double[] robotPosition;

    // Field Elements
    private Dictionary<double[], double[]> elements;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * @param fieldSize The size of the field as [length, width]
     * @param robotPosition The initial position of the robot, as a list of [x, y]
     */
    public VisualPathVirtualField(double[] fieldSize, double[] robotPosition) {
        this.fieldSize = fieldSize;
        this.robotPosition = robotPosition;
    }

    /**
     * @param fieldSize The size of the field as [length, width]
     * @param robotPosition The initial position of the robot, as a list of [x, y]
     * @param elements A Dictionary<double[], double[]> with the positions (top-left corner) and dimensions of all elements as {[x, y], [length, width]}
     */
    public VisualPathVirtualField(double[] fieldSize, double[] robotPosition, Dictionary<double[], double[]> elements) {
        this.fieldSize = fieldSize;
        this.robotPosition = robotPosition;
        this.elements = elements;
    }

    // -------------------------------------------------------------------------
    // Manipulators
    // -------------------------------------------------------------------------

    public void setRobotPosition(double[] robotPosition) {
        this.robotPosition = robotPosition;
    }

    public void shiftRobotPosition(double xShift, double yShift) {
        this.robotPosition[0] += xShift;
        this.robotPosition[1] += yShift;
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public double[] getRobotPosition() {
        return this.robotPosition;
    }

    public double[] getFieldSize() {
        return fieldSize;
    }
}

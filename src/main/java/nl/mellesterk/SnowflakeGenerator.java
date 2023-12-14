package nl.mellesterk;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.list.FixedSizeList;

class Point {
    public double x;
    public double y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return String.format("%f %f", this.x, this.y);
    }
    public Vec2D toVec2D(Point from) {
        return new Vec2D(this.x - from.x, this.y - from.y);
    }

}

class Vec2D {
    public double x;
    public double y;
    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.x + " " + this.y;
    }
    public Vec2D add(Vec2D other) {
        return new Vec2D(this.x + other.x, this.y + other.y);
    }
    public Vec2D div(double size) {
        return new Vec2D( (this.x / size),  (this.y / size));
    }
    public Vec2D mult(double size) {
        return new Vec2D( (this.x * size),  (this.y * size));
    }
    public Vec2D rotate(double angle) {
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        double nx = c * this.x - s * this.y;
        double ny = s * this.x + c * this.y;
        return new Vec2D( nx, ny);
    }
    public Vec2D normalize() {
        return this.div(this.length());
    }
    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    public Vec2D setLength(double other) {
        return this.normalize().mult(other);
    }
    public Point toPoint(Point from) {
        return new Point(this.x + from.x, this.y + from.y);
    }
}

public class SnowflakeGenerator {
    public static void main(String[] args) {
        String svgContent = generateSnowflakeSVG(1);
        saveSVGToFile(svgContent, "./snowflake.svg");
    }

    public static String generateSnowflakeSVG(int level) {
        var points = getPoints(level).stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
        // Generate the SVG content here
        String svgContent = "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"-58.6751345948 -164.337567297 617.35026919 617.35026919\"> "+
                """
                    <polygon stroke="black" fill="#eee" stroke-width="1" points="" />
                        """ +
                "</svg>";

        // Use the 'level' variable in the SVG content
        svgContent = svgContent.replace("points=\"\"", "points=\"" + points + "\"");


        return svgContent;
    }

    public static void saveSVGToFile(String svgContent, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(svgContent);
            System.out.println("Snowflake SVG saved to: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to save SVG to file: " + e.getMessage());
        }
    }


    public static List<Point> getPoints(int level) {
        ArrayList<Point> points = new ArrayList<>(3 * (4 * (1 +level)));
        ArrayList<Point> pointsBuffer = new ArrayList<>(3 * (4 * (1 +level)));
        
        points.add(new Point(0, 0));
        points.add(new Point(500, 0));
        points.add(new Point(250, 433));
        for (int i = 0; i < level; i++) {
            pointsBuffer.clear();
            for (int j = 0; j < points.size(); j++) {                
                Point p1 = points.get(j);
                Point p5 = points.get((j + 1) % points.size());
                Vec2D v = p5.toVec2D(p1);
                Vec2D v1 = v.div(3);
                Vec2D v2 = v1.rotate(-Math.PI / 3);
                Point p2 = v1.toPoint(p1);
                Point p3 = v2.toPoint(p2);
                Point p4 = v1.toPoint(p2);
                pointsBuffer.add(p1);               
                pointsBuffer.add(p2);
                pointsBuffer.add(p3);
                pointsBuffer.add(p4);
                pointsBuffer.add(p5);
            }
            var temp = points;
            points = pointsBuffer;
            pointsBuffer = temp;
            
        }

        return points;
    }
}

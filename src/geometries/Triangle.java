package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Map;

import static primitives.Util.isZero;

/**
 * Triangle class represents triangle in 3D dimension
 */
public class Triangle extends Polygon {

    /**
     * Constructor that gets 3 points and sets them
     * @param p1 point object
     * @param p2 point object
     * @param p3 point object
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3)
    {
        super(p1, p2, p3);
    }

    /**
     * Private constructor that sets the params of 3 points of the triangle
     * @param p0coords String[] params of point0
     * @param p1coords String[] params of point1
     * @param p2coords String[] params of point2
     */
    private Triangle(String[] p0coords, String[] p1coords, String[] p2coords)
    {
        super(new Point3D(Double.valueOf(p0coords[0]), Double.valueOf(p0coords[1]), Double.valueOf(p0coords[2])),
                new Point3D(Double.valueOf(p1coords[0]), Double.valueOf(p1coords[1]), Double.valueOf(p1coords[2])),
                new Point3D(Double.valueOf(p2coords[0]), Double.valueOf(p2coords[1]), Double.valueOf(p2coords[2])));
    }

    /**
     * Constructor that sets the attributes of this object from xml
     * @param triangleAttributes Map<String, String> attributes to set
     */
    public Triangle(Map<String, String> triangleAttributes) {
        this(((String)triangleAttributes.get("p0")).split("\\s+"),
                ((String)triangleAttributes.get("p1")).split("\\s+"),
                ((String)triangleAttributes.get("p2")).split("\\s+"));
    }

    @Override
    public String toString() {
        return "Triangle {\n" +
                "\tVertices: " + _vertices +
                ",\n\t" + _plane +
                "\n}";
    }

    /**
     * calculate the points of the intersections with the given ray to the triangle
     * @param ray Ray which should intersect with the triangle
     * @return List<Point3D> which should return null on none point, or list of points that intersect the triangle
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> intersections = _plane.findIntersections(ray);
        if (intersections == null) return null;

        Point3D p0 = ray.get_p();
        Vector v = ray.get_dir();

        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        Vector v3 = _vertices.get(2).subtract(p0);

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) return null;

        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? intersections : null;

    }
}

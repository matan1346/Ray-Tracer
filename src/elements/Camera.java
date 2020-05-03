package elements;


import primitives.*;

import static primitives.Util.isZero;

/**
 * Camera class for represents a class
 */
public class Camera {
    /**
     * Point3D the center point of the camera
     */
    private Point3D _p0;

    /**
     * Vector the towards vector of the camera
     */
    private Vector _Vto;

    /**
     * Vector the up vector of the camera
     */
    private Vector _Vup;

    /**
     * Vector the right vector of the camera
     */
    private Vector _Vright;

    /**
     * Constructor that gets a point, and 2 vectors of to and up and sets them
     * @param p0 Point3D a point that sets the position of the camera
     * @param vto Vector that represent the direction towards
     * @param vup Vector that represents the direction up
     * @throws IllegalArgumentException throws an exception when Vector up and Vector to are not orthogonal
     */
    public Camera(Point3D p0, Vector vto, Vector vup) throws IllegalArgumentException {

        if(!isZero(vto.dotProduct(vup)))
            throw new IllegalArgumentException("Vto and Vup are not orthogonal");

        this._p0 = p0;
        _Vto = vto.normalized();
        _Vup = vup.normalized();
        _Vright = vto.crossProduct(vup).normalize();
    }

    /**
     * Gets the layout of the view plane, with units of width, height and positions
     * @param nX int width per item
     * @param nY int height per item
     * @param j int the position of the column
     * @param i int the position of thr row
     * @param screenDistance double screen distance
     * @param screenWidth double screen width
     * @param screenHeight double screen height
     * @return Ray the construct ray in the position i,j with this view plane
     */
    public Ray constructRayThroughPixel (int nX, int nY,
                                         int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight)
    {
        Point3D Pc = _p0.add(_Vto.scale(screenDistance));
        double Ry = screenHeight / nY;
        double Rx = screenWidth / nX;

        double Yi = (i - nY/2d)*Ry + Ry/2d;
        double Xj = (j - nX/2d)*Rx + Rx/2d;


        Point3D Pij = Pc;

        if(!isZero(Xj))
            Pij = Pij.add(_Vright.scale(Xj));

        if(!isZero(Yi))
            Pij = Pij.add(_Vup.scale(-Yi));

        Vector Vij = Pij.subtract(_p0);

        return new Ray(_p0, Vij.normalize());
    }

    /**
     * Getter that gets the center point of the camera
     * @return Point3D center point of camera
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * Getter that gets the towards vector of camera
     * @return Vector towards vector of camera
     */
    public Vector getVto() {
        return _Vto;
    }

    /**
     * Getter that gets the vector that points up of camera
     * @return Vector up vector of camera
     */
    public Vector getVup() {
        return _Vup;
    }

    /**
     * Getter that gets the vector that points right of camera
     * @return Vector right vector of camera
     */
    public Vector getVright() {
        return _Vright;
    }
}
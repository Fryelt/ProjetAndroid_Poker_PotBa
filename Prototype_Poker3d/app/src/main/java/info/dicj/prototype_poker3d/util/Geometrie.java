package info.dicj.prototype_poker3d.util;

/**
 * Created by PotBa1632703 on 2018-03-28.
 */

public class Geometrie {



    public static class Point{
        public final float px, py, pz;
        public Point(float x, float y, float z){
            this.px = x;
            this.py = y;
            this.pz = z;
        }
        public Point mouvementY(float distance){
            return new Point(px, py + distance, pz);
        }

        public Point mouvement(Vecteur vecteur) {
            return new Point(px + vecteur.x, py + vecteur.y, pz + vecteur.z);
        }
    }
    public static class Cercle {
        public final Point centre;
        public final float rayon;
        public Cercle(Point centre, float rayon){
            this.centre = centre;
            this.rayon = rayon;
        }
        public Cercle echelle(float echelle){
            return new Cercle(centre, rayon * echelle);
        }
    }
    public static class Cylindre {
        public final Point centre;
        public final float rayon, hauteur;
        public  Cylindre(Point centre, float rayon, float hauteur){
            this.centre = centre;
            this.rayon = rayon;
            this.hauteur = hauteur;
        }
    }
    public static class Sphere{
        public final Point centre;
        public final float rayon;
        public Sphere(Point centre, float rayon){
            this.centre = centre;
            this.rayon = rayon;
        }
    }
    public static class Rectangle{
        public final float longueur, largeur;
        public Rectangle(float longueur, float largeur){
            this.longueur = longueur;
            this.largeur = largeur;
        }
    }
    public static class Ray{
        public final Point point;
        public final Vecteur vecteur;
        public Ray(Point point, Vecteur vecteur){
            this.point = point;
            this.vecteur = vecteur;
        }
    }
    public static class Vecteur{
        public final float x, y, z;
        public Vecteur(float fx, float fy, float fz){
            x = fx;
            y = fy;
            z = fz;
        }
        public float length(){
            return (float)Math.sqrt(x * x + y * y + z * z);
        }
        public Vecteur echelle(float f) {
            return new Vecteur(x * f, y * f, z * f);
        }
        public Vecteur crossProduit(Vecteur vecteur) {
            return new Vecteur((y * vecteur.z) - (z * vecteur.y), (z * vecteur.x) - (x * vecteur.z), (x * vecteur.y) - (y * vecteur.x));
        }
        public float dotProduit(Vecteur norm) {
            return (x * norm.x) + (y * norm.y) + (z * norm.z);
        }


    }
    public static class Plane {
        public final Point point;
        public final Vecteur norm;

        public Plane(Point point, Vecteur norm){
            this.point = point;
            this.norm = norm;
        }
    }
    public static Vecteur vecteurB(Point source, Point dest){
        return new Vecteur(dest.px - source.px, dest.py - source.py, dest.pz - source.pz);
    }
    public static boolean intersect(Sphere sphereJetonP, Ray rayonL) {
        return distanceB(sphereJetonP.centre, rayonL) < sphereJetonP.rayon;
    }
    public static Point intersectP(Ray rayonL, Plane plan) {
        Vecteur rayToPlane = vecteurB(rayonL.point, plan.point);

        float echelleF = rayToPlane.dotProduit(plan.norm) / rayonL.vecteur.dotProduit(plan.norm);
        return rayonL.point.mouvement(rayonL.vecteur.echelle(echelleF));
    }

    private static float distanceB(Point point, Ray rayonL) {
        Vecteur p1TP = vecteurB(rayonL.point, point);
        Vecteur p2TP = vecteurB(rayonL.point.mouvement(rayonL.vecteur), point);

        float aireTriangleF2 = p1TP.crossProduit(p2TP).length();
        float lengthBase = rayonL.vecteur.length();

        float distanceFromPToR = aireTriangleF2 / lengthBase;
        return distanceFromPToR;
    }
}

package info.dicj.prototype_poker3d;

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
        public Point traductionY(float distance){
            return new Point(px, py + distance, pz);
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
        public final float rayon;
        public final float hauteur;
        public  Cylindre(Point centre, float rayon, float hauteur){
            this.centre = centre;
            this.rayon = rayon;
            this.hauteur = hauteur;
        }
    }

}

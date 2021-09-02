/**
 * @author 太易
 */
public class Planet {
    public double xxPos;//Its current x position
    public double yyPos;//Its current y position
    public double xxVel;//Its current velocity in the x direction
    public double yyVel;//Its current velocity in the y direction
    public double mass;//Its mass
    //The name of the file that corresponds to the image that depicts the planet
    public String imgFileName;
    private static double G=6.67e-11;

    //**constructer of Planet with specific paramester */
    public Planet(double xP, double yP, double xV,
    double yV, double m, String im) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = im;
    }
    //**constructer of Planet with another Planet P */
    public Planet(Planet p) {
        xxPos = p.xxPos;
        xxVel = p.xxVel;
        yyPos = p.yyPos;
        yyVel = p.yyVel;
        mass=p.mass;
        imgFileName=p.imgFileName;
    }
    //**calculate the distance between two Planets */
    public double calcDistance(Planet p) {
        double idx = this.xxPos - p.xxPos;
        double idy = this.yyPos - p.yyPos;
        return Math.sqrt(idx*idx+idy*idy);
    }
    //**calculate the force exerted on this planet by the given one */
    public double calcForceExertedBy(Planet p) {
        if(p == this) return 0.0;
        double r2 = this.calcDistance(p) * this.calcDistance(p);
        return (G * this.mass * p.mass) / r2;
    }
    //**calculate the force of x direction exerted on this planet by the given one */
    public double calcForceExertedByX(Planet p) {
        if(p == this) return 0.0;
        double totalForce = this.calcForceExertedBy(p);
        double idx = p.xxPos - this.xxPos;
        double dist = this.calcDistance(p);
        return totalForce * idx / dist;
    }
    //**calculate the force of y direction exerted on this planet by the given one */
    public double calcForceExertedByY(Planet p) {
        if(p == this) return 0.0;
        double totalForce = this.calcForceExertedBy(p);
        double idy = p.yyPos - this.yyPos;
        double dist = this.calcDistance(p);
        return totalForce * idy / dist;
    }
    //**calculate the total force of x direction exerted on this planet by an array of planet  */
    public double calcNetForceExertedByX(Planet[] arr) {
        double totalForceXX=0;
        for(Planet p : arr) {
            if(p != this) {
                totalForceXX += calcForceExertedByX(p);
            }
    
        }
        return totalForceXX;
    }
    //**calculate the total force of y direction exerted on this planet by an array of planet  */
    public double calcNetForceExertedByY(Planet[] arr) {
        double totalForceYY=0;
        for(Planet p : arr) {
            if(p != this) {
                totalForceYY += calcForceExertedByY(p);
            }
            
        }
        return totalForceYY;
    }
    //**update to get the new position of the planet because of the gravity from other planets */
    public void update(double dt,double fX,double fY) {
        //calculate the acceleration of x,y dirction
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        //calculate the velocity of x,y direction
        this.xxVel += dt * aX;
        this.yyVel += dt * aY;
        //calculate the new position 
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }
}

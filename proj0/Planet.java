public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public double calcDistance(Planet p){
        double distance, dx, dy;
        dx = p.xxPos -xxPos;
        dy = p.yyPos -yyPos;
        distance  = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }
    public double calcForceExertedBy(Planet p){
        double m1 = mass;
        double m2 = p.mass;
        double distance = this.calcDistance(p);
        double force = (6.67e-11) * m1 * m2 / (distance * distance);
        return force;
    }
    public double calcForceExertedByX(Planet p){
        double totalForce = calcForceExertedBy(p);
        double distance = calcDistance(p);
        double dx = p.xxPos -xxPos;
        double forceByX = totalForce * (dx / distance);
        return forceByX;
    }
    public double calcForceExertedByY(Planet p){
        double totalForce = calcForceExertedBy(p);
        double distance = calcDistance(p);
        double dy = p.yyPos -yyPos;
        double forceByY = totalForce * (dy / distance);
        return forceByY;
    }
    public double calcNetForceExertedByX(Planet[] planets_list){
        double netForceX =0;
        for (int i =0; i < planets_list.length; i++){
            if (planets_list[i].equals(this) == false) {
                double eachForceX = calcForceExertedByX(planets_list[i]);
                netForceX += eachForceX;
            }
        }
        return netForceX;
    }
    public double calcNetForceExertedByY(Planet[] planets_list){
        double netForceY =0;
        for (int i =0; i < planets_list.length; i++){
            if (planets_list[i].equals(this) == false) {
                double eachForceX = calcForceExertedByY(planets_list[i]);
                netForceY += eachForceX;
            }
        }
        return netForceY;
    }
    public void update(double dt, double fX, double fY){
        double accelerationX = fX / this.mass;
        double accelerationY = fY / this.mass;
        this.xxVel += dt * accelerationX;
        this.yyVel += dt * accelerationY;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }
    public void draw(){
        StdDraw.picture(this.xxPos,this.yyPos, this.imgFileName);
    }
}
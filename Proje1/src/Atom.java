
public class Atom {
	private static double ga = 0;
	int radius, mass;
	double cor, corC;
	double x, y;
	boolean Beyaz = false; // sarı
	boolean Sari = false; // kırmızı
	boolean Kirmizi = false;
	public double xV, yV;
	public double xF;
	public double yF;
	private double xD;
	private double yD;
	private double xA;
	private double yA;
	private double invMass;
	private static Atom white, yellow, red;
	public boolean moving = false;

	public void Renk(Atom Beyaz, Atom Sari, Atom Kirmizi) { // puanlamada topların rengini obje olarak Impulsedan çekme kısmı
		white = Beyaz;
		yellow = Sari;
		red = Kirmizi;

	}

	public Atom(double x, double y, int radius, int mass, double cor, double corC, double xV, double yV) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.mass = mass;
		this.cor = cor;
		this.corC = corC;
		this.xV = xV;
		this.yV = yV;
		xF = 0;
		yF = 0;
		xD = 0;
		yD = 0;
		xA = 0;
		yA = 0;
		invMass = 1.0 / mass;
	}

	public Atom(Atom a) {
		this(a.x, a.y, a.radius, a.mass, a.cor, a.corC, a.xV, a.yV);
	}

	public String toString() {
		return "x:" + x + " y:" + y + " xF:" + xF + " yF:" + yF + " xV:" + xV + " yV:" + yV + " ";
	}

	public double distance(double xM, double yM) {
		return Math.sqrt((xM - x) * (xM - x) + (yM - y) * (yM - y));
	}

	public double angle(double xM, double yM) {
		if (x == xM && y == yM)
			return 0;
		else if (x >= xM)
			return Math.atan((y - yM) / (x - xM));
		else
			return Math.atan((y - yM) / (x - xM)) + Math.PI;
	}

	public double netV() {
		return Math.sqrt(xV * xV + yV * yV);
	}

	public boolean isStationary() {
		return xV == 0 && yV == 0;
	}

	public void applyForce(int xM, int yM, double mF) {//güç kısmı
		xF += (xM - x) / distance(xM, yM) * mF;
		yF += (yM - y) / distance(xM, yM) * mF;
	}

	public void applyForce(char dir, double mF) {//tuslarla kontrol kısmı
		if (dir == 'u')
			yF -= mF;
		else if (dir == 'd')
			yF += mF;
		else if (dir == 'l')
			xF -= mF;
		else if (dir == 'r')
			xF += mF;
		else
			;
	}



	public void applyCollide(int xM, int yM, int field) {//carpısma
		if (distance(xM, yM) <= field) {
			xF += (x - xM) / distance(xM, yM) * netV() * 2 * mass;
			yF += (y - yM) / distance(xM, yM) * netV() * 2 * mass - ga * mass;
		}
	}

	public void applyFriction(double cof) {
		final double netFk = -mass * 0.098 * cof;
		if (Math.abs(netFk / mass) >= netV()) {
			xF -= xV * mass;
			yF -= yV * mass;
		} else if (xV != 0 || yV != 0) {
			xF += netFk / netV() * xV;
			yF += netFk / netV() * yV;
		}
	}

	public void initReact(double cof) {
		if (ga == 0)
			applyFriction(cof);
		xA = xF / mass;
		yA = yF / mass;                                       //mouse çekildiği nokta
		xV += xA;
		yV += yA + ga;
	}

	public void applyDrag(double cod) {
		final double TEMP = -1.292 * 0.47 * Math.PI * radius * radius / 2 * 0.01 * cod;             //mouseun çekildiği yere gitmesini sağlayan od
		xD = xV * xV * TEMP; 
		yD = yV * yV * TEMP;
	}

	public void react(double cof, double cod) {
		initReact(cof);
		applyDrag(cod);
		xA = xD / mass;                                         //bakılacak
		yA = yD / mass;
		xV += xA * Math.signum(xV);
		yV += yA * Math.signum(yV);
	}

	public boolean isReflect(Plane p) {
		return (x - p.xO) * p.x + (y - p.yO) * -p.y + p.mgt < radius && xV * p.x + yV * -p.y < 0;    //bakılacak
	}



	public void reflect(Plane p) {
		if (isReflect(p)) {
			final double Temp = (xV * p.x + yV * -p.y) * (1 + cor);
			xV = xV - p.x * Temp;                                        //bakılacak
			yV = yV - -p.y * Temp;
		}
	}


	public void locate() {//locationu belirleme 
		x += xV;
		y += yV;
		xF = 0;
		yF = 0;
	}

	public static boolean isCollide(Atom a, Atom b) { //çarpışma
		return a.distance(b.x, b.y) < a.radius + b.radius
				&& (a.xV - b.xV) * (a.x - b.x) + (a.yV - b.yV) * (a.y - b.y) <= 0;
	}

	public void collide(Atom b) {//carpısma
		if (isCollide(this, b)) {
			final double TEMP = ((this.xV - b.xV) * Math.cos(this.angle(b.x, b.y))
					+ (this.yV - b.yV) * Math.sin(this.angle(b.x, b.y))) / (this.invMass + b.invMass);
			this.xV -= (1 + this.corC) * Math.cos(this.angle(b.x, b.y)) * TEMP * this.invMass;
			this.yV -= (1 + this.corC) * Math.sin(this.angle(b.x, b.y)) * TEMP * this.invMass;
			b.xV += (1 + b.corC) * Math.cos(this.angle(b.x, b.y)) * TEMP * b.invMass;
			b.yV += (1 + b.corC) * Math.sin(this.angle(b.x, b.y)) * TEMP * b.invMass;
			if (b == white) {
				Beyaz = true;
			} else if (b == red) {
				Kirmizi = true;
			} else if (b == yellow) {
				Sari = true;
			}
		}
	}

	public boolean stop() {//puanlama sisteminde topun durduğu kısım
		if (yellow.xV + white.xV + red.xV == 0 && yellow.yV + white.yV + red.yV == 0) {
			Beyaz = false;
			Kirmizi = false;
			Sari = false;
			return true;
		}
		moving = Math.abs(this.xV) + Math.abs(this.yV) > 0;
		return false;
	}

}

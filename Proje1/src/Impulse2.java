
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Impulse2 implements MouseMotionListener, MouseListener, KeyListener {

//	private String ip= "localhost";
//	private int port =10123;
//	private Scanner scanner= new Scanner(System.in);
//	private Thread thread;
//	private Socket socket;
//	private DataOutputStream dos;
//	private DataInputStream dis;
//	private ServerSocket serverSocket;
//	int drawLocationX = 300;
	int drawLocationY = 300;
	int xM = 0;
	int yM = 0;
	int ballNum = 3;// top sayısı
	int wallNum = 4;// duvar sayısı
	int mForce = 0;// force başlangızı
	int player1 = 0;// player 1 baslangıc puanı
	int player2 = 0;// player 2 baslangıc puanı
	boolean isPrint, isPause, checked = false;
//   private boolean yourturn = false;
//	private boolean circle =false;
//	private boolean accepted =false;
//	private boolean uncommunicate = false;
////	private boolean won = false;
////	private boolean enemywon =false;
////	private String waitingString ="Waiting for another player";
//	private String uncommunicateString ="Unable to communicate with opponent";
////	private String wonString ="you won";
////	private String enemywonString ="enemy won";
//	private int errors =0; // bakılacak
//	private final int WIDTH=506;
//	private final int HEIGHT=527;
//	private Font smallerFont =new Font("Verdana",Font.BOLD,32);

	final int mRadius = 50;
	final int keyForce = 1000;
	final int maxF = 25000;
	final int incF = 150;
	final int fps = 105;
	final double cof = 0.15;
	final double cod = 0.1;
	final int maxBall = 1000;
	final int mass = 850;
	final double cor = 0.7;
	final double corC = 0.95;
	final int radius = 16;
	final int length = 900;
	final int height = 450;
	final int x = 50;
	final int y = 150;
	final int powerBarL = 200;
	final int powerBarW = 20;

	Atom[] ball = new Atom[maxBall];
	Plane[] wall = new Plane[wallNum];
	JFrame frame = new JFrame("Pool Game");
	Drawing draw = new Drawing();
	Dynamics pool = new Dynamics();
	Power shoot = new Power();
	Menu m = new Menu();

	public Impulse2() {

		m.initMenu();
		m.setSize(300, 900);
		m.setTitle("menu");
		m.setVisible(true);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.white);
		frame.getContentPane().add(draw, "Center");
		frame.setResizable(false);
		draw.addMouseMotionListener(this);
		draw.addMouseListener(this);
		frame.addKeyListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 740);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);


		ball[0] = new Atom(x + 150, y + height / 2, radius, mass, cor, corC, 0, 0);
		ball[1] = new Atom(x + length - 200, y + height / 2 + 40, radius, mass, cor, corC, 0, 0);
		ball[2] = new Atom(x + length - 200, y + height / 2 - 40, radius, mass, cor, corC, 0, 0);
		wall[0] = new Plane(x + 42, y + height - 19, x + length - 34, y + height - 19);
		wall[1] = new Plane(x + 42, y + 39, x + 42, y + height - 20);
		wall[2] = new Plane(x - 33 + length, y + height - 20, x + length - 33, y + 39);
		wall[3] = new Plane(x - 33 + length, y + 39, x + 42, y + 39);
		pool.start();
		ball[0].Renk(ball[0], ball[1], ball[2]);
		ball[1].Renk(ball[0], ball[1], ball[2]);
		ball[2].Renk(ball[0], ball[1], ball[2]);
	}
 
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {

		case KeyEvent.VK_9:
			isPause = !isPause;
			if (isPrint) // oyunu durduran kısım gerekliliği tartısılır
				System.out.println("Pause: " + isPause);
			break;
		case KeyEvent.VK_0:
			frame.setVisible(false);
			new Impulse2();
			if (isPrint) // tekrardan baslamasını sağlayan tuş
				System.out.println("Refresh");
			break;
		case KeyEvent.VK_UP:
			ball[0].applyForce('u', keyForce);
			break;
		case KeyEvent.VK_DOWN:
			ball[0].applyForce('d', keyForce);
			break; // tuslarla beyaz topu yönetmeyi saglayan bir cesit cheat kode
		case KeyEvent.VK_LEFT:
			ball[0].applyForce('l', keyForce);
			break;
		case KeyEvent.VK_RIGHT:
			ball[0].applyForce('r', keyForce);
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
		}
		draw.repaint();
	}

	public void keyReleased(KeyEvent e) { // yanlsılıkla basıldığında error vermesin diye eklendi
	}

	public void keyTyped(KeyEvent e) { // yanlsılıkla basıldığında error vermesin diye eklendi
	}

	public void mouseClicked(MouseEvent e) { // yanlsılıkla basıldığında error vermesin diye eklendi
	}

	public void mouseEntered(MouseEvent e) { // yanlsılıkla basıldığında error vermesin diye eklendi
	}

	public void mouseExited(MouseEvent e) { // yanlsılıkla basıldığında error vermesin diye eklendi
	}

	public void mousePressed(MouseEvent e) { // yanlsılıkla basıldığında error vermesin diye eklendi
		shoot = new Power();
		shoot.start(); // gücünü ayarlıyor ne akdar güclü vuracağını sağlıyor

		draw.repaint();

	}

	public void mouseReleased(MouseEvent e) {
		// isTurn = false;
	
			shoot.interrupt();
			ball[0].applyForce(xM, yM, mForce); // mouseu gücünü ayarladıktan gitmesini sağlayan foksiyon bu olmadan
												// mouse pressed de çalışmıyor
			mForce = 0;
			draw.repaint();
		 


	}

	public void mouseDragged(MouseEvent e) { // mouseın basılıyken olduğu noktaya göre ıstakanın dönmesi topun gitmesi
												// ve çizginin gitmesini sağlıyor
		xM = e.getX();
		yM = e.getY();
		draw.repaint();
	}

	public void mouseMoved(MouseEvent e) { // mouseın basılı değilken olduğu noktaya göre ıstakanın dönmesi topun
											// gitmesi ve çizginin gitmesini sağlıyor
		xM = e.getX();
		yM = e.getY();
		draw.repaint();
	}

	

	class Power extends Thread {
		public void run() { // gücün artışı /gücün sınırı artış hızı gibi parametlerin ayarlandığı kısım
			try {
				
					for (; mForce <= maxF; mForce += incF) {
						Thread.sleep(1000 / fps);
					
				}
			} catch (InterruptedException e) {
			}
		}
	}

	class Drawing extends JComponent {
		private static final long serialVersionUID = -5298863361385161406L;
		BufferedImage Table;
		BufferedImage Cue;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// render(g);
		}

		public Drawing() {
			try {
				Table = ImageIO.read(new File("C:/Users/eren/eclipse-workspace/Proje1/img/pool5.PNG"));// masayı
																										// yazdırdığı
																										// yer
			} catch (IOException ex) {
				System.out.println("psdlf");
			}
			try {
				Cue = ImageIO.read(new File("C:/Users/eren/eclipse-workspace/Proje1/img/Cue.PNG")); // ıstakayı
																									// yazdırdığı yer

			} catch (IOException ex) {
				System.out.println("psdle");
			}
			repaint();
		}

		public void paint(Graphics g) {
			g.drawImage(Table, 40, 140, this);// masanın resmini çizdirdiği yer
			
				AffineTransform trans = new AffineTransform();
				double angle = Math.atan2(xM - ball[0].x, -yM + ball[0].y) + Math.PI / 2.0d;
				trans.translate(ball[0].x + 5, ball[0].y + 10); // rotate olmasını sağlayan kısım CALIS ÖNEMLİ SORU
																// GELİCEK
				trans.rotate(angle);
				AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BILINEAR);

				g.setColor(Color.black);
               	if((ball[0].isStationary())) { //tüm topların durmasına bak 
					g.drawImage(op.filter(Cue, null), 0, 0, this);// cue stick
					g.drawLine(xM, yM, (int) ball[0].x, (int) ball[0].y);// topun gittiği yeri gösteren çizgi
				}


			g.setColor(Color.white);
			for (int j = 0; j <= ball[0].radius; j++) {
				g.fillOval((int) ball[0].x - ball[0].radius + j, (int) ball[0].y - ball[0].radius + j, // birinci top
						ball[0].radius * 2 - 2 * j, ball[0].radius * 2 - 2 * j);
			}
			g.setColor(Color.yellow);
			for (int j = 0; j <= ball[1].radius; j++) {
				g.fillOval((int) ball[1].x - ball[1].radius + j, (int) ball[1].y - ball[1].radius + j, // ikinici top
						ball[1].radius * 2 - 2 * j, ball[1].radius * 2 - 2 * j);
			}
			g.setColor(Color.red);
			for (int j = 0; j <= ball[2].radius; j++) {
				g.fillOval((int) ball[2].x - ball[2].radius + j, (int) ball[2].y - ball[2].radius + j, // ücüncü top
						ball[2].radius * 2 - 2 * j, ball[2].radius * 2 - 2 * j);
			}

			g.setColor(Color.DARK_GRAY);
			for (int i = 0; i < wallNum; i++) {

				if (i >= 4) // duvarlar
					g.setColor(Color.blue);

				g.drawLine(wall[i].xEnd1, wall[i].yEnd1, wall[i].xEnd2, wall[i].yEnd2);
			}

			g.setColor(Color.blue);
			g.drawRect(x + length / 2 - powerBarL / 2, y - 90, powerBarL, powerBarW); //
			g.setColor(Color.red);
			g.fillRect(x + length / 2 - powerBarL / 2 + 1, y - 90 + 1, (powerBarL - 1) * mForce / maxF, powerBarW - 1);
			g.setColor(Color.black);
			g.drawString("Power: " + mForce, x + length / 2 - 20, y - 100);
			g.setColor(Color.blue);
			g.drawString("Player-1: " + player1 + " points", x, y - 30);
         	if (isPause)
				g.drawString("PAUSED", x + length / 2 - 10, y + height / 2 - 5);
		}
	}

	class Dynamics extends Thread {

		public void run() {
			try {
				while (true) {

						if (ball[0].Kirmizi && ball[0].Sari) {
							if (!checked) {
								player1++;
							}

							checked = true;

						}
						if (ball[0].stop()) {
							checked = false;
						}
					
				

					for (int i = 0; i < ballNum; i++) {
						for (int j = i + 1; j < ballNum; j++)
							if (ball[j] != null)
								ball[i].collide(ball[j]); // collide fiziğini
						if (ball[i] != null)
							ball[i].react(cof, cod);

						for (int j = 0; ball[i] != null && j < wallNum; j++) {

							if (ball[i] != null)
								ball[i].reflect(wall[j]);
						}

						if (ball[i] != null)
							ball[i].locate();
					}
					draw.repaint();
					Thread.sleep(1000 / fps);
					while (isPause)
						Thread.sleep(100);
				}
			}

			catch (InterruptedException e) {
			}

		}

	
	}
}

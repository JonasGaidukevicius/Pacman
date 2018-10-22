package lt.gaidukevicius;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

//importuoju 3 bibliotekas garsui groti
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

//1 biblioteka laiko atvaizdavimui pagal formata
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class Board extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;
    private final Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;

    private boolean inGame = false;
    private boolean dying = false;

    private final int BLOCK_SIZE = 24; //buvo 24
    private final int N_BLOCKS = 15; //buvo 15
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int PAC_ANIM_DELAY = 4; //buvo 2
    private final int PACMAN_ANIM_COUNT = 5;//buvo 4
    private final int MAX_GHOSTS = 12;
    private final int PACMAN_SPEED = 6;

    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;
    private int N_GHOSTS = 1; //buvo 6
    private int pacsLeft, score;
    private int[] dx, dy;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;
    

    private Image ghost;
    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy, view_dx, view_dy;
    
    private short[] levelData = new short[225];
   
   /* private final short levelData[] = {
        19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 16, 16, 16, 16, 20,
        21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
        17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
        25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
        1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
        1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
        1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
        1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
        1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
        1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
        1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
        9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 17, 16, 16, 16, 20
    };*/

    /*private final short levelData[] = {
    		19,26,26,22, 9,12,19,26,22, 9,12,19,26,26,22,
    		37,11,14,17,26,26,20,15,17,26,26,20,11,14,37,
    		17,26,26,20,11, 6,17,26,20, 3,14,17,26,26,20,
    		21, 3, 6,25,22, 5,21, 7,21, 5,19,28, 3, 6,21,
    		21, 9, 8,14,21,13,21, 5,21,13,21,11, 8,12,21,
    		25,18,26,18,24,18,28, 5,25,18,24,18,26,18,28,
    		 6,21, 7,21, 7,21,11, 8,14,21, 7,21, 7,21,03,
    		 4,21, 5,21, 5,21,11,10,14,21, 5,21, 5,21, 1,
    		12,21,13,21,13,21,11,10,14,21,13,21,13,21, 9,
    		19,24,26,24,26,16,26,18,26,16,26,24,26,24,22,
    		21, 3, 2, 2, 6,21,15,21,15,21, 3, 2, 2,06,21,
    		21, 9, 8, 8, 4,17,26, 8,26,20, 1, 8, 8,12,21,
    		17,26,26,22,13,21,11, 2,14,21,13,19,26,26,20,
    		37,11,14,17,26,24,22,13,19,24,26,20,11,14,37,
    		25,26,26,28, 3, 6,25,26,28, 3, 6,25,26,26,28 
        };*/






    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 6;

    private int currentSpeed = 3;
    private short[] screenData;
    private Timer timer;
    
    private int superPower; //Jeigu 0 - iprastas, heigu 1 - valgo vaiduoklius
    private long superTimer; //Skaiciuoja kiek laiko yra aktyvuotos super galios. Limitas 10 sekundziu
    
    private long timeStarted; // paimu atskaitos taska, kada prasidejo zaidimas
    SimpleDateFormat timeForDisplay = new SimpleDateFormat("mm:ss");
    
    private int ghost_active []; //0 - jeigu vaiduoklis suvalgytas, 1 - jeigu vaiduoklis aktyvus
    
    public Board() {
    	
    	//labirinto nuskaitymas
    	//levelData = nuskaitauLabirinta();
        //System.out.println(levelData[0] + " " + levelData[1]); //Testuoju
    	
        loadImages();
        initVariables();
        initBoard();
    }
    
    private void initBoard() {
        
        addKeyListener(new TAdapter());

        setFocusable(true);

        setBackground(Color.black);
        setDoubleBuffered(true);        
    }

    private void initVariables() {

    	
    	//kaip cia aprasomi kintamieji? Koks tai tipas?
        screenData = new short[N_BLOCKS * N_BLOCKS];
        mazeColor = new Color(5, 100, 5);
        d = new Dimension(400, 400);
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        ghost_active = new int[MAX_GHOSTS]; //mano kintamasis ar vaiduoklis aktyvus
        dx = new int[4];
        dy = new int[4];
        
        timer = new Timer(40, this);
        timer.start();
        superPower = 0;
        superTimer = 0;
       
        timeForDisplay = new SimpleDateFormat("mm:ss");
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    private void doAnim() {

        pacAnimCount--;

        if (pacAnimCount <= 0) {
            pacAnimCount = PAC_ANIM_DELAY;
            pacmanAnimPos = pacmanAnimPos + pacAnimDir;

            if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
                pacAnimDir = -pacAnimDir;
            }
        }
    }

    private void playGame(Graphics2D g2d) {

        if (dying) {

            death();

        } else {

            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

        String s = "Paspausk s, kad pradeti.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
    }

    private void drawScore(Graphics2D g) {

        int i;
        String s;
        String t;
        String tf; //time formated
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        //System.out.println("Ar jis visa laika spausdina taskus ar tik tada, kai atsinaujina?"); - visa laika
        tf = timeForDisplay.format(System.currentTimeMillis() - timeStarted);
        t = "Timer: " + tf;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);
        g.drawString(t, SCREEN_SIZE / 2 - 60, SCREEN_SIZE + 16);
        for (i = 0; i < pacsLeft; i++) {
            g.drawImage(pacman3left, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }
    
    /*private void drawTimer(Graphics2D g) {
    	 String t; // timer eilutes kintamasis
    	 t = "Timer: " + timer;
    	 
    	 g.setFont(smallFont);
         g.setColor(new Color(96, 128, 255));
    	 g.drawString(t, SCREEN_SIZE - 100, SCREEN_SIZE + 16);
    }*/
      
    

    private void checkMaze() {

        short i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {
        	
        	//cia pertikrina ar dar like yra tasku nesuvalgytu
            if ((screenData[i] & 48) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 50;

            if (N_GHOSTS < MAX_GHOSTS) {
                N_GHOSTS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }

    private void death() {
    	 File mirtis = new File("sounds/pacman_death.wav");
        pacsLeft--;

        if (pacsLeft == 0) {
            inGame = false;
            playSound(mirtis, 1000);
            
        }

        continueLevel();
    }

    private void moveGhosts(Graphics2D g2d) {

        short i;
        int pos;
        int count;

        for (i = 0; i < N_GHOSTS; i++) {
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]); // matyt cia naujos vaiduoklio koordinates nustatomos
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            
            //patikrinu ar neperlipo virsutines ir apatines ribos
            if(ghost_y[i] > 336) {
            	ghost_y[i] = 336;
            }
            if(ghost_y[i] < 0) {
            	ghost_y[i] = 0;
            }
            
            //cia piesia vaiduokli
            //tai reikia nekviesti sio metodo, jeigu vaiduoklis suvalgytas
            
            if(ghost_active[i] != 0) {
            	drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);
            }
            
            
            //cia patikrina ar susidure vaiduoklis su pacmanu
            
            if(ghost_active[i] != 0) {
            	if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                        && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                        && inGame) {

                    if(superPower == 0) {
                    	dying = true;
                    } else {
                    	ghost_active[i] = 0;
                    }
            		
                }
            }
            
            
            
        }
    }

    private void drawGhost(Graphics2D g2d, int x, int y) {

        g2d.drawImage(ghost, x, y, this);
    }

    private void movePacman() {

        int pos;
        short ch;
        
        //mano prideta garso saltinis
        File valgau = new File("sounds/pacman_chomp.wav");
		

        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
            pacmand_x = req_dx;
            pacmand_y = req_dy;
            view_dx = pacmand_x;
            view_dy = pacmand_y;
        }

        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE); //panasu, kad cia apsiskaiciuoj kita pacmano pozicija
            ch = screenData[pos];
            
            //atrodo, kad cia valgomi taskai
            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                playSound(valgau, 20000); // cia sugroja muzikyte, kai suvalgo taska
                score++;
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                    view_dx = pacmand_x;
                    view_dy = pacmand_y;
                }
            }

            // Check for standstill  // cia patikrinama ar yra kur eiti (ar nera sienos)
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }
        pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;  //gali buti, kad cia nustatomos naujos pacmano koordinates, i kurias
        pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;	 // jis turi nueiti
        //System.out.println("pacman_x yra: " + pacman_x);
        
        //cia paziurima ar isejo per virsutine ar apatine ribas.
        if(pacman_y < 0) {
        	pacman_y = 336;
        } else if(pacman_y > 336) {
        	pacman_y = 0;
        }
        //Tikrinu ar jau praejo laiko, kad isjungti super Power rezima
        if(System.currentTimeMillis() - superTimer > 10000) {
        	superPower = 0;
        	superTimer = 0;
        }
    }

    private void drawPacman(Graphics2D g2d) {

        if (view_dx == -1) {
            drawPacnanLeft(g2d);
        } else if (view_dx == 1) {
            drawPacmanRight(g2d);
        } else if (view_dy == -1) {
            drawPacmanUp(g2d);
        } else {
            drawPacmanDown(g2d);
        }
    }

    private void drawPacmanUp(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4up, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanDown(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4down, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacnanLeft(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4left, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanRight(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4right, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) { 
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) { 
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) { 
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }
    }

    private void initGame() {

        pacsLeft = 1; // cia nusistato packamnu skaicius
        score = 0;
        initLevel();
        N_GHOSTS = 2;  //buvo 6// cia nusistato vaiduokliu skaicius
        currentSpeed = 3; // buvo 3 // cia nusistaot greitis
        
    }

    private void initLevel() {
    	levelData = nuskaitauLabirinta();
        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel();
    }

    private void continueLevel() {

        short i;
        int dx = 1;
        int random;
        timeStarted = System.currentTimeMillis(); // cia uzfiksavau, kada prasideda laiko atskaita
        
        for (i = 0; i < N_GHOSTS; i++) {

            ghost_y[i] = 4 * BLOCK_SIZE;
            ghost_x[i] = 4 * BLOCK_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            ghost_active[i] = 1; //cia nustatau, kad nauji vaiduokliai visi akvyvus
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = validSpeeds[random];
        }

        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = 11 * BLOCK_SIZE;
        pacmand_x = 0;
        pacmand_y = 0;
        req_dx = 0;
        req_dy = 0;
        view_dx = -1;
        view_dy = 0;
        dying = false;
    }

    private void loadImages() {

        ghost = new ImageIcon("images/ghost.gif").getImage();
        pacman1 = new ImageIcon("images/pacman.gif").getImage();
        pacman2up = new ImageIcon("images/up1.gif").getImage();
        pacman3up = new ImageIcon("images/up2.gif").getImage();
        pacman4up = new ImageIcon("images/up3.gif").getImage();
        pacman2down = new ImageIcon("images/down1.gif").getImage();
        pacman3down = new ImageIcon("images/down2.gif").getImage();
        pacman4down = new ImageIcon("images/down3.gif").getImage();
        pacman2left = new ImageIcon("images/left1.gif").getImage();
        pacman3left = new ImageIcon("images/left2.gif").getImage();
        pacman4left = new ImageIcon("images/left3.gif").getImage();
        pacman2right = new ImageIcon("images/right1.gif").getImage();
        pacman3right = new ImageIcon("images/right2.gif").getImage();
        pacman4right = new ImageIcon("images/right3.gif").getImage();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;            // kaip suprasti sita eilute?

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);
        doAnim();

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                } else if(key == KeyEvent.VK_SPACE) {
                	//Cia darosi pacmanas valgantis vaiduoklius
                	superPower = 1;
                	superTimer = System.currentTimeMillis();
                	
                }
            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    initGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                req_dx = 0;
                req_dy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
    
    //----------------------mano papildomi metodai
    
    //garsu grojimas
    
    public void playSound(File garsas, int pauze) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(garsas));
			clip.start();
			Thread.sleep(clip.getMicrosecondLength() / pauze);
			clip.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    //Failo su labirintu nuskaitymas
    
    public short[] nuskaitauLabirinta() {
    	File failas = new File("maps/level_01.txt");
        //String eilute = "";
        short[] labirintas = new short[225];
    	try {
        	Scanner input = new Scanner(failas);
        	for(int i = 0; i <= 224; i++) {
        		labirintas[i] = input.nextByte();
        	}
        	input.close();
        	
        	        	
        	/*while(input.hasNextLine()){
        		eilute = input.nextLine();
        		input.nextByte();
        		input.close();
        	}*/
        } catch (Exception e) {
        		e.printStackTrace();
        }
		return labirintas;
    }
    
    
} 
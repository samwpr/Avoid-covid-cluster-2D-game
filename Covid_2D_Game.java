import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.List;

// Code is organised in a manner that replicates how it would be if each class was its own file

public class Covid_2D_Game extends JFrame {

    // Create JFrame to contain game panel
    private Covid_2D_Game() {
        add(new GamePanel());
        setResizable(false);
        pack();
        setTitle("Avoid Covid Clusters. Final Version");
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static abstract class Shapes{
        Integer positionX, positionY;

        public Shapes(Integer positionX, Integer positionY){
            this.positionX = positionX;
            this.positionY = positionY;

            ArrayList<Shapes> GameShapaes = new ArrayList<>();
            GameShapaes.add(GamePanel.player);
            GameShapaes.add(GamePanel.checkPoint);
            GameShapaes.add(GamePanel.cluster);
        }
    }

    // Creating the player
    static class Player extends Shapes{

        // Stores location of player
        private int[] x = new int[GamePanel.getUnits()];
        private int[] y = new int[GamePanel.getUnits()];

        // Direction of player
        private boolean leftDirection = false;
        private boolean rightDirection = false;
        private boolean upDirection = false;
        private boolean downDirection = false;

        private int setPlayer = 0;
        public Player(Integer positionX, Integer positionY){
            super(positionX, positionY);
        }

        public int getX(int index){
            return x[index];
        }

        public int getY(int index){
            return y[index];
        }

        public void setX(int i){
            x[0] = i;
        }

        public void setY(int i){
            y[0] = i;
        }

        public boolean isLeftDirection() {
            return leftDirection;
        }

        public void setLeftDirection(boolean leftDirection) {
            this.leftDirection = leftDirection;
        }

        public boolean isRightDirection() {
            return rightDirection;
        }

        public void setRightDirection(boolean rightDirection) {
            this.rightDirection = rightDirection;
        }

        public boolean isUpDirection() {
            return upDirection;
        }

        public void setUpDirection(boolean upDirection) {
            this.upDirection = upDirection;
        }

        public boolean isDownDirection() {
            return downDirection;
        }

        public void setDownDirection(boolean downDirection) {
            this.downDirection = downDirection;
        }

        public int getSetPlayer(){
            return setPlayer;
        }

        public void setSetPlayer(int i) {
            setPlayer = 1;
        }

        public void move(){
            if (leftDirection) {
                x[0] -= GamePanel.getUnitSize();
            }
            // To the right
            if (rightDirection) {
                x[0] += GamePanel.getUnitSize();
            }
            // Down
            if (downDirection) {
                y[0] += GamePanel.getUnitSize();
            }
            // And finally up
            if (upDirection) {
                y[0] -= GamePanel.getUnitSize();
            }
        }

        // Draws the player
        public void draw (Graphics g) {
            for (int i = 0; i < getSetPlayer(); i++){
                if (i == 0 && GamePanel.direction) {
                    g.setColor(Color.WHITE);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.fillOval(getX(i), getY(i), GamePanel.units, GamePanel.units);
                } else {
                    g.setColor(Color.WHITE);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.fillOval(getX(i), getY(i), GamePanel.units, GamePanel.units);
                }
            }
        }

        // If play touches the boarder the game will end
        private void Collisions() {
            if (getY(0) >= GamePanel.height) {
                GamePanel.pointsReport.saveScore(GamePanel.points);
                GamePanel.gameRunning = false;
            }
            if (getY(0) < 0) {
                GamePanel.pointsReport.saveScore(GamePanel.points);
                GamePanel.gameRunning = false;
            }
            if (getX(0) >= GamePanel.width) {
                GamePanel.pointsReport.saveScore(GamePanel.points);
                GamePanel.gameRunning = false;
            }
            if (getX(0) < 0) {
                GamePanel.pointsReport.saveScore(GamePanel.points);
                GamePanel.gameRunning = false;
            }
            if (!GamePanel.gameRunning) {
                GamePanel.timer.stop();
            }
        }

        private void createPlayer(){
            for (int i = 0; i < getSetPlayer(); i++){
                setX(GamePanel.width / 2);
                setY(GamePanel.height / 2);
            }
        }
    }

    // Creating the check points in the game
    static class CheckPoint extends Shapes{
        private Player player = new Player(0, 0);
        private int checkPointX;
        private int checkPointY;

        private int randomPosition = 20;

        public CheckPoint(Integer checkPointX, Integer checkPointY){
            super(checkPointX, checkPointY);
        }

        public void createCheckPoint(){
            int location = (int) (Math.random() * randomPosition);
            checkPointX = ((location * GamePanel.getUnitSize()));

            location = (int) (Math.random() * randomPosition);
            checkPointY = ((location * GamePanel.getUnitSize()));

            if ((checkPointX == player.getX(0)) && (checkPointY == player.getY(0))){
                createCheckPoint();
            }
        }

        public int getX(){
            return checkPointX;
        }

        public int getY(){
            return checkPointY;
        }

        public void draw(Graphics g){
            g.setColor(Color.WHITE);
            g.fillRect(checkPointX, checkPointY, GamePanel.getUnitSize(), GamePanel.getUnitSize());
        }



    }

    // Creating the clusters in the game
    static class Cluster extends Shapes{

        private int ClusterX1, ClusterX2, ClusterX3, ClusterX4, ClusterX5, ClusterX6, ClusterX7, ClusterX8, ClusterX9, ClusterX10;
        private int ClusterY1, ClusterY2, ClusterY3, ClusterY4, ClusterY5, ClusterY6, ClusterY7, ClusterY8, ClusterY9, ClusterY10;

        private int randomPosition1 = 20;
        private int randomPosition2 = 10;
        private int randomPosition3 = 5;
        private int randomPosition4 = 13;
        private int randomPosition5 = 17;

        public Cluster(Integer clusterX1, Integer clusterY1){
            super(clusterX1, clusterY1);
        }

        public void createCluster(){
            int location = (int) (Math.random() * randomPosition1);
            ClusterX1 = ((location * GamePanel.getUnitSize()));
            location = (int) (Math.random() * randomPosition1);
            ClusterY1 = ((location * GamePanel.getUnitSize()));

            location = (int) (Math.random() * randomPosition2);
            ClusterX2 = ((location * GamePanel.getUnitSize()));
            location = (int) (Math.random() * randomPosition2);
            ClusterY2 = ((location * GamePanel.getUnitSize()));

            location = (int) (Math.random() * randomPosition3);
            ClusterX3 = ((location * GamePanel.getUnitSize()));
            location = (int) (Math.random() * randomPosition3);
            ClusterY3 = ((location * GamePanel.getUnitSize()));

            location = (int) (Math.random() * randomPosition4);
            ClusterX4 = ((location * GamePanel.getUnitSize()));
            location = (int) (Math.random() * randomPosition4);
            ClusterY4 = ((location * GamePanel.getUnitSize()));

            location = (int) (Math.random() * randomPosition5);
            ClusterX5 = ((location * GamePanel.getUnitSize()));
            location = (int) (Math.random() * randomPosition5);
            ClusterY5 = ((location * GamePanel.getUnitSize()));

            location = (int) (Math.random() * randomPosition1);
            ClusterX6 = ((location * GamePanel.getUnitSize()));
            location = (int) (Math.random() * randomPosition1);
            ClusterY6 = ((location * GamePanel.getUnitSize()));

            location = (int) (Math.random() * randomPosition2);
            ClusterX7 = ((location * GamePanel.getUnitSize()));
            location = (int) (Math.random() * randomPosition2);
            ClusterY7 = ((location * GamePanel.getUnitSize()));

            location = (int) (Math.random() * randomPosition3);
            ClusterX8 = ((location * GamePanel.getUnitSize()));
            location = (int) (Math.random() * randomPosition3);
            ClusterY8 = ((location * GamePanel.getUnitSize()));

            location = (int) (Math.random() * randomPosition4);
            ClusterX9 = ((location * GamePanel.getUnitSize()));
            location = (int) (Math.random() * randomPosition4);
            ClusterY9 = ((location * GamePanel.getUnitSize()));

            location = (int) (Math.random() * randomPosition5);
            ClusterX10 = ((location * GamePanel.getUnitSize()));
            location = (int) (Math.random() * randomPosition5);
            ClusterY10 = ((location * GamePanel.getUnitSize()));

        }

        public int getX(){
            return ClusterX1;
        }
        public int getY(){
            return ClusterY1;
        }

        public int getX2(){
            return ClusterX2;
        }
        public int getY2(){
            return ClusterY2;
        }

        public int getX3(){
            return ClusterX3;
        }
        public int getY3(){
            return ClusterY3;
        }

        public int getX4(){
            return ClusterX4;
        }
        public int getY4(){
            return ClusterY4;
        }

        public int getX5(){
            return ClusterX5;
        }
        public int getY5(){
            return ClusterY5;
        }

        public int getX6(){
            return ClusterX6;
        }
        public int getY6(){
            return ClusterY6;
        }

        public int getX7(){
            return ClusterX7;
        }
        public int getY7(){
            return ClusterY7;
        }

        public int getX8(){
            return ClusterX8;
        }
        public int getY8(){
            return ClusterY8;
        }

        public int getX9(){
            return ClusterX9;
        }
        public int getY9(){
            return ClusterY9;
        }

        public int getX10(){
            return ClusterX10;
        }
        public int getY10(){
            return ClusterY10;
        }

        public void draw(Graphics g){
            g.setColor(Color.red);
            g.fillRect(ClusterX1, ClusterY1, GamePanel.getUnitSize(), GamePanel.getUnitSize());

            g.setColor(Color.red);
            g.fillRect(ClusterX2, ClusterY2, GamePanel.getUnitSize(), GamePanel.getUnitSize());

            g.setColor(Color.red);
            g.fillRect(ClusterX3, ClusterY3, GamePanel.getUnitSize(), GamePanel.getUnitSize());

            g.setColor(Color.red);
            g.fillRect(ClusterX4, ClusterY4, GamePanel.getUnitSize(), GamePanel.getUnitSize());

            g.setColor(Color.red);
            g.fillRect(ClusterX5, ClusterY5, GamePanel.getUnitSize(), GamePanel.getUnitSize());

            g.setColor(Color.yellow);
            g.fillRect(ClusterX6, ClusterY6, GamePanel.getUnitSize(), GamePanel.getUnitSize());

            g.setColor(Color.yellow);
            g.fillRect(ClusterX7, ClusterY7, GamePanel.getUnitSize(), GamePanel.getUnitSize());

            g.setColor(Color.yellow);
            g.fillRect(ClusterX8, ClusterY8, GamePanel.getUnitSize(), GamePanel.getUnitSize());

            g.setColor(Color.yellow);
            g.fillRect(ClusterX9, ClusterY9, GamePanel.getUnitSize(), GamePanel.getUnitSize());

            g.setColor(Color.yellow);
            g.fillRect(ClusterX10, ClusterY10, GamePanel.getUnitSize(), GamePanel.getUnitSize());

        }

    }

    // Storing and display points of the game
    static class Points {

        public void saveScore(int points) {
            File file = new File("points.txt");
            try {
                BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
                output.newLine();
                output.append("" + points);
                output.close();
            } catch (IOException ez1) {
                System.out.printf("ERROR writing score to file: %s\n", ez1);
            }
        }

        public TreeSet<Integer>SetofScores(List<Integer> points) {
            TreeSet<Integer> set = new TreeSet<>();

            int i = 0;
            while (i < points.size()) {
                set.add(points.get(i));
                i++;
            }
            return set;
        }

        public static List<Integer> readFromFile(String pFilename) throws IOException {
            try {
                BufferedReader in = new BufferedReader(new FileReader(pFilename));
                List<Integer> points = new ArrayList<>();
                String c;
                while ((c = in.readLine()) != null) {
                    //data.append((char)c);
                    if (c.length() != 0)
                        points.add(Integer.parseInt(c));
                }
                in.close();
                //System.out.printf(String.valueOf(scores));
                return points;
            }catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            return new ArrayList<>();
        }

    }

    // Game Logic
    static class GamePanel extends JPanel implements ActionListener{
        public static int width = 1000;
        public static int height = 1000;

        //pixel size for player, check point and cluster
        public static int units = 50;
        public static int startDirection = 0;
        public static boolean direction = true;

        private static int totalUnits  = (width * height) / (units * units);

        public static boolean gameRunning = false;

        private static Timer timer;

        private static int speed = 250;

        // Instance of player, check point and cluster for their methods to be used
        private static Player player = new Player(0, 0);
        private static CheckPoint checkPoint = new CheckPoint(0, 0);
        private static Cluster cluster = new Cluster(0, 0);

        private static int points = 0;

        private static Points pointsReport = new Points();

        JButton newGameButton = new JButton("New Game");
        JButton quitGameButton = new JButton("Quit");
        JTextArea text = new JTextArea(20, 20);

        // Adding components to the game panel
        public GamePanel(){
            setFocusable(true);
            setPreferredSize(new Dimension(width, height));
            setBackground(Color.BLACK);
            initialize();

            Font arial = new Font("Arial", Font.PLAIN, 20);
            newGameButton.setFont(arial);
            quitGameButton.setFont(arial);

            setLayout(null);
            newGameButton.setBounds(370, 320, 130, 45);
            quitGameButton.setBounds(510, 320, 130, 45);
            text.setBounds(370, 400, 270, 170);
            text.setBackground(Color.DARK_GRAY);

            add(newGameButton);
            add(quitGameButton);
            add(text);

            addKeyListener((new KeyArrows(this)));
            newGameButton.addActionListener(new newGameBtn_ActionListener(this));
            quitGameButton.addActionListener(new quitGameBtn_ActionListener());

            newGameButton.setVisible(false);
            quitGameButton.setVisible(false);
            text.setEditable(false);
            text.setVisible(false);
        }

        private boolean contact(int a, int b, int inrange){
            return Math.abs((long) a- b ) <= inrange;
        }

        public static int getUnits(){
            return totalUnits;
        }

        public static int getUnitSize(){
            return units;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        void draw(Graphics g) {
            if (gameRunning == false) {
                StartScreen(g);
            } else {
                checkPoint.draw(g);
                player.draw(g);
                cluster.draw(g);
                Points(g);
            }
        }

        private void Points(Graphics g){
            String message = "Check Points Reached: " + points;
            g.setColor(Color.lightGray);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString(message, 390, 30);
        }

        // Methods that will be called when the game starts
        void initialize(){
            player.setSetPlayer(0);
            player.createPlayer();
            checkPoint.createCheckPoint();
            cluster.createCluster();

            timer = new Timer(speed, this);
            timer.start();
        }

        // Points will increase and new points and clusters will be created
        void CheckPointReached(){
            if ((contact(player.getX(0), checkPoint.getX(), 20)) && (contact(player.getY(0), checkPoint.getY(), 20))) {
                points += 1;
                checkPoint.createCheckPoint();
                cluster.createCluster();
            }
        }

        // Game will end if player comes into contact with a cluster
        void ClusterReached(){
            if ((contact(player.getX(0), cluster.getX(), 20)) && (contact(player.getY(0), cluster.getY(), 20))) {
                GamePanel.gameRunning = false;
            }
            if ((contact(player.getX(0), cluster.getX2(), 20)) && (contact(player.getY(0), cluster.getY2(), 20))) {
                GamePanel.gameRunning = false;
            }
            if ((contact(player.getX(0), cluster.getX3(), 20)) && (contact(player.getY(0), cluster.getY3(), 20))) {
                GamePanel.gameRunning = false;
            }
            if ((contact(player.getX(0), cluster.getX4(), 20)) && (contact(player.getY(0), cluster.getY4(), 20))) {
                GamePanel.gameRunning = false;
            }
            if ((contact(player.getX(0), cluster.getX5(), 20)) && (contact(player.getY(0), cluster.getY5(), 20))) {
                GamePanel.gameRunning = false;
            }
            if ((contact(player.getX(0), cluster.getX6(), 20)) && (contact(player.getY(0), cluster.getY6(), 20))) {
                points -= 1;
            }
            if ((contact(player.getX(0), cluster.getX7(), 20)) && (contact(player.getY(0), cluster.getY7(), 20))) {
                points -= 1;
            }
            if ((contact(player.getX(0), cluster.getX8(), 20)) && (contact(player.getY(0), cluster.getY8(), 20))) {
                points -= 1;
            }
            if ((contact(player.getX(0), cluster.getX9(), 20)) && (contact(player.getY(0), cluster.getY9(), 20))) {
                points -= 1;
            }
            if ((contact(player.getX(0), cluster.getX10(), 20)) && (contact(player.getY(0), cluster.getY10(), 20))) {
                points -= 1;
            }

        }

        private void StartScreen(Graphics g) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("INSTRUCTIONS" , 425, 100);
            g.drawString("Practice safe distancing by avoiding Covid clusters and get to the safe zones to earn points", 85, 130);
            g.drawString(" ", 80, 160);
            g.drawString("White squares are safe zones. The player is the white circle", 230, 190);
            g.drawString("Red squares in the game are Covid clusters. You will lose if you are in contact with a cluster", 80, 220);
            g.drawString("Yellow squares are potential Covid clusters. One point will be deducted if you touch the yellow squares", 50, 250);
            g.drawString("Take note that touching the borders of the game will cause you to lose as well", 140, 280);

            newGameButton.setVisible(true);
            quitGameButton.setVisible(true);
            text.setVisible(true);

            TreeSet pointR = null;
            try {
                pointR = pointsReport.SetofScores(pointsReport.readFromFile("points.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Object[] arr = pointR.toArray();
            int[] revarr = new int[5];
            int j = 0;
            int len = arr.length;
            for (int i = 0; i < 5 && (len >= len - 5 && len > 0); i++){
                revarr[i] = (int) arr[len-1];
                j = j + 1;
                len = len - 1;
            }

            String formatString = Arrays.toString(revarr)
                    .replace(",", "  points\n                        ")
                    .replace("[", " ")
                    .replace("]", "  points");

            g.setColor(Color.white);

            String thisround = "\n                      Your Score: " + points + "\n";
            text.setFont(Font.getFont("Arial"));
            text.setText(thisround + "\n                      TOP 5 SCORES:\n" + "                        "+formatString);
            text.setForeground(Color.white);
            text.setBorder(new LineBorder(Color.BLACK));

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameRunning){
                CheckPointReached();
                ClusterReached();
                player.Collisions();
                player.move();
            }
            repaint();
        }
    }

    static class KeyArrows implements KeyListener {
        private GamePanel theApp;

        public KeyArrows(GamePanel app) {
            this.theApp = app;
        }

        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int code = keyEvent.getKeyCode();


            if ((code == KeyEvent.VK_LEFT) && (!theApp.player.isRightDirection())) {
                GamePanel.direction = false;
                theApp.player.setLeftDirection(true);
                GamePanel.startDirection = 1;
                theApp.player.setUpDirection(false);
                theApp.player.setDownDirection(false);
            }

            if ((code == KeyEvent.VK_RIGHT) && (!theApp.player.isLeftDirection())) {
                GamePanel.direction = false;
                theApp.player.setRightDirection(true);
                GamePanel.startDirection = 0;
                theApp.player.setUpDirection(false);
                theApp.player.setDownDirection(false);
            }

            if ((code == KeyEvent.VK_UP) && (!theApp.player.isDownDirection())) {
                GamePanel.direction = false;
                theApp.player.setUpDirection(true);
                GamePanel.startDirection = 2;
                theApp.player.setRightDirection(false);
                theApp.player.setLeftDirection(false);
            }

            if ((code == KeyEvent.VK_DOWN) && (!theApp.player.isUpDirection())) {
                GamePanel.direction = false;
                theApp.player.setDownDirection(true);
                GamePanel.startDirection = 3;
                theApp.player.setRightDirection(false);
                theApp.player.setLeftDirection(false);
            }
        }
        public void keyReleased(KeyEvent keyEvent) {
        }
    }

    static class quitGameBtn_ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
        }
    }

    static class newGameBtn_ActionListener implements ActionListener {
        private GamePanel theApp;
        public newGameBtn_ActionListener(GamePanel app){
            theApp = app;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            theApp.text.setVisible(false);
            theApp.gameRunning = true;
            theApp.direction = true;
            theApp.startDirection = 0;
            theApp.points = 0;
            theApp.player.setDownDirection(false);
            theApp.player.setRightDirection(false);
            theApp.player.setLeftDirection(false);
            theApp.player.setUpDirection(false);
            theApp.initialize();
            theApp.newGameButton.setVisible(false);
            theApp.quitGameButton.setVisible(false);
        }

    }

    public static void main(String[] args) {
        new Covid_2D_Game();
    }
}


package domino;

        import com.sun.tools.javac.util.ArrayUtils;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;
        import java.util.ArrayList;
        import java.lang.*;

        import java.util.Timer;
        import java.util.TimerTask;

public class MainFrame extends Frame {
    private Graphics graphics;
    private Color backgroundColor;
    private final static int PLAYERS_COUNT = 2;
    private final static int MAX_BONES_COUNT = 10;
    private final static int MAX_BONE_VALUE = 6;
    private ArrayList < Bone > [] playersBones = new ArrayList[PLAYERS_COUNT];
    private ArrayList < Bone >  bonesAllPlayers = new ArrayList<Bone>();
    private ArrayList < Bone > bonesOnTheDesk;
    private boolean selected;
    private int selectedIdx;
    private boolean gameStarted;
    private boolean isHandling;
    private boolean isChoosingBone;
    private int selectedOnBoard;
    private int countFish=0;
    private JTextArea textArea;




    public MainFrame() {
        initComponents();
        graphics = this.getGraphics();
        backgroundColor = getBackground();
    }

    private void initComponents() {
        Button buttonStart = new Button();
        add(buttonStart);
        Button buttonStop = new Button();
        add(buttonStop);

        ActionListener newListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButtonListener(e);
            }
        };

        setBackground(new Color(18, 180, 180));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setLocationRelativeTo(null);
        setResizable(false);
        selected = false;
        isHandling = false;

        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                formWindowActivated(evt);
            }

            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        buttonStart.setLabel("Начать");
        buttonStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startButtonListener(evt);
            }
        });

        buttonStop.setLabel("Считать");
        buttonStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               JOptionPane.showMessageDialog(frame, "Число рыб: "+countFish,    "Ответ", JOptionPane.QUESTION_MESSAGE);

            }
        });



        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(buttonStart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonStop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(1400, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonStop, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonStart, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(1400, Short.MAX_VALUE))
        );
        pack();
    }
    private void formWindowOpened(WindowEvent evt) {}
    private void formWindowActivated(WindowEvent evt) {}
    private void formComponentShown(ComponentEvent evt) {}




    // инициализация костей и раздача их игрокам
    private void initBones() {
        ArrayList < Bone > bonesPool = new ArrayList < Bone > ();
        bonesPool.clear();
        //инициализируем все кости 0..27 (28)
        for (byte p = 0; p <= MAX_BONE_VALUE; p++) {
            for (byte q = 0; q <= p; q++) {
                bonesPool.add(new Bone(p, q));
            }
        }

        for (int i = 0; i < PLAYERS_COUNT; i++) {
            playersBones[i] = new ArrayList < Bone > ();
        }
        //заполнение массивов игроков
        bonesOnTheDesk = new ArrayList < Bone > ();
        for (int i = 0; i < MAX_BONES_COUNT; i++) {
            for (int p = 0; p < PLAYERS_COUNT; p++) {
                int k = (int)(Math.random() * bonesPool.size());
                playersBones[p].add(bonesPool.get(k));
                bonesPool.remove(k);
            }
        }
        //заполнение массива 2n
        for (int p = 0; p < PLAYERS_COUNT; p++) {
            for (int i = 0; i < MAX_BONES_COUNT; i++) {
                bonesAllPlayers.add(playersBones[p].get(i));
            }
        }

       }



    // то что мы делаем при старте
    private void startButtonListener(ActionEvent evt) {
        graphics.clearRect(0, 0, getWidth(), getHeight());
        // Инициализируем пул костей и раздаем их между игроками
        initBones();
        // Размещаем кости игроков на экране
        for (int p = 0; p < PLAYERS_COUNT; p++) {
            for (int i = 0; i < MAX_BONES_COUNT; i++) {
                Bone bone = playersBones[p].get(i);
                int x = 0, y = 0;
                int dx = 0, dy = 0;
                switch (p) {
                    case 0:
                        x = this.getWidth() / 2 - Bone.width * MAX_BONES_COUNT+10;
                        y = this.getHeight() - Bone.width;
                        dx = (Bone.height + 10);
                        dy = 0;
                        bone.rotate((byte) 1, (byte) 0, graphics, backgroundColor);
                        break;
                    case 1:
                        x = this.getWidth() / 2 - Bone.width * MAX_BONES_COUNT+10;
                        y = 50 + Bone.width;
                        dx = (Bone.height + 10);
                        dy = 0;
                        bone.rotate((byte) - 1, (byte) 0, graphics, backgroundColor);
                        break;
                }
                bone.moveTo(x + i * dx, y + i * dy, graphics, backgroundColor);
            }
        }
        Fish fish =new Fish(bonesAllPlayers,playersBones,MAX_BONES_COUNT);
        fish.GeneratePermutation(bonesAllPlayers);
//        bonesAllPlayers.get(1).points(1)
        isChoosingBone = false;

    }



private MainFrame frame;

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                Toolkit toolKit = Toolkit.getDefaultToolkit();
                Dimension dimension =  toolKit.getScreenSize();
                frame.setBounds(dimension.width/2 - 700, dimension.height/2 - 400, 1400,800);
                JFrame.setDefaultLookAndFeelDecorated(true);
                frame.setVisible(true);
//                frame.dispose();
            }
        });
    }

}

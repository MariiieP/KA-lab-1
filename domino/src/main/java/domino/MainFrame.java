package domino;

import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Button;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrame extends Frame {
    private Graphics graphics;
    private Color backgroundColor;
    private final static int PLAYERS_COUNT = 2;
    private int MAX_BONES_COUNT = 7;
    private final static int MAX_BONE_VALUE = 6;
    private List<Bone>[] playersBones = new List[PLAYERS_COUNT];
    private List<Bone> bonesAllPlayers = new ArrayList<Bone>();
    private List<Bone> bonesOnTheDesk;
    private int[] placeJ = new int[2];
    private int[] placeK = new int[2];

    private boolean selected;
    private int countFish = 0;
    private JTextArea textArea;



    public MainFrame() {
        initComponents();
        graphics = this.getGraphics();
        backgroundColor = getBackground();
    }

    private List<Bone.BoneModel> toModels(List<Bone> list) {
        return list.stream().map(bone ->  bone.getModel()).collect(Collectors.toList());
    }

    private void initComponents() {

        Button buttonStart = new Button();
        add(buttonStart);
        Button buttonStop = new Button();
        add(buttonStop);
        TextField field1 = new TextField("7");
        add(field1);

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

        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                formWindowActivated(evt);
            }

            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }

            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
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
                String str = field1.getText()
                    .replaceAll(" ", "");
                int res = Integer.parseInt(str);
                if (res > 0 && res < 15) {
                    MAX_BONES_COUNT = res;
                    startButtonListener(evt);
                } else
                    JOptionPane.showMessageDialog(frame, "Введено неверное значение", "Ответ", JOptionPane.QUESTION_MESSAGE);
            }
        });


        buttonStop.setLabel("Считать");
        buttonStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int max = bonesAllPlayers.size();
                boolean[] tryLayOut = new boolean[max];
                int[] arrSPovtor = new int[max];
                int[] PermsNotP = new int[max];
//                Fish fish = new Fish(bonesAllPlayers.stream().map(bone ->  bone.getModel()).collect(Collectors.toList()),
//                        Arrays.stream(playersBones).map(bone ->  bone.getModel()).collect(Collectors.toList()) , MAX_BONES_COUNT, tryLayOut, arrSPovtor, PermsNotP);
                Fish fish = new Fish(toModels(bonesAllPlayers), Arrays.stream(playersBones).map(this::toModels).toArray(List[]::new),
                        MAX_BONES_COUNT, tryLayOut, arrSPovtor, PermsNotP);

                if (fish.generatePermutation(bonesAllPlayers))
                    searchButtonListener(evt, fish);
                else
                    JOptionPane.showMessageDialog(frame, "Рыбы нет", "Ответ", JOptionPane.QUESTION_MESSAGE);
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
                        .addComponent(field1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(1400, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonStop, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonStart, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addComponent(field1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(1400, Short.MAX_VALUE))
        );
        pack();
    }

    private void formWindowOpened(WindowEvent evt) {
    }

    private void formWindowActivated(WindowEvent evt) {
    }

    private void formComponentShown(ComponentEvent evt) {
    }

    public void addBoneList(ArrayList bone,List boneBone){
        for (int i=0;i<bone.size()-1;i++)
            bone.add(boneBone);
//        return;
    }

    private void exitForm(WindowEvent evt) {
        System.exit(0);
    }

    // инициализация костей и раздача их игрокам
    private void initBones() {
        List<Bone> bonesPool = new ArrayList<Bone>();
        bonesPool.clear();
        //инициализируем все кости 0..27 (28)
        for (byte p = 0; p <= MAX_BONE_VALUE; p++) {
            for (byte q = 0; q <= p; q++) {
                bonesPool.add(new Bone(p, q));
            }
        }
        for (int i = 0; i < PLAYERS_COUNT; i++) {
            playersBones[i] = new ArrayList<Bone>();
        }
        //заполнение массивов игроков
        bonesOnTheDesk = new ArrayList<Bone>();
        for (int i = 0; i < MAX_BONES_COUNT; i++) {
            for (int p = 0; p < PLAYERS_COUNT; p++) {
                int k = (int) (Math.random() * bonesPool.size());
                playersBones[p].add(bonesPool.get(k));
                bonesPool.remove(k);
            }
        }
        bonesAllPlayers.clear();
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
                        x = this.getWidth() / 2 - Bone.width * MAX_BONES_COUNT + 10;
                        y = this.getHeight() - Bone.width;
                        dx = (Bone.height + 10);
                        dy = 0;
                        bone.rotate((byte) 1, (byte) 0, graphics, backgroundColor);
                        break;
                    case 1:
                        x = this.getWidth() / 2 - Bone.width * MAX_BONES_COUNT + 10;
                        y = 60 + Bone.width;
                        dx = (Bone.height + 10);
                        dy = 0;
                        bone.rotate((byte) 1, (byte) 0, graphics, backgroundColor);
                        break;
                }
                bone.moveTo(x + i * dx, y + i * dy, graphics, backgroundColor);
            }
        }
    }

    private void searchButtonListener(ActionEvent evt, Fish fish) {
        int size = fish.tryLayOut.length;
        int maxBoneTrue = 0;
        for (int i = 0; i <= size - 1; i++)
            if (fish.tryLayOut[i] == true)
                maxBoneTrue++;
        int x = 0, y = 0;
        Bone bone1 = bonesAllPlayers.get(fish.PermsP[0]);
        x = this.getWidth() / 2;
        y = this.getHeight() / 2;
        bone1.moveTo(x, y, graphics, backgroundColor);
        placeJ[0] = bone1.getX() - bone1.getSizeX();
        ;
        placeJ[1] = bone1.getX() + bone1.getSizeX();
        placeK[0] = bone1.getY();
        placeK[1] = bone1.getY();

        for (int i = 1; i < maxBoneTrue; i++) {
            Bone bone = bonesAllPlayers.get(fish.PermsP[i]);
            switch (fish.arrSPovtor[i - 1]) {
                case 0:
                    x = placeJ[1];
                    bone.moveTo(x, y, graphics, backgroundColor);
                    placeJ[1] = bone.getX() + bone.getSizeX();
                    break;
                case 1:
                    x = placeJ[1];
                    bone.rotate((byte) -1, (byte) 0, graphics, backgroundColor);
                    bone.moveTo(x, y, graphics, backgroundColor);
                    placeJ[1] = bone.getX() + bone.getSizeX();
                    break;
                case 2:
                    x = placeJ[0];
                    bone.rotate((byte) -1, (byte) 0, graphics, backgroundColor);
                    bone.moveTo(x, y, graphics, backgroundColor);
                    placeJ[0] = bone.getX() - bone.getSizeX();
                    break;
                case 3:
                    x = placeJ[0];
                    bone.moveTo(x, y, graphics, backgroundColor);
                    placeJ[0] = bone.getX() - bone.getSizeX();
                    break;
            }
        }
    }



    private MainFrame frame;

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                Toolkit toolKit = Toolkit.getDefaultToolkit();
                Dimension dimension = toolKit.getScreenSize();
                frame.setBounds(dimension.width / 2 - 700, dimension.height / 2 - 400, 1400, 800);
                JFrame.setDefaultLookAndFeelDecorated(true);
                frame.setVisible(true);
            }
        });
    }


}



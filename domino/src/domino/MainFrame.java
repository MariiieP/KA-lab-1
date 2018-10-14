
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
    private final static int MAX_BONES_COUNT = 2;
    private final static int MAX_BONE_VALUE = 6;
    private ArrayList < Bone > [] playersBones = new ArrayList[PLAYERS_COUNT];
    private ArrayList < Bone >  bonesAllPlayers = new ArrayList<>();
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
        GeneratePermutation();
        isChoosingBone = false;

    }



    public void GeneratePermutation() {

        //int[] arr = {1, 2, 3, 4};
        int count = fuctorial(bonesAllPlayers.size());
        int max = bonesAllPlayers.size() - 1;

        int[] arr = new int[bonesAllPlayers.size()];
        for (int i=0; i<bonesAllPlayers.size();i++)
            arr[i]=i;

        int shift = max;

        while (count > 0) {

            int temp = arr[shift];
            arr[shift] = arr[shift - 1];
            arr[shift - 1] = temp;

//            int[] testArray = {0, 3, 1, 2};
//            if  (testFish(testArray))
//                countFish++;
           if  (testFish(arr))
                    countFish++;

            count--;
            if (shift < 2) {
                shift = max;
            } else {
                shift--;
            }

        }
        for(;;){}
    }


    static int fuctorial(int n) {
        return (n > 0) ? n * fuctorial(n - 1) : 1;
    }



    public boolean whose( int index )
    {

        int i = 0;
       if ( (index  >= 0) && (index  <= (MAX_BONES_COUNT-1) ))
           return true;
       else
           return false;


    }


    public boolean testFish(int[] arr)
    {
        // for(int p = 0; p < PLAYERS_COUNT; p++) {
        for (int i = 0; i < arr.length - 1; i++) {

            int index = arr[i];
            int index2 = arr[i+1];

            if ( (whose(index) && whose(index2)) == true) {
                for (int j = 0; j < MAX_BONES_COUNT; j++) {
                    Bone boneTest = bonesAllPlayers.get(i);
                    Bone bonePlayers = playersBones[1].get(j);
                    if ((boneTest.points(0) == bonePlayers.points(0)) || (boneTest.points(0) == bonePlayers.points(1)))
                        return false;
                }
            } else
                if  ( (whose(index) == false) && ( whose(index2) == false) ) {
                    for (int j = 0; j < MAX_BONES_COUNT; j++) {
                        Bone boneTest = bonesAllPlayers.get(i);
                        Bone bonePlayers = playersBones[0].get(j);
                        if ((boneTest.points(0) == bonePlayers.points(0)) || (boneTest.points(0) == bonePlayers.points(1)))
                            return false;
                    }
                }
        }

        int[] zero = new int[bonesAllPlayers.size()];
        int countUseBonePlayers1=0; int countUseBonePlayers2=0;
        int first = bonesAllPlayers.get(arr[0]).points(1);
        int last = bonesAllPlayers.get(arr[0]).points(0);
        if (whose(arr[0]) == true)
            countUseBonePlayers1++;
            //playersBones[0].remove(0);
        else
            countUseBonePlayers2++;
            //playersBones[1].remove(0);

        for (int i = 0; i < arr.length - 1; i++) {
            while (zero[i] != 4) {
            switch (zero[i]) {

                    case 0:
                        if (first == bonesAllPlayers.get(arr[i + 1]).points(0)) {
                            first = bonesAllPlayers.get(arr[i + 1]).points(1);

                            if (whose(arr[i+1]) == true)
                                countUseBonePlayers1++;
                                //playersBones[0].remove(0);
                            else
                                countUseBonePlayers2++;
                            //playersBones[1].remove(0);
                            zero[i]=3;
                            break;
                        }
                        break;
                    case 1:
                        if (first == bonesAllPlayers.get(arr[i + 1]).points(1)) {
                            first = bonesAllPlayers.get(arr[i + 1]).points(0);

                            if (whose(arr[i+1]) == true)
                                countUseBonePlayers1++;
                                //playersBones[0].remove(0);
                            else
                                countUseBonePlayers2++;
                            //playersBones[1].remove(0);
                            zero[i]=3;
                            break;
                        }
                        break;
                    case 2:
                        if (last == bonesAllPlayers.get(arr[i + 1]).points(0)) {
                           last = bonesAllPlayers.get(arr[i + 1]).points(1);
                           // zero[i]=4;
                            if (whose(arr[i+1] ) == true)
                                countUseBonePlayers1++;
                                //playersBones[0].remove(0);
                            else
                                countUseBonePlayers2++;
                            //playersBones[1].remove(0);
                            zero[i]=3;
                            break;
                        }
                        break;
                    case 3:
                        if (last == bonesAllPlayers.get(arr[i + 1]).points(1)) {
                            last = bonesAllPlayers.get(arr[i + 1]).points(0);
                            //zero[i]=4;
                            if (whose(arr[i]) == true)
                                countUseBonePlayers1++;
                                //playersBones[0].remove(0);
                            else
                                countUseBonePlayers2++;
                            //playersBones[1].remove(0);
                            zero[i]=3;
                            break;
                        }
                        break;
                }
                zero[i]++;
            }

                if (zero[i] == 4){
                    zero[i]=0;
                    zero[i+1]=1;
                }
        }
        if ( ((first == bonesAllPlayers.get(0).points(1) ) && ( last == bonesAllPlayers.get(0).points(0))) ||
                ( (countUseBonePlayers1< MAX_BONES_COUNT) && (countUseBonePlayers2 < MAX_BONES_COUNT) ) )

            return true;
        else
            return false;

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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class panel extends JPanel {
    public static final int NUMBER_OF_ITEMS = 200;
    int height;
    int width;
    int[] numbers = new int[NUMBER_OF_ITEMS];
    int[] pointers = new int[]{-1,-1};
    int[] completed = new int[NUMBER_OF_ITEMS];
    int[] alreadySorted = new int[NUMBER_OF_ITEMS];
    JComboBox sortType = new JComboBox(new String[]{"Merge Sort", "Bubble Sort", "Selection Sort", "Quick Sort", "Insertion Sort", "Tim Sort"});
    JButton startSort = new JButton("Sort");
    JButton reset = new JButton("Reset");
    Thread sortThread;

    public panel(int width, int height)
    {
        add(sortType);
        add(startSort);
        startSort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startSort();
            }
        } );
        add(reset);
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset(NUMBER_OF_ITEMS);
            }
        } );

        this.width = width;
        this.height = height;
        reset(NUMBER_OF_ITEMS);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    public void paintComponent(Graphics g)
    {
        int targetWidth = (7*width/8 - width/8);
        int rectWidth = Math.round((targetWidth)/(float)numbers.length);
        int acutalWidth = rectWidth*numbers.length;
        int startX = width/2 - acutalWidth/2;
        int topY = height/2 - 3*height/8;

        int rectHeight = (3*height)/4;
        setDoubleBuffered(true);
        g.setColor(Color.GRAY);
        g.fillRect(0,0,width,height);

        for (int i = 0; i < numbers.length; i++) {
            if (completed[i] == 1) {
                g.setColor(Color.GREEN);
                g.fillRect(startX + i*rectWidth, topY + (rectHeight-numbers[i]), rectWidth, numbers[i]);
            }
            else if (pointers[0] == i) {
                g.setColor(Color.BLUE);
                g.fillRect(startX + i*rectWidth, topY + (rectHeight-numbers[i]), rectWidth, numbers[i]);
            }
            else if (pointers[1] == i) {
                g.setColor(Color.RED);
                g.fillRect(startX + i*rectWidth, topY + (rectHeight-numbers[i]), rectWidth, numbers[i]);
            }
            else if (alreadySorted[i] == 1) {
                g.setColor(Color.YELLOW);
                g.fillRect(startX + i*rectWidth, topY + (rectHeight-numbers[i]), rectWidth, numbers[i]);
            }
            else {
                g.setColor(Color.WHITE);
                g.fillRect(startX + i * rectWidth, topY + (rectHeight - numbers[i]), rectWidth, numbers[i]);
            }
            g.setColor(Color.BLACK);
            g.drawRect(startX + i*rectWidth, topY + (rectHeight-numbers[i]), rectWidth, numbers[i]);
        }
    }

    public void reset(int length) {
        if (sortThread != null) {
            if (!sortThread.isAlive()) {
                for (int i = 0; i < length; i++) {
                    numbers[i] = (int) (((3*height)/4)*(Math.random()));
                    completed[i] = 0;
                    alreadySorted[i] = 0;
                }
            }
        }
        else {
            for (int i = 0; i < length; i++) {
                numbers[i] = (int) (((3*height)/4)*(Math.random()));
                completed[i] = 0;
                alreadySorted[i] = 0;
            }
        }
    }

    public void startSort() {
        if (sortThread == null) {
            if (completed[0] == 1) {
                reset(NUMBER_OF_ITEMS);
            }
            sortThread = new Thread(new Sort(numbers, pointers, completed, alreadySorted, (String)sortType.getSelectedItem()));
            sortThread.start();
        }
        else if (!sortThread.isAlive()) {
            if (completed[0] == 1) {
                reset(NUMBER_OF_ITEMS);
            }
            sortThread = new Thread(new Sort(numbers, pointers, completed, alreadySorted, (String)sortType.getSelectedItem()));
            sortThread.start();
        }
    }
}

import javax.swing.*;
import java.awt.*;


public class FrameA extends JFrame{
    panel panell;
    public FrameA() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panell = new panel(dim.width, dim.height);
        panell.setLayout(new FlowLayout());
        panell.setSize(dim.width, (dim.height / 4));
        panell.setLocation(0, 0);

        this.add(panell);


        this.setSize(dim.width, dim.height);
        this.setVisible(true);
    }
}
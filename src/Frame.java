import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame(Dimension d){
        setSize(d);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setTitle("MainFrame");
    }
}

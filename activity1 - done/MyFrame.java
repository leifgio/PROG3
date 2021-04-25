
/* run salary.java */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MyFrame extends JFrame implements ActionListener {
    JLabel label;
    JTextField line;
    JLabel answer;
    JButton button;
    float workHour;
    float normalPay = 0F;
    float overtimePay = 0F;
    float totalPay = 0F;

    MyFrame() {
        label = new JLabel();
        label.setBounds(0, 0, 100, 20);
        label.setText("number of hours: ");

        line = new JTextField();
        line.setBounds(100, 0, 50, 20);

        button = new JButton();
        button.setBounds(150, 0, 50, 20);
        button.addActionListener(this);
        button.setText("ok");

        answer = new JLabel();
        answer.setBounds(0, 20, 1000, 60);
        answer.setText("normal pay: " + normalPay + " overtime pay:" + overtimePay + " total pay: " + totalPay);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.setSize(350, 150);
        this.setTitle("Salary Calculator");

        this.add(label);
        this.add(line);
        this.add(button);
        this.add(answer);

    }

    @Override
    public void actionPerformed(ActionEvent press) {
        if (press.getSource() == button) {
            this.workHour = Float.parseFloat(line.getText());
            if (workHour < 40) {
                normalPay = workHour * 35;
                totalPay = normalPay + overtimePay;
            } else {
                normalPay = 1400F;
                float overtime = workHour - 40F;
                overtimePay = overtime * 52.50F;
                System.out.println(overtimePay);
                totalPay = normalPay + overtimePay;
            }
            answer.setText("normal pay: " + normalPay + " overtime pay:" + overtimePay + " total pay: " + totalPay);
        }
    }
}

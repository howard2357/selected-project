package app.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//收到來自陌生人訊息的處理
public class ChatStrange extends JDialog {

    JLabel jLshow = new JLabel(" 收到来自陌生人的訊息");
    JLabel jLname = new JLabel();
    JTextArea jTchat = new JTextArea();
    JScrollPane jSchat = new JScrollPane(jTchat);
    JPanel jP1 = new JPanel();
    JButton jbClose = new JButton(new ImageIcon("src/file/CVclose.jpg"));
    String friendNum;
    String friendName;
    String friendInfo;
    String line_separator = System.getProperty("line.separator");

    public ChatStrange(JFrame owner, String title, boolean b,
                       String friendNum, String friendName, String friendInfo) {
        // TODO Auto-generated constructor stub
        super(owner, title, b);
        this.friendNum = friendNum;
        this.friendName = friendName;
        this.friendInfo = friendInfo;
        this.setSize(408, 225);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        init();
        this.add(jP1);
        this.add(jSchat);
        this.add(jbClose);
    }

    public void init() {
        jLshow.setForeground(Color.WHITE);
        jLshow.setFont(new Font("黑體", Font.BOLD, 16));
        jLshow.setSize(400, 25);
        jLname.setText("     " + friendNum + " (" + friendName + ")");
        jLname.setForeground(Color.WHITE);
        jLname.setFont(new Font("楷體", Font.BOLD, 16));
        jLname.setSize(400, 25);
        jP1.setBackground(Color.PINK);
        jP1.setBounds(0, 0, 408, 50);
        jP1.setLayout(new BorderLayout());
        jP1.add(BorderLayout.NORTH, jLshow);
        jP1.add(BorderLayout.SOUTH, jLname);
        jTchat.setBackground(Color.WHITE);
        jTchat.setForeground(Color.BLACK);
        jTchat.setFont(new Font("黑體", Font.PLAIN, 17));
        Date time = new java.util.Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String timeInfo = format.format(time);
        System.out.println("收到訊息：" + friendInfo);
        jTchat.append(" " + friendName + "  " + timeInfo + line_separator);
        jTchat.append(friendInfo + line_separator + line_separator);
        jTchat.append(line_separator);
        jSchat.setBounds(0, 50, 400, 110);
        jbClose.setBounds(300, 165, 69, 23);
        jbClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                ChatStrange.this.setVisible(false);
            }

        });

    }
}
package app.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutMy extends JDialog {
    JPanel jp = new JPanel();
    JLabel jl = new JLabel("We_Talk");
    JLabel jimage = new JLabel(new ImageIcon("src/file/about.jpg"));
    JLabel j1 = new JLabel("如果您在使用We_Talk聊天服務時有問題");
    JLabel j2 = new JLabel("均可聯繫本公司客戶服務，將有人為您服務");
    JLabel j4 = new JLabel("-----------------------------------------");
    JLabel j5 = new JLabel("根據We_Talk相關條款，本產品一切");
    JLabel j6 = new JLabel("使用權及解釋權皆屬於：");
    JLabel j7 = new JLabel("大拇指");
    JButton jb = new JButton("確定");

    public AboutMy(
            Frame info, String title, boolean b) {
        // TODO Auto-generated constructor stub

        super(info, title, b);
        this.setSize(465, 550);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(jp);
        init();
    }

    public void init() {
        jp.setSize(465, 550);
        jp.setLayout(null);
        jp.setBackground(Color.WHITE);
        jimage.setBounds(10, 20, 100, 100);
        jp.add(jimage);
        jl.setFont(new Font("宋體", Font.BOLD, 38));
        jl.setBounds(165, 55, 300, 40);
        jp.add(jl);
        j1.setFont(new Font("宋體", Font.PLAIN, 18));
        j1.setBounds(80, 140, 380, 25);
        jp.add(j1);
        j2.setFont(new Font("宋體", Font.PLAIN, 18));
        j2.setBounds(44, 190, 380, 25);
        jp.add(j2);
        j4.setFont(new Font("宋體", Font.PLAIN, 18));
        j4.setBounds(44, 290, 380, 25);
        jp.add(j4);
        j5.setFont(new Font("宋體", Font.PLAIN, 18));
        j5.setBounds(80, 330, 380, 25);
        jp.add(j5);
        j6.setFont(new Font("宋體", Font.PLAIN, 18));
        j6.setBounds(44, 380, 380, 25);
        jp.add(j6);
        j7.setFont(new Font("宋體", Font.PLAIN, 18));
        j7.setBounds(220, 430, 100, 25);
        jp.add(j7);
        jb.setBounds(345, 480, 70, 25);
        jb.setFont(new Font("宋體", Font.PLAIN, 14));
        jb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                AboutMy.this.setVisible(false);
            }

        });
        jp.add(jb);
    }
}

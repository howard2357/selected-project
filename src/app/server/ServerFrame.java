package app.server;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import app.common.ShowTimeTask;
import app.common.UserBean;
import app.common.UserInfo;

public class ServerFrame extends JFrame {
    JLabel jshowList = new JLabel("在線用戶列表<10秒刷新一次>");
    JLabel jshowServerLog = new JLabel("伺服器日誌");
    JLabel jUserCount = new JLabel("在線人數 :");
    JLabel jCount = new JLabel("0");
    JLabel jLtime = new JLabel();
    JButton jBgetInfo = new JButton("查看訊息");
    JButton jBkickOut = new JButton("踢出");
    JButton jBpauseServer = new JButton("暫停服務");
    JButton jBexit = new JButton("退出");
    DefaultListModel listModel = new DefaultListModel();
    JList userList = new JList(listModel);
    JScrollPane jSuserList = new JScrollPane(userList);
    JTextArea jTServerLog = new JTextArea();
    JScrollPane jServerLog = new JScrollPane(jTServerLog);
    private Connection con = null;
    ServerThread serverThread = null;
    private Hashtable userTable = new Hashtable();//将UserBean的对象统一存储到HashTable中

    public ServerFrame() {
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("伺服器端控制介面");

        con = ConnectionTo.getConnection();
        init();
        this.add(jshowList);
        this.add(jSuserList);
        this.add(jBgetInfo);
        this.add(jBkickOut);
        this.add(jUserCount);
        this.add(jCount);
        this.add(jshowServerLog);
        this.add(jServerLog);
        this.add(jBpauseServer);
        this.add(jBexit);
        this.add(jLtime);
        serverThread = new ServerThread(jTServerLog);
        serverThread.start();

        java.util.Timer myTime = new java.util.Timer();
        java.util.TimerTask task_showtime = new ShowTimeTask(jLtime);
        myTime.schedule(task_showtime, 0, 1000);
        java.util.Timer time = new java.util.Timer();
        java.util.TimerTask task_time = new LoginUser(listModel, userList, jCount, userTable, con);
        time.schedule(task_time, 0, 10000);
        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void init() {
        jshowList.setBounds(20, 10, 150, 25);
        jshowList.setFont(new Font("宋體", Font.PLAIN, 11));
        jSuserList.setBounds(10, 40, 190, 500);
        jBgetInfo.setBounds(20, 550, 75, 25);
        jBgetInfo.setFont(new Font("宋體", Font.PLAIN, 10));

        jBgetInfo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String selectedUser = null;
                String userNum = null;
                selectedUser = (String) userList.getSelectedValue();
                if (selectedUser == null) {
                    JOptionPane.showMessageDialog(jBgetInfo, "請單擊選擇一個用戶");
                } else {
                    System.out.println(selectedUser);
                    userNum = selectedUser.substring(selectedUser.indexOf("<") + 1, selectedUser.indexOf(">"));
                    UserBean user = (UserBean) userTable.get(userNum);
                    UserInfo userInfo = new UserInfo(ServerFrame.this, "查看訊息", true, user);
                    userInfo.setVisible(true);
                }
            }

        });
        jBkickOut.setBounds(110, 550, 60, 25);
        jBkickOut.setFont(new Font("宋體", Font.PLAIN, 10));

        jBkickOut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int index = userList.getSelectedIndex();
                String userNum = null;
                if (index == -1) {
                    JOptionPane.showMessageDialog(jBkickOut, "請單擊選擇一個用戶");
                } else {
                    String userInfo = (String) listModel.getElementAt(index);
                    userNum = userInfo.substring(userInfo.indexOf("<") + 1, userInfo.indexOf(">"));
                    System.out.println(userNum);
                    removeUser(userNum);
                    listModel.remove(index);
                    int num = Integer.parseInt(jCount.getText()) - 1;
                    jCount.setText(new Integer(num).toString());
                }
            }

        });
        jUserCount.setBounds(25, 595, 100, 30);
        jUserCount.setFont(new Font("宋體", Font.PLAIN, 12));
        jCount.setBounds(125, 595, 20, 30);
        jCount.setFont(new Font("宋體", Font.PLAIN, 12));
        jshowServerLog.setBounds(220, 10, 150, 25);
        jshowServerLog.setFont(new Font("宋體", Font.PLAIN, 11));
        jServerLog.setBounds(210, 40, 575, 550);
        jBpauseServer.setBounds(340, 595, 90, 25);
        jBpauseServer.setFont(new Font("宋體", Font.PLAIN, 11));

        jBpauseServer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String command = e.getActionCommand();
                if (command.equals("暫停服務")) {
                    serverThread.pauseThread();
                    jBpauseServer.setText("恢復服務");
                } else if (command.equals("恢復服務")) {
                    serverThread.reStartThread();
                    jBpauseServer.setText("暫停服務");
                }
            }

        });
        jBexit.setBounds(470, 595, 60, 25);
        jBexit.setFont(new Font("宋體", Font.PLAIN, 11));
        jBexit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int option = JOptionPane.showConfirmDialog(jBexit, "確定要退出嗎？");
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        con.close();

                        System.exit(0);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }

        });
        jLtime.setBounds(600, 615, 250, 50);
        jLtime.setFont(new Font("宋體", Font.PLAIN, 12));
    }


    public void removeUser(String userNum) {
        String sql = "UPDATE UserInformation SET Status = 0 where UserNum= '" + userNum + "'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ServerFrame f = new ServerFrame();
        f.setVisible(true);
    }
}

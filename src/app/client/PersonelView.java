package app.client;
/*Has
 * HashMap<String,String> map=new HashMap<String,String>;
 * map.put("1","student");
 *Iterator<String> mit=map.keySet().iterator();
 *while(mit.hasNext()){
 *String str=mit.next();
 *System.out.println(map.get(str));
 *} */
//dengluh

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.plaf.OptionPaneUI;

import app.common.FriendLabel;
import app.common.UserBean;
import app.common.UserInfo;

/*主介面*/
public class PersonelView extends JFrame implements Runnable {
    JPanel jPtop = new JPanel();//置於頂部
    JPanel jPcentre = new JPanel();//置於中間存放好友列表
    //����
    JLabel jLtitle = new JLabel("We_Talk");
    //QQͷ��ߴ�60*60
    JLabel jLportrait = new JLabel(new ImageIcon("src/file/personelView1.jpg"));
    JLabel jLmyName = new JLabel("我是毛毛蟲");
    JLabel jLmySign = new JLabel("便利貼");
    JTextField jTfind = new JTextField("輸入We_Talk帳號尋找聯絡人");
    ImageIcon imageFind = new ImageIcon("src/file/personelViewFind.jpg");//尺寸19*21
    JButton jBfind = new JButton(imageFind);//尋找聯絡人按鈕
    //過渡
    JLabel jLcenter1 = new JLabel(new ImageIcon("src/file/personelViewCenter1.jpg"));//尺寸305*7
    //中部
    JTabbedPane jTPchoose = new JTabbedPane();//選單
    JPanel jPcentrefriend = new JPanel();//好友列表
    JLabel j1 = new JLabel(new ImageIcon("src/file/你猜1.jpg"));
    JLabel j2 = new JLabel(new ImageIcon("src/file/你猜2.jpg"));
    JLabel j3 = new JLabel(new ImageIcon("src/file/你猜1.jpg"));
    JLabel j4 = new JLabel(new ImageIcon("src/file/你猜2.jpg"));
    DefaultListModel listModel = new DefaultListModel();
    JList userList = new JList(listModel);
    JScrollPane jSuserList = new JScrollPane(userList);
    JMenuItem jM11 = new JMenuItem("顯示列表");
    JMenuItem jM12 = new JMenuItem("刷新好友列表");
    JMenuItem jM13 = new JMenuItem("顯示在線上好友");
    JMenuItem jM14 = new JMenuItem("添加好友");
    JMenuItem jM15 = new JMenuItem("尋找好友");
    JMenuItem jM16 = new JMenuItem("編輯好友");
    JMenuItem jM17 = new JMenuItem("HELP");
    JMenuItem jM18 = new JMenuItem("關於");
    JPopupMenu jPmenuser = new JPopupMenu();
    JMenuItem jM1 = new JMenuItem("傳送訊息");
    JMenuItem jM2 = new JMenuItem("傳送電子郵件");
    JMenuItem jM3 = new JMenuItem("傳送文件");
    JMenuItem jM4 = new JMenuItem("刪除好友");
    JMenuItem jM5 = new JMenuItem("檢舉用戶");
    JMenuItem jM6 = new JMenuItem("修改暱稱");
    JMenuItem jM7 = new JMenuItem("通知紀錄");
    JMenuItem jM8 = new JMenuItem("查看資料");
    JPopupMenu jPmenufriend = new JPopupMenu();
    JLabel jLbase = new JLabel(new ImageIcon("src/file/personelView2.jpg"));

    /*-------------------------------------功能實現---------------------------------------------------*/
    private Hashtable friendInfoTable = new Hashtable(); // 存儲好友列表
    Socket socket; // 定義套接口
    BufferedReader in; // 定義輸入流
    PrintStream out; // 定義輸出流
    InetAddress ip = null; // 伺服器IP
    int port = 0; // 伺服器端口號
    String userNum; // 登入使用者自己的 QQ 號
    String userPass; // 登入使用者自己的密碼
    HomePage login;
    private int currentIndex = 0; // 鼠標所指的列表索引
    private String currentInfo = ""; // 鼠標所指的列表值
    private String currentUserNum = null; // 鼠標所指好友的 QQ 號碼
    private UserBean currentFriend = null; // 鼠標所指好友的信息類
    private UserBean myInfo = new UserBean(); // 存儲自己的信息
    UserBean findUserBean = new UserBean(); // 存儲查找到的使用者的基本信息
    /* 采用 UDP 協議進行通信 */
    private DatagramSocket receiveSocket = null; // 声明接收訊息的數據包套接字
    private DatagramPacket receivePacket = null; // 声明接收訊息的數據包
    int udpPort = getUdpPort("udp.Port"); // UDP 的初始端口號
    InetAddress userIp = null;
    int usePort = getNextPort(udpPort); // 當前使用者使用的端口號
    public static final int BUFFER_SIZE = 5120; // 緩衝陣列的大小
    private byte inBuf[]; // 接收數據的緩衝陣列
    // 實現聊天記錄
    BufferedReader bufr; // 完成聊天記錄的讀取
    String path = null;

    /*-------------------------------------構造方法---------------------------------------------------*/
    public PersonelView(String userNum, String userPass, HomePage login, InetAddress ip, int port) {
        // TODO Auto-generated constructor stub
        this.userNum = userNum;
        this.userPass = userPass;
        this.login = login;
        this.ip = ip;
        this.port = port;
        this.setSize(313, 590);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        init();
        this.add(jPtop);
        this.add(jLcenter1);
        this.add(jLbase);
        this.add(jTPchoose);
        /*------------------------采用 TCP 協議與伺服器開始連接，完成登入以及其他功能---------------------------------------*/
        try {
            socket = new Socket(ip, port);
            System.out.println("與伺服器開始連接");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.out.println("伺服器端口打開出錯");
        }
        if (socket != null) {
            System.out.println("與伺服器連接成功");
            try {
                userIp = InetAddress.getLocalHost();
            } catch (UnknownHostException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            // udpPort = socket.getLocalPort();
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintStream(socket.getOutputStream());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        /*--------------------------------------UDP 協議，聊天模塊-----------------------------------------*/
        try {
            // 創建接收訊息的數據包套接字
            // System.out.println("1我接收數據的端口號:"+usePort);
            receiveSocket = new DatagramSocket(usePort);
            // System.out.println("2我接收數據的端口號:"+usePort);
            inBuf = new byte[BUFFER_SIZE];
            // 創建接收訊息的數據報
            receivePacket = new DatagramPacket(inBuf, BUFFER_SIZE);
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("端口打開出錯");
        }
        loadUserInfo();
        // 啟動線程用來刷新好友信息
        new Thread(this).start();
        this.setVisible(true);
    }

    // 獲取好友信息
    private void loadUserInfo() {
        if (login()) {
            getFriendInfo(); // 獲取好友信息列表
            userList.setCellRenderer(new FriendLabel());
            jLportrait.setIcon(new ImageIcon(myInfo.getPortrait()));
            jLmyName.setText(myInfo.getUserName());
            jLmySign.setText(myInfo.getSign());
        } else {
            login.loginFail();
            System.out.println("獲取信息失敗！");
            return;
        }
    }
// 每隔10秒刷新一次好友信息

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //刷新用戶資訊
            loadUserInfo();
        }

    }

    public void init() {
        jPtop.setBounds(0, 0, 313, 110);
        jPtop.setLayout(null);
        jPtop.setBackground(Color.PINK);
        jLtitle.setForeground(Color.WHITE);
        jLtitle.setFont(new Font("黑體", Font.BOLD, 15));
        jLtitle.setBounds(0, 0, 313, 25);
        jLportrait.setBounds(5, 25, 60, 55);

        jLportrait.addMouseListener(new PersonelView_jLportrait_mouseMotionAdapter());
        jLmyName.setForeground(Color.WHITE);
        jLmyName.setFont(new Font("宋體", Font.BOLD, 17));
        jLmyName.setBounds(80, 27, 180, 25);
        //在個人資料上添加彈出式選單
        JMenuItem jMa = new JMenuItem("�޸ĸ�������");
        JMenuItem jMb = new JMenuItem("����ͷ��");
        //JMenuItem jMc=new JMenuItem("修改密碼");
        jMa.setFont(new Font("楷體", Font.PLAIN, 14));
        jMa.setForeground(Color.BLACK);
        jMa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                ChangeMyInfo changemyInfo = new ChangeMyInfo(PersonelView.this, "�޸�����", true, in, out, myInfo, PersonelView.this);
                changemyInfo.setVisible(true);
                PersonelView.this.refreshMyInfo();
            }

        });
        jMb.setFont(new Font("楷體", Font.PLAIN, 14));
        jMb.setForeground(Color.BLACK);
        jMb.addActionListener(new ActionListener() {//����ͷ��

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                changHead();

            }

        });
        //jMc.setFont(new Font("楷體",Font.PLAIN,14));
        //jMc.setForeground(Color.BLACK);
        JPopupMenu jPmenuMy = new JPopupMenu();
        jPmenuMy.add(jMa);
        jPmenuMy.add(jMb);
        //jPmenuMy.add(jMc);
        jLmyName.setComponentPopupMenu(jPmenuMy);
        jLmySign.setForeground(Color.WHITE);
        jLmySign.setFont(new Font("楷體", Font.PLAIN, 14));
        jLmySign.setBounds(70, 55, 235, 25);
        jLmySign.setComponentPopupMenu(jPmenuMy);
        jTfind.setFont(new Font("宋體", Font.BOLD, 12));
        jTfind.setForeground(Color.GRAY);
        jTfind.setBounds(3, 85, 260, 25);
        jBfind.setBounds(267, 87, 19, 21);
        jBfind.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                String num = jTfind.getText().trim();
                findUser(num);
            }
        });
        jPtop.add(jBfind);
        jPtop.add(jTfind);
        jPtop.add(jLmySign);
        jPtop.add(jLmyName);
        jPtop.add(jLportrait);
        jPtop.add(jLtitle);
        jPtop.add(jLtitle);
        //����
        jLcenter1.setBounds(0, 110, 313, 7);
        //�м�
        /*--------------------------增加彈出式選單---------------------*/
        jPmenufriend.add(jM1);
        jPmenufriend.add(jM2);
        jPmenufriend.add(jM3);
        jPmenufriend.add(jM4);
        jPmenufriend.add(jM5);
        jPmenufriend.add(jM6);
        jPmenufriend.add(jM7);
        jM7.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                path = "src/record/" + myInfo.getUserNum() + "-" + currentFriend.getUserNum() + ".txt";
                try {
                    bufr = new BufferedReader(
                            new InputStreamReader(new FileInputStream(
                                    path)));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                SaveChat save = new SaveChat(PersonelView.this, "record", true, bufr);
                save.setVisible(true);
            }
        });
        jPmenufriend.add(jM8);
        jM1.setFont(new Font("楷體", Font.PLAIN, 14));
        jM1.setForeground(Color.BLUE);
        jM1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                new Thread(new ChatView(myInfo, currentFriend, PersonelView.this, usePort, receiveSocket, receivePacket, friendInfoTable, in, out)).start();
            }

        });
        jM2.setFont(new Font("楷體", Font.PLAIN, 14));
        jM2.setForeground(Color.BLUE);
        jM3.setFont(new Font("楷體", Font.PLAIN, 14));
        jM3.setForeground(Color.BLUE);
        jM4.setFont(new Font("楷體", Font.PLAIN, 14));
        jM4.setForeground(Color.BLUE);
        jM4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int option = JOptionPane.showConfirmDialog(PersonelView.this, "亲，你确定要删除此好友么？");
                if (option == JOptionPane.YES_OPTION) {
                    int index = userList.getSelectedIndex();
                    String friendNum = null;
                    if (index == -1) {
                        JOptionPane.showMessageDialog(PersonelView.this, "请单击选择一个用户！");
                    } else {
                        try {
                            String friendInfo = (String) listModel.getElementAt(index);
                            friendNum = friendInfo.substring(friendInfo.indexOf("<") + 1, friendInfo.indexOf(">"));
                            UserBean deleteFriend = (UserBean) friendInfoTable.get(friendNum);
                            String myUserNum = myInfo.getUserNum();
                            //向伺服器傳送刪除好友請求
                            out.println("deleteFriend");
                            out.flush();
                            //發送自己QQ
                            out.println(myUserNum);
                            out.flush();
                            //發送好友QQ
                            out.println(friendNum);
                            out.flush();
                            String judge_delete = in.readLine();
                            if (judge_delete.equals("deleteFriendOver")) {
                                JOptionPane.showMessageDialog(PersonelView.this, "好友 <" + deleteFriend.getUserName() + "> 已被成功删除 !");
                                listModel.remove(index);
                            } else if (judge_delete.equals("deleteFriendFail")) {
                                JOptionPane.showMessageDialog(PersonelView.this, "系统繁忙,請稍後再試！");
                            }
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(PersonelView.this, "系统繁忙維護中！");
                        }
                    }
                }
            }

        });
        jM5.setFont(new Font("楷體", Font.PLAIN, 14));
        jM5.setForeground(Color.BLUE);
        jM6.setFont(new Font("楷體", Font.PLAIN, 14));
        jM6.setForeground(Color.BLUE);
        jM7.setFont(new Font("楷體", Font.PLAIN, 14));
        jM7.setForeground(Color.BLUE);
        jM8.setFont(new Font("楷體", Font.PLAIN, 14));
        jM8.setForeground(Color.BLUE);
        jM8.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                UserInfo friendInfo = new UserInfo(PersonelView.this, "好友資料", true, currentFriend);
                friendInfo.setVisible(true);
            }

        });
        userList.setComponentPopupMenu(jPmenufriend);
        jTPchoose.setForeground(Color.DARK_GRAY);
        jTPchoose.setBackground(Color.WHITE);
        jTPchoose.setFont(new Font("宋體", Font.PLAIN, 11));
        jTPchoose.addTab("聯絡人", jPcentre);
        jTPchoose.addTab("群組", j1);
        jTPchoose.addTab("最近的聊天室", j2);
        jTPchoose.addTab("朋友", j3);
        jTPchoose.addTab("微博", j4);
        jTPchoose.setBounds(0, 117, 313, 382);
        jPcentre.setLayout(new BorderLayout());
        JLabel test = new JLabel("我 的 好 友");
        test.setFont(new Font("宋體", Font.PLAIN, 14));
        test.setSize(313, 30);
        test.setForeground(Color.BLACK);
        jPmenuser.add(jM11);
        jPmenuser.add(jM12);
        jPmenuser.add(jM13);
        jPmenuser.add(jM14);
        jPmenuser.add(jM15);
        jPmenuser.add(jM16);
        jPmenuser.add(jM17);
        jPmenuser.add(jM18);
        jM11.setFont(new Font("楷體", Font.PLAIN, 14));
        jM11.setForeground(Color.DARK_GRAY);
        jM12.setFont(new Font("楷體", Font.PLAIN, 14));
        jM12.setForeground(Color.DARK_GRAY);
        jM13.setFont(new Font("楷體", Font.PLAIN, 14));
        jM13.setForeground(Color.DARK_GRAY);
        jM14.setFont(new Font("楷體", Font.PLAIN, 14));
        jM14.setForeground(Color.DARK_GRAY);
        jM14.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                PrintNumFindUser printNum = new PrintNumFindUser(PersonelView.this, "添加联系人", false, PersonelView.this);
                printNum.setVisible(true);
            }
        });
        jM15.setFont(new Font("楷體", Font.PLAIN, 14));
        jM15.setForeground(Color.DARK_GRAY);
        jM15.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                PrintNumFindUser printNum = new PrintNumFindUser(PersonelView.this, "查找用户", false, PersonelView.this);
                printNum.setVisible(true);
            }

        });
        jM16.setFont(new Font("楷體", Font.PLAIN, 14));
        jM16.setForeground(Color.DARK_GRAY);
        jM17.setFont(new Font("楷體", Font.PLAIN, 14));
        jM17.setForeground(Color.DARK_GRAY);
        jM18.setFont(new Font("楷體", Font.PLAIN, 14));
        jM18.setForeground(Color.DARK_GRAY);
        jM18.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                AboutMy a = new AboutMy(PersonelView.this, "關於QQ", true);
                a.setVisible(true);
            }

        });
        test.setComponentPopupMenu(jPmenuser);
        jPcentre.setBackground(Color.WHITE);
        jPcentre.setBounds(0, 117, 313, 382);
        userList.addMouseListener(new PersonelView_userList_mouseAdapter());
        userList.addMouseMotionListener(new PersonelView_userList_mouseMotionAdapter());
        jPcentre.add(BorderLayout.NORTH, test);
        jPcentre.add(jSuserList);
        //底部
        jLbase.setBounds(0, 499, 304, 51);
    }

    private Boolean login() {
        String judge = null;

        try {
            out.println("login");
            out.flush();
            out.println(userNum);
            out.flush();
            out.println(userPass);
            out.flush();
            out.println(userIp);
            out.flush();
            out.println(usePort);
            //System.out.println("發送給資料庫我的端口"+usePort);
            out.flush();
            //讀取自己的資訊
            judge = in.readLine();
            if (judge.equals("loginFail")) {
                return false;
            } else if (judge.equals("sendUserInfo")) {
                String flag_1 = in.readLine();
                if (flag_1.equals("queryUserFail")) {
                    return false;
                } else {
                    myInfo.setUserNum(flag_1);
                    myInfo.setUserName(in.readLine());
                    myInfo.setSex(in.readLine());
                    myInfo.setBirth(in.readLine());
                    myInfo.setAddress(in.readLine());
                    myInfo.setSign(in.readLine());
                    myInfo.setPortrait(in.readLine());
                    myInfo.setStatus(Integer.valueOf(in.readLine()));
                    myInfo.setPort(Integer.valueOf(in.readLine()));
                    myInfo.setIp(in.readLine());

                }
            }

            String flag_3 = in.readLine();
            if (flag_3.equals("loginSuccess")) {
                /*-----------------開始從伺服器端讀取好友訊息--------------------------*/
                friendInfoTable.clear();
                String flag2 = "";
                do {
                    flag2 = in.readLine().trim();
                    System.out.println("flag2:" + flag2);
                    if (flag2.equals("queryFriendOver")) {
                        System.out.println("BREAK");
                        break;

                    } else {
                        UserBean friendBean = new UserBean();
                        friendBean.setUserNum(flag2);
                        friendBean.setUserName(in.readLine());
                        friendBean.setSex(in.readLine());
                        friendBean.setBirth(in.readLine());
                        friendBean.setAddress(in.readLine());
                        friendBean.setSign(in.readLine());
                        friendBean.setPortrait(in.readLine());
                        friendBean.setStatus(Integer.valueOf(in.readLine()));
                        friendBean.setPort(Integer.valueOf(in.readLine()));
                        friendBean.setIp(in.readLine());
                        friendInfoTable.put(flag2, friendBean);
                    }
                } while (!(flag2.equals("queryFriendOver")));
            } else if (flag_3.equals("queryUserFail")) {
                return false;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    private void getFriendInfo() {
        listModel.removeAllElements();
        //實現Enumeration接口的物件，生成一系列元素，一次生成一個。連續調用nextElement方法將返回一系列的連續元素。
        Enumeration it = friendInfoTable.elements();
        String name = "";
        String num = "";
        String portrait = "";
        String friendinfo = "";//每一個好友的公開資訊
        int status = 0;
        while (it.hasMoreElements()) {
            UserBean user = (UserBean) it.nextElement();
            name = user.getUserName().trim();
            num = user.getUserNum().trim();
            portrait = user.getPortrait();
            status = user.getStatus();
            friendinfo = status + name + "<" + num + ">" + "*" + portrait + "^";
            listModel.addElement(friendinfo);
        }

    }

    //讀取端口
    private int getUdpPort(String key) {
        int myport = 0;
        Properties p = new Properties();
        try {
            FileInputStream in = new FileInputStream("src/file/udp.txt");
            FileOutputStream out = new FileOutputStream("src/file/udp.txt", true);
            p.load(in);//叢書入流中讀取屬性列表
            myport = Integer.parseInt(p.getProperty(key));
            myport = myport + 1;
            p.setProperty("udp.Port", new Integer(myport).toString());
            p.store(out, "new udp.Port");
            in.close();
            out.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return myport;
    }

    private int getNextPort(int port) {
        int nextport = port;
        Boolean flag = true;
        DatagramSocket testsocket = null;
        //檢查端口是否被占用
        while (true) {
            flag = true;
            try {
                testsocket = new DatagramSocket(++nextport);
            } catch (SocketException e) {
                // TODO Auto-generated catch block
                flag = false;
            }
            if (flag == true) {
                break;
            }
            System.out.println(nextport);

        }
        testsocket.close();
        return nextport;

    }

    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            exit();
        }
    }

    public void exit()
    {
        int option = JOptionPane.showConfirmDialog(PersonelView.this, "亲，你确定要退出么？");
        if (option == JOptionPane.YES_OPTION) {
            try {
                out.println("logout");
                out.flush();
                out.println(myInfo.getUserNum());
                out.flush();
                String msg = in.readLine();
                if (msg.equals("logOut")) {
                    out.println("end");
                    out.flush();
                    in.close();
                    out.close();
                    socket.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                this.dispose();
                System.exit(0);
            }
        }
    }

    //處理滑鼠雙擊好友頭像事件
    class PersonelView_userList_mouseAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                new Thread(new ChatView(myInfo, currentFriend, PersonelView.this, usePort, receiveSocket, receivePacket, friendInfoTable, in, out)).start();

            }
        }
    }

    //處理滑鼠移到好友頭像上的事件
    class PersonelView_userList_mouseMotionAdapter extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            if (!(listModel.isEmpty())) {
                currentIndex = userList.locationToIndex(e.getPoint());//获得当前所在列表的索引
                currentInfo = listModel.getElementAt(currentIndex).toString();//获得当前列表的值
                currentUserNum = currentInfo.substring(currentInfo.indexOf("<") + 1, currentInfo.indexOf(">"));
                //根据好友的QQ号查找好友的信息
                currentFriend = (UserBean) friendInfoTable.get(currentUserNum);
                String friendSign = currentFriend.getSign();
                userList.setToolTipText(friendSign);//设置提示信息，显示好友的个性签名
            }
        }
    }

    class PersonelView_jLportrait_mouseMotionAdapter extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            jLportrait.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        public void mousePressed(MouseEvent e) {

            UserInfo userInfo = new UserInfo(PersonelView.this, "我的資料", true, myInfo);
            userInfo.setVisible(true);
        }
    }

    public void findUser(String num) {
        //UserBean findUser=new UserBean();
        out.println("queryUser");
        out.flush();
        out.println(num);
        out.flush();
        try {
            String judge_find = in.readLine();
            if (judge_find.equals("noUser")) {
                JOptionPane.showMessageDialog(PersonelView.this, "用戶不存在");
            } else if (judge_find.equals("queryUserFail")) {
                JOptionPane.showMessageDialog(PersonelView.this, "尋找用戶失敗");
            } else {
                findUserBean.setUserNum(judge_find);
                findUserBean.setUserName(in.readLine());
                findUserBean.setSex(in.readLine());
                findUserBean.setBirth(in.readLine());
                findUserBean.setAddress(in.readLine());
                findUserBean.setSign(in.readLine());
                findUserBean.setPortrait(in.readLine());
                findUserBean.setStatus(Integer.valueOf(in.readLine()));
                findUserBean.setPort(Integer.valueOf(in.readLine()));
                findUserBean.setIp(in.readLine());
                FindUser find = new FindUser(PersonelView.this, "用戶資料", true, findUserBean, PersonelView.this);
                find.setVisible(true);
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            JOptionPane.showMessageDialog(PersonelView.this, "系統正在維護中！");
        }

    }

    public void addUser() {
        try {
            String name = findUserBean.getUserName().trim();
            String num = findUserBean.getUserNum().trim();
            String portrait = findUserBean.getPortrait();
            int status = findUserBean.getStatus();
            String friendinfo = status + name + "<" + num + ">" + "*" + portrait + "^";
            if (listModel.contains(friendinfo)) {
                JOptionPane.showMessageDialog(PersonelView.this, "已與該用戶成為好友！");
            } else {
                //像伺服器傳送添加好友請求
                out.println("addFriend");
                out.flush();
                out.println(myInfo.getUserNum());
                out.flush();
                out.println(findUserBean.getUserNum());
                out.flush();
                String judge_add = in.readLine();
                if (judge_add.equals("addFriendOver")) {
                    //將新加入的好友資訊存入Hash table 中
                    friendInfoTable.put(findUserBean.getUserNum().trim(), findUserBean);
                    //在列表顯示新加入的好友
                    listModel.addElement(friendinfo);
                    JOptionPane.showMessageDialog(PersonelView.this, "添加成功");
                } else if (judge_add.equals("addFriendFail")) {
                    JOptionPane.showMessageDialog(PersonelView.this, "添加好友失败");
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //更新自己的頭像
    public void refreshMyProtrait() {
        jLportrait.setIcon(new ImageIcon(myInfo.getPortrait()));
    }

    public void refreshMyInfo() {
        jLmyName.setText(myInfo.getUserName());
        jLmySign.setText(myInfo.getSign());
    }

    public void changHead() {
        ChangeHead changeHead = new ChangeHead(PersonelView.this, "更換頭像", true, myInfo, in, out, PersonelView.this);
        changeHead.setVisible(true);
        PersonelView.this.refreshMyProtrait();
    }
}

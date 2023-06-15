package app.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTextArea;


public class ServerThread extends Thread {
    JTextArea jTServerLog = null;
    Boolean flag = true;
    String line_separator = System.getProperty("line.separator");
    ServerSocket server;
    /*-------------------傳送文件----------------------------*/
    Vector clients = new Vector();

    /*-----------------------------------------------------------*/
    public ServerThread(JTextArea jTServerLog) {
        // TODO Auto-generated constructor stub
        this.jTServerLog = jTServerLog;
    }

    public void reStartThread() {
        // TODO Auto-generated method stub
        this.flag = true;
    }

    public void pauseThread() {
        // TODO Auto-generated method stub
        this.flag = false;
    }

    public void run() {
        try {
            server = new ServerSocket(6544);
            jTServerLog.append("聊天系統開始啟用· · · · · " + line_separator);
            jTServerLog.append(line_separator);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            jTServerLog.append("伺服器端口打開錯誤· · · · · ·" + line_separator);
            jTServerLog.append(line_separator);
        }

        if (server != null) {
            while (flag) {
                try {
                    System.out.println("伺服器：" + flag);

                    Socket socket = server.accept();
                    jTServerLog.append("****************************" + line_separator);
                    jTServerLog.append("Connection accept : " + socket + line_separator);
                    Date time = new java.util.Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd kk:mm:ss");
                    String timeInfo = format.format(time);
                    jTServerLog.append("處理時間 : " + timeInfo + line_separator);
                    jTServerLog.append("****************************" + line_separator);

                    new Thread(new Server(socket)).start();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    jTServerLog.append("客戶練線失敗· · · · · ·" + line_separator);
                    jTServerLog.append(line_separator);
                }
            }
        }
    }
}
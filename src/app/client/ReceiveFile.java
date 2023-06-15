package app.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.common.UserBean;

public class ReceiveFile {
    ChatView father;
    BufferedReader in;
    PrintStream out;
    UserBean myInfo;
    UserBean currentFriend;
    String line_separator = System.getProperty("line.separator");

    public ReceiveFile(ChatView father, BufferedReader in, PrintStream out, UserBean myInfo, UserBean currentFriend) {
        this.father = father;
        this.in = in;
        this.out = out;
        this.myInfo = myInfo;
        this.currentFriend = currentFriend;
    }

    public void receive() {
        String receiveQQ = null;
        String sendQQ = null;
        String fileName = null;
        String judge = null;
        //String fileContent=null;
        //-------------------------------------接收文件----------------------------------//
        Date time = new java.util.Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String timeInfo = format.format(time);
        try {
            judge = in.readLine();
            System.out.println("客戶端：傳送文件" + judge);
            if (judge.equals("FILE")) {
                System.out.println("客戶端：傳送文件已經到這裡了");
                receiveQQ = in.readLine();
                System.out.println("接收人We_Talk帳號：" + receiveQQ);
                System.out.println("我的We_Talk帳號：" + myInfo.getUserNum());
                System.out.println("好友的We_Talk帳號：" + currentFriend.getUserNum());
                if (receiveQQ.equals(myInfo.getUserNum())) {
                    sendQQ = in.readLine();
                    System.out.println("傳送人We_Talk：" + sendQQ);
                    fileName = in.readLine();
                    System.out.println("文件名：" + fileName);
                    BufferedWriter bw = new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream("src/接收文件/" + fileName)));
                    String j;
                    do {
                        j = in.readLine();
                        if (j.equals("All")) {
                            break;
                        } else {

                            bw.write(j);
                            bw.newLine();
                            //	fileContent+=j+"\n";
                        }

                    } while (!j.equals("All"));
                    bw.flush();
                    bw.close();
                    father.jTAshowChat.append(" " + sendQQ + "  " + timeInfo + line_separator);
                    father.jTAshowChat.append("文件已成功接收" + line_separator + line_separator);
                    father.jTAshowChat.append(line_separator);
                }
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //---------------------------------------------------------------------------------//
    }

}
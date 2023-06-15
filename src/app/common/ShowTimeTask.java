package app.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

public class ShowTimeTask extends java.util.TimerTask {
    private JLabel showTime = null;

    public ShowTimeTask(JLabel showTime) {
        this.showTime = showTime;
    }

    public void run() {
        Date time = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String timeInfo = format.format(time);
        showTime.setText("現在時間" + timeInfo + "    ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package swingserver;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;


public class ReminderBeep 
{
  Toolkit toolkit;
  Timer timer;

  public ReminderBeep(int seconds) {
    toolkit = Toolkit.getDefaultToolkit();
    timer = new Timer();
    //timer.schedule(new RemindTask(), seconds * 1000);
    timer.schedule(new RemindTask(), 3 * 1000,seconds * 1000);
  }

  class RemindTask extends TimerTask {
    public void run() {
      System.out.println("Triggering...!");
      toolkit.beep();
      
    }
  }

  public static void main(String args[]) {
    System.out.println("About to schedule task.");
    new ReminderBeep(5);
    System.out.println("Task scheduled.");
  }
}
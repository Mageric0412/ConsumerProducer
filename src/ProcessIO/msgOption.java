package ProcessIO;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public interface msgOption {
  public void output(String msg,JTextArea txtAera, JProgressBar jpb);
  public void queueBuffer(JTextField number);
}



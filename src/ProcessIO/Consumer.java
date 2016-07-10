package ProcessIO;

import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class Consumer extends Thread {
	  private CPQueue queue;
	  private JTextArea txtAera;
	  private JProgressBar jpb;
	  
	  public Consumer(CPQueue queue) {
		// TODO Auto-generated constructor stub
		  this.queue = queue;	 
	 }
	  
	  public void setOthers(JTextArea txtAera, JProgressBar jpb){
		  this.txtAera = txtAera;
		  this.jpb = jpb;
	  }
	  
	  
	  @Override
	 public void run(){
	   while(true){
		 this.queue.consume(this.txtAera, this.jpb);
		 try{
			 sleep(1000);
		 }
		 catch(Exception e){
			 
		 }
	   }
	 } 
	}

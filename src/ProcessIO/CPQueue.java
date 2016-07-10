package ProcessIO;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import UIBoundary.*;

public class CPQueue implements msgOption{
	public static final String img= "404.jpg";
    public int maxMessageNum = 0;
    public List<Message>queue = new ArrayList<Message>();
    private static ImageIcon icon = new ImageIcon(img);	
	public Component[] labels;
	//获取当前队列的大小
	public synchronized int getCount() {
		return queue.size();
	}
	
	//生产
	public synchronized void produce(Message msg,JTextArea txtAera,JProgressBar jpb){
		this.notifyAll();
		while(queue.size() == maxMessageNum)
		{
			output(Thread.currentThread().getName()+"队列满，等待消费...", txtAera, jpb);
		    try{
		    	this.wait();
		    }
		    catch(InterruptedException e){
		    	e.printStackTrace();
		    }
		}
		queue.add(msg);
		output(Thread.currentThread().getName()+"正在生产"+msg.getContent()+"...，当前个数："+getCount(), txtAera, jpb);
	    labels = CPBoundary.jlgrd.getComponents();
	   if(queue.size()<=25)
	    ((JLabel)labels[queue.size()-1]).setIcon(icon);
	}
	
	public synchronized void consume(JTextArea txtAera,JProgressBar jpb){
		this.notifyAll();
		while(queue.size() == 0)
		{
			output(Thread.currentThread().getName()+"队列为空，等待生产...", txtAera, jpb);
		    try{
		    	output("等待生产", txtAera, jpb);
		    	wait();
		    	output("结束等待", txtAera, jpb);
		    }
		    catch(InterruptedException e){
		    	e.printStackTrace();
		    }
		}
		Message message =queue.get(0);
		queue.remove(0);
		output(Thread.currentThread().getName()+"正在消费"+message.getContent()+"...，当前个数："+getCount(), txtAera, jpb);
		((JLabel)labels[queue.size()]).setIcon(null);
	}
	
	//实现队列消息的接口
	@Override
	public void output(String msg,JTextArea txtAera, JProgressBar jpb) {
		// TODO Auto-generated method stub
		txtAera.append(msg+ "\n");
		txtAera.append("\n当前容量："+this.getCount()+"\n");
		jpb.setValue(this.getCount());
		
		
	}

	//实现队列容量的接口
	@Override
	public void queueBuffer(JTextField number) {
		// TODO Auto-generated method stub
		this.maxMessageNum = Integer.parseInt(number.getText());
	}

}

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
	//��ȡ��ǰ���еĴ�С
	public synchronized int getCount() {
		return queue.size();
	}
	
	//����
	public synchronized void produce(Message msg,JTextArea txtAera,JProgressBar jpb){
		this.notifyAll();
		while(queue.size() == maxMessageNum)
		{
			output(Thread.currentThread().getName()+"���������ȴ�����...", txtAera, jpb);
		    try{
		    	this.wait();
		    }
		    catch(InterruptedException e){
		    	e.printStackTrace();
		    }
		}
		queue.add(msg);
		output(Thread.currentThread().getName()+"��������"+msg.getContent()+"...����ǰ������"+getCount(), txtAera, jpb);
	    labels = CPBoundary.jlgrd.getComponents();
	   if(queue.size()<=25)
	    ((JLabel)labels[queue.size()-1]).setIcon(icon);
	}
	
	public synchronized void consume(JTextArea txtAera,JProgressBar jpb){
		this.notifyAll();
		while(queue.size() == 0)
		{
			output(Thread.currentThread().getName()+"����Ϊ�գ��ȴ�����...", txtAera, jpb);
		    try{
		    	output("�ȴ�����", txtAera, jpb);
		    	wait();
		    	output("�����ȴ�", txtAera, jpb);
		    }
		    catch(InterruptedException e){
		    	e.printStackTrace();
		    }
		}
		Message message =queue.get(0);
		queue.remove(0);
		output(Thread.currentThread().getName()+"��������"+message.getContent()+"...����ǰ������"+getCount(), txtAera, jpb);
		((JLabel)labels[queue.size()]).setIcon(null);
	}
	
	//ʵ�ֶ�����Ϣ�Ľӿ�
	@Override
	public void output(String msg,JTextArea txtAera, JProgressBar jpb) {
		// TODO Auto-generated method stub
		txtAera.append(msg+ "\n");
		txtAera.append("\n��ǰ������"+this.getCount()+"\n");
		jpb.setValue(this.getCount());
		
		
	}

	//ʵ�ֶ��������Ľӿ�
	@Override
	public void queueBuffer(JTextField number) {
		// TODO Auto-generated method stub
		this.maxMessageNum = Integer.parseInt(number.getText());
	}

}

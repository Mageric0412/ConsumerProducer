package UIBoundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;

import ProcessIO.*;

public class CPBoundary extends JFrame{

	public static final int STARTX = 200;
	public static final int STARTY = 200;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int REPO = 25;
	
	public static List<JLabel> list = new ArrayList<JLabel>();
	public static JLabel jlgrd = new JLabel("");
	public static JTextField txtbox;
	public static JTextArea txtarea;
	public static JScrollPane scrollpane;
	public int index = 0;
	public JProgressBar prgbar =new JProgressBar();
	public JPanel jpup = new JPanel();
	public JPanel jpdwn = new JPanel();
	
	private Thread threadP;
	private Thread threadC;
	
	Container cr = this.getContentPane();
	CPQueue Q = new CPQueue();
	Producer pdr = new Producer(Q);
	Consumer csr = new Consumer(Q);
	
	public CPBoundary(){
		
		this.setBounds(STARTX, STARTY, WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cr.setLayout(new BorderLayout());
		//layoutPanel();
		layoutButtons();
		layoutflds();
		
		jlgrd.setBounds(STARTX+WIDTH/4, STARTY, WIDTH/4, HEIGHT/3);
		jlgrd.setBorder(BorderFactory.createEtchedBorder());
		jlgrd.setLayout(new GridLayout(5,5));
		for(int i=0;i<REPO;i++)
		{
			JLabel JLtmp = new JLabel("");
			JLtmp.setSize(WIDTH/15, HEIGHT/15);
			JLtmp.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			list.add(JLtmp);
			jlgrd.add(JLtmp);
		}
		cr.add(jlgrd);
	    cr.add(jpdwn,BorderLayout.NORTH);
	    cr.add(jpup,BorderLayout.SOUTH);
	}
	
	public void layoutflds(){
		txtarea = new JTextArea(25,25);
		prgbar.setEnabled(true);
		txtarea.append("请按开始DEMO"+"\n");
		txtarea.setEditable(false);
		txtarea.setBackground(Color.green);
		scrollpane = new JScrollPane(txtarea);
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jpup.setLayout(new BorderLayout());
		jpup.add(prgbar,BorderLayout.NORTH);
		jpup.add(scrollpane,BorderLayout.SOUTH);
		
	}
	
	
	public void layoutButtons(){
		JButton button1 = new JButton("开始DEMO");
		JButton button2 = new JButton("暂停DEMO");
		JButton button3 = new JButton("恢复DEMO");
		JButton button4 = new JButton("暂停生产");
		JButton button5 = new JButton("恢复生产");
		JButton button6 = new JButton("暂停消费");
		JButton button7 = new JButton("恢复消费");
		JButton button8 = new JButton("停止DEMO");
		
		txtbox = new JTextField(String.valueOf(REPO),2);
		
		button1.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadC = new Thread(csr,"消费者线程");
				threadP = new Thread(pdr,"生产者线程");
				Q.maxMessageNum =Integer.parseInt(txtbox.getText());
				prgbar.setMaximum(Q.maxMessageNum);
				pdr.setOthers(txtarea, prgbar);
				csr.setOthers(txtarea, prgbar);
				threadP.start();
				threadC.start();
				txtarea.append("线程开始运行"+"\n");
			}
		});
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadP.suspend();
				threadC.suspend();
				txtarea.append("线程暂停"+"\n");
			}
		});
		
		button3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtarea.append("恢复消费和生产"+"\n");
				threadP.resume();
				threadC.resume();
			}
		});
		
		button4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadP.suspend();
				txtarea.append("生产暂停"+"\n");
			}
		});
		
		button5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadP.resume();
				txtarea.append("已恢复生产"+"\n");
			}
		});
		
		button6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadC.suspend();
				txtarea.append("消费暂停"+"\n");
			}
		});
		
		button7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadC.resume();
				txtarea.append("已恢复消费"+"\n");
			}
		});
		
		button8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadP.stop();
				threadC.stop();
				txtarea.append("所有线程已停止"+"\n");
			}
		});
		
		jpdwn.setLayout(new GridLayout());
		jpdwn.add(txtbox);
		jpdwn.add(button1);
		jpdwn.add(button2);
		jpdwn.add(button3);
		jpdwn.add(button4);
		jpdwn.add(button5);
		jpdwn.add(button6);
		jpdwn.add(button7);
		jpdwn.add(button8);
		
		
	}
	
	
}

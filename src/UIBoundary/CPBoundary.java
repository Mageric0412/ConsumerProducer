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
		txtarea.append("�밴��ʼDEMO"+"\n");
		txtarea.setEditable(false);
		txtarea.setBackground(Color.green);
		scrollpane = new JScrollPane(txtarea);
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jpup.setLayout(new BorderLayout());
		jpup.add(prgbar,BorderLayout.NORTH);
		jpup.add(scrollpane,BorderLayout.SOUTH);
		
	}
	
	
	public void layoutButtons(){
		JButton button1 = new JButton("��ʼDEMO");
		JButton button2 = new JButton("��ͣDEMO");
		JButton button3 = new JButton("�ָ�DEMO");
		JButton button4 = new JButton("��ͣ����");
		JButton button5 = new JButton("�ָ�����");
		JButton button6 = new JButton("��ͣ����");
		JButton button7 = new JButton("�ָ�����");
		JButton button8 = new JButton("ֹͣDEMO");
		
		txtbox = new JTextField(String.valueOf(REPO),2);
		
		button1.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadC = new Thread(csr,"�������߳�");
				threadP = new Thread(pdr,"�������߳�");
				Q.maxMessageNum =Integer.parseInt(txtbox.getText());
				prgbar.setMaximum(Q.maxMessageNum);
				pdr.setOthers(txtarea, prgbar);
				csr.setOthers(txtarea, prgbar);
				threadP.start();
				threadC.start();
				txtarea.append("�߳̿�ʼ����"+"\n");
			}
		});
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadP.suspend();
				threadC.suspend();
				txtarea.append("�߳���ͣ"+"\n");
			}
		});
		
		button3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtarea.append("�ָ����Ѻ�����"+"\n");
				threadP.resume();
				threadC.resume();
			}
		});
		
		button4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadP.suspend();
				txtarea.append("������ͣ"+"\n");
			}
		});
		
		button5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadP.resume();
				txtarea.append("�ѻָ�����"+"\n");
			}
		});
		
		button6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadC.suspend();
				txtarea.append("������ͣ"+"\n");
			}
		});
		
		button7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadC.resume();
				txtarea.append("�ѻָ�����"+"\n");
			}
		});
		
		button8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				threadP.stop();
				threadC.stop();
				txtarea.append("�����߳���ֹͣ"+"\n");
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

package com.feldim2425.OTPGen.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;

import com.feldim2425.OTPGen.codegen.CodeFactory;
import com.feldim2425.OTPGen.ui.event.MainHandler;
import com.feldim2425.OTPGen.ui.widget.PanelList;

public class MainUI {
	
	public static final int STD_WIN_HEIGTH = 300;
	public static final int STD_WIN_WIDTH = 450;
	public static final int MIN_WIN_HEIGTH = 300;
	public static final int MIN_WIN_WIDTH = 450;
	public static final String STD_NAME = "TOTP Generator";
	public static MainUI window;
	
	public MainHandler handler = new MainHandler();
	
	public JFrame frame;
	public JMenuBar menubar;
	public JMenu mnFile;
	public JMenu mnSettings;
	public JMenuItem mntmPassword;
	public JMenu mnTags;
	public JMenu mnShow;
	public JMenuItem mntmEditTag;
	public JButton button;
	public PanelList scrollPane;
	public JMenuItem mntmNewF;
	public JMenuItem mntmOpen;
	public JToggleButton tgbtnRun;
	
	private JRadioButtonMenuItem rdbtSAll;
	private JRadioButtonMenuItem rdbtSStd;

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainUI();
					window.frame.setVisible(true);
					CodeFactory.updateUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(MIN_WIN_WIDTH,MIN_WIN_HEIGTH));
		frame.setTitle(STD_NAME);

		//frame.setResizable(false);
		frame.setBounds(100, 100, STD_WIN_WIDTH, STD_WIN_HEIGTH);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(handler);
		frame.addComponentListener(handler);
		frame.getContentPane().setLayout(null);
		
		menubar = new JMenuBar();
		menubar.setBounds(0, 0, STD_WIN_WIDTH, 20);
		frame.getContentPane().add(menubar);
		
		handler.addWidthScale(menubar);
		
		mnFile = new JMenu("File");
		menubar.add(mnFile);
		
		mntmOpen = new JMenuItem("Open/Create File");
		mnFile.add(mntmOpen);
		mntmOpen.addActionListener(handler);
		
		mnSettings = new JMenu("Settings");
		menubar.add(mnSettings);
		
		mntmPassword = new JMenuItem("Set Password");
		mnSettings.add(mntmPassword);
		
		mnTags = new JMenu("Tags");
		menubar.add(mnTags);
		
		mnShow = new JMenu("Show");
		mnTags.add(mnShow);
		
		rdbtSAll = new JRadioButtonMenuItem("All");
		rdbtSAll.addActionListener(handler);
		mnShow.add(rdbtSAll);
		
		rdbtSStd = new JRadioButtonMenuItem("Standard");
		rdbtSStd.addActionListener(handler);
		rdbtSStd.setSelected(true);
		mnShow.add(rdbtSStd);
		
		mnShow.addSeparator();
		
		mntmEditTag = new JMenuItem("Edit Tags");
		mnTags.add(mntmEditTag);
		mntmEditTag.addActionListener(handler);

		
		button = new JButton("+");
		button.setForeground(Color.GREEN);
		button.setFont(new Font("Dialog", Font.BOLD, 14));
		button.setBounds(10, 26, 51, 20);
		frame.getContentPane().add(button);
		
		scrollPane = new PanelList();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(11, 53, 429, 212);
		frame.getContentPane().add(scrollPane);
		handler.addHeigthScale(scrollPane);
		handler.addWidthScale(scrollPane);
		
		tgbtnRun = new JToggleButton("Run");
		tgbtnRun.setBounds(73, 26, 90, 20);
		frame.getContentPane().add(tgbtnRun);
		tgbtnRun.addActionListener(handler);
	}
}
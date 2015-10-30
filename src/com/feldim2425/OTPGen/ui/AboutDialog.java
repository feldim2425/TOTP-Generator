package com.feldim2425.OTPGen.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.feldim2425.OTPGen.Main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutDialog extends JDialog {

	private static final long serialVersionUID = -2839167492836575691L;

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JLabel lblVersion;

	/**
	 * Launch the application.
	 */
	public static void start() {
		try {
			AboutDialog dialog = new AboutDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		super(MainUI.window.frame);
		
		int posX = 100;
		int posY = 100;
		if(MainUI.window!=null){
			Point p = MainUI.window.frame.getLocationOnScreen();
			posX = p.x;
			posY = p.y;
		}
		
		setBounds(posX, posY, 313, 321);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);

		JLabel lblTotpGenerator = new JLabel("TOTP - Generator");
		lblTotpGenerator.setFont(new Font("Dialog", Font.BOLD, 17));
		lblTotpGenerator.setHorizontalAlignment(SwingConstants.CENTER);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblTotpGenerator, 10,
				SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblTotpGenerator, 10,
				SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblTotpGenerator, -15,
				SpringLayout.EAST, contentPanel);
		contentPanel.add(lblTotpGenerator);

		lblNewLabel = new JLabel("created by feldim2425");
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblNewLabel, 0,
				SpringLayout.WEST, lblTotpGenerator);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblNewLabel, 0,
				SpringLayout.EAST, lblTotpGenerator);
		lblNewLabel.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblNewLabel);

		lblVersion = new JLabel("Version:   " + Main.VERSION);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblVersion, 26,
				SpringLayout.SOUTH, lblTotpGenerator);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblVersion, 0,
				SpringLayout.WEST, lblTotpGenerator);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblVersion, 0,
				SpringLayout.EAST, lblTotpGenerator);
		lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblVersion);

		JLabel lblNewLabel_1 = new JLabel("©  2015  feldim2425 ");
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblNewLabel, -64,
				SpringLayout.NORTH, lblNewLabel_1);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblNewLabel_1, 0,
				SpringLayout.WEST, lblTotpGenerator);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblNewLabel_1, -10,
				SpringLayout.SOUTH, contentPanel);
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 9));
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Released:   " + Main.R_DATE);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblNewLabel_2, 6,
				SpringLayout.SOUTH, lblVersion);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblNewLabel_2, 5,
				SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblNewLabel_2, 0,
				SpringLayout.EAST, lblTotpGenerator);
		contentPanel.add(lblNewLabel_2);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Close");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog.this.dispose();
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

	}
}

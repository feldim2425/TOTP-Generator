package com.feldim2425.optgen.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import com.feldim2425.optgen.codegen.CodeEntry;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class ShowQRDialog extends JDialog implements WindowListener {

	private static final long serialVersionUID = 6031882042963823416L;
	
	public static void start(CodeEntry code){
		if(code==null) return;
		try {
			if (MainUI.isEditing())
				return;
			MainUI.setEditing(true);
			ShowQRDialog dialog = new ShowQRDialog(code);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ShowQRDialog(CodeEntry entry){
		super(MainUI.window.frame);
		
		int posX = 100;
		int posY = 100;
		if(MainUI.window!=null){
			Point p = MainUI.window.frame.getLocationOnScreen();
			posX = p.x;
			posY = p.y;
		}
		
		setTitle(MainUI.STD_NAME+" : QR");
		addWindowListener(this);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(posX, posY, 250, 330);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblqr = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, lblqr, 37, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblqr, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblqr, 267, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblqr, 240, SpringLayout.WEST, getContentPane());
		lblqr.setForeground(Color.RED);
		lblqr.setHorizontalAlignment(SwingConstants.CENTER);
		lblqr.setBackground(Color.WHITE);
		getContentPane().add(lblqr);
		
		Image qr = getQR("otpauth://totp/"+entry.getIssuer()+":"+entry.getUser()+
				"?secret="+entry.getSecret().toLowerCase()+"&issuer="+entry.getIssuer(),
				230);
		if(qr!=null){
			lblqr.setIcon(new ImageIcon(qr));
		}
		else{
			lblqr.setText("ERROR! QR-Code image is null!");
		}
		
		JButton btnClose = new JButton("Close");
		springLayout.putConstraint(SpringLayout.NORTH, btnClose, 274, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnClose, 165, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnClose, 294, SpringLayout.NORTH, getContentPane());
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowQRDialog.this.dispose();
			}
		});
		getContentPane().add(btnClose);
		
		JLabel lblName = new JLabel(entry.getIssuer()+" - "+entry.getUser());
		springLayout.putConstraint(SpringLayout.NORTH, lblName, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblName, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblName, 210, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblName);
		
	}
	
	private BufferedImage getQR(String text,int size){
		try {
		  Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
          hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
          QRCodeWriter qrCodeWriter = new QRCodeWriter();
          BitMatrix byteMatrix;
          byteMatrix = qrCodeWriter.encode(text,BarcodeFormat.QR_CODE, size,size, hintMap);
          
          int imageWidth = byteMatrix.getWidth();
          BufferedImage image = new BufferedImage(imageWidth, imageWidth, BufferedImage.TYPE_INT_RGB);
          image.createGraphics();

          Graphics2D graphics = (Graphics2D) image.getGraphics();
          graphics.setColor(Color.WHITE);
          graphics.fillRect(0, 0, imageWidth, imageWidth);
          graphics.setColor(Color.BLACK);
          for (int i = 0; i < imageWidth; i++) {
              for (int j = 0; j < imageWidth; j++) {
                  if (byteMatrix.get(i, j)) {
                      graphics.fillRect(i, j, 1, 1);
                  }
              }
          }
          return image;
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void windowClosed(WindowEvent e) {
		MainUI.setEditing(false);
	}

	//Unused
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {}
}

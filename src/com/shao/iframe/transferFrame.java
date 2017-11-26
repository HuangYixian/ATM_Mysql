package com.shao.iframe;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.shao.DAO.ExecuteSQL;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JTextField;
import javax.swing.JButton;

public class transferFrame extends JFrame {

	private JPanel contentPane;
	private JTextField out_nameField;
	private JTextField out_moneyField;
	//private com.shao.model.user user;
	private com.shao.model.user user_query_deposit;
	private com.shao.model.user user_modMoney_deposit;
	private com.shao.model.user user_query_withdraw;
	private com.shao.model.user user_modMoney_withdraw;
	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					transferFrame frame = new transferFrame(null);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public transferFrame(final String name) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 527, 358);
		setTitle(name);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u8F6C\u8D26\u7528\u6237\u540D\uFF1A");
		lblNewLabel.setFont(new Font("������", Font.BOLD, 20));
		lblNewLabel.setBounds(105, 72, 126, 30);
		contentPane.add(lblNewLabel);
		
		out_nameField = new JTextField();
		out_nameField.setBounds(245, 77, 150, 24);
		contentPane.add(out_nameField);
		out_nameField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\u8F6C\u8D26\u91D1\u989D\uFF1A");
		lblNewLabel_1.setFont(new Font("������", Font.BOLD, 20));
		lblNewLabel_1.setBounds(105, 141, 112, 30);
		contentPane.add(lblNewLabel_1);
		
		out_moneyField = new JTextField();
		out_moneyField.setBounds(243, 146, 152, 24);
		contentPane.add(out_moneyField);
		out_moneyField.setColumns(10);
		
		JButton OKButton = new JButton("\u786E\u8BA4");
		OKButton.setFont(new Font("������", Font.BOLD, 17));
		OKButton.setBounds(104, 225, 113, 27);
		contentPane.add(OKButton);
		OKButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.showConfirmDialog(null, "确认此次转账?", "转账确认", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					user_query_deposit = ExecuteSQL.query(out_nameField.getText());//转出钱
					if (out_nameField.getText().equals(user_query_deposit.getName())) {
						if (Float.parseFloat(out_moneyField.getText()) < 100000) {
							user_query_withdraw = ExecuteSQL.query(name);
							// user_1 = ExecuteSQL.query(out_nameField);
							if (user_query_withdraw.getbalance() > Double.parseDouble(out_moneyField.getText())) {
								double temp = user_query_withdraw.getbalance() - Double.parseDouble(out_moneyField.getText());
								double temp_1 = user_query_deposit.getbalance() + Double.parseDouble(out_moneyField.getText());
								DecimalFormat df = new DecimalFormat("0.00 ");
								int i_withdraw = ExecuteSQL.modifyMoney(name, temp);
								int i_deposit = ExecuteSQL.modifyMoney(out_nameField.getText(), temp_1);
								if(i_withdraw>0 && i_deposit>0){
									setVisible(false);
									atmFrame frame = new atmFrame(name);
									frame.setVisible(true);
									JOptionPane.showMessageDialog(null, "转账交易成功!" + "\n" + "剩余余额为:" + df.format(temp));
								}else{
									JOptionPane.showMessageDialog(null, "转账交易失败!" + "\n" + "剩余余额为:" + df.format(temp));
								}														
							} else {
								JOptionPane.showMessageDialog(null,
										"余额不足,请重新输入!" + "\n" + "当前余额为:" + user_query_withdraw.getbalance());
								out_moneyField.setText("");
							}
						} else {
							JOptionPane.showMessageDialog(null, "转账必须10000,请重新输入!");
							out_moneyField.setText("");
						}
					} else {
						JOptionPane.showMessageDialog(null, "找不到该转账用户!");
						out_nameField.setText("");
					}
				} else {
					return;
				}
			}
		});
		
		JButton backButton_1 = new JButton("\u8FD4\u56DE");
		backButton_1.setFont(new Font("������", Font.BOLD, 18));
		backButton_1.setBounds(282, 225, 113, 27);
		contentPane.add(backButton_1);
	}

}

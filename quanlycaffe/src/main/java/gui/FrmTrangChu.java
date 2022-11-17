package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import entity.Ban;
import entity.NhanVien;
import setting.PathSetting;

/**
 *
 * @author hieud
 */
public class FrmTrangChu extends javax.swing.JFrame {
	private NhanVien nhanVienLogin;

	/**
	 * Creates new form FrmTrangChu
	 * 
	 * @param nv
	 */
	public FrmTrangChu(NhanVien nv) {
		nhanVienLogin = nv;
		initComponents();
		addPanel();
		try {
			addPanelToJpanelMain(new JpnTaiKhoan(nhanVienLogin));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addControls();

	}

	private void addControls() {
		if (nhanVienLogin.getChucVu().equalsIgnoreCase("Nhân viên"))
			enabledBtn(false);
		else
			enabledBtn(true);
		btnNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				if (!btnNhanVien.getBackground().equals(new Color(230, 219, 209)))
					btnNhanVien.setBackground(new Color(210, 210, 210));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {

				if (!btnNhanVien.getBackground().equals(new Color(230, 219, 209))) {
					btnNhanVien.setBackground(new Color(254, 254, 254));
				}

			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					setDefaultButtonColor();
					btnNhanVien.setBackground(new java.awt.Color(230, 219, 209));
					if (!nhanVienLogin.getChucVu().equalsIgnoreCase("Nhân viên"))
						addPanelToJpanelMain(new JpnNhanVien());
					else
						JOptionPane.showMessageDialog(null, "Mục này nhân viên không được vào!!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void addPanelToJpanelMain(JpnNhanVien jpnNhanVien) {
				// TODO Auto-generated method stub
				jpnTrangChu.removeAll();
				jpnTrangChu.setLayout(new BorderLayout());
				jpnTrangChu.add(jpnNhanVien);
				jpnTrangChu.validate();
				jpnTrangChu.repaint();
				jpnTrangChu.updateUI();
			}
		});
		btnBan.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				if (!btnBan.getBackground().equals(new Color(230, 219, 209)))
					btnBan.setBackground(new Color(210, 210, 210));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {

				if (!btnBan.getBackground().equals(new Color(230, 219, 209))) {
					btnBan.setBackground(new Color(254, 254, 254));
				}

			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					setDefaultButtonColor();
					btnBan.setBackground(new java.awt.Color(230, 219, 209));
					addPanelToJpanelMain(new JpnBan());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void addPanelToJpanelMain(JpnBan jpnBan) {
				jpnTrangChu.removeAll();
				jpnTrangChu.setLayout(new BorderLayout());
				jpnTrangChu.add(jpnBan);
				jpnTrangChu.validate();
				jpnTrangChu.repaint();
				jpnTrangChu.updateUI();
			}
		});
		btnHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				if (!btnHoaDon.getBackground().equals(new Color(230, 219, 209)))
					btnHoaDon.setBackground(new Color(210, 210, 210));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {

				if (!btnHoaDon.getBackground().equals(new Color(230, 219, 209))) {
					btnHoaDon.setBackground(new Color(254, 254, 254));
				}

			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					setDefaultButtonColor();
					btnHoaDon.setBackground(new java.awt.Color(230, 219, 209));
					addPanelToJpanelMain(new JpnHoaDon());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void addPanelToJpanelMain(JpnHoaDon jpnHoaDon) {
				jpnTrangChu.removeAll();
				jpnTrangChu.setLayout(new BorderLayout());
				jpnTrangChu.add(jpnHoaDon);
				jpnTrangChu.validate();
				jpnTrangChu.repaint();
				jpnTrangChu.updateUI();
			}
		});
		btnKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				if (!btnKhachHang.getBackground().equals(new Color(230, 219, 209)))
					btnKhachHang.setBackground(new Color(210, 210, 210));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {

				if (!btnKhachHang.getBackground().equals(new Color(230, 219, 209))) {
					btnKhachHang.setBackground(new Color(254, 254, 254));
				}

			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					setDefaultButtonColor();
					btnKhachHang.setBackground(new java.awt.Color(230, 219, 209));
					if (!nhanVienLogin.getChucVu().equalsIgnoreCase("Nhân viên"))
						addPanelToJpanelMain(new JpnKhachHang());
					else
						JOptionPane.showMessageDialog(null, "Mục này nhân viên không được vào!!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void addPanelToJpanelMain(JpnKhachHang jpnKhachHang) {
				jpnTrangChu.removeAll();
				jpnTrangChu.setLayout(new BorderLayout());
				jpnTrangChu.add(jpnKhachHang);
				jpnTrangChu.validate();
				jpnTrangChu.repaint();
				jpnTrangChu.updateUI();
			}
		});
		btnSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				if (!btnSanPham.getBackground().equals(new Color(230, 219, 209)))
					btnSanPham.setBackground(new Color(210, 210, 210));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {

				if (!btnSanPham.getBackground().equals(new Color(230, 219, 209))) {
					btnSanPham.setBackground(new Color(254, 254, 254));
				}

			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					setDefaultButtonColor();
					btnSanPham.setBackground(new java.awt.Color(230, 219, 209));
					addPanelToJpanelMain(new JpnSanPham());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void addPanelToJpanelMain(JpnSanPham jpnSanPham) {
				jpnTrangChu.removeAll();
				jpnTrangChu.setLayout(new BorderLayout());
				jpnTrangChu.add(jpnSanPham);
				jpnTrangChu.validate();
				jpnTrangChu.repaint();
				jpnTrangChu.updateUI();
			}
		});
		btnTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				if (!btnTaiKhoan.getBackground().equals(new Color(230, 219, 209)))
					btnTaiKhoan.setBackground(new Color(210, 210, 210));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {

				if (!btnTaiKhoan.getBackground().equals(new Color(230, 219, 209))) {
					btnTaiKhoan.setBackground(new Color(254, 254, 254));
				}

			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					setDefaultButtonColor();
					btnTaiKhoan.setBackground(new java.awt.Color(230, 219, 209));
					JpnTaiKhoan pnlTaiKhoan = new JpnTaiKhoan(nhanVienLogin);
					addPanelToJpanelMain(pnlTaiKhoan);
					ActionListener listener = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							dispose();
							new FrmDangNhap().setVisible(true);
						}
					};
					pnlTaiKhoan.btnDangXuatAddActionListener(listener);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void addPanelToJpanelMain(JpnTaiKhoan jpnTaiKhoan) {
				jpnTrangChu.removeAll();
				jpnTrangChu.setLayout(new BorderLayout());
				jpnTrangChu.add(jpnTaiKhoan);
				jpnTrangChu.validate();
				jpnTrangChu.repaint();
				jpnTrangChu.updateUI();
			}
		});
		btnThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				if (!btnThongKe.getBackground().equals(new Color(230, 219, 209)))
					btnThongKe.setBackground(new Color(210, 210, 210));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {

				if (!btnThongKe.getBackground().equals(new Color(230, 219, 209))) {
					btnThongKe.setBackground(new Color(254, 254, 254));
				}

			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					setDefaultButtonColor();
					btnThongKe.setBackground(new java.awt.Color(230, 219, 209));
					if (!nhanVienLogin.getChucVu().equalsIgnoreCase("Nhân viên"))
//	 					addPanelToJpanelMain(new JpnNhanVien());
						System.out.println("ok");
					else
						JOptionPane.showMessageDialog(null, "Mục này nhân viên không được vào!!");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				if (!btnTrangChu.getBackground().equals(new Color(230, 219, 209)))
					btnTrangChu.setBackground(new Color(210, 210, 210));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {

				if (!btnTrangChu.getBackground().equals(new Color(230, 219, 209))) {
					btnTrangChu.setBackground(new Color(254, 254, 254));
				}

			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					setDefaultButtonColor();
					btnTrangChu.setBackground(new java.awt.Color(230, 219, 209));
// 					addPanelToJpanelMain(new JpnKhachHang());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void enabledBtn(boolean b) {
		btnNhanVien.setEnabled(b);
		btnKhachHang.setEnabled(b);
		btnThongKe.setEnabled(b);
	}

	private void addPanel() {
		// TODO Auto-generated method stub
//    	btnBan.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	addPanelToJpanelMain(new JpnNhanVien());
//            }
//        });
//		btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	addPanelToJpanelMain(new JpnNhanVien());
//            }
//        });
//		btnKhachHang.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	addPanelToJpanelMain(new JpnNhanVien());
//            }
//        });

//		btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
//            
//        });
//		btnSanPham.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	addPanelToJpanelMain(new JpnNhanVien());
//            }
//        });
//		btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	addPanelToJpanelMain(new JpnNhanVien());
//            }
//        });
//		btnThongKe.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	addPanelToJpanelMain(new JpnNhanVien());
//            }
//        });
//		btnTrangChu.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	addPanelToJpanelMain(new JpnNhanVien());
//            }
//        });

	}

	public FrmTrangChu() {
		// TODO Auto-generated constructor stub
	}

	private void addPanelToJpanelMain(JpnTaiKhoan jpnTaiKhoan) {
		jpnTrangChu.removeAll();
		jpnTrangChu.setLayout(new BorderLayout());
		jpnTrangChu.add(jpnTaiKhoan);
		jpnTrangChu.validate();
		jpnTrangChu.repaint();
		jpnTrangChu.updateUI();
		// TODO Auto-generated method stub

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		btnTrangChu = new javax.swing.JButton();
		btnSanPham = new javax.swing.JButton();
		btnNhanVien = new javax.swing.JButton();
		btnBan = new javax.swing.JButton();
		btnHoaDon = new javax.swing.JButton();
		btnThongKe = new javax.swing.JButton();
		btnKhachHang = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		btnTaiKhoan = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		lbDatePanel = new javax.swing.JLabel();
		lbNVPanel = new javax.swing.JLabel();
		jpnTrangChu = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		jPanel1.setBackground(new java.awt.Color(230, 219, 209));

		jPanel2.setBackground(new java.awt.Color(255, 255, 255));

		jLabel2.setBackground(new java.awt.Color(255, 255, 255));
		jLabel2.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\java_120px.png")); // NOI18N

		btnTrangChu.setBackground(new java.awt.Color(254, 254, 254));
		btnTrangChu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		btnTrangChu.setText("Trang Chủ");
		btnTrangChu.setBorder(null);

		btnSanPham.setBackground(new java.awt.Color(254, 254, 254));
		btnSanPham.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		btnSanPham.setText("Sản Phẩm");
		btnSanPham.setBorder(null);

		btnNhanVien.setBackground(new java.awt.Color(254, 254, 254));
		btnNhanVien.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		btnNhanVien.setText("Nhân Viên");
		btnNhanVien.setBorder(null);
//        btnNhanVien.addMouseListener()

		btnBan.setBackground(new java.awt.Color(254, 254, 254));
		btnBan.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		btnBan.setText("Bàn");
		btnBan.setBorder(null);

		btnHoaDon.setBackground(new java.awt.Color(254, 254, 254));
		btnHoaDon.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		btnHoaDon.setText("Hóa Đơn");
		btnHoaDon.setBorder(null);

		btnThongKe.setBackground(new java.awt.Color(254, 254, 254));
		btnThongKe.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		btnThongKe.setText("Thống Kê");
		btnThongKe.setBorder(null);

		btnKhachHang.setBackground(new java.awt.Color(254, 254, 254));
		btnKhachHang.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		btnKhachHang.setText("Khách Hàng");
		btnKhachHang.setBorder(null);

		jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(204, 0, 204));
		jLabel1.setText("HDH");

		jLabel3.setFont(new java.awt.Font("Times New Roman", 3, 20)); // NOI18N
		jLabel3.setForeground(new java.awt.Color(204, 0, 204));
		jLabel3.setText("- Coffee");

		btnTaiKhoan.setBackground(new java.awt.Color(254, 254, 254));
		btnTaiKhoan.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		btnTaiKhoan.setText("Tài Khoản");
		btnTaiKhoan.setBorder(null);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(btnTrangChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(btnSanPham, javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(jPanel2Layout.createSequentialGroup().addGap(46, 46, 46)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel2Layout.createSequentialGroup()
										.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(jLabel2))
						.addContainerGap(46, Short.MAX_VALUE))
				.addComponent(btnBan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(btnHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(btnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(btnNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(btnKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(btnTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jLabel2).addGap(4, 4, 4)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1).addComponent(jLabel3))
						.addGap(18, 18, 18)
						.addComponent(btnTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, 0)
						.addComponent(btnSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, 0)
						.addComponent(btnBan, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, 0)
						.addComponent(btnHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, 0)
						.addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, 0)
						.addComponent(btnNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, 0)
						.addComponent(btnKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
						.addComponent(btnTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
								javax.swing.GroupLayout.PREFERRED_SIZE)));

		jPanel3.setBackground(new java.awt.Color(255, 255, 255));

		lbDatePanel.setBackground(new java.awt.Color(255, 255, 255));
		lbDatePanel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		lbDatePanel.setText(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));

		lbNVPanel.setBackground(new java.awt.Color(255, 255, 255));
		lbNVPanel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		lbNVPanel.setText(String.format("%s - %s", nhanVienLogin.getTenNhanVien(), nhanVienLogin.getChucVu()));

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel3Layout.createSequentialGroup().addContainerGap(1217, Short.MAX_VALUE)
								.addComponent(lbDatePanel).addGap(24, 24, 24))
				.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel3Layout.createSequentialGroup().addGap(16, 16, 16)
								.addComponent(lbNVPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 420,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(907, Short.MAX_VALUE))));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(lbDatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
				.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
								.addComponent(lbNVPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
								.addContainerGap())));

		jpnTrangChu.setBackground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout jpnTrangChuLayout = new javax.swing.GroupLayout(jpnTrangChu);
		jpnTrangChu.setLayout(jpnTrangChuLayout);
		jpnTrangChuLayout.setHorizontalGroup(jpnTrangChuLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1343, Short.MAX_VALUE));
		jpnTrangChuLayout.setVerticalGroup(jpnTrangChuLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(0, 0, 0)
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(3, 3, 3)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jpnTrangChu, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(0, 0, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(0, 0, 0).addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(3, 3, 3).addComponent(jpnTrangChu, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGap(0, 0, 0)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap().addComponent(jPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		pack();

	}// </editor-fold>//GEN-END:initComponents

	public void setDefaultButtonColor() {
		btnBan.setBackground(new Color(254, 254, 254));
		btnHoaDon.setBackground(new Color(254, 254, 254));
		btnKhachHang.setBackground(new Color(254, 254, 254));
		btnNhanVien.setBackground(new Color(254, 254, 254));
		btnSanPham.setBackground(new Color(254, 254, 254));
		btnTaiKhoan.setBackground(new Color(254, 254, 254));
		btnThongKe.setBackground(new Color(254, 254, 254));
		btnTrangChu.setBackground(new Color(254, 254, 254));

	}

	/**
	 * @param args the command line arguments
	 */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//    
//          
//        
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FrmTrangChu().setVisible(true);
//            }
//        });
//    }

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnBan;
	private javax.swing.JButton btnHoaDon;
	private javax.swing.JButton btnKhachHang;
	private javax.swing.JButton btnNhanVien;
	private javax.swing.JButton btnSanPham;
	private javax.swing.JButton btnTaiKhoan;
	private javax.swing.JButton btnThongKe;
	private javax.swing.JButton btnTrangChu;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jpnTrangChu;
	private javax.swing.JLabel lbDatePanel;
	private javax.swing.JLabel lbNVPanel;
	// End of variables declaration//GEN-END:variables
}

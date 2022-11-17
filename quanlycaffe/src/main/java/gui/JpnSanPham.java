/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dao.LoaiHangDAO;
import dao.MatHangDAO;
import dao.NhanVienDAO;
import entity.DiaChi;
import entity.LoaiHang;
import entity.MatHang;
import entity.NhanVien;
import entity.TaiKhoan;
import setting.PathSetting;
import setting.Regex;
import tablemodel.MatHangTableModel;
import tablemodel.NhanVienTableModel;

/**
 *
 * @author hieud
 */
public class JpnSanPham extends javax.swing.JPanel {

	/**
	 * Creates new form jpnSanPham
	 */
	public JpnSanPham() {
		try {
			matHangDao = new MatHangDAO();
			loaiHangDAO = new LoaiHangDAO();
			initComponents();
			addControls();
			checkIfNoSelectRow();
			clearText();
			setFocus(false);
			updateComboboxLoaiSP();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateComboboxLoaiSP() throws Exception {
		StringBuffer list = new StringBuffer();
		loaiHangDAO.layDanhSachLoaiHang().forEach(x -> list.append(";").append(x.getTenLoaiHang()));

		cmbLoaiSP.setModel(new DefaultComboBoxModel<String>(list.toString().replaceFirst(";", "").split(";")));
		cmbLoaiSP.updateUI();
	}

	private void addControls() throws Exception {
		tfDonGia.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				validteInput(tfDonGia);
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		tfSL.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				validteInput(tfSL);
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		tbMH.setRowHeight(30);
		tbMH.getTableHeader().setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 18));
		dsmh = matHangDao.layDanhSachMatHang();
		updateModelTable(dsmh, HEADERS);

		// table
		tbMH.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String item = (String) tbMH.getModel().getValueAt(tbMH.getSelectedRow(), 0);
//					System.out.println(item);
				if (item != null) {
					MatHang mh = null;
					try {
						mh = matHangDao.timMatHang(item);
//							System.out.println(mh);
						fillThongTinMatHang(mh);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}

			private void fillThongTinMatHang(MatHang mh) throws Exception {
				tfMaSP.setText(mh.getMaMatHang());
				tfTenSP.setText(mh.getTenMatHang());
				tfDonGia.setText(String.valueOf(mh.getDonGia()));
				tfSL.setText(String.valueOf(mh.getSoLuongTon()));
				cmbTrangThai.setSelectedIndex(mh.isTrangThai() ? 0 : 1);
				cmbLoaiSP.setSelectedItem(mh.getLoaiHang().getTenLoaiHang());
				jlbAnh.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SANPHAM + mh.getLinkAnh()));
				jlbAnh.updateUI();
			}
		});
		// xu ly button
		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				try {
					setFocus(true);
					btnThemActionPerformed(evt);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}

			private void btnThemActionPerformed(ActionEvent evt) throws Exception {
				// TODO Auto-generated method stub

				clearText();
				tfMaSP.setText(getNewMaMH());
				pnlChucNangTong.removeAll();
				pnlChucNangTong.setLayout(new BorderLayout());
				PanelXacNhan PanelChucNang = new PanelXacNhan();
				pnlChucNangTong.add(PanelChucNang);
				ActionListener listenerHuy = new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						System.out.println("ok");
						clearText();
						setFocus(false);
						pnlChucNangTong.removeAll();
						pnlChucNangTong.setLayout(new BorderLayout());
						pnlChucNangTong.add(plnChucNang);
						pnlChucNangTong.updateUI();
					}
				};
				ActionListener listenerThem = new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						MatHang mh = null;
						try {
							mh = getFromTF();
							if (mh != null) {
								System.out.println(mh);
//									clearText();
								matHangDao.themMatHang(mh);
//										pnlChucNangTong.removeAll();
//				    					pnlChucNangTong.setLayout(new BorderLayout());
//				    					pnlChucNangTong.add(plnChucNang);
								dsmh = matHangDao.layDanhSachMatHang();
								updateModelTable(dsmh, HEADERS);
								clearText();
								tfMaSP.setText(getNewMaMH());
							}
							pnlChucNangTong.updateUI();
						} catch (Exception e) {

							e.printStackTrace();
						}

					}

					private MatHang getFromTF() throws Exception {
						String maSP = tfMaSP.getText().trim();
						String tenSP = tfTenSP.getText().trim();
						tenSP = Regex.capitalizeName(tenSP);
						int soLuong = 0;
						if (validteInput(tfSL)) {
							soLuong = Integer.parseInt(tfSL.getText());
						}
						double donGia = 0;
						if (validteInput(tfDonGia)) {
							donGia = Double.parseDouble(tfDonGia.getText());
						}
						boolean trangThai = cmbTrangThai.getSelectedItem().toString().equalsIgnoreCase("Còn hàng");
						String loaiSP = cmbLoaiSP.getSelectedItem().toString();
						String imgPath = jlbAnh.getIcon().toString();
						if (imgPath.indexOf(PathSetting.PATH_IMAGE_SANPHAM) != 1) {
							imgPath = imgPath.replace(PathSetting.PATH_IMAGE_SANPHAM, "");
						}

						System.out.println(imgPath);
						System.out.println(PathSetting.PATH_IMAGE_SANPHAM);
						if (!trangThai) {
							tfSL.setText("0");
							tfSL.setEditable(false);
							soLuong = 0;
						} else {
							tfSL.setEditable(true);
						}
						return new MatHang(maSP, tenSP, donGia, soLuong, trangThai,
								loaiHangDAO.timTheoTenLoaiHang(loaiSP), imgPath);

					}
				};
				PanelChucNang.huyAddActionListener(listenerHuy);
				PanelChucNang.xacNhanAddActionListener(listenerThem);

				pnlChucNangTong.repaint();
				pnlChucNangTong.revalidate();
			}

			private String getNewMaMH() throws Exception {
				String maMH = matHangDao.getMaMatHangCaoNhat();
				int maMHTnt = 1;
				if (maMH != null)
					maMHTnt = Integer.parseInt(maMH.replaceAll("\\D+", "")) + 1;
				return String.format("SP%03d", maMHTnt);
			}
		});
		btnThemAnh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser j = new JFileChooser("Image\\SanPham");
				int r = j.showSaveDialog(null);
				if (r == JFileChooser.APPROVE_OPTION)
					jlbAnh.setIcon(new javax.swing.ImageIcon(j.getSelectedFile().getAbsolutePath()));
			}
		});
		btnXoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					String maXoa = (String) tbMH.getModel().getValueAt(tbMH.getSelectedRow(), 0);

					int luaChon = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không", "Xóa",
							JOptionPane.YES_OPTION, JOptionPane.NO_OPTION);
					if (luaChon == JOptionPane.YES_OPTION) {

						boolean ketQua = matHangDao.deleteMatHang(maXoa);
						if (ketQua) {
							dsmh = matHangDao.layDanhSachMatHang();
							updateModelTable(dsmh, HEADERS);
							clearText();

							JOptionPane.showMessageDialog(null, "Xóa thành công");
						} else {
							JOptionPane.showMessageDialog(null, "Xóa thất bại");
							clearText();

						}
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSua.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					setFocus(true);
					btnSuaActionPerformed(evt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			private void btnSuaActionPerformed(ActionEvent evt) throws Exception {

				pnlChucNangTong.removeAll();
				pnlChucNangTong.setLayout(new BorderLayout());
				PanelXacNhan PanelChucNang = new PanelXacNhan();
				pnlChucNangTong.add(PanelChucNang);
				ActionListener listenerHuy = new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						System.out.println("ok");
						clearText();
						setFocus(false);
						pnlChucNangTong.removeAll();
						pnlChucNangTong.setLayout(new BorderLayout());
						pnlChucNangTong.add(plnChucNang);
						pnlChucNangTong.updateUI();
					}
				};
				ActionListener listenerSua = new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						MatHang mh = null;
						try {

							mh = getFromTF();
							if (mh != null) {
								System.out.println(mh);
//									clearText();
								matHangDao.capNhatMatHang(mh);
//										pnlChucNangTong.removeAll();
//				    					pnlChucNangTong.setLayout(new BorderLayout());
//				    					pnlChucNangTong.add(plnChucNang);
								dsmh = matHangDao.layDanhSachMatHang();
								updateModelTable(dsmh, HEADERS);
								JOptionPane.showMessageDialog(null, "Cập nhập thành công!!");

							}
							pnlChucNangTong.updateUI();
						} catch (Exception e) {

							e.printStackTrace();
						}

					}

					private MatHang getFromTF() throws Exception {
						String maSP = tfMaSP.getText().trim();
						String tenSP = tfTenSP.getText().trim();
						String soDienThoai = null;
						tenSP = Regex.capitalizeName(tenSP);
						int soLuong = Integer.parseInt(tfSL.getText());
						double donGia = Double.parseDouble(tfDonGia.getText());
						boolean trangThai = cmbTrangThai.getSelectedItem().toString().equalsIgnoreCase("Còn hàng");
						String loaiSP = cmbLoaiSP.getSelectedItem().toString();
						System.out.println(loaiSP);
						String cv = null;
						String imgPath = jlbAnh.getIcon().toString();
						if (imgPath.indexOf(PathSetting.PATH_IMAGE_SANPHAM) != 1) {
							imgPath = imgPath.replace(PathSetting.PATH_IMAGE_SANPHAM, "");
						}

						System.out.println(imgPath);
						System.out.println(PathSetting.PATH_IMAGE_NHANVIEN);

						return new MatHang(maSP, tenSP, donGia, soLuong, trangThai,
								loaiHangDAO.timTheoTenLoaiHang(loaiSP), imgPath);

					};

				};
				PanelChucNang.huyAddActionListener(listenerHuy);
				PanelChucNang.xacNhanAddActionListener(listenerSua);

				pnlChucNangTong.repaint();
				pnlChucNangTong.revalidate();

			}
		});
		btnXoaTrang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearText();
			}
		});
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

		plnSanPham = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		plnAnh = new javax.swing.JPanel();
		jlbAnh = new javax.swing.JLabel();
		btnThemAnh = new javax.swing.JButton();
		pnlChucNangTong = new javax.swing.JPanel();
		plnChucNang = new javax.swing.JPanel();
		btnThem = new javax.swing.JButton();
		btnXoa = new javax.swing.JButton();
		btnSua = new javax.swing.JButton();
		btnXoaTrang = new javax.swing.JButton();
		btnTimKiem = new javax.swing.JButton();
		jlbTenSP = new javax.swing.JLabel();
		tfTenSP = new javax.swing.JTextField();
		jSeparator1 = new javax.swing.JSeparator();
		jlbDonGia = new javax.swing.JLabel();
		tfDonGia = new javax.swing.JTextField();
		jSeparator2 = new javax.swing.JSeparator();
		jlbSL = new javax.swing.JLabel();
		tfSL = new javax.swing.JTextField();
		jSeparator3 = new javax.swing.JSeparator();
		jlbTrangThai = new javax.swing.JLabel();
		jSeparator4 = new javax.swing.JSeparator();
		jlbLoaiSP = new javax.swing.JLabel();
		jSeparator5 = new javax.swing.JSeparator();
		jSeparator6 = new javax.swing.JSeparator();
		jlbMaSP = new javax.swing.JLabel();
		tfMaSP = new javax.swing.JTextField();
		cmbLoaiSP = new javax.swing.JComboBox<>();
		cmbTrangThai = new javax.swing.JComboBox<>();
		jScrollPane1 = new javax.swing.JScrollPane();
		tbMH = new javax.swing.JTable();

		setBackground(new java.awt.Color(230, 219, 209));

		plnSanPham.setBackground(new java.awt.Color(230, 219, 209));

		jPanel1.setBackground(new java.awt.Color(230, 219, 209));

		jlbAnh.setIcon(new javax.swing.ImageIcon((PathSetting.PATH_IMAGE_SANPHAM + "\\noimage.png"))); // NOI18N

		javax.swing.GroupLayout plnAnhLayout = new javax.swing.GroupLayout(plnAnh);
		plnAnh.setLayout(plnAnhLayout);
		plnAnhLayout.setHorizontalGroup(
				plnAnhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jlbAnh,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		plnAnhLayout.setVerticalGroup(
				plnAnhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jlbAnh,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		btnThemAnh.setBackground(new java.awt.Color(230, 219, 209));
		btnThemAnh.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnThemAnh.setForeground(new java.awt.Color(102, 102, 102));
		btnThemAnh.setText("Thêm Ảnh");
		btnThemAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

		pnlChucNangTong.setBackground(new java.awt.Color(230, 219, 209));

		plnChucNang.setBackground(new java.awt.Color(230, 219, 209));
		plnChucNang.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)), "Chức Năng",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Times New Roman", 0, 13), new java.awt.Color(102, 102, 102))); // NOI18N

		btnThem.setBackground(new java.awt.Color(230, 219, 209));
		btnThem.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnThem.setForeground(new java.awt.Color(102, 102, 102));
		btnThem.setIcon(new javax.swing.ImageIcon((PathSetting.PATH_IMAGE_SYSTEM + "\\Plus_30px.png"))); // NOI18N
		btnThem.setText("Thêm");
		btnThem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

		btnXoa.setBackground(new java.awt.Color(230, 219, 209));
		btnXoa.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnXoa.setForeground(new java.awt.Color(102, 102, 102));
		btnXoa.setIcon(new javax.swing.ImageIcon((PathSetting.PATH_IMAGE_SYSTEM + "\\Recycle Bin_30px.png"))); // NOI18N
		btnXoa.setText("Xóa");
		btnXoa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

		btnSua.setBackground(new java.awt.Color(230, 219, 209));
		btnSua.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnSua.setForeground(new java.awt.Color(102, 102, 102));
		btnSua.setIcon(new javax.swing.ImageIcon((PathSetting.PATH_IMAGE_SYSTEM + "\\recycle_30px.png"))); // NOI18N
		btnSua.setText("Sửa");
		btnSua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

		btnXoaTrang.setBackground(new java.awt.Color(230, 219, 209));
		btnXoaTrang.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnXoaTrang.setForeground(new java.awt.Color(102, 102, 102));
		btnXoaTrang.setIcon(new javax.swing.ImageIcon((PathSetting.PATH_IMAGE_SYSTEM + "\\eraser_30px.png"))); // NOI18N
		btnXoaTrang.setText("Xóa Trắng");
		btnXoaTrang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

		btnTimKiem.setBackground(new java.awt.Color(230, 219, 209));
		btnTimKiem.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnTimKiem.setForeground(new java.awt.Color(102, 102, 102));
		btnTimKiem.setIcon(new javax.swing.ImageIcon((PathSetting.PATH_IMAGE_SYSTEM + "\\search_30px.png"))); // NOI18N
		btnTimKiem.setText("Tìm Kiếm");
		btnTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

		javax.swing.GroupLayout plnChucNangLayout = new javax.swing.GroupLayout(plnChucNang);
		plnChucNang.setLayout(plnChucNangLayout);
		plnChucNangLayout.setHorizontalGroup(plnChucNangLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(plnChucNangLayout.createSequentialGroup().addGap(30, 30, 30)
						.addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
						.addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(62, 62, 62)
						.addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(69, 69, 69)
						.addComponent(btnXoaTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 140,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(66, 66, 66).addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(49, 49, 49)));
		plnChucNangLayout.setVerticalGroup(plnChucNangLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(plnChucNangLayout.createSequentialGroup().addGap(16, 16, 16)
						.addGroup(plnChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnXoaTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(21, Short.MAX_VALUE)));

		javax.swing.GroupLayout pnlChucNangTongLayout = new javax.swing.GroupLayout(pnlChucNangTong);
		pnlChucNangTong.setLayout(pnlChucNangTongLayout);
		pnlChucNangTongLayout
				.setHorizontalGroup(pnlChucNangTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(pnlChucNangTongLayout.createSequentialGroup().addContainerGap().addComponent(
								plnChucNang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		pnlChucNangTongLayout.setVerticalGroup(
				pnlChucNangTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
						plnChucNang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		jlbTenSP.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbTenSP.setText("Tên Sản Phẩm:");

		tfTenSP.setBackground(new java.awt.Color(230, 219, 209));
		tfTenSP.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfTenSP.setText("Coffee - Sữa đá");
		tfTenSP.setBorder(null);

		jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

		jlbDonGia.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbDonGia.setText("Đơn Giá:");

		tfDonGia.setBackground(new java.awt.Color(230, 219, 209));
		tfDonGia.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfDonGia.setText("30.000");
		tfDonGia.setBorder(null);

		jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

		jlbSL.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbSL.setText("Số Lượng:");

		tfSL.setBackground(new java.awt.Color(230, 219, 209));
		tfSL.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfSL.setText("30");
		tfSL.setBorder(null);

		jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

		jlbTrangThai.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbTrangThai.setText("Trạng Thái:");

		jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

		jlbLoaiSP.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbLoaiSP.setText("Loại Sản Phẩm:");

		jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

		jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

		jlbMaSP.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbMaSP.setText("Sản Phẩm:");

		tfMaSP.setEditable(false);
		tfMaSP.setBackground(new java.awt.Color(230, 219, 209));
		tfMaSP.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfMaSP.setText("SP001");
		tfMaSP.setBorder(null);

		cmbLoaiSP.setBackground(new java.awt.Color(230, 219, 209));
		cmbLoaiSP.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		cmbLoaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "Coffee", "Trà sữa", "Hồng trà", "Nước ép", "Sinh tố", "Ăn vặt" }));
		cmbLoaiSP.setBorder(null);

		cmbTrangThai.setBackground(new java.awt.Color(230, 219, 209));
		cmbTrangThai.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		cmbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn hàng", "Đã hết" }));
		cmbTrangThai.setBorder(null);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(pnlChucNangTong, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel1Layout.createSequentialGroup().addGap(25, 25, 25)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jlbMaSP)
														.addGap(18, 18, 18).addComponent(tfMaSP,
																javax.swing.GroupLayout.DEFAULT_SIZE, 223,
																Short.MAX_VALUE))
												.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jlbLoaiSP)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(cmbLoaiSP, 0,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addComponent(jSeparator1).addComponent(jSeparator5)
												.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jlbSL)
														.addGap(18, 18, 18).addComponent(tfSL,
																javax.swing.GroupLayout.PREFERRED_SIZE, 145,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(jSeparator3))
										.addGap(115, 115, 115)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jlbTenSP)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(tfTenSP, javax.swing.GroupLayout.PREFERRED_SIZE,
																200, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(jSeparator4).addComponent(jSeparator2)
												.addGroup(jPanel1Layout.createSequentialGroup()
														.addComponent(jlbTrangThai)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(cmbTrangThai, 0,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jlbDonGia)
														.addGap(18, 18, 18).addComponent(tfDonGia,
																javax.swing.GroupLayout.PREFERRED_SIZE, 200,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(jSeparator6))))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(plnAnh, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnThemAnh, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.PREFERRED_SIZE, 180,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(98, 98, 98)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(18, 18, 18)
						.addComponent(plnAnh, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnThemAnh)
						.addContainerGap(18, Short.MAX_VALUE))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jlbTenSP)
								.addComponent(tfTenSP, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlbMaSP).addComponent(tfMaSP, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(
												jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jlbSL).addComponent(tfSL,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jlbDonGia, javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(tfDonGia, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jlbLoaiSP).addComponent(jlbTrangThai)
										.addComponent(cmbLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(cmbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel1Layout.createSequentialGroup()
												.addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(pnlChucNangTong, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel1Layout.createSequentialGroup()
												.addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(0, 0, Short.MAX_VALUE)))));

		tbMH.setModel(
				new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		jScrollPane1.setViewportView(tbMH);

		javax.swing.GroupLayout plnSanPhamLayout = new javax.swing.GroupLayout(plnSanPham);
		plnSanPham.setLayout(plnSanPhamLayout);
		plnSanPhamLayout.setHorizontalGroup(plnSanPhamLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(plnSanPhamLayout.createSequentialGroup().addContainerGap()
						.addGroup(plnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jScrollPane1))
						.addContainerGap()));
		plnSanPhamLayout
				.setVerticalGroup(plnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(plnSanPhamLayout.createSequentialGroup().addContainerGap()
								.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
								.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(plnSanPham,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(plnSanPham,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	}// </editor-fold>//GEN-END:initComponents

	private void updateModelTable(List<MatHang> dsmh, String[] headers) {
		// TODO Auto-generated method stub
		tableModelMatHang = new MatHangTableModel(headers, dsmh);
		tbMH.setModel(tableModelMatHang);
		tbMH.updateUI();
	}

	private void setFocus(boolean b) {
		btnThemAnh.setVisible(b);
		btnThemAnh.setVisible(b);
		tfTenSP.setEditable(b);
		tfDonGia.setEditable(b);
		tfSL.setEditable(b);
	}

	private void clearText() {
		tfMaSP.setText("");
		tfTenSP.setText("");
		tfDonGia.setText("");
		tfSL.setText("");
		cmbTrangThai.setSelectedIndex(0);
		cmbLoaiSP.setSelectedIndex(0);
		btnXoa.setEnabled(false);
		btnSua.setEnabled(false);
	}

	private boolean validteInput(JTextField tf) {
		String text = tf.getText();
		String pattern = "[\\d+\\s]*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(text);
//		System.out.println(c);
		if (!m.matches()) {
			JOptionPane.showMessageDialog(null, "Vui lòng chỉ nhập số nguyên!!", "Lỗi định dạng",
					JOptionPane.ERROR_MESSAGE);
			tf.setText("");
			tf.requestFocus();
			return false;
		}
		return true;
	}

	private void checkIfNoSelectRow() {
		btnXoa.setEnabled(false);
		btnSua.setEnabled(false);
		ListSelectionModel listSelectionModel = tbMH.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {

			@Override

			public void valueChanged(ListSelectionEvent ev) {
				ListSelectionModel check = (ListSelectionModel) ev.getSource();
				btnXoa.setEnabled(!check.isSelectionEmpty());
				btnSua.setEnabled(!check.isSelectionEmpty());
			}
		});

	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnSua;
	private javax.swing.JButton btnThem;
	private javax.swing.JButton btnThemAnh;
	private javax.swing.JButton btnTimKiem;
	private javax.swing.JButton btnXoa;
	private javax.swing.JButton btnXoaTrang;
	private javax.swing.JComboBox<String> cmbLoaiSP;
	private javax.swing.JComboBox<String> cmbTrangThai;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JSeparator jSeparator3;
	private javax.swing.JSeparator jSeparator4;
	private javax.swing.JSeparator jSeparator5;
	private javax.swing.JSeparator jSeparator6;
	private javax.swing.JTable tbMH;
	private javax.swing.JLabel jlbAnh;
	private javax.swing.JLabel jlbDonGia;
	private javax.swing.JLabel jlbLoaiSP;
	private javax.swing.JLabel jlbMaSP;
	private javax.swing.JLabel jlbSL;
	private javax.swing.JLabel jlbTenSP;
	private javax.swing.JLabel jlbTrangThai;
	private javax.swing.JPanel plnAnh;
	private javax.swing.JPanel plnChucNang;
	private javax.swing.JPanel plnSanPham;
	private javax.swing.JPanel pnlChucNangTong;
	private javax.swing.JTextField tfDonGia;
	private javax.swing.JTextField tfMaSP;
	private javax.swing.JTextField tfSL;
	private javax.swing.JTextField tfTenSP;
	private static final String[] HEADERS = { "Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng tồn", "Trạng thái",
			"Tên loại hàng" };
	private List<MatHang> dsmh;
	private MatHangDAO matHangDao;
	private MatHangTableModel tableModelMatHang;
	private LoaiHangDAO loaiHangDAO;
	// End of variables declaration//GEN-END:variables
}

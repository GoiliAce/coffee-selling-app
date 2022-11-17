/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import setting.Regex;
import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JTextFieldDateEditor;

import dao.DiaChiDAO;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.DiaChi;
import entity.NhanVien;
import entity.TaiKhoan;
import setting.PathSetting;
import tablemodel.NhanVienTableModel;

/**
 *
 * @author hieud
 */
public class JpnNhanVien extends javax.swing.JPanel {

	/**
	 * Creates new form jpnNhanVien
	 */
	public JpnNhanVien() {
		try {
			nhanVienDao = new NhanVienDAO();
			diaChiDao = new DiaChiDAO();
			taiKhoanDao = new TaiKhoanDAO();
			initComponents();
			addControls();
			checkIfNoSelectRow();

			clearText();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addControls() throws Exception {

		setFocus(false);
		IDateEditor dateEditor = jdcNgaySinh.getDateEditor();
		if (dateEditor instanceof JTextFieldDateEditor) {
			JTextFieldDateEditor txtFld = (JTextFieldDateEditor) dateEditor;
			txtFld.setBackground(new java.awt.Color(230, 219, 209));
			txtFld.setEnabled(false);
			txtFld.setBackground(new java.awt.Color(230, 219, 209));
			txtFld.setDisabledTextColor(Color.BLACK);
			txtFld.setBorder(null);
			txtFld.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 20));
		}
		IDateEditor dateEditor1 = jdcNgayVaoLam.getDateEditor();
		if (dateEditor1 instanceof JTextFieldDateEditor) {
			JTextFieldDateEditor txtFld = (JTextFieldDateEditor) dateEditor1;
			txtFld.setBackground(new java.awt.Color(230, 219, 209));
			txtFld.setEnabled(false);
			txtFld.setBackground(new java.awt.Color(230, 219, 209));
			txtFld.setDisabledTextColor(Color.BLACK);
			txtFld.setBorder(null);
			txtFld.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 20));
		}
		tbNV.setRowHeight(30);
		tbNV.setShowGrid(true);
		tbNV.setIntercellSpacing(new java.awt.Dimension(5, 5));
		tbNV.getTableHeader().setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 18));
		dsnv = nhanVienDao.layDanhSachNV();
		updateModelTable(dsnv, HEADERS);
		jScrollPane.setViewportView(tbNV);
		if (tbNV.getColumnModel().getColumnCount() > 0) {
			tbNV.getColumnModel().getColumn(0).setMaxWidth(65);
			tbNV.getColumnModel().getColumn(1).setMaxWidth(220);
			tbNV.getColumnModel().getColumn(2).setMaxWidth(120);
			tbNV.getColumnModel().getColumn(3).setMaxWidth(90);
			tbNV.getColumnModel().getColumn(4).setMaxWidth(120);
			tbNV.getColumnModel().getColumn(5).setMaxWidth(120);
			tbNV.getColumnModel().getColumn(6).setMaxWidth(350);
			tbNV.getColumnModel().getColumn(7).setMaxWidth(110);
			tbNV.getColumnModel().getColumn(8).setMaxWidth(130);
		}

		tbNV.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String item = (String) tbNV.getModel().getValueAt(tbNV.getSelectedRow(), 0);
				if (item != null) {
					NhanVien nv = null;
					try {
						nv = nhanVienDao.timNhanVien(item);
						fillThongTinNhanVien(nv);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}

			private void fillThongTinNhanVien(NhanVien nv) throws Exception {
				// TODO Auto-generated method stub
				tfHoTen.setText(nv.getTenNhanVien());
//				tfDiaChi.setText(nv.getDiaChi());
				tfMaNV.setText(nv.getMaNhanVien());
				tfSDT.setText(nv.getSoDienThoai());
				cmbTinhTP.setSelectedItem(nv.getDiaChi().getTinhTP());
				cmbQuanHuyen.addItem(nv.getDiaChi().getQuanHuyen());
				cmbQuanHuyen.setSelectedItem(nv.getDiaChi().getQuanHuyen());
				cmbPhuongXa.addItem(nv.getDiaChi().getPhuongXa());
				cmbPhuongXa.setSelectedItem(nv.getDiaChi().getPhuongXa());
				cmbChucVu.setSelectedItem(nv.getChucVu());
				cmbGioiTinh.setSelectedItem(nv.isGioiTinh() ? "Nam" : "Nữ");
				jdcNgaySinh.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(nv.getNgaySinh().toString()));
				jdcNgayVaoLam.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(nv.getNgayVaoLam().toString()));
				lbAnh.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_NHANVIEN + nv.getLinkAnhNV())); // NOI18N
				lbAnh.updateUI();
			}
		});
		cmbTinhTP.setBackground(new java.awt.Color(230, 219, 209));
		cmbTinhTP.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		cmbTinhTP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tỉnh, Thành Phố" }));
		cmbTinhTP.setBorder(null);
		cmbTinhTP.setSize(200, cmbTinhTP.getPreferredSize().height);
		updateComboboxTinh();
		cmbQuanHuyen.setBackground(new java.awt.Color(230, 219, 209));
		cmbQuanHuyen.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		cmbQuanHuyen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quận, Huyện" }));
		cmbQuanHuyen.setBorder(null);
		updateComboboxHuyen();
		cmbPhuongXa.setBackground(new java.awt.Color(230, 219, 209));
		cmbPhuongXa.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		cmbPhuongXa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phường, Xã" }));
		cmbPhuongXa.setBorder(null);
		updateComboboxXa();

		cmbTinhTP.addPopupMenuListener(new PopupMenuListener() {

			private boolean stateCmb;

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				JComboBox cmb = (JComboBox) e.getSource();
				// Extend JComboBox
				cmb.setSize(100, cmb.getHeight());
				// If it pops up now JPopupMenu will still be short
				// Fire popupMenuCanceled...
				if (!stateCmb)
					cmb.firePopupMenuCanceled();
				// Reset JComboBox and state
				stateCmb = false;
				cmb.setSize(250, cmb.getHeight());

			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				updateComboboxHuyen();
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub

				JComboBox cmb = (JComboBox) e.getSource();
				stateCmb = true;
				// JPopupMenu is long now, so repop

				cmb.showPopup();
			}
		});
		cmbQuanHuyen.addPopupMenuListener(new PopupMenuListener() {

			private boolean stateCmb;

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				JComboBox cmb = (JComboBox) e.getSource();
				// Extend JComboBox
				cmb.setSize(100, cmb.getHeight());
				// If it pops up now JPopupMenu will still be short
				// Fire popupMenuCanceled...
				if (!stateCmb)
					cmb.firePopupMenuCanceled();
				// Reset JComboBox and state
				stateCmb = false;
				cmb.setSize(250, cmb.getHeight());
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				updateComboboxXa();

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				JComboBox cmb = (JComboBox) e.getSource();

				stateCmb = true;
				// JPopupMenu is long now, so repop
				cmb.showPopup();

			}
		});
		cmbPhuongXa.addPopupMenuListener(new PopupMenuListener() {

			private boolean stateCmb;

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				JComboBox cmb = (JComboBox) e.getSource();
				// Extend JComboBox
				cmb.setSize(100, cmb.getHeight());
				// If it pops up now JPopupMenu will still be short
				// Fire popupMenuCanceled...
				if (!stateCmb)
					cmb.firePopupMenuCanceled();
				// Reset JComboBox and state
				stateCmb = false;
				cmb.setSize(250, cmb.getHeight());
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				JComboBox cmb = (JComboBox) e.getSource();
				stateCmb = true;
				// JPopupMenu is long now, so repop
				cmb.showPopup();

			}
		});
//		xu ly them
		btnThem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					setFocus(true);
					btnThemActionPerformed(evt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			private void btnThemActionPerformed(ActionEvent evt) throws Exception {
				// TODO Auto-generated method stub
				

				clearText();
				tfMaNV.setText(getNewMaNV());
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
						NhanVien nv = null;
						try {
							nv = getFromTF();
							if (nv != null) {
								System.out.println(nv);
//								clearText();
								nhanVienDao.themNhanVien(nv);
//									pnlChucNangTong.removeAll();
//			    					pnlChucNangTong.setLayout(new BorderLayout());
//			    					pnlChucNangTong.add(plnChucNang);
								dsnv = nhanVienDao.layDanhSachNV();
								updateModelTable(dsnv, HEADERS);
								clearText();
								tfMaNV.setText(getNewMaNV());
							}
							pnlChucNangTong.updateUI();
						} catch (Exception e) {
							try {
								taiKhoanDao.deleteTaiKhoan(nv.getTaiKhoan().getTenTaiKhoan());
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							e.printStackTrace();
						}

					}

					private NhanVien getFromTF() throws Exception {
						String maNV = tfMaNV.getText().trim();
						String tenNV = null;
						String soDienThoai = null;
						if (!Regex.checkRegex(tfHoTen.getText().trim(), "Tên")) {
							JOptionPane.showMessageDialog(null, "Sai định dạng tên");
							return null;
						}
						if (!Regex.checkRegex(tfSDT.getText().trim(), "SDT")) {
							JOptionPane.showMessageDialog(null, "Sai định dạng số điện thoại");
							return null;
						}
						tenNV =Regex.capitalizeName(tfHoTen.getText().trim());
						Date ngaySinh = null;
						try {
							jdcNgaySinh.setDateFormatString("MMM d, y");
							ngaySinh = jdcNgaySinh.getDate();
							jdcNgaySinh.setDateFormatString("dd/MM/YYYY");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						Date ngayVaoLam = null;
						try {
							jdcNgayVaoLam.setDateFormatString("MMM d, y");
							ngayVaoLam = jdcNgayVaoLam.getDate();
							jdcNgayVaoLam.setDateFormatString("dd/MM/YYYY");
						} catch (Exception e) {
							e.printStackTrace();
						}
						boolean gioiTinh = cmbGioiTinh.getSelectedItem().toString() == "Nam" ? true : false;
						String chucVu = cmbChucVu.getSelectedItem().toString();
						soDienThoai = tfSDT.getText().trim();
						DiaChi dc = diaChiDao.getDiaChiTheoTinhHuyenXa(cmbTinhTP.getSelectedItem().toString(),
								cmbQuanHuyen.getSelectedItem().toString(), cmbPhuongXa.getSelectedItem().toString());
						String cv = null;
						String imgPath = lbAnh.getIcon().toString();
						if (imgPath.indexOf(PathSetting.PATH_IMAGE_NHANVIEN) != 1) {
							imgPath = imgPath.replace(PathSetting.PATH_IMAGE_NHANVIEN, "");
						}
						System.out.println(imgPath);
						System.out.println(PathSetting.PATH_IMAGE_NHANVIEN);
						int taiKhoanInt = 1;
						cv = chucVu.equalsIgnoreCase("Nhân Viên") ? "NV" : "QL";
						String tks = taiKhoanDao.getTenTaiKhoanCaoNhat();
						if (tks != null)
							taiKhoanInt = Integer.parseInt(tks.replaceAll("\\D+", "")) + 1;

						String maTK = String.format("TKNV%03d", taiKhoanInt);
						System.out.println(maTK);
						TaiKhoan tk = new TaiKhoan(maTK, "1111");
						return new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam, soDienThoai, chucVu, dc, tk,
								imgPath);

					}
				};
				PanelChucNang.huyAddActionListener(listenerHuy);
				PanelChucNang.xacNhanAddActionListener(listenerThem);

				pnlChucNangTong.repaint();
				pnlChucNangTong.revalidate();

			}

			private String getNewMaNV() throws Exception {
				String maNV = nhanVienDao.getMaNhanVienCaoNhat();
				int maNVTnt = 1;
				if (maNV != null)
					maNVTnt = Integer.parseInt(maNV.replaceAll("\\D+", "")) + 1;
				return String.format("NV%03d", maNVTnt);
			}
		});
		btnThemAnh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser j = new JFileChooser("Image\\NhanVien");
				int r = j.showSaveDialog(null);
				if (r == JFileChooser.APPROVE_OPTION)
					lbAnh.setIcon(new javax.swing.ImageIcon(j.getSelectedFile().getAbsolutePath()));
			}
		});
		// xu ly xóa
		btnXoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					String maXoa = (String) tbNV.getModel().getValueAt(tbNV.getSelectedRow(), 0);

					int luaChon = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không", "Xóa",
							JOptionPane.YES_OPTION, JOptionPane.NO_OPTION);
					if (luaChon == JOptionPane.YES_OPTION) {

						boolean ketQua = nhanVienDao.deleteNhanVien(maXoa);
						if (ketQua) {
							dsnv = nhanVienDao.layDanhSachNV();
							updateModelTable(dsnv, HEADERS);
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
						NhanVien nv = null;
						try {

							nv = getFromTF();
							if (nv != null) {
								System.out.println(nv);
//								clearText();
								nhanVienDao.capNhatNhanVien(nv);
//									pnlChucNangTong.removeAll();
//			    					pnlChucNangTong.setLayout(new BorderLayout());
//			    					pnlChucNangTong.add(plnChucNang);
								dsnv = nhanVienDao.layDanhSachNV();
								updateModelTable(dsnv, HEADERS);
								JOptionPane.showMessageDialog(null, "Cập nhập thành công!!");

							}
							pnlChucNangTong.updateUI();
						} catch (Exception e) {

							e.printStackTrace();
						}

					}

					private NhanVien getFromTF() throws Exception {
						String maNV = tfMaNV.getText().trim();
						String tenNV = null;
						String soDienThoai = null;
						if (!Regex.checkRegex(tfHoTen.getText().trim(), "Tên")) {
							JOptionPane.showMessageDialog(btnThem, "Sai định dạng tên");
							return null;
						}
						if (!Regex.checkRegex(tfSDT.getText().trim(), "SDT")) {
							JOptionPane.showMessageDialog(btnThem, "Sai định dạng số điện thoại");
							return null;
						}
						tenNV =Regex.capitalizeName(tfHoTen.getText().trim());

						Date ngaySinh = null;
						try {
							jdcNgaySinh.setDateFormatString("MMM d, y");
							ngaySinh = jdcNgaySinh.getDate();
							jdcNgaySinh.setDateFormatString("dd/MM/YYYY");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						Date ngayVaoLam = null;
						try {
							jdcNgayVaoLam.setDateFormatString("MMM d, y");
							ngayVaoLam = jdcNgayVaoLam.getDate();
							jdcNgayVaoLam.setDateFormatString("dd/MM/YYYY");
						} catch (Exception e) {
							e.printStackTrace();
						}
						boolean gioiTinh = cmbGioiTinh.getSelectedItem().toString() == "Nam" ? true : false;
						String chucVu = cmbChucVu.getSelectedItem().toString();
						soDienThoai = tfSDT.getText().trim();
						DiaChi dc = diaChiDao.getDiaChiTheoTinhHuyenXa(cmbTinhTP.getSelectedItem().toString(),
								cmbQuanHuyen.getSelectedItem().toString(), cmbPhuongXa.getSelectedItem().toString());
						String cv = null;
						String imgPath = lbAnh.getIcon().toString();
						if (imgPath.indexOf(PathSetting.PATH_IMAGE_NHANVIEN) != 1) {
							imgPath = imgPath.replace(PathSetting.PATH_IMAGE_NHANVIEN, "");
						}
						System.out.println(imgPath);
						TaiKhoan tkn = nhanVienDao.timNhanVien(tfMaNV.getText().trim()).getTaiKhoan();

						return new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam, soDienThoai, chucVu, dc, tkn,
								imgPath);

					}
				};
				PanelChucNang.huyAddActionListener(listenerHuy);
				PanelChucNang.xacNhanAddActionListener(listenerSua);

				pnlChucNangTong.repaint();
				pnlChucNangTong.revalidate();

			}
		});

		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					
					timKiemActionPerformed(evt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void timKiemActionPerformed(ActionEvent evt) {
				try {
					tfMaNV.setEditable(true);
					setFocus(true);
					btnThemAnh.setVisible(false);
					PanelXacNhan panelXacNhan = new PanelXacNhan();
					pnlChucNangTong.removeAll();
					pnlChucNangTong.setLayout(new BorderLayout());
					cmbGioiTinh.addItem("All");
					cmbChucVu.addItem("All");
					pnlChucNangTong.add(panelXacNhan);
					pnlChucNangTong.repaint();
					pnlChucNangTong.validate();
					ActionListener listenerHuy = new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								dsnv = nhanVienDao.layDanhSachNV();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							cmbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ"}));
							cmbChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân Viên", "Quản Lý" }));
							updateModelTable(dsnv, HEADERS);
							clearText();
							setFocus(false);
							btnThemAnh.setVisible(false);
							pnlChucNangTong.removeAll();
							pnlChucNangTong.setLayout(new BorderLayout());
							pnlChucNangTong.add(plnChucNang);
							pnlChucNangTong.updateUI();

						}
					};
					panelXacNhan.huyAddActionListener(listenerHuy);
					ActionListener listenerThem = new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								dsnv = getFromTF();
								updateModelTable(dsnv, HEADERS);

							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					};
					panelXacNhan.xacNhanAddActionListener(listenerThem);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private List<NhanVien> getFromTF() throws Exception {
				String maNV = tfMaNV.getText().isEmpty() ? null : tfMaNV.getText().trim();
				String tenNV = tfHoTen.getText().trim().isEmpty() ? null : tfHoTen.getText().trim();
				String soDienThoai = tfSDT.getText().isEmpty() ? null : tfSDT.getText().trim();

				
				Date ngaySinh = null;
				try {
					jdcNgaySinh.setDateFormatString("MMM d, y");
					ngaySinh = jdcNgaySinh.getDate();
					jdcNgaySinh.setDateFormatString("dd/MM/YYYY");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Date ngayVaoLam = null;
				try {
					jdcNgayVaoLam.setDateFormatString("MMM d, y");
					ngayVaoLam = jdcNgayVaoLam.getDate();
					jdcNgayVaoLam.setDateFormatString("dd/MM/YYYY");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String gioiTinh = cmbGioiTinh.getSelectedItem().toString().trim().equalsIgnoreCase("All") ? null
						: cmbGioiTinh.getSelectedItem().toString();
				String chucVu = cmbChucVu.getSelectedItem().toString().trim().equalsIgnoreCase("All") ? null
						: cmbChucVu.getSelectedItem().toString();

//				DiaChi dc = diaChiDao.getDiaChiTheoTinhHuyenXa(cmbTinhTP.getSelectedItem().toString(),
//						cmbQuanHuyen.getSelectedItem().toString(), cmbPhuongXa.getSelectedItem().toString());
				return nhanVienDao.timKiemNhanVienTheoNhieuThuocTinh(maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam,
						soDienThoai, chucVu);

			}
		});
	}

	private void setFocus(boolean b) {
		btnThemAnh.setVisible(b);
		tfHoTen.setEditable(b);
		tfSDT.setEditable(b);
	}

	private void checkIfNoSelectRow() {
		btnXoa.setEnabled(false);
		btnSua.setEnabled(false);
		ListSelectionModel listSelectionModel = tbNV.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {

			@Override

			public void valueChanged(ListSelectionEvent ev) {
				ListSelectionModel check = (ListSelectionModel) ev.getSource();
				btnXoa.setEnabled(!check.isSelectionEmpty());
				btnSua.setEnabled(!check.isSelectionEmpty());
			}
		});

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() throws Exception {

		plnNhanVien = new javax.swing.JPanel();
		plnNV1 = new javax.swing.JPanel();
		jlbMaNV = new javax.swing.JLabel();
		tfMaNV = new javax.swing.JTextField();
		jSeparator1 = new javax.swing.JSeparator();
		jlbHoten = new javax.swing.JLabel();
		tfHoTen = new javax.swing.JTextField();
		jSeparator2 = new javax.swing.JSeparator();
		jlbNgaySinh = new javax.swing.JLabel();
		jSeparator3 = new javax.swing.JSeparator();
		jlbNgayVaoLam = new javax.swing.JLabel();
		jSeparator4 = new javax.swing.JSeparator();
		jlbDiaChi = new javax.swing.JLabel();
		tfSDT = new javax.swing.JTextField();
		jSeparator6 = new javax.swing.JSeparator();
		jlbChucVu = new javax.swing.JLabel();
		cmbChucVu = new javax.swing.JComboBox<>();
		plnAnh = new javax.swing.JPanel();
		lbAnh = new javax.swing.JLabel();
		btnThemAnh = new javax.swing.JButton();
		jlbGioiTinh = new javax.swing.JLabel();
		cmbGioiTinh = new javax.swing.JComboBox<>();
		jSeparator8 = new javax.swing.JSeparator();
		jlbSDT = new javax.swing.JLabel();
		jSeparator5 = new javax.swing.JSeparator();
		jdcNgaySinh = new com.toedter.calendar.JDateChooser();
		jdcNgayVaoLam = new com.toedter.calendar.JDateChooser();
		jScrollPane = new javax.swing.JScrollPane();
		tbNV = new javax.swing.JTable();
		cmbPhuongXa = new javax.swing.JComboBox<>();
		cmbQuanHuyen = new javax.swing.JComboBox<>();
		cmbTinhTP = new javax.swing.JComboBox();
		pnlChucNangTong = new javax.swing.JPanel();
		plnChucNang = new javax.swing.JPanel();
		btnThem = new javax.swing.JButton();
		btnXoa = new javax.swing.JButton();
		btnSua = new javax.swing.JButton();
		btnXoaRong = new javax.swing.JButton();
		btnTimKiem = new javax.swing.JButton();
		jSeparator9 = new javax.swing.JSeparator();

		setPreferredSize(new java.awt.Dimension(1340, 750));

		plnNhanVien.setBackground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout plnNhanVienLayout = new javax.swing.GroupLayout(plnNhanVien);
		plnNhanVien.setLayout(plnNhanVienLayout);
		plnNhanVienLayout.setHorizontalGroup(plnNhanVienLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1, Short.MAX_VALUE));
		plnNhanVienLayout.setVerticalGroup(plnNhanVienLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 735, Short.MAX_VALUE));

		plnNV1.setBackground(new java.awt.Color(230, 219, 209));

		jlbMaNV.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbMaNV.setText("Mã Nhân Viên:");

		tfMaNV.setEditable(false);
		tfMaNV.setBackground(new java.awt.Color(230, 219, 209));
		tfMaNV.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfMaNV.setText("NV01");
		tfMaNV.setBorder(null);

		jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

		jlbHoten.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbHoten.setText("Họ Và Tên:");

		tfHoTen.setBackground(new java.awt.Color(230, 219, 209));
		tfHoTen.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfHoTen.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		tfHoTen.setText("Cao Nguyễn Gia Hưng");
		tfHoTen.setBorder(null);

		jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

		jlbNgaySinh.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbNgaySinh.setText("Ngày Sinh:");

		jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

		jlbNgayVaoLam.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbNgayVaoLam.setText("Ngày Vào Làm:");

		jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

		jlbDiaChi.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbDiaChi.setText("Địa Chỉ:");

		tfSDT.setBackground(new java.awt.Color(230, 219, 209));
		tfSDT.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfSDT.setText("0347825051");
		tfSDT.setBorder(null);
		tfSDT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfSDTActionPerformed(evt);
			}
		});

		jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

		jlbChucVu.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbChucVu.setText("Chức Vụ:");

		cmbChucVu.setBackground(new java.awt.Color(230, 219, 209));
		cmbChucVu.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		cmbChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân Viên", "Quản Lý" }));
		cmbChucVu.setBorder(null);

//		lbAnh.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\imgNhanVien/noimage.png"))); // NOI18N

		javax.swing.GroupLayout plnAnhLayout = new javax.swing.GroupLayout(plnAnh);
		plnAnh.setLayout(plnAnhLayout);
		plnAnhLayout.setHorizontalGroup(
				plnAnhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lbAnh,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		plnAnhLayout.setVerticalGroup(
				plnAnhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lbAnh,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		btnThemAnh.setBackground(new java.awt.Color(230, 219, 209));
		btnThemAnh.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		btnThemAnh.setForeground(new java.awt.Color(10, 10, 10));
		btnThemAnh.setText("Thêm Ảnh");
		btnThemAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(10, 10, 10)));

		jlbGioiTinh.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbGioiTinh.setText("Giới Tính:");

		cmbGioiTinh.setBackground(new java.awt.Color(230, 219, 209));
		cmbGioiTinh.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		cmbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ"}));
		cmbGioiTinh.setBorder(null);

		jSeparator8.setForeground(new java.awt.Color(0, 0, 0));

		jlbSDT.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbSDT.setText("Số Điện Thoại:");

		jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

		jdcNgaySinh.setDateFormatString("dd/MM/YYYY");

		jdcNgayVaoLam.setBackground(new java.awt.Color(255, 255, 255));
		jdcNgayVaoLam.setDateFormatString("dd/MM/YYYY");

		tbNV.setAutoCreateRowSorter(true);
		tbNV.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N

		pnlChucNangTong.setBackground(new java.awt.Color(230, 219, 209));

		plnChucNang.setBackground(new java.awt.Color(230, 219, 209));
		plnChucNang.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chức Năng",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Times New Roman", 0, 13))); // NOI18N

		btnThem.setBackground(new java.awt.Color(230, 219, 209));
		btnThem.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnThem.setForeground(new java.awt.Color(10, 10, 10));
		btnThem.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\Plus_30px.png")); // NOI18N
		btnThem.setText("Thêm");
		btnThem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(10, 10, 10)));

		btnXoa.setBackground(new java.awt.Color(230, 219, 209));
		btnXoa.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnXoa.setForeground(new java.awt.Color(10, 10, 10));
		btnXoa.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\Recycle Bin_30px.png")); // NOI18N
		btnXoa.setText("Xóa");
		btnXoa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(10, 10, 10)));

		btnSua.setBackground(new java.awt.Color(230, 219, 209));
		btnSua.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnSua.setForeground(new java.awt.Color(10, 10, 10));
		btnSua.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\recycle_30px.png")); // NOI18N
		btnSua.setText("Sửa");
		btnSua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(10, 10, 10)));

		btnXoaRong.setBackground(new java.awt.Color(230, 219, 209));
		btnXoaRong.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnXoaRong.setForeground(new java.awt.Color(10, 10, 10));
		btnXoaRong.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\eraser_30px.png")); // NOI18N
		btnXoaRong.setText("Xóa Trắng");
		btnXoaRong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(10, 10, 10)));
		btnXoaRong.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXoaTrangnActionPerformed(evt);
			}

			private void btnXoaTrangnActionPerformed(ActionEvent evt) {
				clearText();
			}
		});
		btnTimKiem.setBackground(new java.awt.Color(230, 219, 209));
		btnTimKiem.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnTimKiem.setForeground(new java.awt.Color(10, 10, 10));
		btnTimKiem.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\search_30px.png")); // NOI18N
		btnTimKiem.setText("Tìm Kiếm");
		btnTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(10, 10, 10)));

		javax.swing.GroupLayout plnChucNangLayout = new javax.swing.GroupLayout(plnChucNang);
		plnChucNang.setLayout(plnChucNangLayout);
		plnChucNangLayout
				.setHorizontalGroup(
						plnChucNangLayout
								.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(plnChucNangLayout.createSequentialGroup().addGap(30, 30, 30)
										.addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(73, 73, 73)
										.addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53,
												Short.MAX_VALUE)
										.addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(66, 66, 66)
										.addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 140,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(67, 67, 67)
										.addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
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
								.addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(21, Short.MAX_VALUE)));

		javax.swing.GroupLayout pnlChucNangTongLayout = new javax.swing.GroupLayout(pnlChucNangTong);
		pnlChucNangTong.setLayout(pnlChucNangTongLayout);
		pnlChucNangTongLayout.setHorizontalGroup(pnlChucNangTongLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlChucNangTongLayout.createSequentialGroup().addContainerGap().addComponent(plnChucNang,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		pnlChucNangTongLayout.setVerticalGroup(
				pnlChucNangTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
						plnChucNang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		jSeparator9.setForeground(new java.awt.Color(0, 0, 0));

		javax.swing.GroupLayout plnNV1Layout = new javax.swing.GroupLayout(plnNV1);
		plnNV1.setLayout(plnNV1Layout);
		plnNV1Layout.setHorizontalGroup(plnNV1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(plnNV1Layout.createSequentialGroup().addGroup(plnNV1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(plnNV1Layout.createSequentialGroup().addContainerGap().addGroup(plnNV1Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING, plnNV1Layout
										.createSequentialGroup().addComponent(jlbMaNV)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(tfMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 265,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
										plnNV1Layout.createSequentialGroup().addComponent(jlbNgaySinh)
												.addGap(60, 60, 60).addComponent(jdcNgaySinh,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jSeparator1)
								.addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING, plnNV1Layout
										.createSequentialGroup().addComponent(jlbSDT)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41,
												Short.MAX_VALUE)
										.addComponent(tfSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 265,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
										plnNV1Layout.createSequentialGroup().addComponent(jlbNgayVaoLam)
												.addGap(24, 24, 24).addComponent(jdcNgayVaoLam,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addGap(121, 121, 121)
								.addGroup(plnNV1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addGroup(plnNV1Layout.createSequentialGroup().addGroup(plnNV1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 369,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(plnNV1Layout.createSequentialGroup().addComponent(jlbHoten)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(tfHoTen, javax.swing.GroupLayout.PREFERRED_SIZE,
																265, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(plnNV1Layout.createSequentialGroup().addComponent(jlbGioiTinh)
														.addGap(18, 18, 18).addComponent(cmbGioiTinh,
																javax.swing.GroupLayout.PREFERRED_SIZE, 160,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGap(53, 53, 53))
										.addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 422,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(javax.swing.GroupLayout.Alignment.LEADING, plnNV1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(javax.swing.GroupLayout.Alignment.LEADING, plnNV1Layout
														.createSequentialGroup().addComponent(jlbChucVu)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27,
																Short.MAX_VALUE)
														.addComponent(cmbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE,
																160, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.LEADING))
										.addGroup(javax.swing.GroupLayout.Alignment.LEADING, plnNV1Layout
												.createSequentialGroup().addComponent(jlbDiaChi)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(cmbTinhTP, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(cmbQuanHuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(cmbPhuongXa, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addGroup(plnNV1Layout.createSequentialGroup()
								.addComponent(pnlChucNangTong, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(plnNV1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(plnNV1Layout.createSequentialGroup().addGap(97, 97, 97)
										.addComponent(plnAnh, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(81, 81, 81))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										plnNV1Layout.createSequentialGroup()
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(btnThemAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(119, 119, 119))))
				.addGroup(plnNV1Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane)
						.addContainerGap()));
		plnNV1Layout.setVerticalGroup(plnNV1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(plnNV1Layout.createSequentialGroup().addGap(6, 6, 6).addGroup(plnNV1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addGroup(plnNV1Layout.createSequentialGroup()
								.addComponent(plnAnh, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(btnThemAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(32, 32, 32))
						.addGroup(
								plnNV1Layout.createSequentialGroup()
										.addGroup(plnNV1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(plnNV1Layout.createSequentialGroup().addGroup(plnNV1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jlbHoten)
														.addComponent(tfHoTen, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(
																jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(plnNV1Layout.createSequentialGroup().addGroup(plnNV1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(tfMaNV, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jlbMaNV))
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(jSeparator1,
																javax.swing.GroupLayout.PREFERRED_SIZE, 10,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(plnNV1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jdcNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jlbNgaySinh)
												.addGroup(plnNV1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jlbGioiTinh).addComponent(cmbGioiTinh,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(plnNV1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(plnNV1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(plnNV1Layout.createSequentialGroup().addGap(10, 10, 10)
														.addGroup(plnNV1Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(jlbChucVu)
																.addComponent(cmbChucVu,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGroup(plnNV1Layout.createSequentialGroup()
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(plnNV1Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(jdcNgayVaoLam,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(jlbNgayVaoLam,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 28,
																		javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(plnNV1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.PREFERRED_SIZE, 10,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(plnNV1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(plnNV1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jlbDiaChi)
														.addComponent(cmbPhuongXa,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(cmbQuanHuyen,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(cmbTinhTP, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(plnNV1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jlbSDT).addComponent(tfSDT,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(plnNV1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(pnlChucNangTong, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(6, 6, 6)))
						.addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
						.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(0, 0, 0)
						.addComponent(plnNV1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, 0).addComponent(plnNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(0, 0, 0)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addGap(0, 15, Short.MAX_VALUE).addComponent(plnNhanVien,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(plnNV1, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));
	}// </editor-fold>//GEN-END:initComponents

	private void tfSDTActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tfSDTActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_tfSDTActionPerformed

	private void updateModelTable(List<NhanVien> dsnv, String[] headers) {
		// TODO Auto-generated method stub
		tableModelNhanVien = new NhanVienTableModel(headers, dsnv);
		tbNV.setModel(tableModelNhanVien);
		tbNV.updateUI();
	}

	private void clearText() {
		tfHoTen.setText("");
//		tfDiaChi.setText("");
		tfMaNV.setText("");
		tfSDT.setText("");
		cmbChucVu.setSelectedItem("Nhân Viên");
		cmbGioiTinh.setSelectedItem("Nam");
		updateComboboxTinh();
		updateComboboxHuyen();
		updateComboboxXa();
		jdcNgaySinh.setDate(null);
		jdcNgayVaoLam.setDate(null);
		lbAnh.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_NHANVIEN + "noimage.png")); // NOI18N
		lbAnh.updateUI();
		btnXoa.setEnabled(false);
		btnSua.setEnabled(false);
//		tbNV.getCellEditor().stopCellEditing();
	}

	private void updateComboboxTinh() {
		List<String> list = diaChiDao.layDanhSachTinh();

		String[] dsString = new String[list.size()];
		list.toArray(dsString);
		cmbTinhTP.setModel(new DefaultComboBoxModel<String>(dsString));
		cmbTinhTP.updateUI();
	}

	private void updateComboboxXa() {
		// TODO Auto-generated method stub
		String tinh = (String) cmbTinhTP.getSelectedItem();
		String huyen = (String) cmbQuanHuyen.getSelectedItem();
		System.out.println(huyen);
		List<String> list = diaChiDao.layDSXaTheoHuyenTinh(tinh, huyen);
//		list.forEach(x->System.out.println(x));
		String[] dsString = new String[list.size()];
		list.toArray(dsString);
		cmbPhuongXa.setModel(new DefaultComboBoxModel<String>(dsString));
		cmbPhuongXa.updateUI();
	}

	private void updateComboboxHuyen() {
		String tinh = (String) cmbTinhTP.getSelectedItem();
		List<String> list = diaChiDao.layDSHuyenTheoTinh(tinh);
		String[] dsString = new String[list.size()];
		list.toArray(dsString);
		cmbQuanHuyen.setModel(new DefaultComboBoxModel<String>(dsString));
		cmbQuanHuyen.updateUI();
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnSua;
	private javax.swing.JButton btnThem;
	private javax.swing.JButton btnThemAnh;
	private javax.swing.JButton btnTimKiem;
	private javax.swing.JButton btnXoa;
	private javax.swing.JButton btnXoaRong;
	private javax.swing.JComboBox<String> cmbChucVu;
	private javax.swing.JComboBox<String> cmbGioiTinh;
	private javax.swing.JComboBox<String> cmbPhuongXa;
	private javax.swing.JComboBox<String> cmbQuanHuyen;
	private javax.swing.JComboBox<String> cmbTinhTP;
	private javax.swing.JScrollPane jScrollPane;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JSeparator jSeparator3;
	private javax.swing.JSeparator jSeparator4;
	private javax.swing.JSeparator jSeparator5;
	private javax.swing.JSeparator jSeparator6;
	private javax.swing.JSeparator jSeparator8;
	private javax.swing.JSeparator jSeparator9;
	private javax.swing.JTable tbNV;
	private com.toedter.calendar.JDateChooser jdcNgaySinh;
	private com.toedter.calendar.JDateChooser jdcNgayVaoLam;
	private javax.swing.JLabel jlbChucVu;
	private javax.swing.JLabel jlbDiaChi;
	private javax.swing.JLabel jlbGioiTinh;
	private javax.swing.JLabel jlbHoten;
	private javax.swing.JLabel jlbMaNV;
	private javax.swing.JLabel jlbNgaySinh;
	private javax.swing.JLabel jlbNgayVaoLam;
	private javax.swing.JLabel jlbSDT;
	private javax.swing.JLabel lbAnh;
	private javax.swing.JPanel plnAnh;
	private javax.swing.JPanel plnChucNang;
	private javax.swing.JPanel plnNV1;
	private javax.swing.JPanel plnNhanVien;
	private javax.swing.JPanel pnlChucNangTong;
	private javax.swing.JTextField tfHoTen;
	private javax.swing.JTextField tfMaNV;
	private javax.swing.JTextField tfSDT;
	private static final String[] HEADERS = { "Mã", "Họ Và Tên", "Ngày Sinh", "Giới Tính", "Ngày Vào Làm",
			"Số Điện Thoại", "Địa Chỉ", "Chức Vụ", "Lương" };
	private List<NhanVien> dsnv;
	private NhanVienDAO nhanVienDao;
	private NhanVienTableModel tableModelNhanVien;
	private DiaChiDAO diaChiDao;
	private TaiKhoanDAO taiKhoanDao;
//	private final String PATHIMGNHANVIENNULL = "file:/";
//	private final String PATHIMAGENHANVIEN = "D:\\LTHSK\\quanlycaffe\\quanlycaffe\\Image\\NhanVien\\";
}

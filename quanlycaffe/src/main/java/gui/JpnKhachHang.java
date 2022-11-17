package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JTextFieldDateEditor;

import dao.KhachHangDAO;
import entity.DiaChi;
import entity.KhachHang;
import entity.NhanVien;
import entity.TaiKhoan;
import setting.PathSetting;
import setting.Regex;
import tablemodel.KhachHangTableModel;

public class JpnKhachHang extends javax.swing.JPanel {

	private static final String[] HEADERS = "Mã;Họ Tên;Giới Tính;Ngày Sinh;Số Điện Thoại;Điểm;Hạng".split(";");
	private List<KhachHang> dskh;
	private KhachHangDAO khachHangDao;
	private KhachHangTableModel modelTableKhachHang;

	/**
	 * Creates new form jpnKhachHang
	 */
	public JpnKhachHang() {
		khachHangDao = new KhachHangDAO();
		try {
			initComponents();
			clearText();
			addControls();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearText() {
		tfMaKH.setText("");
		tfHoTen.setText("");
		jcbGioiTinh.setSelectedItem("Nam");
		tfDiemTL.setText("");
		tfHang.setText("");
		tfSDT.setText("");
		jdcNgaySinhKhachHang.setDate(null);
	}

	private void addControls() throws Exception {
		// fix gui
		jdcNgaySinhKhachHang.setDateFormatString("dd/MM/YYYY");
		jTable1.setIntercellSpacing(new java.awt.Dimension(15, 5));
		jTable1.setRowHeight(30);
		jTable1.getTableHeader().setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 18));
		// đổi màu cho jdcNgaySinhKhachHang
		IDateEditor dateEditor1 = jdcNgaySinhKhachHang.getDateEditor();
		if (dateEditor1 instanceof JTextFieldDateEditor) {
			JTextFieldDateEditor txtFld = (JTextFieldDateEditor) dateEditor1;
			txtFld.setBackground(new java.awt.Color(230, 219, 209));
			txtFld.setEnabled(false);
			txtFld.setBackground(new java.awt.Color(230, 219, 209));
			txtFld.setDisabledTextColor(Color.BLACK);
			txtFld.setBorder(null);
			txtFld.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 20));
		}
		// table

		// đổ dữ liệu vào table
		dskh = khachHangDao.getAllKhachHang();
		updateTableKhachHang(dskh, HEADERS);

		// add action đổ dữ liệu lên khi nhấn vào dòng
		jTable1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String item = (String) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0);
				try {
					KhachHang kh = khachHangDao.timKhachHang(item);
					if (kh != null) {
						fillThongTinKhachHang(kh);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			private void fillThongTinKhachHang(KhachHang kh) throws Exception {
				tfMaKH.setText(kh.getIdKhachHang());
				tfHoTen.setText(kh.getTenKhachHang());
				jcbGioiTinh.setSelectedItem(kh.isGioiTinh() ? "Nam" : "Nữ");
				tfDiemTL.setText(kh.getDiemTichLuy() + "");
				tfHang.setText(kh.getHang());
				tfSDT.setText(kh.getSoDienThoai());
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(kh.getNgaySinh().toString());
				} catch (Exception e) {
				}
				jdcNgaySinhKhachHang.setDate(date);
			}
		});

		// sự kiện nút thêm
		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnThemActionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			private void btnThemActionPerformed(ActionEvent e) throws Exception {
				PanelXacNhan jpnXacNhan = new PanelXacNhan();
				clearText();
				tfDiemTL.setText("0");
				tfHang.setText("E");

				tfMaKH.setText(getNewMaKH());
				pnlChucNangTong.removeAll();
				pnlChucNangTong.setLayout(new BorderLayout());
				pnlChucNangTong.add(jpnXacNhan);
				pnlChucNangTong.repaint();
				pnlChucNangTong.revalidate();

				ActionListener listenerThem = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							addWarningDiemTichLuy();
							khachHangDao.themKhachHang(getThongTinKhachHang());
							dskh = khachHangDao.getAllKhachHang();
							updateTableKhachHang(dskh, HEADERS);
							clearText();
							pnlChucNangTong.updateUI();
							tfMaKH.setText(getNewMaKH());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

					private KhachHang getThongTinKhachHang() throws Exception {
						String maKh = tfMaKH.getText().isEmpty() ? null : tfMaKH.getText().trim();
						String tenKh = tfHoTen.getText().isEmpty() ? null : tfHoTen.getText().trim();
						if (tenKh != null && !Regex.checkRegex(tenKh, "Tên")) {
							JOptionPane.showMessageDialog(null, "Định dạng tên sai!!", "Lỗi định dạng tên",
									JOptionPane.ERROR_MESSAGE);
							return null;
						}
						if (tenKh != null)
							tenKh = Regex.capitalizeName(tenKh);
						else {
							JOptionPane.showMessageDialog(null, "Không để trống tên!!", "Lỗi định dạng tên",
									JOptionPane.ERROR_MESSAGE);
							tfHoTen.requestFocus();
							return null;
						}
						boolean gioiTinhKh = jcbGioiTinh.getSelectedItem().toString().equalsIgnoreCase("Nam") ? true
								: false;
						String sdtKh = tfSDT.getText().trim();
						if (!Regex.checkRegex(sdtKh, "SDT")) {
							JOptionPane.showMessageDialog(null, "Định dạng số điện thoại sai!!",
									"Lỗi định dạng số điện thoại", JOptionPane.ERROR_MESSAGE);
							tfSDT.requestFocus();
							return null;
						}
						if (khachHangDao.timKhachHangTheoSDT(sdtKh) != null) {
							JOptionPane.showMessageDialog(null, "Số điện thoại trùng!!", "Lỗi trùng số điện thoại",
									JOptionPane.ERROR_MESSAGE);
							tfSDT.requestFocus();
							return null;
						}
						Date ngaySinh = null;
						try {
							jdcNgaySinhKhachHang.setDateFormatString("MMM d, y");
							ngaySinh = jdcNgaySinhKhachHang.getDate();
							jdcNgaySinhKhachHang.setDateFormatString("dd/MM/YYYY");
						} catch (Exception e) {
							e.printStackTrace();
						}

						int diemKh = Integer.parseInt(tfDiemTL.getText().trim());
						return new KhachHang(maKh, tenKh, gioiTinhKh, ngaySinh, sdtKh, diemKh);

					}
				};
				jpnXacNhan.xacNhanAddActionListener(listenerThem);
				ActionListener listenerHuy = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						clearText();
						pnlChucNangTong.removeAll();
						pnlChucNangTong.setLayout(new BorderLayout());
						pnlChucNangTong.add(plnChucNang);
						pnlChucNangTong.repaint();
						pnlChucNangTong.validate();
						pnlChucNangTong.updateUI();
					}
				};
				jpnXacNhan.huyAddActionListener(listenerHuy);
			}

			private String getNewMaKH() throws Exception {
				String maKH = khachHangDao.getMaxIDKhachHang();
				if (maKH != null)
					maKH = String.format("KH%04d", Integer.parseInt(maKH.replaceAll("\\D+", "")) + 1);
				else
					maKH = String.format("KH%04d", 1);
				return maKH;
			}
		});
		btnXoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					String maXoa = (String) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0);

					int luaChon = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không", "Xóa",
							JOptionPane.YES_OPTION, JOptionPane.NO_OPTION);
					if (luaChon == JOptionPane.YES_OPTION) {

						boolean ketQua = khachHangDao.deleteKhachHang(maXoa);
						if (ketQua) {
							dskh = khachHangDao.getAllKhachHang();
							updateTableKhachHang(dskh, HEADERS);
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

		btnSua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnThemActionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			private void btnThemActionPerformed(ActionEvent e) throws Exception {
				PanelXacNhan jpnXacNhan = new PanelXacNhan();

				pnlChucNangTong.removeAll();
				pnlChucNangTong.setLayout(new BorderLayout());
				pnlChucNangTong.add(jpnXacNhan);
				pnlChucNangTong.repaint();
				pnlChucNangTong.revalidate();

				ActionListener listenerThem = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						KhachHang kh = null;
						try {
							kh = getThongTinKhachHang();
							System.out.println(kh);
							if (kh != null) {
								addWarningDiemTichLuy();
								khachHangDao.updateKhachHang(kh);
								System.out.println(kh);
								dskh = khachHangDao.getAllKhachHang();
								updateTableKhachHang(dskh, HEADERS);
								pnlChucNangTong.updateUI();

							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

					private KhachHang getThongTinKhachHang() throws Exception {
						String maKh = tfMaKH.getText().isEmpty() ? null : tfMaKH.getText().trim();
						String tenKh = tfHoTen.getText().isEmpty() ? null : tfHoTen.getText().trim();
						if (tenKh != null && !Regex.checkRegex(tenKh, "Tên")) {
							JOptionPane.showMessageDialog(null, "Định dạng tên sai!!", "Lỗi định dạng tên",
									JOptionPane.ERROR_MESSAGE);
							return null;
						}
						if (tenKh != null)
							tenKh = Regex.capitalizeName(tenKh);
						else {
							JOptionPane.showMessageDialog(null, "Không để trống tên!!", "Lỗi định dạng tên",
									JOptionPane.ERROR_MESSAGE);
							tfHoTen.requestFocus();
							return null;
						}
						boolean gioiTinhKh = jcbGioiTinh.getSelectedItem().toString().equalsIgnoreCase("Nam") ? true
								: false;
						String sdtKh = tfSDT.getText().trim();
						if (!Regex.checkRegex(sdtKh, "SDT")) {
							JOptionPane.showMessageDialog(null, "Định dạng số điện thoại sai!!",
									"Lỗi định dạng số điện thoại", JOptionPane.ERROR_MESSAGE);
							tfSDT.requestFocus();
							return null;
						}
						KhachHang kh = khachHangDao.timKhachHangTheoSDT(sdtKh);
						if (kh!=null &&!kh.getIdKhachHang().equalsIgnoreCase(maKh)) {
							tfSDT.requestFocus();
							JOptionPane.showMessageDialog(null, "Số điện thoại trùng!!", "Lỗi trùng số điện thoại",
									JOptionPane.ERROR_MESSAGE);
							return null;
						}
						Date ngaySinh = null;
						try {
							jdcNgaySinhKhachHang.setDateFormatString("MMM d, y");
							ngaySinh = jdcNgaySinhKhachHang.getDate();
							jdcNgaySinhKhachHang.setDateFormatString("dd/MM/YYYY");
						} catch (Exception e) {
							e.printStackTrace();
						}

						int diemKh = Integer.parseInt(tfDiemTL.getText().trim());
						return new KhachHang(maKh, tenKh, gioiTinhKh, ngaySinh, sdtKh, diemKh);

					}
				};
				jpnXacNhan.xacNhanAddActionListener(listenerThem);
				ActionListener listenerHuy = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
//						clearText();
						pnlChucNangTong.removeAll();
						pnlChucNangTong.setLayout(new BorderLayout());
						pnlChucNangTong.add(plnChucNang);
						pnlChucNangTong.repaint();
						pnlChucNangTong.validate();
						pnlChucNangTong.updateUI();
					}
				};
				jpnXacNhan.huyAddActionListener(listenerHuy);
			}
		});
	}

	private void updateTableKhachHang(List<KhachHang> dskh2, String[] headers2) {
		modelTableKhachHang = new KhachHangTableModel(headers2, dskh2);
		jTable1.setModel(modelTableKhachHang);
		jTable1.updateUI();
	}

	private void initComponents() {

		plnKhachHang = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jlbMaKH = new javax.swing.JLabel();
		tfMaKH = new javax.swing.JTextField();
		jSeparator1 = new javax.swing.JSeparator();
		jlbHoTen = new javax.swing.JLabel();
		tfHoTen = new javax.swing.JTextField();
		jSeparator2 = new javax.swing.JSeparator();
		jlbGioiTinh = new javax.swing.JLabel();
		jcbGioiTinh = new javax.swing.JComboBox<>();
		jSeparator3 = new javax.swing.JSeparator();
		jlbNgaySinh = new javax.swing.JLabel();
		jdcNgaySinhKhachHang = new com.toedter.calendar.JDateChooser();
		jSeparator4 = new javax.swing.JSeparator();
		jlbSDT = new javax.swing.JLabel();
		tfSDT = new javax.swing.JTextField();
		jSeparator5 = new javax.swing.JSeparator();
		jlbHang = new javax.swing.JLabel();
		tfHang = new javax.swing.JTextField();
		jSeparator6 = new javax.swing.JSeparator();
		jlbDiemTL = new javax.swing.JLabel();
		tfDiemTL = new javax.swing.JTextField();
		jSeparator7 = new javax.swing.JSeparator();
		pnlChucNangTong = new javax.swing.JPanel();
		plnChucNang = new javax.swing.JPanel();
		btnThem = new javax.swing.JButton();
		btnXoa = new javax.swing.JButton();
		btnSua = new javax.swing.JButton();
		btnXoaTrang = new javax.swing.JButton();
		btnTimKiem = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();

		plnKhachHang.setBackground(new java.awt.Color(230, 219, 209));

		jPanel1.setBackground(new java.awt.Color(230, 219, 209));

		jlbMaKH.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbMaKH.setText("Mã Khách Hàng:");

		tfMaKH.setEditable(false);
		tfMaKH.setBackground(new java.awt.Color(230, 219, 209));
		tfMaKH.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfMaKH.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		tfMaKH.setText("KH01");
		tfMaKH.setBorder(null);

		jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

		jlbHoTen.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbHoTen.setText("Họ Và Tên:");

		tfHoTen.setBackground(new java.awt.Color(230, 219, 209));
		tfHoTen.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfHoTen.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		tfHoTen.setText("Nguyễn Văn An");
		tfHoTen.setBorder(null);

		jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

		jlbGioiTinh.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbGioiTinh.setText("Giới Tính:");

		jcbGioiTinh.setBackground(new java.awt.Color(230, 219, 209));
		jcbGioiTinh.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jcbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
		jcbGioiTinh.setBorder(null);

		jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

		jlbNgaySinh.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbNgaySinh.setText("Ngày Sinh:");

		jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

		jlbSDT.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbSDT.setText("Số Điện Thoại:");

		tfSDT.setBackground(new java.awt.Color(230, 219, 209));
		tfSDT.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfSDT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		tfSDT.setText("0363748894");
		tfSDT.setBorder(null);

		jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

		jlbHang.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbHang.setText("Hạng:");

		tfHang.setEditable(false);
		tfHang.setBackground(new java.awt.Color(230, 219, 209));
		tfHang.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfHang.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		tfHang.setText("D");
		tfHang.setBorder(null);

		jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

		jlbDiemTL.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
		jlbDiemTL.setText("Điểm Tích Lũy:");

		tfDiemTL.setEditable(false);
		tfDiemTL.setBackground(new java.awt.Color(230, 219, 209));
		tfDiemTL.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		tfDiemTL.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		tfDiemTL.setText("0");
		tfDiemTL.setBorder(null);
		tfDiemTL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfDiemTLActionPerformed(evt);
			}
		});

		jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

		pnlChucNangTong.setBackground(new java.awt.Color(230, 219, 209));

		plnChucNang.setBackground(new java.awt.Color(230, 219, 209));
		plnChucNang.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chức Năng",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Times New Roman", Font.ITALIC, 17))); // NOI18N

		btnThem.setBackground(new java.awt.Color(230, 219, 209));
		btnThem.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnThem.setForeground(new java.awt.Color(102, 102, 102));
		btnThem.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\Plus_30px.png")); // NOI18N
		btnThem.setText("Thêm");
		btnThem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

		btnXoa.setBackground(new java.awt.Color(230, 219, 209));
		btnXoa.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnXoa.setForeground(new java.awt.Color(102, 102, 102));
		btnXoa.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\Recycle Bin_30px.png")); // NOI18N
		btnXoa.setText("Xóa");
		btnXoa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

		btnSua.setBackground(new java.awt.Color(230, 219, 209));
		btnSua.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnSua.setForeground(new java.awt.Color(102, 102, 102));
		btnSua.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\recycle_30px.png")); // NOI18N
		btnSua.setText("Sửa");
		btnSua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

		btnXoaTrang.setBackground(new java.awt.Color(230, 219, 209));
		btnXoaTrang.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnXoaTrang.setForeground(new java.awt.Color(102, 102, 102));
		btnXoaTrang.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\eraser_30px.png")); // NOI18N
		btnXoaTrang.setText("Xóa Trắng");
		btnXoaTrang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

		btnTimKiem.setBackground(new java.awt.Color(230, 219, 209));
		btnTimKiem.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		btnTimKiem.setForeground(new java.awt.Color(102, 102, 102));
		btnTimKiem.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SYSTEM + "\\search_30px.png")); // NOI18N
		btnTimKiem.setText("Tìm Kiếm");
		btnTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

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
										.addGap(66, 66, 66)
										.addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(71, 71, 71)
										.addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(69, 69, 69)
										.addComponent(btnXoaTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 140,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64,
												Short.MAX_VALUE)
										.addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(38, 38, 38)));
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
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								pnlChucNangTongLayout.createSequentialGroup().addContainerGap()
										.addComponent(plnChucNang, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addContainerGap()));
		pnlChucNangTongLayout.setVerticalGroup(
				pnlChucNangTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
						plnChucNang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(24, 24, 24)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
										.createSequentialGroup().addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addGroup(jPanel1Layout.createSequentialGroup().addComponent(
														jlbNgaySinh)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(jdcNgaySinhKhachHang,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
														.createSequentialGroup().addComponent(jlbMaKH)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(tfMaKH))
												.addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 311,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 115,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(105, 165, Short.MAX_VALUE)
										.addGroup(jPanel1Layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addGroup(jPanel1Layout.createSequentialGroup().addComponent(
																jlbSDT)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(tfSDT))
														.addGroup(jPanel1Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.TRAILING)
																.addGroup(jPanel1Layout.createSequentialGroup()
																		.addComponent(jlbHoTen).addGap(18, 18, 18)
																		.addComponent(tfHoTen,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				200,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
																.addComponent(jSeparator2,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 311,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGap(110, 110, 110)
														.addGroup(jPanel1Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING,
																		false)
																.addGroup(jPanel1Layout.createSequentialGroup()
																		.addComponent(jlbGioiTinh)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(jcbGioiTinh,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				95,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
																.addComponent(jSeparator3,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 244,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGroup(jPanel1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING,
																false)
														.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
																jPanel1Layout.createSequentialGroup()
																		.addComponent(jlbDiemTL).addGap(18, 18, 18)
																		.addComponent(tfDiemTL))
														.addComponent(jSeparator7,
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jSeparator5, javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE, 305,
																Short.MAX_VALUE))))
								.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jlbHang)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(tfHang, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addComponent(pnlChucNangTong, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap(175, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup().addGap(12, 12, 12).addGroup(jPanel1Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jlbMaKH)
								.addComponent(tfMaKH, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlbHoTen)
								.addComponent(tfHoTen, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlbGioiTinh, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jcbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jlbNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jdcNgaySinhKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jlbSDT).addComponent(tfSDT,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(28, 28, 28)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlbDiemTL)
								.addComponent(tfDiemTL, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlbHang).addComponent(tfHang, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(pnlChucNangTong, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(172, 172, 172)));

		jTable1.setAutoCreateRowSorter(true);
		jTable1.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		jTable1.setShowGrid(true);
		jScrollPane1.setViewportView(jTable1);
		if (jTable1.getColumnModel().getColumnCount() > 0) {
			jTable1.getColumnModel().getColumn(0).setResizable(false);
			jTable1.getColumnModel().getColumn(1).setMaxWidth(200);
			jTable1.getColumnModel().getColumn(2).setResizable(false);
			jTable1.getColumnModel().getColumn(3).setResizable(false);
			jTable1.getColumnModel().getColumn(4).setResizable(false);
			jTable1.getColumnModel().getColumn(5).setResizable(false);
			jTable1.getColumnModel().getColumn(6).setResizable(false);
		}

		javax.swing.GroupLayout plnKhachHangLayout = new javax.swing.GroupLayout(plnKhachHang);
		plnKhachHang.setLayout(plnKhachHangLayout);
		plnKhachHangLayout.setHorizontalGroup(plnKhachHangLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(plnKhachHangLayout.createSequentialGroup().addGap(0, 0, 0)
						.addGroup(plnKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(plnKhachHangLayout.createSequentialGroup().addGap(6, 6, 6)
										.addComponent(jScrollPane1).addContainerGap())
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));
		plnKhachHangLayout
				.setVerticalGroup(plnKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(plnKhachHangLayout.createSequentialGroup().addGap(0, 0, 0)
								.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 315,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
								.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(plnKhachHang,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(plnKhachHang,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	}// </editor-fold>//GEN-END:initComponents

	private void tfDiemTLActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tfDiemTLActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_tfDiemTLActionPerformed

	private void addWarningDiemTichLuy() {
		tfDiemTL.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(tfDiemTL, "ok");
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnLuu;
	private javax.swing.JButton btnSua;
	private javax.swing.JButton btnThem;
	private javax.swing.JButton btnTimKiem;
	private javax.swing.JButton btnXoa;
	private javax.swing.JButton btnXoaTrang;
	private javax.swing.JComboBox<String> jcbGioiTinh;
	private com.toedter.calendar.JDateChooser jdcNgaySinhKhachHang;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JSeparator jSeparator3;
	private javax.swing.JSeparator jSeparator4;
	private javax.swing.JSeparator jSeparator5;
	private javax.swing.JSeparator jSeparator6;
	private javax.swing.JSeparator jSeparator7;
	private javax.swing.JTable jTable1;
	private javax.swing.JLabel jlbDiemTL;
	private javax.swing.JLabel jlbGioiTinh;
	private javax.swing.JLabel jlbHang;
	private javax.swing.JLabel jlbHoTen;
	private javax.swing.JLabel jlbMaKH;
	private javax.swing.JLabel jlbNgaySinh;
	private javax.swing.JLabel jlbSDT;
	private javax.swing.JPanel plnChucNang;
	private javax.swing.JPanel plnKhachHang;
	private javax.swing.JPanel pnlChucNangTong;
	private javax.swing.JTextField tfDiemTL;
	private javax.swing.JTextField tfHang;
	private javax.swing.JTextField tfHoTen;
	private javax.swing.JTextField tfMaKH;
	private javax.swing.JTextField tfSDT;
	private Regex regex;
	// End of variables declaration//GEN-END:variables
}

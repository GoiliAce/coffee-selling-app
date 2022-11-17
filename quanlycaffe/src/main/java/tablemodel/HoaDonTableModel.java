package tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.HoaDon;

public class HoaDonTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MAHOADON = 0;
	private static final int NGAYTAO = 1;
	private static final int KHACHANG = 2;
	private static final int NHANVIEN = 3;
	private static final int THANHTIEN = 4;
	private static final int TIENKHACHTRA = 5;
	private String[] headers;
	private List<HoaDon> dsHD;
	
	public HoaDonTableModel(String[] headers, List<HoaDon> dsHD) {
		super();
		this.headers = headers;
		this.dsHD = dsHD;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsHD.size();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HoaDon hd = dsHD.get(rowIndex);
		switch (columnIndex) {
		case MAHOADON:
			return hd.getMaHoaDon();
		case NGAYTAO:
			return hd.getNgayTao();
		case KHACHANG:
			return hd.getKhachHang();
		case NHANVIEN:
			return hd.getNhanVien();
		case THANHTIEN:
			return hd.getThanhTien();
		case TIENKHACHTRA:
			return hd.getTienTra();

		default:
			return hd;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
}

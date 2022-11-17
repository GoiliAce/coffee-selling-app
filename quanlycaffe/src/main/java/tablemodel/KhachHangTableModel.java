package tablemodel;

import java.time.LocalDate;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.KhachHang;

public class KhachHangTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MAKH = 0;
	private static final int TENKH = 1;
	private static final int GIOITINH = 2;
	private static final int NGAYSINH = 3;
	private static final int SODIENTHOAI = 4;
	private static final int DIEMTICHLUY = 5;
	private static final int HANG = 6;
	private String[] headers;
	private List<KhachHang> dsKH;
	
	public KhachHangTableModel(String[] headers, List<KhachHang> dsKH) {
		super();
		this.headers = headers;
		this.dsKH = dsKH;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsKH.size();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		KhachHang kh = dsKH.get(rowIndex);
		switch (columnIndex) {
		case MAKH:
			return kh.getIdKhachHang();
		case TENKH:
			return kh.getTenKhachHang();
		case GIOITINH:
			return kh.isGioiTinh() ? "Nam": "Nữ";
		case NGAYSINH:
			return kh.getNgaySinh();
		case SODIENTHOAI:
			return kh.getSoDienThoai();
		case DIEMTICHLUY:
			return kh.getDiemTichLuy();
		case HANG:
			return kh.getHang();
			
		default:
			return kh;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == NGAYSINH)
			return LocalDate.class;
		if (columnIndex ==DIEMTICHLUY)
			return Integer.class;
		return String.class;
	}
}

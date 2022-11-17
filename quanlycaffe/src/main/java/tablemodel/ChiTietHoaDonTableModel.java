package tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.ChiTietHoaDon;

public class ChiTietHoaDonTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MACTHOADON = 0;
	private static final int MATHANG = 1;
	private static final int SOLUONG = 2;
	private static final int HOADON = 3;
	private String[] headers;
	private List<ChiTietHoaDon> dsCTHD;
	
	public ChiTietHoaDonTableModel(String[] headers, List<ChiTietHoaDon> dsCTHD) {
		super();
		this.headers = headers;
		this.dsCTHD = dsCTHD;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsCTHD.size();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ChiTietHoaDon cthd = dsCTHD.get(rowIndex);
		switch (columnIndex) {
		case MACTHOADON:		
			return cthd.getMaCTHoaDon();
		case MATHANG:
			return cthd.getMaMatHang();
		case SOLUONG:
			return cthd.getSoLuong();
		case HOADON:
			return cthd.getMaHoaDon();

		default:
			return cthd;
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
		if (columnIndex== SOLUONG)
			return Integer.class;
		return String.class;
	}
	
}

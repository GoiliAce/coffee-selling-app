package tablemodel;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.MatHang;

public class MatHangTableModel extends AbstractTableModel {
	private static final int MAMH = 0;
	private static final int DONGIA = 2;
	private static final int SOLUONGTON = 3;
	private static final int TRANGTHAI = 4;
	private static final int MALH = 5;
	private static final int TENMH = 1;
	private String[] headers;
	private List<MatHang> dsMH;
	
	public MatHangTableModel(String[] headers, List<MatHang> dsMH) {
		super();
		this.headers = headers;
		this.dsMH = dsMH;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsMH.size();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		MatHang mh = dsMH.get(rowIndex);
		switch (columnIndex) {
		case MAMH:
			return mh.getMaMatHang();
		case TENMH:
			return mh.getTenMatHang();
		case DONGIA:
			DecimalFormat decimalFormat = new DecimalFormat("###,###,### đ");
			return decimalFormat.format(mh.getDonGia());
		case SOLUONGTON:
			return mh.getSoLuongTon();
		case TRANGTHAI:
			return mh.isTrangThai()? "Còn hàng" : "Hết hàng";
		case MALH:
			return mh.getLoaiHang().getTenLoaiHang();
		default:
			return mh;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
}

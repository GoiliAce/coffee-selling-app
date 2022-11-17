package tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.LoaiHang;

public class LoaiHangTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MALH = 0;
	private static final int TENLH = 1;
	private String[] headers;
	private List<LoaiHang> dsLH;
	
	public LoaiHangTableModel(String[] headers, List<LoaiHang> dsLH) {
		super();
		this.headers = headers;
		this.dsLH = dsLH;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsLH.size();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		LoaiHang lh = dsLH.get(rowIndex);
		switch (columnIndex) {
		case MALH:
			return lh.getMaLoaiHang();
		case TENLH:
			return lh.getTenLoaiHang();

		default:
			return lh;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
	
}

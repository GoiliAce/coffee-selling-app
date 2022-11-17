package tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Ban;

public class BanTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MABAN = 0;
	private static final int TRANGTHAI = 1;
	private String[] headers;
	private List<Ban> dsBan;
	
	public BanTableModel(String[] headers, List<Ban> dsBan) {
		super();
		this.headers = headers;
		this.dsBan = dsBan;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsBan.size();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Ban ban = dsBan.get(rowIndex);
		switch (columnIndex) {
		case MABAN:
			return ban.getMaBan();
		case TRANGTHAI:
			return ban.isTrangThai();
		default:
			return ban;

		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
}

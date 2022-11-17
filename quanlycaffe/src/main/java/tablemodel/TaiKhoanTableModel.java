package tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.TaiKhoan;

public class TaiKhoanTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int TENTAIKHOAN = 0;
	private static final int MATKHAU = 1;
	private String[] headers;
	private List<TaiKhoan> dsTK;
	
	public TaiKhoanTableModel(String[] headers, List<TaiKhoan> dsTK) {
		super();
		this.headers = headers;
		this.dsTK = dsTK;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsTK.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TaiKhoan tk = dsTK.get(rowIndex);
		switch (columnIndex) {
		case TENTAIKHOAN:
			return tk.getTenTaiKhoan();
		case MATKHAU:
			return tk.getMatKhau();

		default:
			return tk;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
	
}

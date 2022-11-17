package tablemodel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import entity.NhanVien;

public class NhanVienTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MANV = 0;
	private static final int TENNV = 1;
	private static final int NGAYSINH = 2;
	private static final int GIOITINH = 3;
	private static final int NGAYVAOLAM = 4;
	private static final int SODIENTHOAI = 5;
	private static final int DIACHI = 6;
	private static final int CHUCVU = 7;
	private static final int LUONG = 8;
	private String[] headers;
	private List<NhanVien> dsNV;
	
	public NhanVienTableModel(String[] headers, List<NhanVien> dsNV) {
		super();
		this.headers = headers;
		this.dsNV = dsNV;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsNV.size();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		NhanVien nv = dsNV.get(rowIndex);
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		switch (columnIndex) {
		case MANV:
			return nv.getMaNhanVien();
		case TENNV:
			return nv.getTenNhanVien();
		case NGAYSINH:
			return sf.format(nv.getNgaySinh());
		case GIOITINH:
			return nv.isGioiTinh() ? "Nam": "Nữ";
		case NGAYVAOLAM:
			return sf.format(nv.getNgayVaoLam());
		case SODIENTHOAI:
//			String sdt = nv.getSoDienThoai();
//			sdt = sdt.replaceFirst("0", "+84");
//			String number = sdt.replaceFirst("([+84]{3})(\\d{3})(\\d{3})(\\d+)", "($1) $2 $3 $4");
//			return number;
			return nv.getSoDienThoai();
		case CHUCVU:
			return nv.getChucVu();
		case LUONG:
			DecimalFormat decimalFormat = new DecimalFormat("###,###,### đ");
			return decimalFormat.format(nv.getLuong());
		case DIACHI:
			return nv.getDiaChi();
		default:
			return nv;
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
		if (columnIndex ==NGAYSINH || columnIndex == NGAYVAOLAM)
			return LocalDate.class;
		if (columnIndex==LUONG)
			return Integer.class;
		return String.class;
	}
	
}

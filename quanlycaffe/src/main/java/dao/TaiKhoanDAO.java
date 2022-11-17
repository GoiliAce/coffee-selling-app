package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDb.ConnectDB;
import entity.TaiKhoan;

public class TaiKhoanDAO {
	private Connection con;
	public TaiKhoanDAO() {
		con = ConnectDB.getInstance().getConnection();
	}
	public boolean themTaiKhoan(TaiKhoan tk) throws Exception {
		if(timTaiKhoan(tk.getTenTaiKhoan()) !=null) {
			return false;
		}
		String sql = ""
				+ "INSERT INTO [dbo].[TaiKhoan]"
				+ "           ([tenTaiKhoan]"
				+ "           ,[matKhau])"
				+ "     VALUES"
				+ "           (?,?)";
		int n = 0;
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, tk.getTenTaiKhoan());
			stmt.setString(2, tk.getMatKhau());
			n = stmt.executeUpdate();
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return n>0;
	}
	public String getTenTaiKhoanCaoNhat() throws Exception {
		String sql = "SELECT MAX(tenTaiKhoan) FROM TaiKhoan";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				return rs.getString(1);
		}
		return null;
	}
	public TaiKhoan timTaiKhoan(String tenTK) throws Exception {
		String sql = "select * from TaiKhoan where tenTaiKhoan = ? ";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, tenTK);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return new TaiKhoan(rs.getString("tenTaiKhoan"), rs.getString("matKhau"));
			}
		}
		return null;
	}
	public List<TaiKhoan> layDanhSachTK() throws Exception {
		List<TaiKhoan> dsTK = new ArrayList<>();
		String sql = "select * from TaiKhoan";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				TaiKhoan tk = new TaiKhoan(rs.getString("tenTaiKhoan"), rs.getString("matKhau"));
				dsTK.add(tk);
			}
		}
		return dsTK;
	}
	public boolean doiMatKhau(TaiKhoan tk) throws Exception {
		if(timTaiKhoan(tk.getTenTaiKhoan())!=null){	
			String sql = "UPDATE [dbo].[TaiKhoan] "
					+ "   SET [matKhau] = ? "
					+ " WHERE  [tenTaiKhoan] = ? ";
			
			try(PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, tk.getMatKhau());
				stmt.setString(2, tk.getTenTaiKhoan());
				int n = stmt.executeUpdate();
				return n>0;
			}		
		}
		return false;
	}
	public boolean deleteTaiKhoan(String tenTaiKhoan) throws Exception {
		String sql = "DELETE FROM [dbo].[TaiKhoan] "
				+ "      WHERE tenTaiKhoan = ? ";
		int n = 0;
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, tenTaiKhoan);
			n = stmt.executeUpdate();
		}
		return n>0;
	}
}

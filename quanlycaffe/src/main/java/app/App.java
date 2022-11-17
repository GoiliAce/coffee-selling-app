package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

import entity.DiaChi;
import entity.NhanVien;
import entity.TaiKhoan;

import gui.FrmDangNhap;
import gui.FrmDoiMatKhau;
import gui.FrmTrangChu;


public class App {
	public static void main(String[] args) {
		 try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("windows".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(FrmDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(FrmDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(FrmDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(FrmDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
//		FrmTrangChu frmtrangChu = new FrmTrangChu(new NhanVien("NV01", "Cao Nguyễn Gia Hưng", true, new Date(), new Date(), "0344567891", "Quản lý", new DiaChi(), new TaiKhoan("TKQL01","1111"), "chua co"));
//		frmtrangChu.setLocationRelativeTo(null);
//		frmtrangChu.setVisible(true);
//		System.out.println();
		FrmDangNhap frmDangNhap = new FrmDangNhap();
		frmDangNhap.setLocationRelativeTo(null);
		frmDangNhap.setVisible(true);
		
	}
}
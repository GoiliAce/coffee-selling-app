create database CoffeShopDatabase
drop database CoffeShopDatabase
use CoffeShopDatabase

create table TaiKhoan(
	tenTaiKhoan varchar(10) not null primary key,
	matKhau varchar(20) default '1111')


create table NhanVien(
	maNhanVien varchar(10) primary key,
	tenNhanVien nvarchar(50) not null,
	ngaySinh date,
	gioiTinh varchar(10),
	ngayVaoLam date,
	soDienThoai varchar(20),
	chucVu nvarchar(20),
	maDc nvarchar(10) not null,
	tenTaiKhoan varchar(10) not null unique,
	linkAnh varchar(100),
	foreign key(tenTaiKhoan)  references  TaiKhoan(tenTaiKhoan) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key (maDC) REFERENCES DiaChi(maDC) ON UPDATE CASCADE ON DELETE CASCADE)

create table KhachHang(
	maKhachHang varchar(10) primary key not null,
	tenKhachHang nvarchar(50) not null,
	gioiTinh varchar(10),
	ngaySinh date,
	soDienThoai varchar(20) not null,
	diemTichLuy int default 0)

create table LoaiHang(
	maLoaiHang varchar(10) primary key not null,
	tenLoaiHang nvarchar(20))
alter table LoaiHang
alter column tenLoaiHang nvarchar(50)
create table MatHang(
	maMatHang varchar(10) primary key not null,
	
	donGia money,
	soLuongTon int default 0,
	trangThai varchar(10),
	maLoaiHang varchar(10),
	linkAnh varchar(100),
	foreign key(maLoaiHang) references LoaiHang(maLoaiHang) ON UPDATE CASCADE ON DELETE CASCADE) 
	alter table matHang add tenMatHang nvarchar(200)

create table Ban(
	maBan int IDENTITY(1,1) PRIMARY KEY,
	trangThai varchar(10))



create table HoaDon(
	maHoaDon varchar(10) primary key,
	ngayTao date,
	maKhachHang varchar(10),
	maNhanVien varchar(10) ,
	thanhTien money,
	tienKhachTra money,
	maBan int,
	foreign key(maKhachHang) references KhachHang(maKhachHang) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key(maNhanVien) references NhanVien(maNhanVien) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key(maBan) references Ban(maBan) ON UPDATE CASCADE ON DELETE CASCADE)

create table ChiTietHoaDon(
	maCTHoaDon varchar(10) primary key,
	maMatHang varchar(10),
	soLuong int,
	maHoaDon varchar(10),
	foreign key (maMatHang) references MatHang(maMatHang) ON UPDATE CASCADE
ON DELETE CASCADE,
	foreign key(maHoaDon) references HoaDon(maHoaDon)ON UPDATE CASCADE
ON DELETE CASCADE) 
	


select * from NhanVien 
select * from TaiKhoan
select * from KhachHang
select * from HoaDon
delete NhanVien where maNhanVien = 'NV009'
delete KhachHang where maKhachHang ='S'

select * from DiaChi where tinhTP = N'Thành phố Cần Thơ'
select * from diachi where tinhTP = N'Tỉnh Long An' and quanHuyen = N'Huyện Cần giuộc'  and phuongXa = N'Xã Phước Lại'

INSERT [dbo].[DiaChi] ([maDC], [tinhTP], [quanHuyen], [phuongXa]) VALUES (N'DC-0011163', N'Tỉnh/Thành Phố', N'Quận, Huyện', N'Phường, Xã')

delete NhanVien
delete TaiKhoan where tenTaiKhoan = 'TKNV007'

select * from NhanVien where  tenNhanVien = N'Cao Nguyễn Gia Hưng'  


insert LoaiHang values ('LH01',N'Coffee')
insert LoaiHang values ('LH02',N'Hồng trà')
insert LoaiHang values ('LH03',N'Trà sữa')
insert LoaiHang values ('LH04',N'Nước ép')
insert LoaiHang values ('LH05',N'Sinh tố')
insert LoaiHang values ('LH06',N'Ăn vặt')
insert LoaiHang values ('LH07',N'Nguyên liệu')

select * from MatHang
delete from ban
select * from LoaiHang where tenLoaiHang = 'Coffee' 

select * from ban where maBan = 2
insert ban values ('0')
USE [CoffeShopDatabase]
GO

INSERT INTO [dbo].[Ban]
           ([trangThai])
     VALUES (?)
GO


package cit.ctu;
import java.util.Date;

public class NguoiDung {

    private int id;
    private String tenDangNhap;
    private String matKhau;
    private String matKhauMoi;
	private String hoTen;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String vaiTro;
    private String trangThai;
    private MyDate ngayTao;

    public NguoiDung() {}

    public NguoiDung(int id, String tenDangNhap, String matKhau, String hoTen,
                     String email, String soDienThoai, String diaChi,
                     String vaiTro, String trangThai, MyDate ngayTao) {
		this.id = id;
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.hoTen = hoTen;
		this.email = email;
		this.soDienThoai = soDienThoai;
		this.diaChi = diaChi;
		this.vaiTro = vaiTro;
		this.trangThai = trangThai;
		this.ngayTao = ngayTao;
	}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public MyDate getNgayTao() { return ngayTao; }
    public void setNgayTao(MyDate ngayTao) { this.ngayTao = ngayTao; }
    
    public String getMatKhauMoi() {
		return matKhauMoi;
	}
	public void setMatKhauMoi(String matKhauMoi) {
		this.matKhauMoi = matKhauMoi;
	}
}
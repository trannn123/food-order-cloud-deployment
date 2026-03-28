package cit.ctu;

public class ChiTietHoaDon {

    private int id;
    private int hoaDonId;
    private int monAnId;
    private int soLuong;
    private double donGiaTaiThoiDiemTaoHoaDon;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int id, int hoaDonId, int monAnId, int soLuong, double gia) {
        this.id = id;
        this.hoaDonId = hoaDonId;
        this.monAnId = monAnId;
        this.soLuong = soLuong;
        this.setDonGiaTaiThoiDiemTaoHoaDon(gia);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHoaDonId() {
        return hoaDonId;
    }

    public void setHoaDonId(int hoaDonId) {
        this.hoaDonId = hoaDonId;
    }

    public int getMonAnId() {
        return monAnId;
    }

    public void setMonAnId(int monAnId) {
        this.monAnId = monAnId;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

	public double getDonGiaTaiThoiDiemTaoHoaDon() {
		return donGiaTaiThoiDiemTaoHoaDon;
	}

	public void setDonGiaTaiThoiDiemTaoHoaDon(double donGiaTaiThoiDiemTaoHoaDon) {
		this.donGiaTaiThoiDiemTaoHoaDon = donGiaTaiThoiDiemTaoHoaDon;
	}
}
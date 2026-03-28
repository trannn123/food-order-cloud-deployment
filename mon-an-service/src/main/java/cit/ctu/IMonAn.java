package cit.ctu;
import java.util.List;

public interface IMonAn {
	List<MonAn> layDanhSachMon();
	List<MonAn> layDanhSachMonCon();
    boolean themMon(MonAn m);
    boolean suaMon(MonAn m);
    boolean xoaMon(int id);
    MonAn timMonTheoId(int id);
    boolean giamSoLuongMon(int id, int number);
}

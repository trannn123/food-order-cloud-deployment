package cit.ctu.Client;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import cit.ctu.QLDTAN.ChiTietHoaDon;
import cit.ctu.QLDTAN.ItemGioHang;
import cit.ctu.QLDTAN.HoaDon;
import cit.ctu.QLDTAN.NguoiDung;

import static cit.ctu.Constants.BASE_API_URL_HOA_DON;

/**
 * Servlet implementation class DatMon
 */
@WebServlet("/DatMon")
public class DatMon extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private static final URI uri = UriBuilder.fromUri(BASE_API_URL_HOA_DON).build();
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(uri);
	public DatMon() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		NguoiDung nd = (NguoiDung) session.getAttribute("user");

		if (nd == null) {
			response.sendRedirect("DangNhap");
			return;
		}
		
		List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");
		if (cart == null || cart.isEmpty()) {
			response.sendRedirect("DanhSachMonAn");
			return;
		}
		
		double tongTien = 0;
		int tongSoLuong = 0;

		for (ItemGioHang g : cart) {
			tongTien += g.getThanhTien();
			tongSoLuong += g.getSoLuong();
		}

		HoaDon hd = new HoaDon();
		hd.setNguoiDungId(nd.getId());
		hd.setTrangThai("dang_xu_ly");

		int hoaDonId = target
				.path("rest")
				.path("hoadon")
				.path("TaoHoaDon")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(hd, MediaType.APPLICATION_JSON), Integer.class);
		
		if (hoaDonId <= 0) {
			response.sendRedirect("DanhSachMonAn");
			return;
		}

		for (ItemGioHang g : cart) {
			ChiTietHoaDon ct = new ChiTietHoaDon();
			ct.setHoaDonId(hoaDonId);
			ct.setMonAnId(g.getMon().getId());
			ct.setSoLuong(g.getSoLuong());
			ct.setDonGiaTaiThoiDiemTaoHoaDon(g.getMon().getGia());

			Response res = target
					.path("rest")
					.path("hoadon")
					.path("ThemChiTietHoaDon")
					.request(MediaType.APPLICATION_JSON).post(Entity.entity(ct, MediaType.APPLICATION_JSON));

			if (res.getStatus() != 200) {
				res.close();
				response.sendRedirect("DanhSachMonAn");
				return;
			}
			res.close();
		}
		session.removeAttribute("cart");
		response.sendRedirect("HoaDonDaDat");
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

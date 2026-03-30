package cit.ctu.Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import org.glassfish.jersey.client.ClientConfig;

import cit.ctu.QLDTAN.MonAn;
import cit.ctu.QLDTAN.NguoiDung;

import static cit.ctu.Constants.BASE_API_URL_HOA_DON;
import static cit.ctu.Constants.BASE_API_URL_MON_AN;

/**
 * Servlet implementation class SuaHoaDon
 */
@WebServlet("/SuaHoaDon")
public class SuaHoaDon extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    private static final URI URI_HOADON =
            UriBuilder.fromUri(BASE_API_URL_HOA_DON).build();
    private static final URI URI_MONAN =
            UriBuilder.fromUri(BASE_API_URL_MON_AN).build();
    Client client = ClientBuilder.newClient(new ClientConfig());
    WebTarget target_HoaDon = client.target(URI_HOADON);
    WebTarget target_MonAn = client.target(URI_MONAN);
    public SuaHoaDon() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        NumberFormat vnd = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        if (nd == null || !"nhanvien".equals(nd.getVaiTro())) {
            response.sendRedirect("DangNhap");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect("QuanLyHoaDon");
            return;
        }
        int hoaDonId = Integer.parseInt(idStr);
        
        List<cit.ctu.QLDTAN.ChiTietHoaDon> dsChiTiet = target_HoaDon
                .path("rest").path("hoadon").path("LayChiTietHoaDon")
                .path(String.valueOf(hoaDonId))
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<cit.ctu.QLDTAN.ChiTietHoaDon>>() {});

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        out.println(".navbar-brand{color:#ff6b2c !important;font-weight:bold;}");
        out.println(".card{border:none;border-radius:16px;box-shadow:0 4px 12px rgba(0,0,0,0.08);}");
        out.println(".table thead{background:#ff6b2c;color:white;}");
        out.println(".page-title{font-weight:700;color:#333;}");
        out.println(".btn-orange{background:#ff6b2c;color:white;border:none;}");
        out.println(".btn-orange:hover{background:#e85d22;color:white;}");
        out.println("</style>");

        out.println("</head><body>");

        out.println("<nav class='navbar bg-white border-bottom mb-4'>");
        out.println("<div class='container'>");
        out.println("<span class='navbar-brand'>Quản Lý Đặt Thức Ăn</span>");
        out.println("<div>");
        out.println("<a href='TrangChu' class='btn btn-outline-danger btn-sm me-2'>Trang chủ</a>");
        out.println("<a href='QuanLyHoaDon' class='btn btn-outline-primary btn-sm me-2'>Quay lại</a>");
        out.println("<a href='DangXuat' class='btn btn-danger btn-sm'>Đăng xuất</a>");
        out.println("</div></div></nav>");

        out.println("<div class='container'><div class='card p-4'>");
        out.println("<h3 class='text-center page-title mb-4'>Sửa hóa đơn #" + hoaDonId + "</h3>");

        out.println("<table class='table table-bordered table-hover text-center'>");
        out.println("<thead><tr>");
        out.println("<th>Món</th><th>Số lượng</th><th>Giá</th><th>Hành động</th>");
        out.println("</tr></thead><tbody>");

        for (cit.ctu.QLDTAN.ChiTietHoaDon ct : dsChiTiet) {

            String tenMon = "N/A";
            try {
                MonAn mon = target_MonAn.path("rest").path("monan")
                        .path("TimMon").path(String.valueOf(ct.getMonAnId()))
                        .request(MediaType.APPLICATION_JSON)
                        .get(MonAn.class);
                if (mon != null) tenMon = mon.getTenMon();
            } catch (Exception e) {}

            out.println("<tr>");

            out.println("<td>" + tenMon + "</td>");
            out.println("<form method='post' action='SuaHoaDon'>");
            out.println("<input type='hidden' name='hoaDonId' value='" + hoaDonId + "'>");
            out.println("<input type='hidden' name='chiTietHoaDonId' value='" + ct.getId() + "'>");
            out.println("<input type='hidden' name='monAnId' value='" + ct.getMonAnId() + "'>");
            out.println("<td>");
            out.println("<input type='number' name='soLuong' value='" + ct.getSoLuong() + "' min='1' class='form-control'>");
            out.println("</td>");
            out.println("<td class='text-primary fw-bold'>" + vnd.format(ct.getDonGiaTaiThoiDiemTaoHoaDon()) + "</td>");
            out.println("<td>");
            out.println("<button class='btn btn-orange btn-sm'>Cập nhật</button>");
            out.println("<a href='XoaChiTietHoaDon?chiTietId=" + ct.getId() 
            + "&hoaDonId=" + hoaDonId + "' "
            + "onclick=\"return confirm('Bạn có chắc muốn xóa món này?');\" "
            + "class='btn btn-danger btn-sm'>Xóa</a>");
            out.println("</td>");
            out.println("</form>");

            out.println("</tr>");
        }
        out.println("</tbody></table>");
        out.println("</div></div>");
        out.println("</body></html>");
    }
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int hoaDonId = Integer.parseInt(request.getParameter("hoaDonId"));
        int chiTietHoaDonId = Integer.parseInt(request.getParameter("chiTietHoaDonId"));
        int monAnId = Integer.parseInt(request.getParameter("monAnId"));
        int soLuong = Integer.parseInt(request.getParameter("soLuong"));

        try {
        	Response res = target_HoaDon
        		    .path("rest")
        		    .path("hoadon")
        		    .path("CapNhatSoLuong")
        		    .queryParam("chiTietHoaDonId", chiTietHoaDonId)
        		    .queryParam("monAnId", monAnId)
        		    .queryParam("soLuong", soLuong)
        		    .request()
        		    .put(Entity.text(""));

        		if (res.getStatus() != 200) {
        		    String msg = res.readEntity(String.class);
        		    System.out.println("Lỗi: " + msg);
        		} else {
        		    System.out.println("Cập nhật thành công");
        		}
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("SuaHoaDon?id=" + hoaDonId);
    }
}
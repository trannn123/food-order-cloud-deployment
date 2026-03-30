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

import cit.ctu.QLDTAN.HoaDon;
import cit.ctu.QLDTAN.NguoiDung;

import static cit.ctu.Constants.BASE_API_URL_HOA_DON;

/**
 * Servlet implementation class QuanLyHoaDon
 */
@WebServlet("/QuanLyHoaDon")
public class QuanLyHoaDon extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    private static final URI URI_HOADON =
            UriBuilder.fromUri(BASE_API_URL_HOA_DON).build();
    Client client = ClientBuilder.newClient();
    WebTarget target_HoaDon = client.target(URI_HOADON);
    public QuanLyHoaDon() {
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        NumberFormat vnd = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        if (nd == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        List<HoaDon> dsHoaDon = target_HoaDon
                .path("rest")
                .path("hoadon")
                .path("LayDanhSachHoaDon")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<HoaDon>>() {});
 
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Quản lý hóa đơn</title>");
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
        out.println(".status-select { color: black; }");
        out.println(".status-dang_xu_ly { color: #0d6efd !important; font-weight: 600; }"); 
        out.println(".status-thanh_cong { color: #28a745 !important; font-weight: 600; }");
        out.println(".status-huy { color: #6c757d !important; font-weight: 600; }"); 
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<nav class='navbar bg-white border-bottom mb-4'>");
        out.println("<div class='container'>");
        out.println("<span class='navbar-brand'>Quản Lý Đặt Thức Ăn</span>");
        out.println("<div>");
        out.println("<a href='TrangChu' class='btn btn-outline-danger btn-sm me-2'><i class='bi bi-house'></i> Trang chủ</a>");
        out.println("<a href='DangXuat' class='btn btn-danger btn-sm'><i class='bi bi-box-arrow-right'></i> Đăng xuất</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container'>");
        out.println("<div class='card p-4'>");
        out.println("<h3 class='mb-4 text-center page-title'>");
        out.println("<i class='bi bi-receipt'></i> Quản lý hóa đơn");
        out.println("</h3>");

        out.println("<div class='table-responsive'>");
        out.println("<table class='table table-bordered table-hover align-middle text-center'>");

        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Mã</th>");
        out.println("<th>Ngày</th>");
        out.println("<th>Tổng SL</th>");
        out.println("<th>Tổng tiền</th>");
        out.println("<th>Trạng thái</th>");
        out.println("<th>Thao tác</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        int tongSoLuong;
        double tongTien;
        
        for (HoaDon hd : dsHoaDon) {
        	
        	String slStr =
                    target_HoaDon.path("rest")
                            .path("hoadon")
                            .path("TongSoLuongHoaDon")
                            .path(String.valueOf(hd.getId()))
                            .request()
                            .get(String.class);

            tongSoLuong = Integer.parseInt(slStr);

            String tienStr =
                    target_HoaDon.path("rest")
                            .path("hoadon")
                            .path("TongTienHoaDon")
                            .path(String.valueOf(hd.getId()))
                            .request()
                            .get(String.class);

            tongTien = Double.parseDouble(tienStr);
            
            out.println("<tr>");
            out.println("<td>#" + hd.getId() + "</td>");
            out.println("<td>" + hd.getNgayDat() + "</td>");
            out.println("<td>" + tongSoLuong + "</td>");
            out.println("<td class='text-danger fw-bold'>" + vnd.format(tongTien) + "</td>");
            String currentStatus = hd.getTrangThai();
            out.println("<td>");
            out.println("<select name='trangThai' class='form-select form-select-sm status-select' " +
                    "onchange='confirmAndSubmit(this, " + hd.getId() + ")'>");

            out.println("<option value='dang_xu_ly'" +
                    (currentStatus.equals("dang_xu_ly") ? " selected" : "") + ">Đang xử lý</option>");
            out.println("<option value='thanh_cong'" +
                    (currentStatus.equals("thanh_cong") ? " selected" : "") + ">Thành công</option>");
            out.println("<option value='huy'" +
                    (currentStatus.equals("huy") ? " selected" : "") + ">Đã hủy</option>");
            out.println("</select>");
            out.println("</td>");
            
            out.println("<td>");
            out.println("<a href='QuanLyChiTietHoaDon?id=" + hd.getId() + "' class='btn btn-primary btn-sm me-1'><i class='bi bi-eye'></i></a>");
            out.println("<a href='SuaHoaDon?id=" + hd.getId() + "' class='btn btn-warning btn-sm me-1'><i class='bi bi-pencil'></i></a>");
            out.println("<a href='XoaHoaDon?id=" + hd.getId() + "' class='btn btn-danger btn-sm' " +
                    "onclick='return confirm(\"Bạn có chắc muốn xóa hóa đơn #" + hd.getId() + " không?\")'>" +
                    "<i class='bi bi-trash'></i></a>");
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");

        out.println("</div>");
        out.println("</div>");
        
        out.println("<script>");
        out.println("function confirmAndSubmit(select, hoaDonId) {");
        out.println("    let value = select.value;");
        out.println("    let text = select.options[select.selectedIndex].text;");
        out.println("    if (confirm('Bạn có chắc muốn đổi trạng thái thành: ' + text + '?')) {");
        out.println("        fetch('QuanLyHoaDon', {");
        out.println("            method: 'POST',");
        out.println("            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },");
        out.println("            body: 'hoaDonId=' + hoaDonId + '&trangThai=' + value");
        out.println("        }).then(() => {");
        out.println("            location.reload();");
        out.println("        });");
        out.println("    } else {");
        out.println("        location.reload();");
        out.println("    }");
        out.println("}");
        out.println("</script>");

        out.println("</body>");
        out.println("</html>");
    }
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int hoaDonId = Integer.parseInt(request.getParameter("hoaDonId"));
        String trangThai = request.getParameter("trangThai");

        target_HoaDon
                .path("rest")
                .path("hoadon")
                .path("CapNhatTrangThaiHoaDon")
                .queryParam("hoaDonId", hoaDonId)
                .queryParam("trangThai", trangThai)
                .request()
                .put(Entity.text(""));
        
        response.sendRedirect("QuanLyHoaDon");
    }
}
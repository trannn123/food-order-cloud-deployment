package cit.ctu.Client;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import cit.ctu.QLDTAN.MonAn;
import cit.ctu.QLDTAN.NguoiDung;

import static cit.ctu.Constants.BASE_API_URL_MON_AN;

/**
 * Servlet implementation class QuanLyMon
 */
@WebServlet("/QuanLyMon")
public class QuanLyMon extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    private static final URI uri =
            UriBuilder.fromUri(BASE_API_URL_MON_AN).build();
    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);
    public QuanLyMon() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Quản lý món ăn</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println(".no-wrap { white-space: nowrap; }");
        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        out.println(".navbar-custom{background:#ffffff;box-shadow:0 2px 10px rgba(0,0,0,0.06);}");
        out.println(".navbar-brand{color:#ff6b2c !important;font-weight:700;font-size:1.3rem;}");
        out.println(".user-text{color:#555;font-weight:500;}");
        out.println(".user-text b{color:#ff6b2c;}");
        out.println(".page-title{color:#333;font-weight:700;}");
        out.println(".card-box{background:white;padding:28px;border-radius:18px;box-shadow:0 4px 14px rgba(0,0,0,0.08);}");
        out.println(".btn-main{background:#ff6b2c;color:white;border:none;}");
        out.println(".btn-main:hover{background:#e85d22;color:white;}");
        out.println(".btn-outline-main{color:#ff6b2c;border:1px solid #ff6b2c;background:white;}");
        out.println(".btn-outline-main:hover{background:#ff6b2c;color:white;}");
        out.println(".table thead th{background:#ff6b2c !important;color:white !important;vertical-align:middle;text-align:center;}");
        out.println(".table tbody td{vertical-align:middle;}");
        out.println(".table tbody tr:hover{background:#fff3ee;}");
        out.println(".badge-con{background:#d1fae5;color:#065f46;padding:8px 12px;border-radius:999px;font-weight:600;}");
        out.println(".badge-het{background:#fee2e2;color:#991b1b;padding:8px 12px;border-radius:999px;font-weight:600;}");
        out.println(".action-group{white-space:nowrap;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<nav class='navbar navbar-expand-lg navbar-custom mb-4'>");
        out.println("<div class='container'>");
        out.println("<a class='navbar-brand' href='TrangChu'><i class='bi bi-shop'></i> Quản Lý Đặt Thức Ăn</a>");
        out.println("<div class='d-flex align-items-center'>");
        out.println("<span class='user-text me-3'>Xin chào <b>" + nd.getHoTen() + "</b></span>");
        out.println("<a href='TrangChu' class='btn btn-outline-danger btn-sm me-2'><i class='bi bi-house'></i> Trang chủ</a>");
        out.println("<a href='DangXuat' class='btn btn-danger btn-sm'><i class='bi bi-box-arrow-right'></i> Đăng xuất</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container'>");
        out.println("<div class='card-box'>");
        out.println("<div class='d-flex justify-content-between align-items-center flex-wrap gap-2 mb-4'>");
        out.println("<h2 class='page-title mb-0'><i class='bi bi-grid'></i> Quản lý món ăn</h2>");
        out.println("<div>");
        out.println("<a href='ThemMon' class='btn btn-main me-2'><i class='bi bi-plus-lg'></i> Thêm món</a>");
        out.println("<a href='TimMon' class='btn btn-outline-main'><i class='bi bi-search'></i> Tìm kiếm</a>");
        out.println("</div>");
        out.println("</div>");

        try {
            List<MonAn> list = target
                    .path("rest")
                    .path("monan")
                    .path("LayDanhSachMonAn")
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<MonAn>>() {});

            out.println("<div class='table-responsive'>");
            out.println("<table class='table table-bordered table-hover align-middle'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Mã</th>");
            out.println("<th>Tên món</th>");
            out.println("<th>Mô tả</th>");
            out.println("<th>Số lượng</th>");
            out.println("<th>Giá</th>");
            out.println("<th>Trạng thái</th>");
            out.println("<th width='170'>Thao tác</th>");
            out.println("</tr>");
            out.println("</thead>");

            out.println("<tbody>");
            for (MonAn m : list) {
                out.println("<tr>");
                out.println("<td class='text-center'>" + m.getId() + "</td>");
                out.println("<td><strong>" + m.getTenMon() + "</strong></td>");
                out.println("<td>" + m.getMoTa() + "</td>");
                out.println("<td class='text-center'>" + m.getSoLuong() + "</td>");
                out.println("<td class='text-end text-primary fw-bold'>" + m.getGia() + "</td>");

                if ("con".equalsIgnoreCase(m.getTrangThai())) {
                	out.println("<td class='text-center no-wrap'><span class='badge-con'>Còn hàng</span></td>");
                } else {
                	out.println("<td class='text-center no-wrap'><span class='badge-het'>Hết hàng</span></td>");
                }

                out.println("<td class='text-center action-group'>");
                out.println("<a class='btn btn-outline-main btn-sm me-1' href='SuaMon?id=" + m.getId() + "' title='Sửa'>");
                out.println("<i class='bi bi-pencil-square'></i>");
                out.println("</a>");

                out.println("<a class='btn btn-main btn-sm' onclick=\"return confirm('Bạn có chắc muốn xóa món này?')\" href='XoaMon?id=" + m.getId() + "' title='Xóa'>");
                out.println("<i class='bi bi-trash'></i>");
                out.println("</a>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
        } catch (Exception e) {
            out.println("<div class='alert alert-danger'>Không lấy được danh sách món</div>");
        }
        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	doGet(request, response);
    }
}

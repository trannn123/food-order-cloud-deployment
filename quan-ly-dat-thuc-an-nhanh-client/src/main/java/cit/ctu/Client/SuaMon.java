package cit.ctu.Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
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
import cit.ctu.QLDTAN.MonAn;
import cit.ctu.QLDTAN.NguoiDung;

import static cit.ctu.Constants.BASE_API_URL_MON_AN;

/**
 * Servlet implementation class SuaMon
 */
@WebServlet("/SuaMon")
public class SuaMon extends HttpServlet {
	private static final long serialVersionUID = 1L;	
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri =
			UriBuilder.fromUri(BASE_API_URL_MON_AN).build();
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(uri);
    public SuaMon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {
			response.sendRedirect("DangNhap");
			return;
		}
		
		NguoiDung nd = (NguoiDung) session.getAttribute("user");

		String id = request.getParameter("id");
		
		if (id == null) {
            response.sendRedirect("QuanLyMon");
            return;
        }
		
		MonAn mon = target.path("rest")
		        .path("monan")
		        .path("TimMon")
		        .path(id)
		        .request(MediaType.APPLICATION_JSON)
		        .get(MonAn.class);
		
		if (mon == null) {
		    response.sendRedirect("QuanLyMon");
		    return;
		}
		
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>Sửa món</title>");
		out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
		out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

		out.println("<style>");		
		out.println(".navbar-brand{color:#ff6b2c !important;}");
		out.println(".me-3{color:#ff6b2c !important;}");
		out.println(".btn-outline-danger{");
		out.println("border-color:#ff6b2c;");
		out.println("color:#ff6b2c;");
		out.println("}");
		out.println(".btn-outline-danger:hover{");
		out.println("background:#ff6b2c;");
		out.println("color:white;");
		out.println("}");
		out.println("body{background:#f8f9fa;font-family:system-ui;}");
		out.println(".title{color:#ff6b2c;font-weight:700;}");
		out.println(".btn-main{background:#ff6b2c;color:white;border:none;}");
		out.println(".btn-main:hover{background:#ff5722;color:white;}");
		out.println(".card-box{background:white;padding:25px;border-radius:12px;box-shadow:0 8px 20px rgba(0,0,0,0.08);}");
		out.println(".form-control:focus{border-color:#ff6b2c;box-shadow:0 0 0 0.1rem rgba(255,107,44,0.25);}");
		out.println("</style>");
		
		out.println("</head>");
		out.println("<body>");
		
		out.println("<nav class='navbar bg-white border-bottom'>");
		out.println("<div class='container'>");
		out.println("<span class='navbar-brand fw-bold'>Quản Lý Đặt Thức Ăn</span>");
		out.println("<div class='d-flex align-items-center'>");
		out.println("<a href='TrangChu' class='btn btn-outline-danger btn-sm me-2'>");
		out.println("<i class='bi bi-house'></i>");
		out.println("</a>");
		out.println("<span class='me-3'>Xin chào <b>" + nd.getHoTen() + "</b></span>");
		out.println("<a href='DangXuat' class='btn btn-outline-danger btn-sm'>Đăng xuất</a>");
		out.println("</div>");
		out.println("</div>");
		out.println("</nav>");

		out.println("<div class='container mt-5'>");
		out.println("<div class='card-box'>");
		out.println("<h2 class='title mb-4'><i class='bi bi-pencil'></i> Sửa món</h2>");
		
		out.println("<form method='post'>");
		out.println("<input type='hidden' name='id' value='" + mon.getId() + "'>");
		
		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Tên món</label>");
		out.println("<input class='form-control' name='ten' value='" + mon.getTenMon() + "'>");
		out.println("</div>");

		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Giá</label>");
		out.println("<input class='form-control' name='gia' value='" + mon.getGia() + "'>");
		out.println("</div>");

		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Số lượng</label>");
		out.println("<input class='form-control' name='soluong' value='" + mon.getSoLuong() + "'>");
		out.println("</div>");

		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Mô tả</label>");
		out.println("<input class='form-control' name='mota' value='" + mon.getMoTa() + "'>");
		out.println("</div>");
		
		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Trạng thái</label>");
		out.println("<select class='form-control' name='trangthai'>");
		out.println("<option value='con' "
		        + ("con".equals(mon.getTrangThai()) ? "selected" : "") 
		        + ">Còn hàng</option>");
		out.println("<option value='het' "
		        + ("het".equals(mon.getTrangThai()) ? "selected" : "") 
		        + ">Hết hàng</option>");
		out.println("</select>");
		out.println("</div>");

		out.println("<div class='mt-3'>");
		out.println("<button class='btn btn-main me-2'>");
		out.println("<i class='bi bi-check-lg'></i> Cập nhật");
		out.println("</button>");

		out.println("<a href='QuanLyMon' class='btn btn-outline-secondary'>");
		out.println("Quay lại");
		out.println("</a>");
		out.println("</div>");

		out.println("</form>");

		out.println("</div>");
		out.println("</div>");

		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String ten = request.getParameter("ten");
        String gia = request.getParameter("gia");
        String soluong = request.getParameter("soluong");
        String mota = request.getParameter("mota");
        String trangthai = request.getParameter("trangthai");

        if (id == null || ten == null || gia == null || soluong == null || mota == null) {
            response.sendRedirect("QuanLyMon");
            return;
        }

        MonAn mon = new MonAn();
        mon.setId(Integer.parseInt(id));
        mon.setTenMon(ten);
        mon.setGia(Double.parseDouble(gia));
        mon.setSoLuong(Integer.parseInt(soluong));
        mon.setMoTa(mota);
        mon.setTrangThai(trangthai);

        Response res = target.path("rest")
                .path("monan")
                .path("SuaMon")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(mon, MediaType.APPLICATION_JSON));

        if (res.getStatus() != 200) {
            res.close();
            response.sendRedirect("Loi");
            return;
        }

        res.close();
        response.sendRedirect("QuanLyMon");
	}
}

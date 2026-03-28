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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import cit.ctu.QLDTAN.MonAn;
import cit.ctu.QLDTAN.NguoiDung;

import static cit.ctu.Constants.BASE_API_URL_MON_AN;

/**
 * Servlet implementation class TimMon
 */
@WebServlet("/TimMon")
public class TimMon extends HttpServlet {
	private static final long serialVersionUID = 1L;      
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri =
            UriBuilder.fromUri(BASE_API_URL_MON_AN).build();
    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);
    public TimMon() {
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
		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {
			response.sendRedirect("DangNhap");
			return;
		}
		
		NguoiDung nd = (NguoiDung) session.getAttribute("user");

		String id = request.getParameter("id");

		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
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
		out.println(".btn-outline-main{color:#ff6b2c;border:1px solid #ff6b2c;background:white;}");
        out.println(".btn-outline-main:hover{background:#ff6b2c;color:white;}");
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
		out.println("<form class='d-flex align-items-center mb-4'>");		
		out.println("<a href='QuanLyMon' class='btn btn-outline-main'>");
		out.println("<i class='bi bi-arrow-left'></i>");
		out.println("</a>");
		out.println("<input class='form-control me-2 mx-2' name='id' placeholder='Nhập ID món'>");
		out.println("<button class='btn btn-main me-2'>");
		out.println("<i class='bi bi-search'></i>");
		out.println("</button>");
		out.println("</form>");

		if(id != null){
		    try{
		    	MonAn m = target
		    	        .path("rest")
		    	        .path("monan")
		    	        .path("TimMon")
		    	        .path(id)
		    	        .request(MediaType.APPLICATION_JSON)
		    	        .get(MonAn.class);
		    	if (m == null) {
		    	    out.println("<div class='alert alert-danger'>Không tìm thấy món</div>");
		    	} else {
		    		out.println("<div class='card p-4'>");
			        out.println("<h5 class='mb-3'>Kết quả tìm kiếm</h5>");
			        out.println("<p><b>ID:</b> " + m.getId() + "</p>");
			        out.println("<p><b>Tên món:</b> " + m.getTenMon() + "</p>");
			        out.println("<p><b>Mô tả:</b> " + m.getMoTa() + "</p>");
			        out.println("<p><b>Số lượng:</b> " + m.getSoLuong() + "</p>");
			        out.println("<p><b>Giá:</b> " + m.getGia() + "</p>");
			        out.println("<p><b>Trạng thái:</b> " + m.getTrangThai() + "</p>");
			        out.println("<div class='mt-3'>");
			        out.println("<a class='btn btn-outline-main me-2' href='SuaMon?id=" + m.getId() + "'>");
			        out.println("<i class='bi bi-pencil'></i> Sửa");
			        out.println("</a>");
			        out.println("<a class='btn btn-main' onclick=\"return confirm('Bạn có chắc muốn xóa món này?')\" href='XoaMon?id=" + m.getId() + "'>");
			        out.println("<i class='bi bi-trash'></i> Xóa");
			        out.println("</a>");
			        out.println("</div>");
			        out.println("</div>");
		    	}      
		    }catch(Exception e){
		        out.println("<div class='alert alert-danger'>");
		        out.println("Không tìm thấy món");
		        out.println("</div>");
		    }
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

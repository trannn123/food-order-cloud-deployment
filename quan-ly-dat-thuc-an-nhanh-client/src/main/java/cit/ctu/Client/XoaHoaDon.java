package cit.ctu.Client;
import java.io.IOException;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import static cit.ctu.Constants.BASE_API_URL_HOA_DON;

/**
 * Servlet implementation class XoaHoaDon
 */
@WebServlet("/XoaHoaDon")
public class XoaHoaDon extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri= UriBuilder
            .fromUri(BASE_API_URL_HOA_DON).build();
	Client client = ClientBuilder.newClient();
    WebTarget target = client.target(uri);  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XoaHoaDon() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        Object nd = session.getAttribute("user");
        if (nd == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            int hoaDonId = Integer.parseInt(idStr);

            Response apiResponse = target
                    .path("rest")
                    .path("hoadon")
                    .path("XoaHoaDon/" + hoaDonId)
                    .request(MediaType.APPLICATION_JSON)
                    .delete();

            if (apiResponse.getStatus() == 200) {
                System.out.println("Đã xóa hóa đơn #" + hoaDonId);
            } else {
                System.out.println("Xóa hóa đơn #" + hoaDonId + " thất bại!");
            }
        }
        response.sendRedirect("QuanLyHoaDon");
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

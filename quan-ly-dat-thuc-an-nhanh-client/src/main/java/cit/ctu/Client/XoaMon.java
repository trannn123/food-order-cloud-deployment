package cit.ctu.Client;
import java.io.IOException;
import java.net.URI;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;

import static cit.ctu.Constants.BASE_API_URL_MON_AN;

/**
 * Servlet implementation class XoaMon
 */
@WebServlet("/XoaMon")
public class XoaMon extends HttpServlet {
	private static final long serialVersionUID = 1L;      
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri =
			UriBuilder.fromUri(BASE_API_URL_MON_AN).build();
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(uri);
    public XoaMon() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		Response res = target
		        .path("rest")
		        .path("monan")
		        .path("XoaMon")
		        .path(id)
		        .request()
		        .delete();

		if (res.getStatus() != 200) {
		    res.close();
		    response.sendRedirect("Lỗi");
		    return;
		}
		res.close();
		response.sendRedirect("QuanLyMon");
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

package cit.ctu;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("/monan")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class MonAnWS {
    IMonAn service = new MonAnImpl();

    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok("pong").build();
    }
    
    @GET
    @Path("/LayDanhSachMonCon")
    public Response layDanhSachMonCon() {
        List<MonAn> kq = service.layDanhSachMonCon();
        return kq != null
                ? Response.ok(kq).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/LayDanhSachMonAn")
    public Response layDanhSachMonAn() {
        List<MonAn> kq = service.layDanhSachMon();
        return kq != null
                ? Response.ok(kq).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/ThemMon")
    public Response themMon(MonAn mon) {
        mon.setTrangThai("con");
        boolean kq = service.themMon(mon);
        return kq
                ? Response.ok("Thêm món thành công").build()
                : Response.status(Response.Status.BAD_REQUEST).entity("Thêm thất bại").build();
    }

    @PUT
    @Path("/SuaMon")
    public Response suaMon(MonAn mon) {
        boolean kq = service.suaMon(mon);
        return kq
                ? Response.ok("Sửa thành công").build()
                : Response.status(Response.Status.BAD_REQUEST).entity("Sửa thất bại").build();
    }
    
    @DELETE
    @Path("/XoaMon/{id}")
    public Response xoaMon(@PathParam("id") int id) {
        boolean kq = service.xoaMon(id);
        if (kq) {
            return Response.ok("Xóa thành công").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Xóa thất bại")
                    .build();
        }
    }
    
    @GET
    @Path("/TimMon/{id}")
    public Response timMon(@PathParam("id") int id) {
        MonAn mon = service.timMonTheoId(id);
        if(mon != null){
            return Response.ok(mon).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Không tìm thấy món")
                .build();
    } 
    
    @GET
    @Path("/GiamSoLuong/{id}/{number}")
    public Response giamSoLuongMonAn(@PathParam("id") int id,@PathParam("number") int number) {
    	boolean result = service.giamSoLuongMon(id, number);
    	if(result) {
    		return Response.ok("Giảm số lượng món thành công").build();
    	}
    	return Response.status(Response.Status.BAD_REQUEST)
    			.entity("Giảm số lượng không thành công")
    			.build();
    }
}
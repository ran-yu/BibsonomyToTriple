package GetBibAPI;

import edu.l3s.GetBibsonomy;
import org.bibsonomy.rest.client.exception.ErrorPerformingRequestException;
import org.codehaus.jettison.json.JSONException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by ranyu on 7/14/16.
 */

@WebService(serviceName = "GetBib")
@Path("/GetBib")
//@Stateless()

public class GetBibApi {
    @GET
    @WebMethod
    @Produces(MediaType.TEXT_HTML)
    @Path("/getUsers")
    public String getUsers(@QueryParam("start") final int s,
                           @QueryParam("end") final int e) throws Exception {
        String res = "";
        GetBibsonomy gb = new GetBibsonomy();

        res = gb.get_users(s,e);

        return res;
    }
    @GET
    @WebMethod
    @Produces(MediaType.TEXT_HTML)
    @Path("/getUserDetail")
    public String getUserDetail(@QueryParam("username") final String username) throws ErrorPerformingRequestException, JSONException, IOException {
        String res = "";
        GetBibsonomy gb = new GetBibsonomy();

        res = gb.get_user_detail(username).toString();
        return res;
    }
//    @GET
//    @WebMethod
//    @Produces(MediaType.TEXT_HTML)
//    @Path("/getUserPosts")
//    public String getUserPosts(@QueryParam("user_name") final String username) throws ErrorPerformingRequestException, JSONException, IOException {
//        String res = "";
//        GetBibsonomy gb = new GetBibsonomy();
//
//        res = gb.get_user_posts(username).toString();
//
//        return res;
//    }
    @GET
    @WebMethod
    @Produces(MediaType.TEXT_HTML)
    @Path("/getPosts")
    public String getPosts(@QueryParam("start") final int s,
                           @QueryParam("end") final int e) throws IOException, JSONException {
        String res = "";
        GetBibsonomy gb = new GetBibsonomy();

        res = gb.get_posts(s,e);
        return res;
    }

}

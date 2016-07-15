package edu.l3s;

import org.bibsonomy.model.BibTex;
import org.bibsonomy.rest.client.exception.ErrorPerformingRequestException;
import org.bibsonomy.rest.client.queries.get.GetUserListQuery;
import org.codehaus.jettison.json.JSONException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        if(args.length != 3){
            System.out.println("ERROR:\twrong parameters.");
            System.exit(1);
        }

        int start = Integer.valueOf(args[1]);
        int end = Integer.valueOf(args[2]);

//        start = 1000;
//        end = 2000;

        GetBibsonomy gb = new GetBibsonomy();




        for( int i = start; i< end ;i+=1000){
            int ret = 0;
            if(args[0].equals("post")){
                gb.get_posts(i, (i+1000) >end?end:(i+1000) );
            }
            else if ( args[0].equals("user")){
                gb.get_users(i, (i+1000) >end?end:(i+1000));
            }
            else{
                System.out.println("ERROR:\twrong parameter 0 : [method].");
                System.exit(1);
            }
            if(ret != 200){
                System.out.println("ERROR:\tget bibsonomy returned: "+ret);
                break ;
            }
        }

//        gb.get_user_detail("yu");
    }

}

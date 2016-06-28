package edu.l3s;

import org.bibsonomy.rest.client.exception.ErrorPerformingRequestException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ErrorPerformingRequestException {
	// write your code here

        GetBibsonomy gb = new GetBibsonomy();

        gb.get_users();
//        gb.get_posts();
//        gb.get_user("yu");
    }

}

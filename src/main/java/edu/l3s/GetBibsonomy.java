package edu.l3s;


import org.bibsonomy.model.User;
import org.bibsonomy.model.logic.LogicInterface;
import org.bibsonomy.rest.client.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;
import org.bibsonomy.model.BibTex;
import org.bibsonomy.model.Post;
import org.bibsonomy.rest.client.exception.ErrorPerformingRequestException;
import org.bibsonomy.rest.client.queries.get.GetPostsQuery;
import org.bibsonomy.rest.client.queries.get.GetUserDetailsQuery;
import org.bibsonomy.rest.client.queries.get.GetUserListQuery;

import java.util.List;


/**
 * Created by ranyu on 6/27/16.
 */
public class GetBibsonomy {

    Bibsonomy _bib;
    String _prefix;

    public GetBibsonomy(){
        // create webservice client object with credentials, set API URL
        _bib = new Bibsonomy("yu", "1c2383a596402eb3b90744b9aecc2e18");
        _bib.setApiURL("http://www.bibsonomy.org/api");
        _prefix = "afel:";
    }

    public void get_bib_data(){

    }
    public void get_users() throws IOException {

        //initial query
        GetUserListQuery guq = new GetUserListQuery(30000,30010);

        try {
            // perform query
            _bib.executeQuery(guq);

            // on success, read results
            if (guq.getHttpStatusCode() == 200) {
                List<User> users = guq.getResult();

                BufferedWriter bw = new BufferedWriter(new FileWriter(new File("users.nq")));

                for( int i = 0; i < users.size(); i++){
                    User user = users.get(i);
                    //write to triple with Afel ontology
                    if(user.getName() != null){
                        bw.write("_:" + user.getName() + " " + _prefix + "userName _:" + user.getName() + " .\n");
                        bw.write("_:" + user.getName() + " " + _prefix + "class " + _prefix + "User .\n");
                        bw.write("_:"+user.getName()+" "+_prefix+"platform <http://www.bibsonomy.org/> .\n");

//                        print_user(user, bw);
                        get_user_detail(user.getName(), bw);

                    }

                    else continue;
//                    bw.write("_:"+user.getName()+" "+_prefix+"id \""+user.getApiKey()+"\" .\n");


                }

                bw.close();
            }
        } catch (ErrorPerformingRequestException e) {
                        /*
                         * happens on network failure for example
                         */
        }
    }

    private void print_user(User user, BufferedWriter bw) throws IOException {
        if(user.getRealname() != null && !user.getRealname().equals("") ){
            bw.write("_:"+user.getName()+" "+_prefix+"fullName \""+user.getRealname()+"\" .\n");
        }
        if(user.getBirthday() != null ){
            bw.write("_:"+user.getName()+" "+_prefix+"dateOfBirth \""+user.getBirthday()+"\" .\n");
        }
        if(user.getPlace() != null ){
            bw.write("_:"+user.getName()+" "+_prefix+"locatedIn \""+user.getPlace()+"\" .\n");
        }
        if(user.getEmail() != null ){
            bw.write("_:"+user.getName()+" "+_prefix+"email \""+user.getEmail()+"\" .\n");
        }
        if(user.getGender() != null ){
            bw.write("_:"+user.getName()+" "+_prefix+"gender \""+user.getGender()+"\" .\n");
        }
        if(user.getProfession() != null ){
            bw.write("_:"+user.getName()+ " "+_prefix+"occupation \""+user.getProfession() +"\" .\n");
        }
        if(user.getInterests() != null ){
            bw.write("_:"+user.getName()+ " "+_prefix+"interests \""+user.getInterests() +"\" .\n");
        }
        if(user.getHobbies() != null ){
            bw.write("_:"+user.getName()+ " "+_prefix+"hobbies \""+user.getHobbies() +"\" .\n");
        }
        if(user.getHomepage() != null ){
            bw.write("_:"+user.getName()+ " "+_prefix+"homepage <"+user.getHomepage() +"> .\n");
        }
    }
    public void get_user_detail(String id, BufferedWriter bw) throws ErrorPerformingRequestException, IOException {
        GetUserDetailsQuery gudq = new GetUserDetailsQuery(id);
        _bib.executeQuery(gudq);
        User user = gudq.getResult();

       print_user(user, bw);
        //get all posts
        if(user.getPosts() != null){
            List<Post<?>> posts = user.getPosts();


        }

    }

    public void get_posts(){
        // instantiate query object
        GetPostsQuery gpq = new GetPostsQuery();

        // some queries can be parametrized
        // -> in this example we want to fetch only bibtex entries
        gpq.setResourceType(BibTex.class);

        try {
            // perform query
            _bib.executeQuery(gpq);

            // on success, read results
            if (gpq.getHttpStatusCode() == 200) {
                List<Post<?>> posts = gpq.getResult();

                for (Post post : posts) {
                                        /*
                                         * now do something with your posts :)
                                         * e.g. print the bibtex keys
                                         */
                    BibTex bibEntry = (BibTex) post.getResource();
                    System.out.println(bibEntry.getBibtexKey());
                }
            }
        } catch (ErrorPerformingRequestException e) {
                        /*
                         * happens on network failure for example
                         */
        }
    }
    private void get_group(){

    }
    private void get_friends(){

    }
}

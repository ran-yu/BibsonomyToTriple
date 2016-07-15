package edu.l3s;


import org.bibsonomy.model.User;
import org.bibsonomy.model.logic.LogicInterface;
import org.bibsonomy.rest.client.*;

import java.io.*;
import java.nio.Buffer;
import java.util.List;
import org.bibsonomy.model.BibTex;
import org.bibsonomy.model.Post;
import org.bibsonomy.rest.client.exception.ErrorPerformingRequestException;
import org.bibsonomy.rest.client.queries.get.GetPostsQuery;
import org.bibsonomy.rest.client.queries.get.GetUserDetailsQuery;
import org.bibsonomy.rest.client.queries.get.GetUserListQuery;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;


/**
 * Created by ranyu on 6/27/16.
 */
public class GetBibsonomy {

    Bibsonomy _bib;
    String _prefix;

    public GetBibsonomy(){
        // create webservice client object with credentials, set API URL
        _bib = new Bibsonomy("yu", "4902223a8bc5a14f0e9f37053eb3b560");
        _bib.setApiURL("http://www.bibsonomy.org/api");
        _prefix = "afel:";
    }

    public void get_bib_data(){

    }

//    public int get_users(int start, int end) throws IOException, InterruptedException {
//
//        System.out.println("User #NO : " + start + " - " + end);
//        //initial query
//        GetUserListQuery guq = new GetUserListQuery(start,end);
//        int res = 0;
//        try {
//            // perform query
//            _bib.executeQuery(guq);
//
//            // on success, read results
//             res = guq.getHttpStatusCode();
//            if ( res == 200) {
//                List<User> users = guq.getResult();
//
//                BufferedWriter bw = new BufferedWriter(new FileWriter(new File("users_"+start+"-"+end+".nq")));
//
//                for( int i = 0; i < users.size(); i++){
//                    User user = users.get(i);
//                    //write to triple with Afel ontology
//                    String username = user.getName();
//                    if(username_is_valid(username)){
////                        System.out.println("User #NO : " + i + " : "+ username);
//                        bw.write("_:" + username + " " + _prefix + "userName _:" + username + " .\n");
//                        bw.write("_:" + username + " rdf:type " + _prefix + "User .\n");
//                        bw.write("_:"+username+" "+_prefix+"platform <http://www.bibsonomy.org/> .\n");
//
////                        print_user(user, bw);
//                        Thread.sleep(100);
//                        get_user_detail(user.getName(), bw);
//
//                    }
//
//                    else continue;
////                    bw.write("_:"+user.getName()+" "+_prefix+"id \""+user.getApiKey()+"\" .\n");
//
//
//                }
//
//                bw.close();
//            }
//        } catch (ErrorPerformingRequestException error) {
//                        /*
//                         * happens on network failure for example
//                         */
//        }
//        return res;
//    }
    public String get_users(int start, int end) throws Exception {

        JSONObject json_users = new JSONObject();

        System.out.println("User #NO : " + start + " - " + end);
        //initial query
        GetUserListQuery guq = new GetUserListQuery(start,end);
        int res = 0;
        try {
            // perform query
            _bib.executeQuery(guq);

            // on success, read results
            res = guq.getHttpStatusCode();
            if ( res == 200) {
                List<User> users = guq.getResult();


                for( int i = 0; i < users.size(); i++){
                    User user = users.get(i);


                    //write to triple with Afel ontology
                    String username = user.getName();
                    if(username_is_valid(username)){
                        JSONObject json_user = get_user_detail(user.getName());
                        Thread.sleep(100);
                        json_users.put("_:"+user.getName(), json_user);
                    }

                    else continue;
//                    bw.write("_:"+user.getName()+" "+_prefix+"id \""+user.getApiKey()+"\" .\n");


                }

            }
        } catch (ErrorPerformingRequestException error) {
                        /*
                         * happens on network failure for example
                         */
        }
        return json_users.toString();
    }
    private boolean username_is_valid(String str){
        if(str == null || str.length() > 15 || str.length() < 1) return false;
        for(int i=0; i< str.length(); i++){
            char chr = str.charAt(i);
            if(('A' <= chr && 'Z' >= chr) ||
                    ('a' <= chr && 'z' >= chr) ||
                    ('0' <= chr && '9' >= chr) ||
                    (chr == '.') || ('-' == chr) || ('_' == chr)) {
                continue;
            }
            else return false;
        }
        return true;
    }
//    private void print_user(User user, BufferedWriter bw) throws IOException {
//        if(user.getRealname() != null && !user.getRealname().equals("") ){
//            bw.write("_:"+user.getName()+" "+_prefix+"fullName \""+user.getRealname()+"\" .\n");
//        }
//        if(user.getBirthday() != null ){
//            bw.write("_:"+user.getName()+" "+_prefix+"dateOfBirth \""+user.getBirthday()+"\" .\n");
//        }
//        if(user.getPlace() != null ){
//            bw.write("_:"+user.getName()+" "+_prefix+"locatedIn \""+user.getPlace()+"\" .\n");
//        }
//        if(user.getEmail() != null ){
//            bw.write("_:"+user.getName()+" "+_prefix+"email \""+user.getEmail()+"\" .\n");
//        }
//        if(user.getGender() != null ){
//            bw.write("_:"+user.getName()+" "+_prefix+"gender \""+user.getGender()+"\" .\n");
//        }
//        if(user.getProfession() != null ){
//            bw.write("_:"+user.getName()+ " "+_prefix+"occupation \""+user.getProfession() +"\" .\n");
//        }
//        if(user.getInterests() != null ){
//            bw.write("_:"+user.getName()+ " "+_prefix+"interests \""+user.getInterests() +"\" .\n");
//        }
//        if(user.getHobbies() != null ){
//            bw.write("_:"+user.getName()+ " "+_prefix+"hobbies \""+user.getHobbies() +"\" .\n");
//        }
//        if(user.getHomepage() != null ){
//            bw.write("_:"+user.getName()+ " "+_prefix+"homepage <"+user.getHomepage() +"> .\n");
//        }
//        if(user.getInstitution() != null ){
//            bw.write("_:"+user.getName()+ " "+_prefix+"member \""+user.getInstitution() +"\" .\n");
//        }
//        if(user.getInstitution() != null ){
//            bw.write("_:"+user.getName()+ " "+_prefix+"member \""+user.getInstitution() +"\" .\n");
//        }
//    }
//    public void get_user_detail(String id, BufferedWriter bw) throws ErrorPerformingRequestException, IOException {
////        System.out.println("Username: "+ id);
//        GetUserDetailsQuery gudq = new GetUserDetailsQuery(id);
//        _bib.executeQuery(gudq);
//        if(gudq.getHttpStatusCode() == 200 && gudq.isExecuted() && gudq.isSuccess()){
//            User user = gudq.getResult();
//            print_user(user, bw);
//        }
//    }

    public JSONObject get_user_detail(String id) throws ErrorPerformingRequestException, IOException, JSONException {

        JSONObject json_user = new JSONObject();
        json_user.put(_prefix + "userName", "_:" + id);
        json_user.put("rdf:type", _prefix + "User");
        json_user.put(_prefix+"platform", "<http://www.bibsonomy.org/>");


        System.out.println("Username: "+ id);
        GetUserDetailsQuery gudq = new GetUserDetailsQuery(id);
        _bib.executeQuery(gudq);
        if(gudq.getHttpStatusCode() == 200 && gudq.isExecuted() && gudq.isSuccess()){
            User user = gudq.getResult();

            if(user.getRealname() != null && !user.getRealname().equals("") ){
                json_user.put(_prefix+"fullName", "\""+user.getRealname()+"\"");
            }
            if(user.getBirthday() != null ){
                json_user.put(_prefix+"dateOfBirth", "\""+user.getBirthday()+"\"");
            }
            if(user.getPlace() != null ){
                json_user.put(_prefix+"locatedIn" , "\""+user.getPlace()+"\"");
            }
            if(user.getEmail() != null ){
                json_user.put(_prefix+"email", "\""+user.getEmail()+"\"");
            }
            if(user.getGender() != null ){
                json_user.put(_prefix+"gender", "\""+user.getGender()+"\"");
            }
            if(user.getProfession() != null ){
                json_user.put(_prefix+"occupation", "\"user.getProfession()\"");
            }
            if(user.getInterests() != null ){
                json_user.put(_prefix+"interests", "\""+user.getInterests() +"\"");
            }
            if(user.getHobbies() != null ){
                json_user.put(_prefix+"hobbies", "\""+user.getHobbies() +"\"");
            }
            if(user.getHomepage() != null ){
                json_user.put(_prefix+"homepage", "<"+user.getHomepage() +">" );
            }
            if(user.getInstitution() != null ){
                json_user.put(_prefix+"member", "\""+user.getInstitution() +"\"");
            }
            if(user.getInstitution() != null ){
                json_user.put(_prefix+"member", "\""+user.getInstitution() +"\"");
            }
        }
        return json_user;
    }

    //get all posts
//    public int get_posts(int s, int e) throws IOException {
//        // instantiate query object
//        System.out.println("Post #NO : " + s + " - " + e);
//        GetPostsQuery gpq = new GetPostsQuery(s,e);
//        int res = 0;
//
//        // some queries can be parametrized
//        // -> in this example we want to fetch only bibtex entries
//        gpq.setResourceType(BibTex.class);
//
//        try {
//            // perform query
//            _bib.executeQuery(gpq);
//
//            // on success, read results
//            res = gpq.getHttpStatusCode();
//            if ( res == 200) {
//                List<Post<?>> posts = gpq.getResult();
//
//                BufferedWriter bw = new BufferedWriter(new FileWriter(new File("posts_"+s+"-"+e+".nq")));
//
//                for (Post post : posts) {
//                    BibTex bibEntry = (BibTex) post.getResource();
////                    System.out.println(bibEntry.getBibtexKey());
//                    if(bibEntry.getBibtexKey() != null){
//////                        bw.write("_:" + bibEntry.getBibtexKey() + " " + _prefix + "userName _:" + user.getName() + " .\n");
////                        bw.write("_:" + bibEntry.getBibtexKey() + " " + _prefix + "class " + _prefix + "User .\n");
////                        bw.write("_:"+user.getName()+" "+_prefix+"platform <http://www.bibsonomy.org/> .\n");
//                        bw.write("_:" + bibEntry.getBibtexKey() + " rdf:type " + _prefix + "SocialMediaPosting .\n");
//                        bw.write("_:" + bibEntry.getBibtexKey() + " " + _prefix + "hostingPlatform <http://www.bibsonomy.org/>\n");
//                        bw.write("_:"+bibEntry.getBibtexKey() + " "+_prefix+"user \""+ post.getUser().getName() +"\" .\n");
//                        print_post(bibEntry, bw);
//
//                    }
//
//                }
//
//                bw.close();
//            }
//        } catch (ErrorPerformingRequestException error) {
//                        /*
//                         * happens on network failure for example
//                         */
//        }
//        return res;
//    }
    //get all posts

    public String get_posts(int s, int e) throws IOException, JSONException {
        // instantiate query object
        System.out.println("Post #NO : " + s + " - " + e);
        GetPostsQuery gpq = new GetPostsQuery(s,e);
        int res = 0;

        JSONObject json_posts = new JSONObject();

        // some queries can be parametrized
        // -> in this example we want to fetch only bibtex entries
        gpq.setResourceType(BibTex.class);

        try {
            // perform query
            _bib.executeQuery(gpq);

            // on success, read results
            res = gpq.getHttpStatusCode();
            if ( res == 200) {
                List<Post<?>> posts = gpq.getResult();

                for (Post post : posts) {

                    JSONObject json_post = new JSONObject();

                    BibTex bibEntry = (BibTex) post.getResource();
//                    System.out.println(bibEntry.getBibtexKey());
                    if(bibEntry.getBibtexKey() != null){
                        json_post.put("rdf:type", _prefix + "SocialMediaPosting");
                        json_post.put(_prefix + "hostingPlatform", "<http://www.bibsonomy.org/>");
                        json_post.put(_prefix+"user", "\""+post.getUser().getName()+"\"");

                        BibTex resource = bibEntry;
                        if(resource.getAuthor() != null ){
                            json_post.put(_prefix+"author", "\""+ resource.getAuthor() +"\"" );
                        }
                        if(resource.getTitle() != null ){
                            json_post.put(_prefix+"headline", "\""+ resource.getTitle() +"\"");
                        }
                        if(resource.getEntrytype() != null ){
                            json_post.put(_prefix+"learningResourceType", "\""+ resource.getEntrytype() +"\"");
                        }
                        if(resource.getEditor() != null ){
                            json_post.put(_prefix+"editor", "\""+ resource.getEditor() +"\"");
                        }
                        if(resource.getPages() != null ){
                            String[] pages = resource.getPages().split("-");
                            if(pages.length ==2 ) {
                                json_post.put(_prefix + "pageStart", "\"" + pages[0] + "\"");
                                json_post.put(_prefix + "pageEnd", "\"" + pages[1] + "\"");
                            }
                        }
                        if(resource.getPublisher() != null ){
                            json_post.put(_prefix+"publisher", "\""+ resource.getPublisher() +"\"");
                        }
                        if(resource.getYear() != null ){
                            json_post.put(_prefix+"copyrightYear", "\""+ resource.getYear() +"\"");
                        }
                        if(resource.getUrl() != null ){
                            json_post.put(_prefix+"url", "<"+ resource.getUrl() +">");
                        }

                    }

                    json_posts.put("_:" + bibEntry.getBibtexKey(), json_post);

                }
            }
        } catch (ErrorPerformingRequestException error) {
                        /*
                         * happens on network failure for example
                         */
        }
        return json_posts.toString();
    }


    private void print_post( BibTex resource, BufferedWriter bw) throws IOException {
        if(resource.getAuthor() != null ){
            bw.write("_:"+resource.getBibtexKey() + " "+_prefix+"author \""+ resource.getAuthor() +"\" .\n");
        }
        if(resource.getTitle() != null ){
            bw.write("_:"+resource.getBibtexKey() + " "+_prefix+"headline \""+ resource.getTitle() +"\" .\n");
        }
        if(resource.getEntrytype() != null ){
            bw.write("_:"+resource.getBibtexKey() + " "+_prefix+"learningResourceType \""+ resource.getEntrytype() +"\" .\n");
        }
        if(resource.getEditor() != null ){
            bw.write("_:"+resource.getBibtexKey() + " "+_prefix+"editor \""+ resource.getEditor() +"\" .\n");
        }
        if(resource.getPages() != null ){
            String[] pages = resource.getPages().split("-");
            if(pages.length ==2 ) {
                bw.write("_:" + resource.getBibtexKey() + " " + _prefix + "pageStart \"" + pages[0] + "\" .\n");
                bw.write("_:" + resource.getBibtexKey() + " " + _prefix + "pageEnd \"" + pages[1] + "\" .\n");
            }
        }
        if(resource.getPublisher() != null ){
            bw.write("_:"+resource.getBibtexKey() + " "+_prefix+"publisher \""+ resource.getPublisher() +"\" .\n");
        }
        if(resource.getYear() != null ){
            bw.write("_:"+resource.getBibtexKey() + " "+_prefix+"copyrightYear \""+ resource.getYear() +"\" .\n");
        }
        if(resource.getUrl() != null ){
            bw.write("_:"+resource.getBibtexKey() + " "+_prefix+"url <"+ resource.getUrl() +"> .\n");
        }

    }
    private void get_group(){

    }
    private void get_friends(){

    }
}

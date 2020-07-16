package com.itcr.datos.cooktimeserver.restfull;

import com.itcr.datos.cooktimeserver.data_structures.AlphNodeSplay;
import com.itcr.datos.cooktimeserver.data_structures.AlphNodeTree;
import com.itcr.datos.cooktimeserver.data_structures.AlphSplayTree;
import com.itcr.datos.cooktimeserver.data_structures.SinglyList;
import com.itcr.datos.cooktimeserver.object.Company;
import com.itcr.datos.cooktimeserver.object.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class will be called whenever we need to modify, clear, add or access the list of companies.
 */
public class CompanyTree {
    private static AlphSplayTree<Company> splayCompanyTree;

    /**
     * This method initializes the user list and adds all of the users from the Recipe.json json file
     */
    public static void initSplayList(){
        splayCompanyTree = new AlphSplayTree<Company>();
        updateCompanyList();
    }
    /**
     * This method will access to the Companies.json and load all of the users in the CompanyList
     */
    public static void updateCompanyList(){
        splayCompanyTree.clear();

        JSONParser companyParser = new JSONParser();
        try{
            JSONObject companyJSON = (JSONObject) companyParser.parse(new FileReader("res/data/Companies.json"));
            getBranch(companyJSON);
        }
        catch (Exception e){
            e.printStackTrace();
            getBranch(new JSONObject());
        }
    }

    /**
     * Function that casts the json object into company object
     * @param jsonObject
     */
    private static void getBranch(JSONObject jsonObject){
        Company newCompany = new Company();

        try{newCompany.setName(jsonObject.get("name").toString());}
        catch (NullPointerException e){newCompany.setName(null);}

        try{newCompany.setEmail(jsonObject.get("email").toString());}
        catch (NullPointerException e){newCompany.setEmail(null);}

        try{newCompany.setSchedule(jsonObject.get("schedule").toString());}
        catch (NullPointerException e){newCompany.setSchedule(null);}

        try{newCompany.setLogo(jsonObject.get("logo").toString());}
        catch (NullPointerException e){newCompany.setLogo(null);}

        try{newCompany.setLocation(jsonObject.get("location").toString());}
        catch (NullPointerException e){ newCompany.setLocation(null);}

        try{newCompany.setNumber(Integer.parseInt(jsonObject.get("number").toString()));}
        catch (NullPointerException e){newCompany.setNumber(0);}

        try{newCompany.setPosts(Integer.parseInt(jsonObject.get("number").toString()));}
        catch (NullPointerException e){newCompany.setPosts(0);}

        try{newCompany.setFollowers(TypeConversion.makeStringList((JSONArray)jsonObject.get("followers"), new SinglyList<String>()));}
        catch (NullPointerException e){newCompany.setFollowers(new SinglyList<String>());}

        try{newCompany.setFollowing(TypeConversion.makeStringList((JSONArray) jsonObject.get("following"), new SinglyList<String>()));}
        catch (NullPointerException e){newCompany.setFollowing(new SinglyList<String>());}

        try{newCompany.setMembers(TypeConversion.makeStringList((JSONArray) jsonObject.get("members"), new SinglyList<String>()));}
        catch (NullPointerException e){newCompany.setFollowing(new SinglyList<String>());}

        splayCompanyTree.add(newCompany, newCompany.getName());

        if(jsonObject.get("right")!=null){
            getBranch((JSONObject) jsonObject.get("right"));
        }
        if(jsonObject.get("left")!=null){
            getBranch((JSONObject) jsonObject.get("left"));
        }
    }
    /**
     * This method is a getter for the UserList
     * @return UserList
     */
    public static AlphSplayTree<Company> getUserTree(){
        return splayCompanyTree;
    }
    /**
     * This method receives a User and it adds it to the UserList
     * @param newCompany
     */
    public static void addUser(Company newCompany){
        if(newCompany!=null){
            splayCompanyTree.add(newCompany, newCompany.getEmail());
            saveUser();
            updateCompanyList();
        }
        else{
            System.out.println("Something went wrong while adding the user, the user was empty");
        }
    }
    /**
     * This method will write the users into the Recipe.json
     */
    public static void saveUser(){
        try(FileWriter file = new FileWriter("res/data/Users.json")){
            file.write(splayTravel(splayCompanyTree.getRoot(), new JSONObject()).toString());
            file.flush();

        }
        catch (IOException e) { e.printStackTrace();}
    }

    /**
     * Recursive function that stores the data into the json
     * @param company the node
     * @param jsonObject the json object
     * @return returns the jsonObject
     */
    @SuppressWarnings("unchecked")
    public static JSONObject splayTravel(AlphNodeSplay<Company> company, JSONObject jsonObject){

        try{jsonObject.put("name", company.getData().getName());}
        catch (NullPointerException e){jsonObject.put("title",null);}

        try{jsonObject.put("email", company.getData().getEmail());}
        catch (NullPointerException e){jsonObject.put("email",null);}

        try{jsonObject.put("schedule", company.getData().getSchedule());}
        catch (NullPointerException e){jsonObject.put("schedule",null);}

        try{jsonObject.put("logo", company.getData().getLogo());}
        catch (NullPointerException e){jsonObject.put("logo",null);}

        try{jsonObject.put("location", company.getData().getLocation());}
        catch (NullPointerException e){jsonObject.put("location",null);}

        try{jsonObject.put("number", company.getData().getNumber());}
        catch (NullPointerException e){jsonObject.put("number",null);}

        try{jsonObject.put("posts", company.getData().getPosts());}
        catch (NullPointerException e){jsonObject.put("posts",null);}


        try{jsonObject.put("followers",TypeConversion.makeStringArray(company.getData().getFollowers(), new JSONArray()));}
        catch (NullPointerException e){jsonObject.put("followers",null);}

        try{jsonObject.put("following",TypeConversion.makeStringArray(company.getData().getFollowing(), new JSONArray()));}
        catch (NullPointerException e){jsonObject.put("following",null);}

       try{jsonObject.put("users", TypeConversion.makeStringArray(company.getData().getMembers(),new JSONArray()));}
       catch (NullPointerException e){jsonObject.put("user",null);}

        jsonObject.put("left", null);
        jsonObject.put("right",null);

        if(company.getLeft()!=null){
            jsonObject.replace("left", splayTravel(company.getLeft(), new JSONObject()));
        }
        if(company.getRight()!=null){
            jsonObject.replace("right", splayTravel(company.getRight(), new JSONObject()));
        }
        return jsonObject;
    }
}

package com.example.raccacoonie;

import java.util.ArrayList;

public class User
/**
 * User handling class, VERY EARLY PROTOTYPE
 */
{

    int id;
    String username;
    String password;
    String picture;
    ArrayList<Integer> posts;

    public User(String username, String password, String picture) {
        this.username = username;
        this.password = password;
        this.picture = picture;

        this.id = -1; // if not set by the database, id is -1
        posts = new ArrayList<>();
    }

    public int getPost(int id)
    /**
     * returns the index of the post asked. Returns -1 if the post with that id has not been created by this user or does not exist
     */
    {
        for (int i = 0; i < posts.size();i++)
        {
            if (posts.get(i) == id)
            {
                return i;
            }
        }
        return -1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

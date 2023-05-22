package com.example.raccacoonie;

/**
 * Handles all app operations (user interactions,  keeps app info etc
 */
public class Raccacoonie {

    DatabaseHandler db_handler;
    int current_user_id;
    User current_user;

    Raccacoonie(int id)
    {


        if (this.db_handler.userExists(id))
        {
            this.current_user = this.db_handler.getUserById(id);
            this.current_user_id = id;
        }
        else
        {
            this.current_user_id = 1;
            this.current_user = this.db_handler.getUserById(1);
        }


    }

    public int getCurrent_user_id() {
        return current_user_id;
    }

    public void setCurrent_user_id(int current_user_id) {
        this.current_user_id = current_user_id;
    }



}

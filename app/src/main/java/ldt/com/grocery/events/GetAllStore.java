package ldt.com.grocery.events;

import java.util.List;

import ldt.com.grocery.database.User;

/**
 * Created by dtlinh on 11/19/2015.
 */
public class GetAllStore {
    public static class Success {
        public List<User> data;

        public Success(List<User> result) {
            this.data = result;
        }
    }

    public static class Failed {
        public int code;
        public String msg;

        public Failed(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

}

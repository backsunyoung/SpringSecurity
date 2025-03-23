package com.example.securityexam.beforesecurity;

public class UserContext {

    private static final ThreadLocal<User> userThreadLocal = ThreadLocal.withInitial(()->null);

    public static void setUser(User user){
        userThreadLocal.set(user);
    }
    public static User getUser(){
        return userThreadLocal.get();
    }

    public static void clear(){
        userThreadLocal.remove();
    }

  //  private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>(){

//        @Override
//        protected User initialValue(){
//            return super.initialValue();
//        }
 //   };


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gori.scb.controle;

import br.com.gori.scb.dao.impl.UserDAOImpl;
import br.com.gori.scb.entidade.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class UserControlador implements Serializable {

    private final UserDAOImpl userDao;
    private User user;
    private List<User> users = new ArrayList<>();

    public UserControlador() {
        userDao = new UserDAOImpl();
        user = new User();
    }

    @PostConstruct
    public void init() {
        users = userDao.listAll();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}

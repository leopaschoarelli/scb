///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//import br.com.gori.scb.controle.util.HibernateNextId;
//import java.math.BigInteger;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author Leonardo
// */
//public class HibernateNextIdTest {
//
//    private final HibernateNextId hibernateNextId;
//
//    public HibernateNextIdTest() {
//        this.hibernateNextId = HibernateNextId.newInstance("PUBLICACAO_SEQUENCE");
//    }
//
//    public void hello() {
//        BigInteger idZinho = hibernateNextId.getValue();
//        Assert.assertNotNull(idZinho);
//        Assert.assertNotEquals(0, idZinho.longValue());
//    }
//
//    public void bucketTest() {
//        for (int i = 0; i <= 1000; i++) {
//            BigInteger idZinho = hibernateNextId.getValue();
//            System.out.println("ID: " + idZinho);
//            Assert.assertNotNull(idZinho);
//            Assert.assertNotEquals(0, idZinho.longValue());
//        }
//    }
//}

//package com.example.android.sumbangin_android;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class TransaksiTest {
//    private Transaksi transaksi;
//
//    @Test
//    public void testConstractor(){
//        transaksi = new Transaksi("Mobil", "http://www.google.com", "Kendaraan",
//                "Bagus", 2);
//        assertEquals("Mobil", transaksi.getItem_name());
//        assertEquals("http://www.google.com", transaksi.getItem_photo());
//        assertEquals("Kendaraan", transaksi.getCategory());
//        assertEquals("Bagus", transaksi.getDescription());
//        assertEquals(2, transaksi.getUser());
//    }
//
//    @Test
//    public void testGetItemName(){
//        transaksi = new Transaksi();
//        transaksi.setItem_name("Mobil");
//        assertEquals("Mobil", transaksi.getItem_name());
//    }
//
//    @Test
//    public void testSetItemName(){
//        transaksi = new Transaksi();
//        transaksi.setItem_name("Mobil");
//        assertEquals("Mobil", transaksi.getItem_name());
//    }
//
//    @Test
//    public void testGetPhoto(){
//        transaksi = new Transaksi();
//        transaksi.setItem_photo("http://www.google.com");
//        assertEquals("http://www.google.com", transaksi.getItem_photo());
//    }
//
//    @Test
//    public void testSetPhoto(){
//        transaksi = new Transaksi();
//        transaksi.setItem_photo("http://www.google.com");
//        assertEquals("http://www.google.com", transaksi.getItem_photo());
//    }
//
//    @Test
//    public void testGetCategory(){
//        transaksi = new Transaksi();
//        transaksi.setCategory("Kendaraan");
//        assertEquals("Kendaraan", transaksi.getCategory());
//    }
//
//    @Test
//    public void testSetCategory(){
//        transaksi = new Transaksi();
//        transaksi.setCategory("Kendaraan");
//        assertEquals("Kendaraan", transaksi.getCategory());
//    }
//
//    @Test
//    public void testGetDesc(){
//        transaksi = new Transaksi();
//        transaksi.setDescription("Bagus");
//        assertEquals("Bagus", transaksi.getDescription());
//    }
//
//    @Test
//    public void testSetDesc(){
//        transaksi = new Transaksi();
//        transaksi.setDescription("Bagus");
//        assertEquals("Bagus", transaksi.getDescription());
//    }
//
//    @Test
//    public void testGetUser(){
//        transaksi = new Transaksi();
//        transaksi.setUser(2);
//        assertEquals(2, transaksi.getUser());
//    }
//
//    @Test
//    public void testSetUser(){
//        transaksi = new Transaksi();
//        transaksi.setUser(2);
//        assertEquals(2, transaksi.getUser());
//    }
//
//}
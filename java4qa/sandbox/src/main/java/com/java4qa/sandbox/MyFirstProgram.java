package com.java4qa.sandbox;

class MyFirstProgram {

    public static void main(String[] args){
//        hello("world");
//        hello("user");
//        hello("Alexey");
//
//        Square s = new Square(5);
//        System.out.println("Squere area is: " + s.l + "=" + s.area());
//
//        Rectangle r = new Rectangle( 4, 5);
//        System.out.println("Rectangkle area with the lines " + r.a + " и " + r.b + " = " + r.area());
        String a = new String("123");
        String b = new String("123");

        boolean somebody = a.equals(b);
        System.out.println(somebody);
    }

    private static void hello(String somebody) {System.out.println("Hello, " + somebody + "!");}

}

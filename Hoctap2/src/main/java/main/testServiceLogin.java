//package main;
//
//import com.example.demo.model.LoginResponse;
//import com.example.demo.service.DangNhapService;
//
//import java.util.Scanner;
//
//public class testServiceLogin {
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        AuthService authService = new AuthService();
//
//        System.out.print("Nhập username: ");
//        String username = scanner.nextLine();
//
//        System.out.print("Nhập password: ");
//        String password = scanner.nextLine();
//
//        LoginResponse res = authService.login(username, password);
//
//        if (res != null && res.isSuccess()) {
//            System.out.println("CHECK: tài khoản hợp lệ");
//        } else {
//            System.out.println("CHECK: tài khoản không hợp lệ");
//        }
//
//        scanner.close();
//    }
//}
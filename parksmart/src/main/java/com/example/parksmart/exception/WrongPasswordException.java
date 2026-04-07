package com.example.parksmart.exception;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Mật khẩu không chính xác. Vui lòng kiểm tra lại.");
    }
}

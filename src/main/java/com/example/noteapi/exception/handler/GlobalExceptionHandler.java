package com.example.noteapi.exception.handler;

import com.example.noteapi.exception.LoginArgumentNotValidException;
import com.example.noteapi.exception.NoteArgumentNotValidException;
import com.example.noteapi.exception.NoteNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<String> handleNoteNotFoundException(NoteNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // こっちだとエラーメッセージを送れる

//        return ResponseEntity.notFound().build(); // こっちはBodyがないので無理
    }

    @ExceptionHandler(NoteArgumentNotValidException.class)
    public ResponseEntity<String> handleNoteArgumentNotValidException(NoteArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

//    IDもしくはPWのどちらかが空の場合
    @ExceptionHandler(LoginArgumentNotValidException.class)
    public ResponseEntity<String> handleLoginArgumentNotValidException(LoginArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // ユーザIDまたはPWが間違っている場合
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of(
                "error", "BadCredentials",
                "message", "Invalid username or password"
            ));
    }

    // トークンの有効期限切れ
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwt(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "TokenExpired", "message", "JWT token has expired"));
    }

    // 署名が一致しない
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String, String>> handleSignature(SignatureException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "InvalidSignature", "message", "JWT signature is invalid"));
    }

    // トークンの形式が不正
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Map<String, String>> handleMalformed(MalformedJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "MalformedToken", "message", "JWT token is malformed"));
    }
}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {
    @Value("${spring.servlet.multipart.location}") // properties 등록된 설정(경로) 주입
    private String uploadFolder;

    @PostMapping("/upload_email")
    public String uploadEmail( // 이메일, 제목, 메시지를 전달받음
            //원래는 @RequestParam("email") String email 이였으나, 파일 업로드 시 에러 페이지 구현을 위해 필수 입력값이 누락될 경우를 추가함
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "message", required = false) String message,
            RedirectAttributes redirectAttributes) {

        // 이메일 업로드 유효성 검사(필드값이 비어있는지 확인)
        if (email == null || email.isEmpty() || 
        subject == null || subject.isEmpty() || 
        message == null || message.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "모든 필드를 입력해야 합니다.");//에러 발생하면
            return "error_page/article_error"; // 에러 페이지 출력
        }
        try {
            Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            
            }
            String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
            Path filePath = uploadPath.resolve(sanitizedEmail + ".txt"); // 업로드 폴더에 .txt 이름 설정
            System.out.println("File path: " + filePath); // 디버깅용 출력
        
            // 동일한 파일이 존재할 경우 다른 이름으로 저장하기(뒤에 언더바+숫자 붙이기)
            int counter = 1;
            while (Files.exists(filePath)) {
                filePath = uploadPath.resolve(sanitizedEmail + "_" + counter + ".txt");
                counter++;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("메일 제목: " + subject); // 쓰기
                writer.newLine(); // 줄 바꿈
                writer.write("요청 메시지:");
                writer.newLine();
                writer.write(message);
            }

            redirectAttributes.addFlashAttribute("message", "메일 내용이 성공적으로 업로드되었습니다!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "업로드 중 오류가 발생했습니다.");
            return "/error_page/article_error"; // 오류 처리 페이지로 연결
        }

            return "upload_end";
        }
}
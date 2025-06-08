package com.javtest.Controllers;

import com.javtest.Models.Note;
import com.javtest.Models.Order;
import com.javtest.Models.Payment;
import com.javtest.Reprositories.PaymentReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.javtest.Reprositories.NoteReprository;
import com.javtest.Reprositories.OrderReprository;

@Controller
public class PaymentController {

    private static final String TOKEN = "y0_AgAAAABxawxSAAttGAAAAAD954fiAACEyB-Dh4pHgJh930oTGo2L1HWpHQ";
    private static final String BASE_URL = "https://cloud-api.yandex.net/v1/disk/";

    @Autowired
    private PaymentReprository paymentRepository;

    @Autowired
    private NoteReprository noteRepo;
  
    @Autowired
    private OrderReprository orderRepo;

    @GetMapping("/payments")
    public String getAllPayments(Model model) {
        List<Payment> payments = paymentRepository.findAll();
        model.addAttribute("payments", payments);
        return "payments";
    }

    @GetMapping("/payments/{id}")
    public String getPaymentById(@PathVariable Integer id, Model model) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            model.addAttribute("payment", paymentOptional.get());
            return "paymentDetails";
        } else {
            return "paymentNotFound";
        }
    }

    @PostMapping("/uploadPayment")
    public String uploadPayment(@RequestParam("orderId") Integer orderId, Model model, HttpSession session) {

        // Определяем путь для сохранения файла
        //String diskPath = "orders/" + orderId + receipt.getOriginalFilename(); 

        // Получаем URL для загрузки
        

        // Создаем директорию, если она не существует
        /*File directory = new File("orders/" + orderId);
        if (!directory.exists()) {
        directory.mkdirs(); // Создает директорию и все необходимые родительские директории
        }
        
        String uploadUrl = getUploadUrl(diskPath);
        
        try {
        // Сохраняем временный файл на сервере
        File tempFile = new File(directory, receipt.getOriginalFilename());
        receipt.transferTo(tempFile);
        
        // Устанавливаем соединение для загрузки файла на Яндекс.Диск
        HttpURLConnection connection = (HttpURLConnection) new URL(uploadUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Authorization", "OAuth " + TOKEN);
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        
        // Загружаем файл на Яндекс.Диск
        try (OutputStream os = connection.getOutputStream();
        FileInputStream fis = new FileInputStream(tempFile)) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        }
        
        // Проверяем код ответа
        int responseCode = connection.getResponseCode();
        if (responseCode == 201) {
        System.out.println("Файл успешно загружен на Яндекс.Диск.");
        System.out.println("Ссылка на загруженный файл: " + uploadUrl); // Вывод ссылки на файл в консоль
        return "success"; 
        } else {
        System.out.println("Ошибка загрузки файла: " + responseCode);
        return "error"; 
        }
        } catch (IOException e) {
        e.printStackTrace();
        return "error"; 
        }/* */
            String pathCheck = "https://disk.yandex.ru/i/7EYSmdLLJ8NUnA";

            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setPathChek(pathCheck);
            payment.setPaymentDate(new Date());
            paymentRepository.save(payment);
            
            Note newNote = new Note();
            newNote.setContent(pathCheck);
            newNote.setNoteDate(new Date());
            newNote.setIdOrder(orderId);
            noteRepo.save(newNote);
            
            Optional<Order> orderOptional = orderRepo.findById(orderId);
            Order order = orderOptional.get();
            order.setStatusOrder(4);
            orderRepo.save(order);

            Integer userId = (Integer) session.getAttribute("userId");

            return "redirect:/users/" + userId;
}

    /*public static String getUploadUrl(String diskPath) {
        try {
            String url = BASE_URL + "resources/upload?path=" + diskPath + "&overwrite=true";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "OAuth " + TOKEN);

            if (connection.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String jsonResponse = response.toString();
                    // Извлекаем ссылку для загрузки из JSON-ответа
                    return jsonResponse.split("\"href\":\"")[1].split("\"")[0];
                }
            } else {
                System.out.println("Ошибка получения ссылки для загрузки: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }/* */
}


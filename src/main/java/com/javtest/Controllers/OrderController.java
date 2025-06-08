package com.javtest.Controllers;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.javtest.Models.Order;
import com.javtest.Reprositories.OrderReprository;
import com.javtest.Models.Note;
import com.javtest.Reprositories.NoteReprository;
import com.javtest.Models.User;
import com.javtest.Reprositories.UserReprository;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderReprository orderReprository;

    @Autowired
    private NoteReprository noteReprository;
 
    @Autowired
    private UserReprository userRepo;

    @Transactional
    @PostMapping("/updateOrder")
    public String updateOrderResearcher(@RequestParam Map<String, String> researcherIds, Model model) {
        for (Map.Entry<String, String> entry : researcherIds.entrySet()) {
            Integer orderId = Integer.parseInt(entry.getKey());
            Integer newResearcherId = Integer.parseInt(entry.getValue());
            
            Optional<Order> orderOptional = orderReprository.findById(orderId);
            Order order = orderOptional.get();
            order.setResearcherId(newResearcherId);
            order.setStatusOrder(2);
            orderReprository.save(order);
        }
        return "redirect:/users/6"; 
    }

@PostMapping("/createOrder")
public String submitOrder( 
        @RequestParam Integer userId, 
        @RequestParam String molecularSystem1, 
        @RequestParam(required = false) String molecularSystem2, 
        @RequestParam String wishes) {

    Order order = new Order();
    order.setUserId(userId);
    order.setCreationDate(new Date());

    // Проверка на null для molecularSystem2
    if (molecularSystem2 == null || molecularSystem2.equals("null")) {
        order.setCommentOrder("Выбранная сложная молекулярная система: " + molecularSystem1 + "\n" + wishes);
        order.setOrderType(2);
    } else {
        order.setCommentOrder("Выбранные молекулярные системы: " + molecularSystem1 + ", " + molecularSystem2 + "\n" + wishes);
        order.setOrderType(1);
    }

    order.setStatusOrder(1);
    orderReprository.save(order);

    return "redirect:/users/" + userId;
}

    @PostMapping("/createPaymentGateway")
    public String createPaymentGateway(@RequestParam Integer orderId, @RequestParam Double orderAmount, Model model) {

        Optional<Order> orderOptional = orderReprository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            String note = "Сумма оплаты: " + orderAmount;
            Note newNote = new Note();
            newNote.setIdOrder(orderId);
            newNote.setNoteDate(new Date());
            newNote.setContent(note);
            noteReprository.save(newNote);

            order.setStatusOrder(3);
            orderReprository.save(order);
            System.out.println(order.getStatusOrder());
        }

        return "redirect:/users/5";
    }

@PostMapping("/attachLink")
public String attachLink(@RequestParam Integer orderId, @RequestParam String link, Model model) {
    Optional<Order> orderOptional = orderReprository.findById(orderId);

    if (orderOptional.isPresent()) {
        Order order = orderOptional.get();

        // Создание новой записи
        String note = link;
        Note newNote = new Note();
        newNote.setIdOrder(orderId);
        newNote.setNoteDate(new Date());
        newNote.setContent(note);
        noteReprository.save(newNote);

        // Обновление статуса заказа
        order.setStatusOrder(5);
        order.setCompletionDate(new Date());
        orderReprository.save(order);

        // Генерация документа .docx
        generateOrderDocument(order);

        return "redirect:/users/5";
    } else {
        // Обработка случая, когда заказ не найден
        model.addAttribute("error", "Заказ не найден");
        return "error"; // или другой подходящий путь
    }
}

private void generateOrderDocument(Order order) {
    // Создаем новый документ
    try (XWPFDocument document = new XWPFDocument()) {
        // Заголовок
        XWPFParagraph title = document.createParagraph();
        title.createRun().setText("ЗАКАЗ № " + order.getOrderId());
        
        List<Note> notes = noteReprository.findByIdOrder(order.getOrderId());

        List<User> users = userRepo.findAll();
        
        Map<Integer, String> userLogins = new HashMap<>();
        for (User u : users) {
            userLogins.put(u.getId(), u.getLogin());
        }
        
        // Создаем экземпляр SimpleDateFormat для даты и времени
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        // Основные данные с форматированием дат
        document.createParagraph().createRun().setText("Заказчик: " + userLogins.get(order.getUserId()));
        document.createParagraph().createRun().setText("Исследователь: " + userLogins.get(order.getResearcherId()));
        document.createParagraph().createRun().setText("Комментарий к заказу: " + order.getCommentOrder());
        document.createParagraph().createRun().setText("Дата создания заказа: " + dateFormat.format(order.getCreationDate()) + " " + timeFormat.format(order.getCreationDate()));
        document.createParagraph().createRun().setText("Дата выполнения заказа: " + dateFormat.format(order.getCompletionDate()) + " " + timeFormat.format(order.getCompletionDate()));
        document.createParagraph().createRun().setText("Ссылка на чек: " + notes.get(notes.size() - 2).getContent());
        document.createParagraph().createRun().setText("Ссылка на результат: " + notes.get(notes.size() - 1).getContent());

        // Подзаголовок
        XWPFParagraph subtitle = document.createParagraph();
        subtitle.createRun().setText("Примечания по заказу");

        XWPFTable table = document.createTable();

        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("Дата отправки");
        headerRow.createCell().setText("Содержание примечания");

        for (Note note : notes) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(note.getNoteDate().toString());
            row.getCell(1).setText(note.getContent());
        }

        String filePath = "order_" + order.getOrderId() + ".docx"; 
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            document.write(out);
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
    
}

    @PostMapping("/rejectPayment")
    public String rejectPayment(@RequestParam Integer orderId, @RequestParam String reason, Model model) {
        Optional<Order> orderOptional = orderReprository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            
            order.setStatusOrder(3);
            orderReprository.save(order);
            
            Note newNote = new Note();
            newNote.setIdOrder(orderId);
            newNote.setNoteDate(new Date());
            newNote.setContent("Оплата отклонена: " + reason);
            noteReprository.save(newNote);
        }
        return "redirect:/users/5"; 
    }

}


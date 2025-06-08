package com.javtest.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javtest.Models.Order;
import com.javtest.Models.Role;
import com.javtest.Models.StatusOrder;
import com.javtest.Models.TypeOrder;
import com.javtest.Models.User;
import com.javtest.Models.Note;
import com.javtest.Reprositories.NoteReprository;
import com.javtest.Reprositories.OrderReprository;
import com.javtest.Reprositories.RoleReprository;
import com.javtest.Reprositories.StatusOrderReprository;
import com.javtest.Reprositories.TypeOrderReprository;
import com.javtest.Reprositories.UserReprository;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {

    @Autowired
    private UserReprository repo;

    @Autowired
    private RoleReprository roleRepo;

    @Autowired
    private OrderReprository orderRepo;

    @Autowired
    private TypeOrderReprository typeOrderRepo;

    @Autowired
    private StatusOrderReprository statusOrderRepo;
    
    
    @Autowired
    private NoteReprository noteRepo;

    @GetMapping("/")
    public String home(HttpSession session) {
        User user = (User ) session.getAttribute("user");
        if (user != null) {
            return "redirect:/users/" + user.getId();
        }
        return "Authorization";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String login, @RequestParam(required = false) String password,
            HttpSession session, Model model) {
        if (login != null && password != null) {
            Optional<User> userOptional = repo.findByLogin(login);
            if (!userOptional.isPresent()) {
                model.addAttribute("errorMessage", "Пользователь не найден.");
                return "Authorization";
            }
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userRole", user.getRole());
                return "redirect:/users/" + user.getId();
            } else {
                model.addAttribute("errorMessage", "Неверный пароль.");
                return "Authorization";
            }
        }
        return "Authorization";
    }

    @GetMapping("users/{id}")
    public String getUserById(@PathVariable Integer id, Model model, HttpSession session) {
        
        Optional<User> userOptional = repo.findById(id);
        User user = userOptional.get();
        model.addAttribute("user", user);
        
        List<User> users = repo.findAll();
        model.addAttribute("users", users);
        
        Map<Integer, String> userLogins = new HashMap<>();
        for (User  u : users) {
            userLogins.put(u.getId(), u.getLogin());
        }
        model.addAttribute("userLogins", userLogins);

        Map<Integer, String> statusMeanings = new HashMap<>();
        List<StatusOrder> statuses = statusOrderRepo.findAll(); 
        for (StatusOrder status : statuses) {
            statusMeanings.put(status.getId(), status.getMeaning());
        }

        model.addAttribute("statusMeanings", statusMeanings);

        Map<Integer, String> typeMeanings = new HashMap<>();

        List<TypeOrder> types = typeOrderRepo.findAll(); 
        for (TypeOrder type : types) {
            typeMeanings.put(type.getId(), type.getMeaning());
        }
        model.addAttribute("typeMeanings", typeMeanings);
        
        Integer userId = (Integer) session.getAttribute("userId");

        List<Note> notes = noteRepo.findByIdOrder(userId);
        model.addAttribute("notes", notes);
                
        switch (user.getRole()) {
            
            case 1:
                List<Order> customerOrders = orderRepo.findOrdersByUserId(user.getId());
                model.addAttribute("orders", customerOrders);

                Map<Integer, Note> lastNotesMapCustomer = new HashMap<>();
                for (Order order : customerOrders) {
                    List<Note> orderNotes = noteRepo.findByIdOrder(order.getOrderId());
                    if (!orderNotes.isEmpty()) {   
                        Note lastNote = orderNotes.get(orderNotes.size() - 1); 
                        lastNotesMapCustomer.put(order.getOrderId(), lastNote);
                    } else {
                        lastNotesMapCustomer.put(order.getOrderId(), null); 
                    }
                }
                model.addAttribute("lastNotes", lastNotesMapCustomer);

                return "PersonalCustomerAccount";
                    
            case 2:
                List<Order> orders = orderRepo.findAll();
                model.addAttribute("orders", orders); 
                
                List<User> researchers = repo.findByRole(3);
                model.addAttribute("researchers", researchers);

                return "PersonalManagerAccount";

            case 3:
                
                List<Order> researcherOrders = orderRepo.findOrdersByResearcherId(5);
                model.addAttribute("orders", researcherOrders);

                Map<Integer, Note> lastNotesMap = new HashMap<>();
                for (Order order : researcherOrders) {
                    List<Note> orderNotes = noteRepo.findByIdOrder(order.getOrderId());
                    if (!orderNotes.isEmpty()) {   
                        Note lastNote = orderNotes.get(orderNotes.size() - 1); 
                        lastNotesMap.put(order.getOrderId(), lastNote);
                    } else {
                        lastNotesMap.put(order.getOrderId(), null); 
                    }

                }
                model.addAttribute("lastNotes", lastNotesMap);

                return "PersonalResearcherAccount";

            case 4: 
               
                Map<Integer, String> roleMeanings = new HashMap<>();
                List<Role> roles = roleRepo.findAll();
                for (Role role : roles) {
                    roleMeanings.put(role.getId(), role.getMeaning());
                }
                model.addAttribute("role", roleMeanings);
                return "PersonalAdministratorAccount";
        }
        
        return "users";
    }

    @PostMapping("/registration")
    public String registerUser (@RequestParam String login, @RequestParam String password,
                               @RequestParam String confirmed_password, Model model) {
        Optional<User> existingUser = repo.findByLogin(login);
        
        if (existingUser.isPresent()) {
            model.addAttribute("errorMessage", "Пользователь с таким логином уже существует.");
            return "Registration";
        }
        
        if (!password.equals(confirmed_password)) {
            model.addAttribute("errorMessage", "Пароли не совпадают.");
            return "Registration";
        }

        User newUser  = new User();
        newUser .setLogin(login);
        newUser .setPassword(password);
        newUser .setRole(1);
        repo.save(newUser);
        
        return "Authorization";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "Authorization";
    }

    @PostMapping("/updateRoles")
    public String updateRoles(@RequestParam Map<String, String> roles, Model model) {
        for (Map.Entry<String, String> entry : roles.entrySet()) {
            Integer userId = Integer.parseInt(entry.getKey());

            Integer newRole = Integer.parseInt(entry.getValue());

            Optional<User> userOptional = repo.findById(userId);
            User user = userOptional.get();
            user.setRole(newRole);
            repo.save(user);

        }
        
        return "redirect:/users/4";
    }

    @PostMapping("/updateUser")
    public String updateUser (
            @RequestParam String login,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String confirmed_password,
            @RequestParam(required = false) String oldPassword,
            Model model,
            HttpSession session) {

        Integer id = (Integer) session.getAttribute("userId");

        Optional<User> userOptional = repo.findById(id);

        User user = userOptional.get();

        if (oldPassword != null && !oldPassword.isEmpty() && !user.getPassword().equals(oldPassword)) {
            model.addAttribute("errorMessage", "Неверный старый пароль.");
        }

        if (!login.equals(user.getLogin())) {
            Optional<User> existingUser = repo.findByLogin(login);
            if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
                model.addAttribute("errorMessage", "Пользователь с таким логином уже существует.");
            }
            user.setLogin(login);
        }

        if (password != null && !password.isEmpty()) {
            if (confirmed_password == null || !password.equals(confirmed_password)) {
                model.addAttribute("errorMessage", "Пароли не совпадают.");
            }
            user.setPassword(password);
        }

        repo.save(user);
        session.setAttribute("user", user);
        return "Authorization";
    }
    
}


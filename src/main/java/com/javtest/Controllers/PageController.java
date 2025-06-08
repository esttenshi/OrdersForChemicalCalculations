package com.javtest.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.javtest.Models.MolecularSystem;
import com.javtest.Models.ComplexMolecularSystem;
import com.javtest.Models.User;
import com.javtest.Reprositories.ComplexMolecularSystemReprository;
import com.javtest.Reprositories.MolecularSystemReprository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    
    @Autowired
    private MolecularSystemReprository repoMS;
    @Autowired
    private ComplexMolecularSystemReprository repoSMS;

    @GetMapping("/RegistrationHtml")
    public String getRegistrationHtml() {
        return "Registration";
    }

    @GetMapping("/createOrderHtml")
    public String createOrder(HttpSession session, Model model) {
        List<ComplexMolecularSystem> complexMolecularSystems = repoSMS.findAll();
        model.addAttribute("complexMolecularSystems", complexMolecularSystems); 
        List<MolecularSystem> molecularSystems = repoMS.findAll();
        model.addAttribute("molecularSystems", molecularSystems); 
        User user = (User ) session.getAttribute("user");
        model.addAttribute("user", user);
        return "FormingOrder"; 
    }

}

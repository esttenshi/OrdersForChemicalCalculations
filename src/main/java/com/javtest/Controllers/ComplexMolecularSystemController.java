package com.javtest.Controllers;

import com.javtest.Models.ComplexMolecularSystem;
import com.javtest.Reprositories.ComplexMolecularSystemReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complexMolecularSystems") 
public class ComplexMolecularSystemController {

    @Autowired
    private ComplexMolecularSystemReprository complexMolecularSystemRepository;

    @GetMapping
    public List<ComplexMolecularSystem> getAllComplexMolecularSystems() {
        return complexMolecularSystemRepository.findAll();
    }
}

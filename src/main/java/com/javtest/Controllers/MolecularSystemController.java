package com.javtest.Controllers;

import com.javtest.Models.MolecularSystem;
import com.javtest.Reprositories.MolecularSystemReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/molecularSystems") 
public class MolecularSystemController {

    @Autowired
    private MolecularSystemReprository molecularSystemRepository;

    @GetMapping
    public List<MolecularSystem> getAllMolecularSystems() {
        return molecularSystemRepository.findAll();
    }
}

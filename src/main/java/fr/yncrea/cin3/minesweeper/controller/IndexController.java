package fr.yncrea.cin3.minesweeper.controller;

import fr.yncrea.cin3.minesweeper.domain.Minefield;
import fr.yncrea.cin3.minesweeper.domain.MinefieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    private MinefieldRepository minefields;

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("minefields", minefields.findAll());
        return "index";
    }

    @GetMapping("/minesweeper/create")
    public String create(Model model) {
        model.addAttribute("form", new Minefield());
        return "create";
    }

    @PostMapping("/minesweeper/create")
    public String createPost(@ModelAttribute Minefield minefield) {
        minefield.setMinefield();
        minefields.save(minefield);
        return "redirect:/"; //"redirect:/minesweeper/play/" + minefield.getId(); TEST
    }

    @GetMapping("/jeu/{id}")
    public String play(@PathVariable(required = false) UUID id, Model model) {
        Minefield m2 = new Minefield();
        model.addAttribute("m2", m2);
        if (id != null) {
            Minefield m = minefields.findById(id).orElseThrow(() -> new RuntimeException("Minefield not found"));
            m2.setId(m.getId());
            m2.setWidth(m.getWidth());
            m2.setHeight(m.getHeight());
            m2.setCount(m.getCount());
        }
        return "/jeu";

    }
        @PostMapping("/delete/{id}")
        public String delete (@PathVariable UUID id){
            minefields.deleteById(id);
            return "redirect:/";
        }

}

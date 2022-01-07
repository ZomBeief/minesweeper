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

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("form", new Minefield());
        return "create";
    }

   /*@PostMapping("/create")
    public String createPost(@ModelAttribute Minefield form) {
        Minefield m = new Minefield();
        if (form.getId() != null) {
            m = minefields.findById(form.getId()).orElseThrow(() ->  new RuntimeException("User not found"));
        }
        m.setCount(form.getCount());
        m.setWidth(form.getWidth());
        m.setHeight(form.getHeight());
        minefields.save(m);
        return "redirect:/play/" + m.getId();
    }*/

    @PostMapping("/create")
    public String createPost(@ModelAttribute Minefield minefield) {
        minefield.setMinefield();
        minefields.save(minefield);
        return "redirect:/play/" + minefield.getId();
    }

    @GetMapping("/play/{id}")
    public String play(@PathVariable UUID id, Model model) {
        Minefield m = minefields.findById(id).orElseThrow(() -> new RuntimeException("Minefield not found"));


        model.addAttribute("m", m);
        return "play";
    }
    @PostMapping("/play/{id}")
    public String postPlay (@PathVariable UUID id){
        return "redirect:/play/" + id;
    }
        @PostMapping("/delete/{id}")
        public String delete (@PathVariable UUID id){
            minefields.deleteById(id);
            return "redirect:/";
        }

}

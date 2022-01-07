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

    @PostMapping("/create")
    public String createPost(@ModelAttribute Minefield minefield) {
        minefield.setMinefield();
        minefields.save(minefield);
        return "redirect:/play/" + minefield.getId();
    }

    @PostMapping("/play/{id}")
    public String postPlay (@PathVariable UUID id){
        return "redirect:/play/" + id;
    }
    @GetMapping({"/play/{id}","/play/{id}/{x}/{y}"})
    public String play(@PathVariable UUID id, @PathVariable(required = false) Long x, @PathVariable(required = false) Long y,  Model model) {
        Minefield m = minefields.findById(id).orElseThrow(() -> new RuntimeException("Minefield not found"));
        if(x != null && y != null){
            if(!m.discover(y-1,x-1)){
                m.setPerdue(true);
            }
        }
        if(m.win()){
            m.setGagner(true);

        }
        minefields.save(m);
        model.addAttribute("m", m);
        return "play";
    }
        @PostMapping("/delete/{id}")
        public String delete (@PathVariable UUID id){
            minefields.deleteById(id);
            return "redirect:/";
        }

}

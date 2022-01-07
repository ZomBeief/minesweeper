package fr.yncrea.cin3.minesweeper.form;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class minefieldform {
    private UUID id;

    private Long count;

    private Long width;

    private Long height;
}

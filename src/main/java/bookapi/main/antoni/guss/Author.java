package bookapi.main.antoni.guss;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Author {

    private String name;
    private List<Book> books;

}

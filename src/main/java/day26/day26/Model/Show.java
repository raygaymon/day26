package day26.day26.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Show {
    private String name;
    private String type;
    private Integer runtime;
    private List<String> genre;
    private Object averageRating;
    private String imageURL;

}

package by.it_academy.lib.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {

    @JsonProperty(index = 0)
    private int number;

    @JsonProperty(index = 1)
    private int size;

    @JsonProperty(value = "total_pages", index = 2)
    private int totalPages;

    @JsonProperty(value = "total_elements", index = 3)
    private long totalElements;

    @JsonProperty(value = "number_of_elements", index = 5)
    private int numberOfElements;

    @JsonProperty(index = 4)
    private boolean first;

    @JsonProperty(index = 6)
    private boolean last;

    @JsonProperty(index = 7)
    private List<T> content;
}

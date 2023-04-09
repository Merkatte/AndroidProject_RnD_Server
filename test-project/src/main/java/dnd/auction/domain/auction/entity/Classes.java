package dnd.auction.domain.auction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Classes {

    @JsonIgnore
    @Id
    String name;

    @Builder
    public Classes(String name) {
        this.name = name;
    }
}

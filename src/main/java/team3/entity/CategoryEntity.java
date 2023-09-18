package team3.entity;


import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity(name = "tbl_categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "status")
    String status;

    @Column(name = "created_at")
    Date createdAt;
}

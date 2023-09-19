package team3.entity;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity()
@Table(name = "tbl_spending")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SpendingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id") String userId;
    @Column(name = "category_id") String categoryId;
    @Column(name = "currency_type") String currencyType;
    @Column(name = "value") String value;
    @Column(name = "description") String description;
    @Column(name = "date_spend") String date_spend;
}

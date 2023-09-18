package team3.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "tbl_daily_spending")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DailySpendingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Id @Column(name = "user_id") private String userId;
    @Id @Column(name = "category_id") private String categoryId;
    @Column(name = "currency_type") private String currencyType;
    @Column(name = "value") private String value;
    @Column(name = "description") private String description;
    @Column(name = "date_spend") private String date_spend;
}

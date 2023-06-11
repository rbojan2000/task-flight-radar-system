package model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Builder
public class Test {
    private Long id;
    private String userId;
    private long timestamp;
    private String activity;
    private String detailsPage;
    private String detailsButton;

}

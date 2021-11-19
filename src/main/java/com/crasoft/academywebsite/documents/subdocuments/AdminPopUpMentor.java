package com.crasoft.academywebsite.documents.subdocuments;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class AdminPopUpMentor {
    @Id
    private String Id;
    private String name;
}

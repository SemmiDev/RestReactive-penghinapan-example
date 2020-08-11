package com.sammidev.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "penghinapans")
public class Penghinapan {

    @Id
    private String id;

    @NotBlank
    @Size(max = 100)
    private String name;

    private String address;
    private Date createdAt;

    public Penghinapan(@NotBlank @Size(max = 140) String name) {
        this.name = name;
    }
}

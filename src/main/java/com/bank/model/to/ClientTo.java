package com.bank.model.to;

import com.bank.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientTo {
    private String name;
    private String email;
    private Date registered = new Date();

    public ClientTo(Client client) {
        this.name = client.getName();
        this.email = client.getEmail();
        this.registered = client.getRegistered();
    }
}

package org.chintanu.jmstest_activemq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    private String msg;
    private UUID uuid;
}

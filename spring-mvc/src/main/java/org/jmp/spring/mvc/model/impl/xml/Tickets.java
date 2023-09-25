package org.jmp.spring.mvc.model.impl.xml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jmp.spring.mvc.model.impl.TicketImpl;
import java.util.List;

@NoArgsConstructor
@Setter
@XmlRootElement(name = "tickets")
public class Tickets
{
    @Getter(onMethod = @__({@XmlElement(name = "ticket")}))
    private List<TicketImpl> tickets;
}

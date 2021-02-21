package at.ac.tuwien.damap.rest.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveDmpResponse {

    private long id;

    public SaveDmpResponse(long id){
        this.id = id;
    }
}

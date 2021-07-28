package at.ac.tuwien.damap.rest.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveDmpResponse {

    private long id;

    public SaveDmpResponse(long id){
        this.id = id;
    }
}

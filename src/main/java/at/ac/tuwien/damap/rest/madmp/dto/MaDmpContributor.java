package at.ac.tuwien.damap.rest.madmp.dto;

import at.ac.tuwien.damap.enums.EContributorRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpContributor extends MaDmpPerson{

    private MaDmpIdentifier contributor_id;
    private List<EContributorRole> role;
    private String mbox;
    private String name;
}

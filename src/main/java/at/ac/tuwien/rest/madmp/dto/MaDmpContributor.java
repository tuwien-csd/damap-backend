package at.ac.tuwien.rest.madmp.dto;

import at.ac.tuwien.damap.enums.EContributorRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpContributor extends MaDmpPerson{

    private MaDmpIdentifier contributor_id;
    private List<EContributorRole> role;
}

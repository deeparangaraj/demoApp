package com.task.station.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "Create/Update station request.")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateUpdateStationRequest {

    @ApiModelProperty(required = true)
    @NotEmpty
    private String stationName;
    @ApiModelProperty(required = true)
    @NotEmpty
    private String callSign;
    @ApiModelProperty(required = true)
    @NotNull
    private Boolean hdEnabled;

}

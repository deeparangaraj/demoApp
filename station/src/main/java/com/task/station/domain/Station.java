package com.task.station.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "station")
@NamedQueries({
        @NamedQuery(name = "Station.findByName", query = "SELECT s FROM Station s WHERE LOWER(s.name) = LOWER(?1)"),
        @NamedQuery(name = "Station.findHdEnabled", query = "SELECT s FROM Station s WHERE s.hdEnabled = true")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Station implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String stationId;
    @NotBlank
    private String name;
    @NotBlank
    private String callSign;
    @NotNull
    private Boolean hdEnabled;

    @Override
    public int hashCode() {
        return Objects.hash(stationId, name, callSign, hdEnabled);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Station
                && Objects.equals(stationId, ((Station)obj).stationId)
                && Objects.equals(name, ((Station) obj).name)
                && Objects.equals(callSign, ((Station) obj).callSign)
                && Objects.equals(hdEnabled, ((Station) obj).hdEnabled);
    }



}
